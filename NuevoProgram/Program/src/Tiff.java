import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tiff {
 
	public static void Selectsql() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException{
		Connection con = null;
	    PreparedStatement stmt = null;
		String surl="jdbc:sqlserver://222.15.19.138:1433;DatabaseName=basasql;SelectMethod=cursor;username=sa;password=Basa2012"; 
		String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				  
		try {
			  Class.forName(sDriver).newInstance();    
			  con = DriverManager.getConnection(surl);
			 
			  
			  try {
				  
				  
				System.out.println("Desk dem");
				
				
			    stmt = con.prepareStatement(" SELECT [ID], [FK_LEGAJO_ETIQUETA], [DIRECTORIO_PASO]"
			    		+" FROM [basasql].[dbo].[DOCUMENTOS_DIGITALES] where FK_DOCUMENTOS_DIGITALES_LOTE IN (38999, 49724, 49725, 49726, 49727, 49728, 49729"
								+" ,49730, 49731, 49732, 49733, 49734, 49735, 49736, 49737, 49738)") ;

			    ResultSet rs = stmt.executeQuery();
			 
			    System.out.println();
				
			    while (rs.next()){

			    	
			  String paso1 = (rs.getString("ID"));
			  String paso2 = (rs.getString("DIRECTORIO_PASO")); 
			  String paso3 = (rs.getString("FK_LEGAJO_ETIQUETA"));
			  System.out.println(paso1);
			  System.out.println(paso2);
			  System.out.println(paso3);
			  
			  copiarArch(paso1,paso2,paso3);
			    }
			    
			    } catch (SQLException sqle) { 
			      System.out.println("Error en la ejecución:" 
			            + sqle.getErrorCode() + " " + sqle.getMessage());    
			      
			}
			  
		} finally {
			
		   	
		}
		
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		
		Selectsql();
		
}
	
	public static void copiarArch(String paso1, String paso2, String paso3) throws IOException{
		//String PasoDiez = "C:\\Imagenes\\PDF\\" ;
		String PasoDisco = "C:\\110001105354\\";
	//	DecimalFormat formateador = new DecimalFormat("############");
		String tomato1 = "Y:/";
		String tomato  = tomato1 + paso2+"\\"+ paso1+".pdf" ;
		
		
		//int numpaso = Integer.parseInt(paso3);
		//formateador.format(paso3);
	//	int numpaso1 = Integer.parseInt(paso2);
		
		Path FROM = Paths.get(tomato);
		
        Path TO = Paths.get(PasoDisco+(paso3.substring(0,12))+".pdf");
        
        //Path TO = Paths.get(PasoDisco+paso1+"_"+paso2+"_"+paso3+ ".pdf");
        
        CopyOption[] options = new CopyOption[]{
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.COPY_ATTRIBUTES
        };
        File archivo = new File(tomato);
        if (archivo.exists()) {
        	Files.copy(FROM, TO, options);
        
        	
        } else{
        	
        	File f;
        	f = new File(PasoDisco+"Errores.txt");
        	//PasoDisco+"Errores.txt"
        
        	try{
        	FileWriter w = new FileWriter(f,true);
        	
        	BufferedWriter bw = new BufferedWriter(w);
        	PrintWriter wr = new PrintWriter(bw);  	
        	wr.write("\r\n");
        	wr.write(tomato);
        	wr.append(" -");
        	wr.close();
        	bw.close();
        	return;
        	}catch(IOException e){};
        	
        	 }
	}
}