
import Lexer.LexerAnalyzer;
import Lexer.Token;
import Parser.Parser;
import SymbolTable.Attribute;
import SymbolTable.SymbolTable;
import SyntacticTree.SyntacticTree;
import AssemblerGenerator.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
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
        	//if(args.length>0) {
                try {
                    //source = loadFile(args[0]);
                	//source = loadFile("C:\\Users\\Yago\\Compilador\\Proyecto-Compiladores\\Compilador\\src\\Entrada.txt");
                	source = loadFile("C:\\Users\\Camila Barreiro\\Desktop\\Compiladores\\Proyecto-Compiladores\\Compilador\\src\\Entrada.txt");
                } catch (IOException e) {
                    System.out.println("No se encuentra el archivo");
                    return;
                }

            LexerAnalyzer lexerAnalyzer = new LexerAnalyzer(source);
            Parser parser = new Parser(lexerAnalyzer);
            parser.run();

            //System.out.println("************* Tokens reconocidos *************"+"\n");
            /*List<String> recognizedTokens = lexerAnalyzer.getRecognizedTokens();
            for (String t: recognizedTokens){
                System.out.println(t);
            }*/

            String textoSalida = "";

            textoSalida+= "************* Tabla de simbolos *************"+"\n";
            textoSalida+= lexerAnalyzer.printSymbolTable();
            textoSalida+="************* Errores Lexicos Reconocidos *************"+"\n";
            textoSalida+=lexerAnalyzer.getErrors()+"\n";
            textoSalida+="*************	Warning  Reconocidos *************"+"\n";
            textoSalida+=lexerAnalyzer.getWarning()+"\n";
            textoSalida+="************* Reglas Reconocidas *************"+"\n";
            for(int i=0;i<parser.getRules().size();i++)
                textoSalida+=parser.getRules().get(i)+"\n";
            textoSalida+="************* Errores Sintacticos y Semánticos Reconocidos *************"+"\n";
            for(int i=0;i<parser.getErrors().size();i++)
                textoSalida+=parser.getErrors().get(i)+"\n";
            if(lexerAnalyzer.getErrors().isBlank() && parser.getErrors().size() == 0){
                textoSalida+="************* Arbol sintactico *************"+"\n";
                textoSalida+=parser.printSyntacticTree();
                textoSalida+='\n'+"************* Arbol sintactico procedimientos *************"+"\n";
                textoSalida+=parser.printPROCtree();
                List<SyntacticTree> PROCtrees = parser.getPROCtreeList();
                System.out.println('\n'+"************* ASSEMBLER *************"+"\n");
                RegisterContainer registerContainer = new RegisterContainer();
                SyntacticTree syntacticTree = parser.returnTree();
                AssemblerGenerator ag = new AssemblerGenerator(syntacticTree);
                SymbolTable st = lexerAnalyzer.getSt();
                String assembler = ag.printAssembler(PROCtrees, syntacticTree, registerContainer, st);
                //generarArchivo(args[2], assembler);
                generarArchivo("C:\\Users\\Camila Barreiro\\Desktop\\Compiladores\\Proyecto-Compiladores\\Compilador\\src\\Salida.txt", assembler);
            }
            //generarArchivo(args[1], textoSalida);
            generarArchivo("C:\\Users\\Camila Barreiro\\Desktop\\Compiladores\\Proyecto-Compiladores\\Compilador\\src\\Salida.txt", textoSalida);
            System.out.println(textoSalida);


            /*System.out.println("************* Tabla de simbolos *************"+"\n");
            System.out.println(lexerAnalyzer.printSymbolTable()+"\n"+"\n");
            System.out.println("************* Errores Lexicos Reconocidos *************"+"\n");
            System.out.println(lexerAnalyzer.getErrors());
            System.out.println("*************	Warning  Reconocidos *************"+"\n");
            System.out.println(lexerAnalyzer.getWarning()+"\n");
            System.out.println("************* Reglas Reconocidas *************"+"\n");
            for(int i=0;i<parser.getRules().size();i++)
                System.out.println(parser.getRules().get(i)+"\n");
            if(lexerAnalyzer.getErrors().isBlank() ) {
                System.out.println("************* Errores Sintacticos y Semánticos Reconocidos *************" + "\n");
                for (int i = 0; i < parser.getErrors().size(); i++)
                    System.out.println(parser.getErrors().get(i) + "\n");
            }

            if(lexerAnalyzer.getErrors().isBlank() && parser.getErrors().size() == 0){
                System.out.println("************* Arbol sintactico *************"+"\n");
                System.out.print(parser.printSyntacticTree());

                System.out.println('\n'+"************* Arbol sintactico procedimientos *************"+"\n");
                System.out.print(parser.printPROCtree());
                List<SyntacticTree> PROCtrees = parser.getPROCtreeList();

                System.out.println('\n'+"************* ASSEMBLER *************"+"\n");
                RegisterContainer registerContainer = new RegisterContainer();
                SyntacticTree syntacticTree = parser.returnTree();
                AssemblerGenerator ag = new AssemblerGenerator(syntacticTree);
                SymbolTable st = lexerAnalyzer.getSt();
                String assembler = ag.printAssembler(PROCtrees, syntacticTree, registerContainer, st);
                generarArchivo("C:\\Users\\Camila Barreiro\\Desktop\\Compiladores\\Proyecto-Compiladores\\Compilador\\src\\assembler.asm", assembler);
            }
            //generarArchivo("C:\\Users\\Camila Barreiro\\Desktop\\Compiladores\\Proyecto-Compiladores\\Compilador\\src\\Salida.txt", textoSalida);
        	//}*/


       }
   }

