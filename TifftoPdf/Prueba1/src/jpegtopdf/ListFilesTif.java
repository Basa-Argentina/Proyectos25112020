package jpegtopdf;

import java.io.File;

public class ListFilesTif {

	
    public static  void main () 
    {

        // Aquí la carpeta que queremos explorar
        String path = "C:/java/"; 

        String files1;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles(); 

        for (int i = 0; i < listOfFiles.length; i++) 
        {

            if (listOfFiles[i].isFile()) 
            {
                files1 = listOfFiles[i].getName();
                if (files1.endsWith(".tif") || files1.endsWith(".tif"))
                {
                    System.out.println(files1);
                }
            }
        }
        
    }
  




/*	public String cargaTif() {
		// TODO Auto-generated method stub
		  String path = "C:/java/"; 

	        String files = null;
	        File folder = new File(path);
	        File[] listOfFiles = folder.listFiles(); 

	        for (int i = 0; i < listOfFiles.length; i++) 
	        {

	            if (listOfFiles[i].isFile()) 
	            {
	                files = listOfFiles[i].getName();
	                if (files.endsWith(".tif") || files.endsWith(".tif"))
	                {
	                    System.out.println(files);
	                }
	            }
	        }
	        System.out.println("Fin");
	        return  files ;
	}
    public static void main(String[] args){
    	
}*/
}	

