package File;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileReaderSource {
	  //Declarar una variable BufferedReader
    private  BufferedReader br = null;
    
    
    public FileReaderSource(String route) {
     try {
        //Crear un objeto BufferedReader al que se le pasa 
        //un objeto FileReader con el nombre del fichero
        br = new BufferedReader(new FileReader(route));
        }
     catch (FileNotFoundException e) {
         System.out.println("Error: Fichero no encontrado");
         System.out.println(e.getMessage());
     }
     catch(Exception e) {
         System.out.println("Error de lectura del fichero");
         System.out.println(e.getMessage());
     }
     finally {
         try {
             if(br != null)
                 br.close();
         }
         catch (Exception e) {
             System.out.println("Error al cerrar el fichero");
             System.out.println(e.getMessage());
         }
     }
    }
    
    public BufferedReader getBufferedReader() {
    	return br;
    }
}
