import Lexer.LexerAnalyzer;
import Lexer.Token;
import Parser.Parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

        public static String loadFile(String path) throws IOException {
            StringBuilder builder = new StringBuilder();
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fstream));

            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            builder.append('$');
            reader.close();
            return builder.toString();
        }
        
        public static void generarArchivo(String name, String message){
    		Charset charset = Charset.forName("UTF-8");
    		Path path = Paths.get(name);
    		try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
    		    writer.write(message);
    		} catch (IOException x) {
    		    System.err.format("IOException: %s%n", x);
    		}
    	}

        public static void main(String[] args) {
        	String source = "";
        	if(args.length>0) {
                try {
                	//source = loadFile("C:\\Users\\Yago\\Compilador\\Proyecto-Compiladores\\Compilador\\src\\Entrada.txt");
                    source = loadFile(args[0]);
                	//source = loadFile("C:\\Users\\Camila Barreiro\\Desktop\\Compiladores\\Proyecto-Compiladores\\Compilador\\src\\Entrada.txt");
                } catch (IOException e) {
                    System.out.println("No se encuentra el archivo");
                    return;
                }
                
            String textoSalida="************* Tokens reconocidos *************"+"\n";
            LexerAnalyzer lexerAnalyzer = new LexerAnalyzer(source);
            Parser parser = new Parser(lexerAnalyzer);
            parser.run();
           
            List<String> recognizedTokens = lexerAnalyzer.getRecognizedTokens();
            for (String t: recognizedTokens){
                textoSalida+=t+"\n";
            }
            textoSalida+="\n";
            textoSalida+="************* Tabla de simbolos *************"+"\n";
            textoSalida+=lexerAnalyzer.printSymbolTable()+"\n"+"\n";//cargue tabal de simbolos


            textoSalida+="************* Errores Lexicos Reconocidos *************"+"\n";
            textoSalida+=lexerAnalyzer.getErrors()+"\n";
            
            textoSalida+="*************	Warning  Reconocidos *************"+"\n";
            textoSalida+=lexerAnalyzer.getWarning()+"\n";
            
            textoSalida+="************* Reglas Reconocidas *************"+"\n";
            for(int i=0;i<parser.getRules().size();i++)
            	textoSalida+=parser.getRules().get(i)+"\n";
            
            textoSalida+="************* Errores Sintacticos Reconocidos *************"+"\n";
            for(int i=0;i<parser.getErrors().size();i++)
            	textoSalida+=parser.getErrors().get(i)+"\n";
            
            generarArchivo("C:\\Users\\Yago\\Desktop\\Salida.txt", textoSalida);

        }
       }
   }

