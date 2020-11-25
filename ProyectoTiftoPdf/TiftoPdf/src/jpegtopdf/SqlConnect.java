package jpegtopdf;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;





public class SqlConnect {

private static int retorno;
public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException  {
seleccionar();
	
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
			    stmt = con.prepareStatement("SELECT CONVERSION_TIPO_ARCHIVO,ID_IMAGEN,TIPO_ARCHIVO, ID,PASO FROM V_CONVERSION_TIPO_ARCHIVO WHERE CONVERSION_TIPO_ARCHIVO = 0");
			    ResultSet rs = stmt.executeQuery();
			 
			    // Recorremos el resultado
			    while (rs.next())
			      System.out.println (rs.getString("PASO"));
			    
			 
			    } catch (SQLException sqle) { 
			      System.out.println("Error en la ejecución:" 
			            + sqle.getErrorCode() + " " + sqle.getMessage());    
			}
		} finally {
		    // ... cleanup that will execute whether or not an error occurred ...
		}
}

public static void actualizar() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	Connection con = null;
   
	String surl="jdbc:sqlserver://222.15.19.138:1433;DatabaseName=basasql;SelectMethod=cursor;username=sa;password=Basa2012"; 
	String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			  
	try {
		Class.forName(sDriver).newInstance();
		con = DriverManager.getConnection(surl);
		 
		  try {
			  String sql = "update Pruebas set  DOCUMENTO=?, TIPO=? where CODIGO=100";

			  PreparedStatement preparedStatement =
			          con.prepareStatement(sql);

			  preparedStatement.setString(1, "UPDATE");
			  preparedStatement.setString(2, "UPDATE");
			

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



