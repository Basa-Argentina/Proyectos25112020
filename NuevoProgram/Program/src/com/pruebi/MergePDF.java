package com.pruebi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
 
public class MergePDF {
    public static void cruzee(String Paso1, String Paso2, String bin1) {
        try {
        	
        	
            List<InputStream> pdfs = new ArrayList<InputStream>();

                	pdfs.add(new FileInputStream(Paso1));
                	
              

                	OutputStream output = new FileOutputStream(bin1+"/"+Paso2+".pdf");
                    MergePDF.concatPDFs(pdfs, output, true);
                   
  
                           
            
          //  pdfs.add(new FileInputStream("C:\\IDE_eclipse_helios\\proyectos\\CombinarDosPdf\\documento1.pdf"));
          //  pdfs.add(new FileInputStream(
                 //   "C:\\IDE_eclipse_helios\\proyectos\\CombinarDosPdf\\documento2.pdf"));
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void concatPDFs(List<InputStream> streamOfPDFFiles,
            OutputStream outputStream, boolean paginate) {
 
        Document document = new Document();
        try {
            List<InputStream> pdfs = streamOfPDFFiles;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<InputStream> iteratorPDFs = pdfs.iterator();
 
            while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
 
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
 
            document.open();
            PdfContentByte cb = writer.getDirectContent();
 
            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();
 
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();
 
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
 
                    Rectangle rectangle = pdfReader.getPageSizeWithRotation(1);
                    document.setPageSize(rectangle);
                    document.newPage();
 
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,
                            pageOfCurrentReaderPDF);
                    switch (rectangle.getRotation()) {
                    case 0:
                        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                        break;
                    case 90:
                        cb.addTemplate(page, 0, -1f, 1f, 0, 0, pdfReader
                                .getPageSizeWithRotation(1).getHeight());
                        break;
                    case 180:
                        cb.addTemplate(page, -1f, 0, 0, -1f, 0, 0);
                        break;
                    case 270:
                        cb.addTemplate(page, 0, 1.0F, -1.0F, 0, pdfReader
                                .getPageSizeWithRotation(1).getWidth(), 0);
                        break;
                    default:
                        break;
                    }
                    if (paginate) {
                        cb.beginText();
                        cb.getPdfDocument().getPageSize();
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen())
                document.close();
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }



public static void leerDir()throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	
	try{
		try {	    	
		    	  String files;
		    	String bin1 = "E:/Single/Singlepdf/";
		    	String bin2 ="E:/Single/Multipdf/";


  		    	String sourceDir = (bin1);
		    	File folder = new File(sourceDir);
		    	String bin =sourceDir+"/";
		    	File[] listOfFiles = folder.listFiles(); 
		    	String archivos[]  = folder.list();
		    	for (int e = 0; e < archivos.length;e++)
		    		System.out.println(archivos+ "estoes archivos ");
		    		
		    	     for (int i = 0; i < listOfFiles.length;i++)
		    	    	// 
		    	     {  
		    	    	 //System.out.println(listOfFiles);
		    	         if (listOfFiles[i].isFile()) 
		    	         {
		    	             files = listOfFiles[i].getName();
		    	             
		    	             if (files.endsWith(".pdf") || files.endsWith(".pdf"))	 	
		    	            	 
		    	             {      
		    	            	 
		    	               System.out.println(bin1+files);
		    	               
		    	              // System.out.println(bin2+files);
		    	            	 cruzee(bin1+files,bin1.substring(10,19),bin1);			    	 	    	                 
		    	            }	else{ System.out.println("sa1");  
		    	            }	
		    	            } else{ System.out.println("sa");
		    	            }
		    	         
		    	     }
		    	     
		    	     
		    	     
		    	        	    
		      } catch (ArrayIndexOutOfBoundsException e) {
		    	
		  
		      } 
		
		
		}catch (Exception e) {
			System.out.println(e);
		}
	
		finally{
			System.out.println("Fin");
		}

}


public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	leerDir();
}
}


