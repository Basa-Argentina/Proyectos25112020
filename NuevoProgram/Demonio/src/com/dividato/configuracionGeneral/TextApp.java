/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.vision.samples.text;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.Status;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import opennlp.tools.stemmer.snowball.SnowballStemmer;
import opennlp.tools.tokenize.TokenizerME;

import org.apache.commons.io.FilenameUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * A sample application that uses the Vision API to OCR text in an image.
 */


@SuppressWarnings("serial")
public class TextApp {
    private static final int MAX_RESULTS = 6;
    private static final int BATCH_SIZE = 10;

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "Google-VisionTextSample/1.0";
    private final Vision vision;
    private final Index index;
    //private Document document;

    /**
     * Constructs a {@code TextApp} using the {@link Vision} service.
     */
    public TextApp(Vision vision, Index index) {
        this.vision = vision;
        this.index = index;
    }

    /**
     * Connects to the Vision API using Application Default Credentials.
     */
    public static Vision getVisionService() throws IOException, GeneralSecurityException {
        GoogleCredential credential =
                GoogleCredential.getApplicationDefault().createScoped(VisionScopes.all());
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        System.out.println("Tesseract");
        return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Annotates an image using the Vision API.
     */
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        if (args.length > 1) {
            System.err.println("Usage:");
            System.err.printf(
                    "\tjava %s inputDirectory\n",
                    TextApp.class.getCanonicalName());
            System.exit(1);
        }

        JedisPool pool = Index.getJedisPool();
        try {
            Index index =
                    new Index(
                            new TokenizerME(Index.getEnglishTokenizerMeModel()),
                            new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH),
                            pool);

            TextApp app = new TextApp(TextApp.getVisionService(), index);

            if (args.length == 0) {
                app.lookupWords();
                return;
            }
            Path inputPath = Paths.get(args[0]);
            app.indexDirectory(inputPath);
        } finally {
            if (pool != null) {
                pool.destroy();

            }

        }

    }

    /**
     * Looks up words in the index that the user enters into the console.
     */
    public void lookupWords() {
        System.out.println("Entering word lookup mode.");
        System.out
                .println("To index a directory, add an input path argument when you run this command.");
        System.out.println();

        Console console = System.console();
        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        while (true) {
            String words =
                    console.readLine("Enter word(s) (comma-separated, leave blank to exit): ").trim();
            if (words.equals("")) {
                break;
            }
            index.printLookup(Splitter.on(',').split(words));
        }
    }

    /**
     * Indexes all the images in the {@code inputPath} directory for text.
     */
    public void indexDirectory(Path inputPath) throws IOException {
        List<Path> unprocessedImages =
                Files.walk(inputPath)
                        .filter(Files::isRegularFile)
                        .filter(index::isDocumentUnprocessed)
                        .collect(Collectors.toList());

        Lists.<Path>partition(unprocessedImages, BATCH_SIZE)
                .stream()
                .map(this::detectText)
                .flatMap(l -> l.stream())
                .filter(this::successfullyDetectedText)
                .map(this::extractDescriptions)
                .forEach(index::addDocument);
        try {
            webs();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /**
     * Gets up to {@code maxResults} text annotations for images stored at {@code paths}.
     */
    public ImmutableList<ImageText> detectText(List<Path> paths) {
        ImmutableList.Builder<AnnotateImageRequest> requests = ImmutableList.builder();
        try {
            for (Path path : paths) {
                byte[] data;
                data = Files.readAllBytes(path);
                requests.add(
                        new AnnotateImageRequest()
                                .setImage(new Image().encodeContent(data))
                                .setFeatures(ImmutableList.of(
                                        new Feature()
                                                .setType("TEXT_DETECTION")
                                                .setMaxResults(MAX_RESULTS))));
            }

            Vision.Images.Annotate annotate =
                    vision.images()
                            .annotate(new BatchAnnotateImagesRequest().setRequests(requests.build()));
            // Due to a bug: requests to Vision API containing large images fail when GZipped.
            annotate.setDisableGZipContent(true);
            BatchAnnotateImagesResponse batchResponse = annotate.execute();
            assert batchResponse.getResponses().size() == paths.size();

            ImmutableList.Builder<ImageText> output = ImmutableList.builder();
            for (int i = 0; i < paths.size(); i++) {
                Path path = paths.get(i);
                AnnotateImageResponse response = batchResponse.getResponses().get(i);
                output.add(
                        ImageText.builder()
                                .path(path)
                                .textAnnotations(
                                        MoreObjects.firstNonNull(
                                                response.getTextAnnotations(),
                                                ImmutableList.<EntityAnnotation>of()))
                                .error(response.getError())
                                .build());
            }
            return output.build();
        } catch (IOException ex) {
            // Got an exception, which means the whole batch had an error.
            ImmutableList.Builder<ImageText> output = ImmutableList.builder();
            for (Path path : paths) {
                output.add(
                        ImageText.builder()
                                .path(path)
                                .textAnnotations(ImmutableList.<EntityAnnotation>of())
                                .error(new Status().setMessage(ex.getMessage()))
                                .build());
            }
            return output.build();
        }
    }
    public List<String> webs() throws IOException
    {
        String uri ="http://localhost:9955/?index=true";
        StringBuilder sb = new StringBuilder();
        URL url = new URL(uri);
        StringBuilder postData = new StringBuilder();

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        connection.setDoOutput(true);
        connection.getOutputStream().write(postDataBytes);

        connection.setConnectTimeout(10000);
        connection.connect();
        int status = connection.getResponseCode();

        switch (status) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
        }
        return Arrays.asList(sb.toString().split("\\s*,\\s*"));

    }
    /**
     * Checks that there was not an error processing an {@code image}.
     */
    public synchronized boolean successfullyDetectedText(ImageText image) {
        String extension = null;
        if (image.error() != null) {
            //System.out.printf("Error reading %s:\n%s\n", image.path(), image.error().getMessage());
            extension = FilenameUtils.getExtension(image.path().toString());

            try {
                // directorio/ejecutable es el path del ejecutable y un nombre /


                if ( extension.toLowerCase().equals("gif") || extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("png")) {
                    //System.out.printf("Error reading %s:\n%s\n", image.path(), image.error().getMessage());


                    // crear carpeta texto

                    String path = image.path().toString();
                    String pathTmp=path.substring(0, image.path().toString().length()-image.path().getFileName().toString().length()-1);



                    File folder = new File((pathTmp + "\\texto\\"));
                    folder.mkdirs(); //

                    Process p = Runtime.getRuntime().exec(new String[]{"C:\\Program Files (x86)\\Tesseract-OCR\\tesseract", image.path().toString(), (pathTmp + "\\texto\\") + image.path().getFileName() });
                    String extTmp = path.substring(path.length()-4,path.length());
                    String fileNameTmp = null;

                    p.waitFor();

                    if(extTmp.equals(".png")){
                        fileNameTmp = image.path().getFileName().toString().substring(0,  image.path().getFileName().toString().length()-7);

                    }
                    else  {
                        fileNameTmp = image.path().getFileName().toString();

                    }


                    String FILENAME = (pathTmp + "\\texto\\") + image.path().getFileName() + ".txt";


                    try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
                        String sCurrentLine;
                        Jedis jedis = new Jedis("localhost");

                        while ((sCurrentLine = br.readLine()) != null) {
                            String[] words = sCurrentLine.split(" ");

                            for (String a : words) {
                                jedis.select(1);
                                jedis.set(pathTmp + "\\" + fileNameTmp, fileNameTmp);
                                jedis.select(0);
                                jedis.sadd(a, pathTmp + "\\" + fileNameTmp);
                                //System.out.println(a);

                            }
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                // Se lanza una excepción si no se encuentra en ejecutable o el fichero no es ejecutable. /
            }
            //System.out.printf("Error reading %s:\n%s\n", image.path(), image.error().getMessage());
            //System.out.printf("success");
            return false;
        }

        return true;
    }

 /*   public Document ConvertPdf (Document archivoPDF){

    }*/

    /**
     * Extracts as a combinded string, all the descriptions from text annotations on an {@code image}.
     */
    public Word extractDescriptions(ImageText image) {
        String document = "";
        for (EntityAnnotation text : image.textAnnotations()) {
            document += text.getDescription();
        }
        if (document.equals("")) {
            System.out.printf("%s had no discernible text.\n", image.path());
        }
        // Output a progress indicator.
        System.out.print('.');
        System.out.flush();
        return Word.builder().path(image.path()).word(document).build();
    }
}
