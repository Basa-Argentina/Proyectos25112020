import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Hespa�olhistorial {
	
	 static String Paso = "C:\\Para_Subir\\"; 
	 static String Paso2 ="C:\\Archivos_Digitales\\pdf\\";
	
	 public static void mover(String ficheros){
		 
		File fichero = new File(Paso + ficheros);
		
		System.out.println(Paso + ficheros);
		
		try {
		
			
	if (fichero.renameTo(new File(Paso2 + ficheros)))
		System.out.println("Fichero movido");
	
	
	
	 String Paso3 = Paso2+ficheros;
	 String Paso31 = ficheros.substring(0,ficheros.length()-4);
	 System.out.println(Paso31);
	 actualizar(Paso31,Paso3);

		} catch (Exception e) {
			
			 System.out.println("No se pudo mover el fichero");
		}
	} 
	public static void main(String[] args) {
		

		leerDirectorio();
	}
	
	
		public static void actualizar(String paso31, String paso3) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			Connection con = null;
			PreparedStatement stmt = null;   
			String surl="jdbc:sqlserver://222.15.19.114:1433;DatabaseName=basa;SelectMethod=cursor;username=sa;password=Basa2012"; 
			String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				//System.out.println(paso3);  
			
				try {
					  Class.forName(sDriver).newInstance();    
					  con = DriverManager.getConnection(surl);
					  System.out.println("");
					 
					  try {
					    // La Query
						 
					   // stmt = con.prepareStatement("SELECT  elemento_id FROM referencia WHERE elemento_id  = "+paso31+"" );
						  stmt = con.prepareStatement("SELECT codigo FROM elementos WHERE codigo ="+paso31+"  ");
						  ResultSet rs = stmt.executeQuery();
					 
						// Recorremos el resultado
					    while (rs.next()){
					    
					     // String pasa = (rs.getString("elemento_id"));
					    	String pasa = (rs.getString("codigo"));
					    	System.out.println(pasa);
					    	System.out.println(paso3);
					    	
					    	if ( !((pasa = paso31) ==null) ){
						  try {
								Class.forName(sDriver).newInstance();
								con = DriverManager.getConnection(surl);
								
								  try {
						  
			
						 // String sql = "update referencia  set   pathLegajo=? where elemento_id= "+pasa+" ";
							String sql = "UPDATE referencia SET pathLegajo = ? FROM elementos INNER JOIN "+
                        " referencia ON elementos.id = referencia.elemento_id WHERE   elementos.codigo = "+pasa+"";
						  PreparedStatement preparedStatement =
						          con.prepareStatement(sql);
						 preparedStatement.setString( 1 , paso3);
						  int rowsAffected = preparedStatement.executeUpdate();
						  System.out.println("Actualizado");
						  
						  
								  } catch (SQLException sqle) { 
									  JOptionPane.showMessageDialog( null, "No pudo ser actualizado");
								      System.out.println("Error en la ejecuci�n:" 
								            + sqle.getErrorCode() + " " + sqle.getMessage());    
								}
							} finally {
							    // ... cleanup that will execute whether or not an error occurred ...
							}
						
					  }
					    	 	
					    break;
					    }
					    } catch (SQLException sqle) { 
					      System.out.println("Error en la ejecuci�n:" 
					            + sqle.getErrorCode() + " " + sqle.getMessage());    
					}
				} finally {
				    // ... cleanup that will execute whether or not an error occurred ...
					
				}
				 
				
  }
					  
	public static void leerDirectorio(){
		
		File dir = new File("C:\\Para_Subir\\");
		   
		String[] ficheros = dir.list();
		   
		   
		if (ficheros == null)
			
			System.out.println("No hay ficheros en el directorio especificado");
		
		else { 
			
			for (int x=0;x<ficheros.length;x++){

				mover(ficheros[x]);

			}
		}
	}
}
