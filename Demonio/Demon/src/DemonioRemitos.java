import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.swing.JOptionPane;

public class DemonioRemitos {
	
	static Properties propiedades = new Properties();
	static InputStream entrada = null;

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {

		leerDirectorio();
	}

	public static void actualizar(String paso31, String paso3)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection con = null;
		PreparedStatement stmt = null;
		entrada = new FileInputStream("C:/Demonio/configuracion.properties");
		// cargamos el archivo de propiedades
		propiedades.load(entrada);

		
		String surl = "jdbc:sqlserver://" + propiedades.getProperty("IPServidor")
				+ ";DatabaseName=basa;SelectMethod=cursor;username=sa;password=Aconc46u4";

		String sDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		
		// System.out.println(paso3);

		try {
			Class.forName(sDriver).newInstance();

			
			con = DriverManager.getConnection(surl);

			try {
				// La Query

				// stmt = con.prepareStatement("SELECT elemento_id FROM
				// referencia WHERE elemento_id = "+paso31+"" );

				stmt = con.prepareStatement("SELECT codigo FROM elementos WHERE codigo =" + paso31 + "  ");

				ResultSet rs = stmt.executeQuery();

				// Recorremos el resultado
				while (rs.next()) {

					// String pasa = (rs.getString("elemento_id"));
					String pasa = (rs.getString("codigo"));
					System.out.println(pasa);
					System.out.println(paso3);

					if (!((pasa = paso31) == null)) {
						try {
							Class.forName(sDriver).newInstance();
							con = DriverManager.getConnection(surl);

							try {

								// String sql = "update referencia set
								// pathLegajo=? where elemento_id= "+pasa+" ";
								String sql = "UPDATE referencia SET pathLegajo = ? FROM elementos INNER JOIN " +

										" referencia ON elementos.id = referencia.elemento_id WHERE   elementos.codigo = "
										+ pasa + "";
								PreparedStatement preparedStatement = con.prepareStatement(sql);

								preparedStatement.setString(1, paso3);

								int rowsAffected = preparedStatement.executeUpdate();
								System.out.println("Actualizado");

								File del0 = new File(paso3);
								if (del0.exists()) {
									File del = new File(propiedades.getProperty("DireccionImagenes") + paso31 + ".pdf");
									del.delete();

									ConvertirPdfaPng a = new ConvertirPdfaPng();
									a.convertirPdf(paso3);
								}

							} catch (SQLException sqle) {

								JOptionPane.showMessageDialog(null, "No pudo ser actualizado");
								System.out.println(
										"Error en la ejecuci�n:" + sqle.getErrorCode() + " " + sqle.getMessage());
							}
						} finally {
							// ... cleanup that will execute whether or not an
							// error occurred ...
						}

					}

					break;
				}
			} catch (SQLException sqle) {
				System.out.println("Error en la ejecuci�n:" + sqle.getErrorCode() + " " + sqle.getMessage());
			}
		} finally {
			// ... cleanup that will execute whether or not an error occurred
			// ...
			entrada.close();

		}

	}

	public static void leerDirectorio()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		/*
		 * Lee los directorios definidos en el properties , Por defecto
		 * "C/Para_Subir/"
		 */

		entrada = new FileInputStream("C:/Demonio/configuracion.properties");
		// cargamos el archivo de propiedades
		propiedades.load(entrada);

		File dir = new File(propiedades.getProperty("DireccionImagenes"));

		String[] ficheros = dir.list();

		if (ficheros == null)

			System.out.println("No hay ficheros en el directorio especificado");

		else {

			for (int x = 0; x < ficheros.length; x++) {

				mover2(ficheros[x]);

			}
		}
	}

	public static void mover2(String ficheros)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		entrada = new FileInputStream("C:/Demonio/configuracion.properties");
		// cargamos el archivo de propiedades
		propiedades.load(entrada);

		String PasoDisco = (propiedades.getProperty("DireccionImagenes"));
		String PasoMuni1 = (propiedades.getProperty("DireccionImagenesSubidas"));

		// DecimalFormat formateador = new DecimalFormat("0000000000");
		// int numpaso = Integer.parseInt(paso1);
		// int numpaso1 = Integer.parseInt(paso2);

		Path FROM = Paths.get(PasoDisco + ficheros);

		Path TO = Paths.get(PasoMuni1 + ficheros);

		// Path TO = Paths.get(PasoDisco+paso1+"_"+paso2+"_"+paso3+ ".pdf");

		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES };
		File archivo = new File(PasoDisco + ficheros);
		if (archivo.exists()) {
			Files.copy(FROM, TO, options);
			System.out.println("Movido");
			String Paso3 = ((propiedades.getProperty("DireccionImagenesSubidas") + ficheros));
			String Paso31 = ficheros.substring(0, ficheros.length() - 4);
			String logs = propiedades.getProperty("Logs");
			System.out.println(Paso31);
			actualizar(Paso31, Paso3);
			File f;
			f = new File(logs + "RegistroDemonio.txt");

			try {
				FileWriter w = new FileWriter(f, true);
				BufferedWriter bw = new BufferedWriter(w);
				PrintWriter wr = new PrintWriter(bw);
				wr.write("\r\n");
				wr.write(PasoDisco + ficheros);
				wr.append(" -");
				wr.close();
				bw.close();

				System.out.println("Terminado");

			} finally {
			

			}

		}
	

}
	
	private static void enviarConGMail(String destinatario, String asunto, String cuerpo, int idelemento) {
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente tambi�n.
	    String remitente = "basaargentina2013@gmail.com";  //Para la direcci�n nomcuenta@gmail.com
	    String clave = "Sisba2017";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    props.put("mail.smtp.clave", clave);    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticaci�n mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);

	    try {
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podr�an a�adir varios de la misma manera
	        message.setSubject(asunto);
	        message.setText(cuerpo);
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, clave );
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    //   actualizartabla(idelemento);
	    }
	    catch (MessagingException me) {
	        me.printStackTrace() ;//Si se produce un error
	        System.out.println("Error D");
	    }
	}


}
