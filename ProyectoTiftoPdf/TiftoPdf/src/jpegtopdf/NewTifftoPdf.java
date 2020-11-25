package jpegtopdf;

import com.itextpdf.text.pdf.RandomAccessFileOrArray;
//Read Tiff File, Get number of Pages
import com.itextpdf.text.pdf.codec.TiffImage;


import java.io.File;
//We need the library below to write the final 
//PDF file which has our image converted to PDF
import java.io.FileOutputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Timer;



import javax.swing.JOptionPane;


//The image class to extract separate images from Tiff image
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
//PdfWriter object to write the PDF document
import com.itextpdf.text.pdf.PdfWriter;
//Document object to add logical image files to PDF
import com.itextpdf.text.Document;
public class NewTifftoPdf {


static Timer timer;
static boolean frozen = false;


public static void pasarArch(String pasoorig, String pasofinal){
    try{
    	
        RandomAccessFileOrArray myTiffFile=new RandomAccessFileOrArray(pasoorig);

        int numberOfPages=TiffImage.getNumberOfPages(myTiffFile);
        System.out.println("Number of Images in Tiff File" + numberOfPages+pasoorig);
        Document TifftoPDF=new Document();
        
        PdfWriter.getInstance(TifftoPDF, new FileOutputStream(pasofinal));
        TifftoPDF.open();
        System.out.println(pasofinal);
        for(int i=1;i<=numberOfPages;i++){
            Image tempImage=TiffImage.getTiffImage(myTiffFile, i);
            Rectangle pageSize = new Rectangle(tempImage.getWidth(),
           tempImage.getHeight());
           TifftoPDF.setPageSize(pageSize);
           TifftoPDF.newPage(); 
           TifftoPDF.add(tempImage);
         
        }
        TifftoPDF.close();
       System.out.println("Terminado" );
        
    }
    catch (Exception i1){
        i1.printStackTrace();
    }   
    
	
} 
public static void leerDir(String pasosql)throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	
	try{
	
			
		      try {
		    	
		    	  String files;
		    	  LinkedList <String> lista= new LinkedList();
		    
		     
		    	String desk ="//222.15.19.251//Imagenes//";
		    	String bin2 = "//222.15.19.251//ImagenesPDF//"+pasosql;
		    	String sourceDir = (desk+pasosql);
		    	System.out.println(sourceDir + "  sourceDir");
		    	System.out.println(pasosql + "  pasosql");
		    	

		    	File folder = new File(sourceDir);
		    	
		        if (folder.exists()){ 

		        	File[] ficheros = folder.listFiles();
		        	for (int x=0;x<ficheros.length;x++){
		        	//	System.out.println(fichero + "  fichero");
		        		String fichero=ficheros[x].getName();
		        		System.out.println(fichero + "  fichero");
		        		if(fichero.indexOf(".tif")!=-1){
		        			
		 System.out.println(fichero + "  fichero");
		 lista.add(fichero);
	//	 lista.add(fichero);
		    	//String[] ficheros = folder.list();
		    	
		    //	String bin =sourceDir+"/";

		    	//File[] listOfFiles = folder.listFiles(); 
		    	
		    	    // for (int i = 0; i < listOfFiles.length;i++)
		    	//for (int i = 0 ; ;i++)
		    //	for (int x=0;x<ficheros.length;x++){
		    	    // {  

		    	       //  if (listOfFiles[i].isFile()) 
		    	       //  {
		    	            // files = listOfFiles[i].getName();
		    	            // if (files.endsWith(".TIF") || files.endsWith(".TIF"))
		    	            	 			
		    	           //  {
		    	            	/* String  New1 = (bin2+files.substring(0,files.length()-4)+".pdf");
		    	            	 File archivo = new File (New1);
		    	            	 if (archivo.exists()) {
		    	            		 System.out.println("Ya existe archivo Pasar");
		    	            	 } else {
		    	                 pasarArch(bin+files,bin2+files.substring(0,files.length()-4)+".pdf");	
		    	                 String Base1 = files.substring(0,files.length()-4);
		    	                 System.out.println(Base1);
		    	                 actualizar(Base1);*/
		    	            	 String  New1 = (bin2+".pdf");
		    	            	 System.out.println(New1);
		    	            	 System.out.println(pasosql);
		    	            	 File archivo = new File (New1);
		    	            	// if (archivo.exists()) {
		    	            		// System.out.println("Ya existe archivo Pasar");
		    	            	 } else {
		    	            		 System.out.println(folder + "  folder");
		    	            		 System.out.println(bin2 + "  bin2");
		    	            		 String bin4 = folder+".tif";
		    	            		 String bin5 = folder + ".pdf";
		    	            		 System.out.println(bin4 + "  bin4");
		    	            		 System.out.println(bin5 + "  bin5");
		    	            		
		    	                 pasarArch(bin4,bin5);	
		    	              //   String Base1 = folder;
		    	                // System.out.println(folder);
		    	                 actualizar(pasosql);
		    	               //  break;
		    	            	 }
		    	                 
		    	      //   }	else{ System.out.println("sa1");
		    	       //    }	
		    	       // } else{ System.out.println("sa");
		    	           
		    	        }
		    	     }
		    	        
		    	    
		      } catch (ArrayIndexOutOfBoundsException e) {
		    	
		        JOptionPane.showMessageDialog(null, " Por favor reintente de Nuevo");
		      } 
		 // } else {
		    
			 
		//  } 
		}catch (Exception e) {
			System.out.println(e);
		}
	
