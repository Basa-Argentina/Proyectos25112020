package jpegtopdf;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificarExistentePDF {
	
	
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
				  stmt = con.prepareStatement("/****** Script for SelectTopNRows command from SSMS  ******/\r\n" + 
				  		"SELECT        ID, CONVERSION_TIPO_ARCHIVO, DIRECTORIO_PASO FROM DOCUMENTOS_DIGITALES WHERE (ID >= 5464925 ) AND CONVERSION_TIPO_ARCHIVO = 1");
				  ResultSet rs = stmt.executeQuery();
			 
				// Recorremos el resultadoqq
			  
			    while (rs.next()){
			    	
			      System.out.println (rs.getString("ID"));
			    String  PASO = (rs.getString("DIRECTORIO_PASO") +"//"+ (rs.getString("ID") ));
			    
			    
			    String IDIMAGEN = (rs.getString("ID"));
			    		 
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
		    	

		    	//String desk ="//222.15.19.251//Imagenes//";
		    	String bin2 = "//222.15.19.251//ImagenesPDF//";
		    	System.out.println(pasosql + "   pasosql");
		   // 	String sourceDir = (desk+pasosql+".tif");
		    	String sourceDir1 = (bin2+pasosql+".pdf");
		    	//System.out.println(sourceDir + "  sourceDir" );
		    	System.out.println(sourceDir1 + "  sourceDir1");

		    	File folder = new File(sourceDir1);
		    	if (folder.exists()) {
		    	
		    	for (int i= 0; i <1; i++){
			    		//pasarArch (sourceDir, sourceDir1 ,iDIMAGEN);
			    		
			    		System.out.println("Ya existe : " + iDIMAGEN);
			  
		    	}
		    	}
		    	else {
		    		System.out.println("No existe");
		    		
		    		actualizar(iDIMAGEN);
					  
		    	}
		    	
		    	
		    	
		    	
		      } catch(Exception e)  {
		    	
		    	  e.printStackTrace();
} 
	} finally {
		
		    		
		    
	System.out.println("Fin de Program");
		
	
	
	}
		      
}   
	
public static void actualizar(String base1) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
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


				  preparedStatement.setString(1, "0");
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
