package jpegtopdf;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;

public class TIFSAPDF {
	
	
	public static void seleccionar()throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Connection con = null;
	    PreparedStatement stmt = null;
		String surl="jdbc:sqlserver://222.15.19.150:1433;DatabaseName=basasql;SelectMethod=cursor;username=sa;password=Sicuyo123"; 
		String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				  
		try {
			  Class.forName(sDriver).newInstance();    
			  con = DriverManager.getConnection(surl);
			 
			  try {
			    // La Query
				 // stmt = con.prepareStatement("SELECT  CONVERSION_TIPO_ARCHIVO,ID_IMAGEN,TIPO_ARCHIVO, ID, PASO FROM V_CONVERSION_TIPO_ARCHIVO WHERE CONVERSION_TIPO_ARCHIVO = 0  ");
				  stmt = con.prepareStatement("SELECT  [CONVERSION_TIPO_ARCHIVO] ,[ID_IMAGEN] ,[TIPO_ARCHIVO],[ID],[PASO] FROM [basasql].[dbo].[V_CONVERSION_TIPO_ARCHIVO]");
				  ResultSet rs = stmt.executeQuery();
			 
				// Recorremos el resultadoqq
			  
			    while (rs.next()){
			    	
			      System.out.println (rs.getString("PASO"));
			    String  PASO = (rs.getString("PASO") +"//"+ (rs.getString("ID_IMAGEN") ));
			    
			    
			    String IDIMAGEN = (rs.getString("ID_IMAGEN"));
			    		 
			      leerDir(PASO,IDIMAGEN);
			      
			    	//leerDir(rs.getString("PASO"));
			    	//leerDir("NuevaCarpeta");
			  //  break;
			    }
			    } catch (SQLException sqle) { 
			      System.out.println("Error en la ejecución:" 
			            + sqle.getErrorCode() + " " + sqle.getMessage());    
			}
		} finally {
		   System.out.println("No hay Archivos por convertir");
			// ... cleanup that will execute whether or not an error occurred ...
			
		}

	}
	
	
	
	public static void leerDir(String pasosql, String iDIMAGEN)throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		try{
		
				
			      try {
			    	

			    	String desk ="//222.15.19.251//Imagenes//";
			    	String bin2 = "//222.15.19.251//ImagenesPDF//";
			    	System.out.println(pasosql + "   pasosql");
			    	String sourceDir = (desk+pasosql+".tif");
			    	String sourceDir1 = (bin2+pasosql+".pdf");
			    	System.out.println(sourceDir + "  sourceDir" );
			    	System.out.println(sourceDir1 + "  sourceDir1");

			    	File folder = new File(sourceDir);
			    	if (folder.exists()) {
			    	
			    	for (int i= 0; i <1; i++){
				    		pasarArch (sourceDir, sourceDir1 ,iDIMAGEN);
				    		
				    	//	actualizar(iDIMAGEN);
				  
			    	}
			    	}
			    	else {
			    		System.out.println("No existe");
			    	}
			    	
			    	
			    	
			    	
			      } catch(Exception e)  {
			    	
			    	  e.printStackTrace();
	} 
		} finally {
			
			    		
			    
		System.out.println("Fin de Program");
			
		
		
		}
			      
			   
		
		
	}
	
	
	
	
	
	public static void pasarArch(String pasoorig, String pasofinal, String iDIMAGEN){
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
	       
	       File fichero = new File(pasofinal);
	       if (fichero.exists()){
	    	   System.out.println(pasofinal);
	       	actualizar(iDIMAGEN);
	    }else {
	         System.out.println("No existe : " +pasofinal);   
	       actualizar(iDIMAGEN);
	    }  
	    }
	    catch (Exception i1){
	        i1.printStackTrace();
	    }   
	    
		
	} 

	public static void actualizar(String base1) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection con = null;
	   
		String surl="jdbc:sqlserver://222.15.19.150:1433;DatabaseName=basasql;SelectMethod=cursor;username=sa;password=Sicuyo123"; 
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


public static void main(String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{

	seleccionar();
	//leerDir();
   }  
}
