package conversionPDFPDN;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class leer {

	public static void leerDirectorio()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		/*
		 * Lee los directorios definidos en el properties , Por defecto
		 * "C/Para_Subir/"
		 */


		File dir = new File("C:/Archivos_Digitales/PDF");
		
		String[] ficheros = dir.list();

		if (ficheros == null)

			System.out.println("No hay ficheros en el directorio especificado");

		else {

			for (int x = 0; x < ficheros.length; x++) {
				System.out.println(ficheros);
				ConvertirPdfaPng a = new ConvertirPdfaPng();
				a.convertirPdf(ficheros[x]);

			}
		}
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException {
		leerDirectorio();
	}
}
