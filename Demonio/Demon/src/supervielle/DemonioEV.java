package supervielle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DemonioEV {
	
	public static void Selectsql() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException{
		Connection con = null;
	    PreparedStatement stmt = null;
		String surl="jdbc:sqlserver://222.15.19.150:1433;DatabaseName=basa;SelectMethod=cursor;username=sa;password=Sicuyo123"; 
		String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				  
		try {
			  Class.forName(sDriver).newInstance();    
			  con = DriverManager.getConnection(surl);
			 
			  try {
				
				    stmt = con.prepareStatement( " SELECT  id, descripcion, fecha1, fecha2, indice_individual, numero1, numero2, texto1, texto2, clasificacion_documental_id, elemento_id, lote_referencia_id, descripcion_rearchivo, referencia_rearchivo_id, \r\n" + 
				    		"                         prefijoCodigoTipoElemento, ordenRearchivo, pathArchivoDigital, pathLegajo, fechaHora, codigoUsuario, descripcionTarea, estadoTarea, pathLegajo1\r\n" + 
				    		"FROM            referencia\r\n" + 
				    		"WHERE        (indice_individual = 0) AND (clasificacion_documental_id IN (81696, 81866, 81697, 81867, 81698, 81868, 81699, 81700, 81869, 81701, 81870, 81702, 81871, 81703, 81872, 81704, 81873, 81705, 81874, 81706, 81876, 81707, 81877, 81708, 81880, 81709, 81882, 81710, 81885, \r\n" + 
				    		"                         81711, 81887, 81712, 81889, 81713, 81890, 81726, 81910, 81727, 81913, 81728, 81915, 81729, 81917, 81934, 81935, 81683, 81907, 81686, 81911, 71127, 81912, 81730, 81919, 81684, 81914, 81731, 81920, 81930, 81931, \r\n" + 
				    		"                         81714, 81895, 81685, 81916, 81687, 81918, 81734, 81923, 81716, 81898, 81732, 81922, 81825, 81863, 81718, 81901, 92015, 92016, 81682))");
				
			 
				    
			    ResultSet rs = stmt.executeQuery();
			 
				
			    while (rs.next()){

			  String id = (rs.getString("id"));
			  String idCaja = (rs.getString("elemento_id"));
			//  String clasifD = (rs.getString("clasificacion_documental_id"));
			  
			  System.out.println("Id de la referencia es :   " + id);
			  System.out.println("Id de la caja  es :  " +idCaja);

			
			  buscarultimaetiquetav(id,idCaja);
			    
			    }
			    } catch (SQLException sqle) { 
			      System.out.println("Error en la ejecución:" 
			            + sqle.getErrorCode() + " " + sqle.getMessage());    
			}
		} finally {
		   	
		}
		
	}

	
	private static void buscarultimaetiquetav(String id, String idCaja) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection con = null;
	    PreparedStatement stmt = null;
		String surl="jdbc:sqlserver://222.15.19.150:1433;DatabaseName=basa;SelectMethod=cursor;username=sa;password=Sicuyo123"; 
		String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				  
		try {
			  Class.forName(sDriver).newInstance();    
			  con = DriverManager.getConnection(surl);
			 
			  try {
				
				    stmt = con.prepareStatement( " SELECT TOP (1) [id], [codigo] FROM [basa].[dbo].[elementos]\r\n" + 
				    		"  where tipoElemento_id = 11 and estado = 'Creado' and clienteEmp_id is null and contenedor_id is null order by id asc ");
				
			 
				    
			    ResultSet rs = stmt.executeQuery();
			 
				
			    while (rs.next()){

			  String idEV	 = (rs.getString("id"));
	
		  System.out.println("Busco ultima etiqueta es:   " + idEV );
			  actualizarreferenciaelemento(id,idCaja,idEV);
			
			  	    
			    }
			    } catch (SQLException sqle) { 
			      System.out.println("Error en la ejecución:" 
			            + sqle.getErrorCode() + " " + sqle.getMessage());    
			}
		} finally {
		   	
		}
		
		
		
		// TODO Auto-generated method stub
		
	}

	

	private static void actualizarreferenciaelemento(String id, String idCaja, String idEV) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

        //  PreparedStatement ps = null;
          String surl = "jdbc:sqlserver://222.15.19.150:1433;DatabaseName=basa;SelectMethod=cursor;username=sa;password=Sicuyo123";
          String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                      try {            	   
						Connection con = DriverManager.getConnection(surl);
                   	System.out.println(id);
						PreparedStatement ps = con.prepareStatement(
                   			      "UPDATE referencia SET indice_individual = ? , elemento_id = ?  WHERE id = "+id+"");

                   			    // set the preparedstatement parameters
                   			    ps.setString(1,"1");
                   			    ps.setString(2, idEV);
                   			   
                   			    
                   			    // call executeUpdate to execute our sql update statement
                   			    ps.executeUpdate();
                   			    System.out.println("Paso");
                   			    ps.close();
                   	 PreparedStatement ps1 = con.prepareStatement(
                         			      "UPDATE elementos SET contenedor_id = ? , clienteEmp_id = ?  WHERE id = "+idEV+"");

                         			    // set the preparedstatement parameters
                         			    ps1.setString(1,idCaja);
                         			    ps1.setString(2,"20042");
                         			   

                         			    // call executeUpdate to execute our sql update statement
                         			    ps1.executeUpdate();
                   			    System.out.println("Hasta aca llego");
                   			  
                   			    ps1.close();
                   			    
             
              } catch (SQLException sqle) {
                  System.out.println("Error en la ejecucioon:"
                          + sqle.getErrorCode() + " " + sqle.getMessage() + "update");
              }


           finally {

        
          }
		
		// TODO Auto-generated method stub
		
	}


	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Selectsql();
	}

}
