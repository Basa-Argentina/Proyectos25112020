package com.pruebi;

import java.io.File;
//import java.io.InputStream;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Date;
//import java.sql.Statement;
//import java.util.Scanner;



import java.sql.ResultSet;
import java.text.SimpleDateFormat;




import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

//import com.basa.sql.Global;
;

public class LectorXML {
	
	public static void Insertar(){
		
		File dir = new File("c:/Temp/FAE/LEE/");
		   
		String[] ficheros = dir.list();
		   
		if (ficheros == null)
			System.out.println("No hay ficheros en el directorio especificado");
		
		else { 
			for (int x=0;x<ficheros.length;x++){
				System.out.println(ficheros[x]);
				main1(ficheros[x]);
			}
		}
	}
	
	
  public static void main1 (String ficheros) {
	  
    try {
    String Abrir = "c:/Temp/FAE/LEE/";
    String AbrirFile  = Abrir + ficheros ;
	//File fXmlFile = new File("c:/Temp/FAE/LEE/2011_12_186_4452.8.faeout");
	File fXmlFile = new File (AbrirFile);	
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();

	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	NodeList nList = doc.getElementsByTagName("HOJA");
//	NodeList nList1 = doc.getElementsByTagName("GUIDFactura");

	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
		//for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
			//Node nNode1 = nList1.item(temp);
		
		

		System.out.println("\nCurrent Element :" + nNode.getNodeName());
		
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			//if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
				
			//id, descipcion, fecha1, fecha2, , numero1, numero2, texto1, texto2,
			//clasificacion_documental_id, elemento_id, lote_referencia_id, prefijoCodigoTipoElemento, ordenRearchivo,
			//pathArchivoDigital, pathLegajo, fechaHora

			Element eElement = (Element) nNode;
			  Connection conn;
		        try {
		          // String idcom  = (eElement.getElementsByTagName("IDComprobante").item(0).getTextContent());
		           
		       //    int idcomlisto = Integer.parseInt(idcom);
		         
		            String fecha1  = (eElement.getElementsByTagName("fecha1").item(0).getTextContent());  
		            String fecha2  = (eElement.getElementsByTagName("fecha2").item(0).getTextContent());
		            String numero11  = (eElement.getElementsByTagName("numero1").item(0).getTextContent());
		            int numero1= Integer.parseInt(numero11);
		            String numero12  = (eElement.getElementsByTagName("numero2").item(0).getTextContent());
		            int numero2= Integer.parseInt(numero12);
		            String texto1  = (eElement.getElementsByTagName("texto1").item(0).getTextContent());
		            String texto2  = (eElement.getElementsByTagName("texto2").item(0).getTextContent());
		            String clasificacion1  = (eElement.getElementsByTagName("clasificacion_documental_id").item(0).getTextContent());
		            int clasificacion= Integer.parseInt(clasificacion1);
		            String numeroEtiqueta  = (eElement.getElementsByTagName("NumeroEtiqueta").item(0).getTextContent());
		           // int numeroEtiqueta= Integer.parseInt(numeroEtiqueta1);
		            String descripcion  = (eElement.getElementsByTagName("descripcion").item(0).getTextContent());
		            
		            System.out.println("Finalizado");
			           
		            System.out.println(fecha1);
		            System.out.println(fecha2);
		            System.out.println(numero1);
		            System.out.println(numero2);
		            System.out.println(texto1);
		            System.out.println(texto2);
		            System.out.println(clasificacion);
		            System.out.println(numeroEtiqueta);
		            System.out.println(descripcion);
		            
		            System.out.println("Fecha1 : " + eElement.getElementsByTagName("fecha1").item(0).getTextContent());
					System.out.println("Fecha2 : " + eElement.getElementsByTagName("fecha2").item(0).getTextContent());
					System.out.println("Numero1 : " + eElement.getElementsByTagName("numero1").item(0).getTextContent());
					System.out.println("Numero2 : " + eElement.getElementsByTagName("numero2").item(0).getTextContent());
					System.out.println("Texto1 : " + eElement.getElementsByTagName("texto1").item(0).getTextContent());
					System.out.println("Texto2 : " + eElement.getElementsByTagName("texto2").item(0).getTextContent());
					System.out.println("Clasificacion Documental : " + eElement.getElementsByTagName("clasificacion_documental_id").item(0).getTextContent());
					System.out.println("NumeroEtiqueta : " + eElement.getElementsByTagName("NumeroEtiqueta").item(0).getTextContent());
					System.out.println("Descripcion : " + eElement.getElementsByTagName("descripcion").item(0).getTextContent());
					
//					Class.forName(Conect.forName);
//			        conn = DriverManager.getConnection(Conect.url);
//			        PreparedStatement stmt = conn.prepareStatement("select codigo , elemento_id from elementos where codigo = ");
					
					
		         //  String dos = (eElement.getElementsByTagName("").item(0).getTextContent()));
		      //     String Nafip  = (eElement.getElementsByTagName("NotificacionAFIP").item(0).getTextContent());
		        	
		           Insertar1(  fecha1, fecha2, numero1, numero2, texto1, texto2, clasificacion, numeroEtiqueta, descripcion);
		           /*  System.out.println(idcom);
		        	Class.forName(Conect.forName);
		            conn = DriverManager.getConnection(Conect.url);

		            PreparedStatement stmt = conn.prepareStatement("insert into recepcion_factura_electronica (idcomprobante, CAE, FechaVtoCAE, CodigoAFIP, MotivoError, CodigoError, NumeroComprobante) values (?,?,?,?,?,?,?)");
		            stmt.setLong(1, idcomlisto);
		            stmt.setString(2, cae);
		            stmt.setString(3, fechavtocae);
		            stmt.setString(4, codAFIP);
		            stmt.setString(5, MERROR);
		            stmt.setString(6, CERROR);
		            stmt.setString(7, NCOMPRO);
		            stmt.executeUpdate();

		            stmt.close();
		            conn.close();*/
		            
		        } catch (Exception e) {
		            JOptionPane.showMessageDialog(null, "Exception Insertar recepcion_factura_electronica: " + e.getMessage()+e.getCause());
		            return;
		        }

			
			
		}
	}
		  } catch (Exception e) {
	e.printStackTrace();
    }
	
  }






