package jpegtopdf;
import java.io.*;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
public class MultiaSinglePdf {  
    
	public static void leerDir()throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		try{
			try {	    	
			    	  String files;
			    	String bin1 = "C:/Multi/Multipdf/";
			    	String bin2 ="C:/Multi/Singlepdf/";
			    	
			    	String sourceDir = (bin1);
			    	File folder = new File(sourceDir);
			    	String bin =sourceDir+"/";
			    	File[] listOfFiles = folder.listFiles(); 
			    	     for (int i = 0; i < listOfFiles.length;i++)
			    	     {  
			    	         if (listOfFiles[i].isFile()) 
			    	         {
			    	             files = listOfFiles[i].getName();
			    	             if (files.endsWith(".pdf") || files.endsWith(".pdf"))	 			
			    	             {      
			    	               System.out.println(bin1+files);
			    	              // System.out.println(bin2+files);
			    	            	 multiap(bin1+files,files.substring(0,files.length()-4));			    	 	    	                 
			    	            }	else{ System.out.println("sa1");  
			    	            }	
			    	            } else{ System.out.println("sa");
			    	            }
			    	     }
			    	     
			    	     
			    	     
			    	        	    
			      } catch (ArrayIndexOutOfBoundsException e) {
			    	
			        JOptionPane.showMessageDialog(null, " Por favor reintente de Nuevo");
			      } 
			
			
			}catch (Exception e) {
				System.out.println(e);
			}
		
			finally{
				System.out.println("Fin");
			}

	}
	
	public static void multiap(String Split_PDF_Document, String FileNa4me){
        try {
            PdfReader Split_PDF_Document1 = new PdfReader(Split_PDF_Document);
            Document document; 
            PdfCopy copy; 
            String bin2 ="C:/Multi/Singlepdf/";
            String case1 = FileNa4me + "/" ; 
            System.out.println(case1);
            String pasocarpet  = (bin2 + case1 ) ;
            
            
            File pasocarpet1 = new File (pasocarpet);

            if (pasocarpet1.isDirectory()) { 
            int	num= 0;
            
            int number_of_pages = Split_PDF_Document1.getNumberOfPages();
            for (int i = 0; i < number_of_pages; ) {
                   
          	  	document = new Document();
          	  DecimalFormat format = new DecimalFormat("0000");
              String d = format.format(i);
             
             
                   String FileName= pasocarpet1 +"/" + FileNa4me+ " "+ ++i +".pdf"; 
                 
                   System.out.println(d);
                 //  File FileName1 = new File(FileName);
                    copy = new PdfCopy(document,new FileOutputStream(FileName));
                    document.open();                
                    copy.addPage(copy.getImportedPage(Split_PDF_Document1, i));              
                    document.close();

                    System.out.println(FileName);
                    File f;
                	f = new File(bin2+"Paso.txt");
                   // System.out.println("No Existe");
                		try{
                	
                	FileWriter w = new FileWriter(f,true);
                	BufferedWriter bw = new BufferedWriter(w);
                	PrintWriter wr = new PrintWriter(bw);  	
                	wr.write("\r\n");
                	wr.write(FileName + "  " + case1.substring(0,10) + "  "+case1.substring(10,case1.length()));
                	wr.append(" -");
                	wr.close();
                	bw.close();
                		}
                	finally{
                		
                	}
                    
            } 	
            } 
            else {
            	pasocarpet1.mkdir();
            int number_of_pages = Split_PDF_Document1.getNumberOfPages();

            
            for (int i = 0; i < number_of_pages; ) {
            	
          	  	document = new Document();
          	
          	
                   String FileName= pasocarpet1 +"/" + FileNa4me+" "+  ++i+".pdf"; 
                   DecimalFormat format = new DecimalFormat("0000");
                   String d = format.format(i);
                   System.out.println(d);
                   //File FileName1 = new File(FileName);
                    copy = new PdfCopy(document,new FileOutputStream(FileName));
                    document.open();   
                    
                    copy.addPage(copy.getImportedPage(Split_PDF_Document1, i));              
                    document.close();
                    
                //    System.out.println(FileName);
                    
                    File f;
                	f = new File(bin2+"Paso.txt");
                   // System.out.println("No Existe");
                		try{
                	
                	FileWriter w = new FileWriter(f,true);
                	BufferedWriter bw = new BufferedWriter(w);
                	PrintWriter wr = new PrintWriter(bw);  	
                	wr.write("\r\n");
                	wr.write(FileName + "  " + case1.substring(0,10) + "  "+case1.substring(10,case1.length()));
                	wr.append(" -");
                	wr.close();
                	bw.close();
                		}
                	finally{
                	
                	}
                	
                	
                	
                    
                    
                    	}
                    }
          }
          catch (Exception i)
          {
              System.out.println(i.getMessage());
          }
        		
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		leerDir();
}
}