		finally{
			System.out.println("Fin");
		}

}

public static void seleccionar()throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	
	Connection con = null;
    PreparedStatement stmt = null;
	String surl="jdbc:sqlserver://222.15.19.138:1433;DatabaseName=basasql;SelectMethod=cursor;username=sa;password=Basa2012"; 
	String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			  
	try {
		  Class.forName(sDriver).newInstance();    
		  con = DriverManager.getConnection(surl);
		 
		  try {
		    // La Query
			 // stmt = con.prepareStatement("SELECT  CONVERSION_TIPO_ARCHIVO,ID_IMAGEN,TIPO_ARCHIVO, ID, PASO FROM V_CONVERSION_TIPO_ARCHIVO WHERE CONVERSION_TIPO_ARCHIVO = 0  ");
			  stmt = con.prepareStatement("SELECT  [CONVERSION_TIPO_ARCHIVO] ,[ID_IMAGEN] ,[TIPO_ARCHIVO],[ID],[PASO] FROM [basasql].[dbo].[V_CONVERSION_TIPO_ARCHIVO]");
			  ResultSet rs = stmt.executeQuery();
		 
			// Recorremos el resultado
		  
		    while (rs.next()){
		    	
		      System.out.println (rs.getString("PASO"));
		    String  PASO = (rs.getString("PASO") +"//"+ (rs.getString("ID_IMAGEN") ));
		   // String IDIMAGEN = (rs.getString("ID_IMAGEN"));
		    		 
		      leerDir(PASO);
		      
		    	//leerDir(rs.getString("PASO"));
		    	//leerDir("NuevaCarpeta");
		    break;
		    }
		    } catch (SQLException sqle) { 
		      System.out.println("Error en la ejecución:" 
		            + sqle.getErrorCode() + " " + sqle.getMessage());    
		}
	} finally {
	   
		// ... cleanup that will execute whether or not an error occurred ...
		
	}

}
public static void main(String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{

	seleccionar();
	//leerDir();
   }  

/*public static void actualizar() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	Connection con = null;
   
	String surl="jdbc:sqlserver://222.15.19.138:1433;DatabaseName=basasql;SelectMethod=cursor;username=sa;password=Basa2012"; 
	String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			  
	try {
		Class.forName(sDriver).newInstance();
		con = DriverManager.getConnection(surl);
		 
		  try {
			  String sql = "UPDATE DOCUMENTOS_DIGITALES SET CONVERSION_TIPO_ARCHIVO = 1 WHERE ID = ID_IMAGEN ";
			
			  PreparedStatement preparedStatement =
			          con.prepareStatement(sql);

			 preparedStatement.setString(1, "1");
			  preparedStatement.setString(2, "1");
			

			  int rowsAffected = preparedStatement.executeUpdate();
			  System.out.println("Actualizado");
			 
			    } catch (SQLException sqle) { 
			      System.out.println("Error en la ejecución:" 
			            + sqle.getErrorCode() + " " + sqle.getMessage());    
			}
		} finally {
		    // ... cleanup that will execute whether or not an error occurred ...
		}
			  
		  }*/
public static void actualizar(String base1) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	Connection con = null;
   
	String surl="jdbc:sqlserver://222.15.19.138:1433;DatabaseName=basasql;SelectMethod=cursor;username=sa;password=Basa2012"; 
	String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			  
	try {
		
		Class.forName(sDriver).newInstance();
		con = DriverManager.getConnection(surl);
		 
		  try {
			  String sql = "update DOCUMENTOS_DIGITALES  set   CONVERSION_TIPO_ARCHIVO=? where ID = " +base1+"  " ;
			  
			
			  PreparedStatement preparedStatement =
			          con.prepareStatement(sql);


			  preparedStatement.setString(1, "1");
			//  preparedStatement.setLong(2,0 );
			

			  int rowsAffected = preparedStatement.executeUpdate();
			  System.out.println("Actualizado");
			 
			    } catch (SQLException sqle) { 
			      System.out.println("Error en la ejecución:" 
			            + sqle.getErrorCode() + " " + sqle.getMessage());    
			}
		} finally {
		    // ... cleanup that will execute whether or not an error occurred ...
		}
			  
		  }

}		 