public static void Insertar1(String fecha1, String fecha2, int numero1, int numero2, String texto1, String texto2, int clasificacion, String numeroEtiqueta, String descripcion){
	
	  Connection conn;
	  System.out.println("EMpieza insertar");
	  
	  
    //Statement sta = conn.createStatement();
    //Conects
  /*  String sqlComando = "insert into recepcion_factura_electronica "
           + "(idcomprobante, CAE, FechaVtoCAE, CodigoAFIP, MotivoError, CodigoError, NumeroComprobante, NotificacionAFIP) "
            + " values (  )";*/
    try {
    		
    //	System.out.println("idP:"+request.getParameter("numeroetiqueta")+"*");
    	Class.forName(Conect.forName);
    	
        conn = DriverManager.getConnection(Conect.url);
        
        PreparedStatement stmt = conn.prepareStatement("SELECT    elementos.id, elementos.codigo, elementos.fechaModificacion, referencia.elemento_id, referencia.id AS Expr1 "
        		+"	FROM            elementos INNER JOIN "
                     +"    referencia ON elementos.id = referencia.elemento_id "
                    +"     	WHERE        elementos.codigo  ="+numeroEtiqueta+"  ");
		  ResultSet rs = stmt.executeQuery();
		  System.out.println("Continua");
		  
		  
	    while (rs.next()){
	    	
	    	String pasa = (rs.getString("elemento_id"));
	    	System.out.println(pasa);
	    	System.out.println("Hasta aca llega ");
	    	stmt.close();
	    	try { 
	    	
	    		
	    	PreparedStatement stmt1 = conn.prepareStatement("insert into referencias (fecha1, fecha2, numero1, numero2, texto1, texto2, descripcion, elemento_id) values (?,?,?,?,?,?,?,?)  ");
	    	
	    	
   
	    	if (fecha1 == null){
    	stmt1.setNString(1,"NULL");
    	
    	
    }else {
    	 
    	stmt1.setNString(1, fecha1);
    	 System.out.println("paso fecha1");
    	 
    }
    
    if (fecha2 == null ){
    	stmt1.setNString(2,"NULL");
    }else {
    	 stmt1.setNString(2, fecha2);
    	 System.out.println("paso fecha2");
    }
    if ( numero1 == 0){
    	stmt1.setLong(3,0);
    }else {
    	 stmt1.setLong(3,numero1);
    	 System.out.println("paso numero1");
    }
    if (numero2 == 0){
    	stmt1.setLong(4,0);
    }else {
    	 stmt1.setLong(4,numero2);
    	 System.out.println("paso numero2");
    }
    if (texto1 == null){
    	stmt1.setNString(5,"NULL");
    }else {
    	 stmt1.setNString(5, texto1);
    	 System.out.println("paso texto1");
    }
    if (texto2 == null){
    	stmt1.setNString(6,"NULL");
    }else {
    	 stmt1.setNString(6,texto2);
    	 System.out.println("paso texto2");
    }
    if (descripcion == null){
    	stmt1.setNString(7,"NULL");
    }else {
    	 stmt1.setNString(7,descripcion);
    	 System.out.println("paso desc");
    }
    stmt1.setNString(8,pasa);
	 System.out.println("elemento");
 
    stmt1.executeUpdate();
    System.out.println("Finalizado");
//System.out.println(sqlComando);
   //sta.execute(sqlComando);
    															
    stmt1.close();
    
    conn.close();
    
    
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Exception Referencia 1: " + e.getMessage()+e.getCause());
    return;
}
	    } } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Exception Referencia2: " + e.getMessage()+e.getCause());
	        return;
	    }
}

public static void main(String[] args) {
	Insertar();
	
}


}