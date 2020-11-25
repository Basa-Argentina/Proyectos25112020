import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class ConvertirPdfaPng {
	public void convertirPdf (String archivoNombre) throws IOException{
		
		String archivoNombreTmp=archivoNombre.substring(0, archivoNombre.length()-4) +".pdf";
		
		PDDocument document = PDDocument.load(new File(archivoNombreTmp));
	    String extension = "png";
	    PDFRenderer pdfRenderer = new PDFRenderer(document);
	    for (int page = 0; page < document.getNumberOfPages(); ++page) {
	        BufferedImage bim = pdfRenderer.renderImageWithDPI(
	        page, 300, ImageType.GRAY);
	        ImageIOUtil.writeImage(
	          bim, String.format("%s%03d.%s",archivoNombreTmp, page + 1, extension), 300);
	    }
	    document.close();
	    
	}
}