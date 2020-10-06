import Lexer.LexerAnalyzer;
import Lexer.Token;
import Parser.Parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

        public static void main(String[] args) {
            String source = "";
            //if(args != null && args.length>0){
                try {
                	//source = loadFile("C:\\Users\\Yago\\Compilador\\Proyecto-Compiladores\\Compilador\\src\\Entrada.txt");
                    //source = loadFile("C:\\Users\\marti\\Desktop\\Proyecto-Compiladores\\Casos de Prueba.txt"); //Desde linea de comando se le pasa el path del archivo de entrada
                	source = loadFile("C:\\Users\\Camila Barreiro\\Desktop\\Compiladores\\Proyecto-Compiladores\\Compilador\\src\\Entrada.txt");
                } catch (IOException e) {
                    System.out.println("No se encuentra el archivo");
                    return;
                }
            //}else{
            //    System.out.println("No se encuentra el archivo");
            //    return;
            //}

            System.out.println("Entrada:\n" + source);
            LexerAnalyzer lexerAnalyzer = new LexerAnalyzer(source);

            Parser parser = new Parser(lexerAnalyzer);

            System.out.println("************* Compilando ************* \n");
            parser.run();
            System.out.println("********** Finalizó la compilación ********** \n");

            System.out.println("**************** Analizador Lexico ****************");
            //Tokens reconocidos
            System.out.println("Tokens reconocidos analizador lexico");
            List<Token> recognizedTokens = lexerAnalyzer.getRecognizedTokens();
            for (Token t: recognizedTokens){
                System.out.println("Lexema: " + t.getLexema());
                System.out.println("Id: " + t.getId());
            }

            //Tabla de símbolos
            System.out.println("Tabla de símbolos");
            lexerAnalyzer.printSymbolTable();

            //Errores y warnings analizador léxico
            System.out.println("Errores analizador léxico");
            System.out.println(lexerAnalyzer.getErrors());
            System.out.println("Warnings analizador léxico");
            System.out.println(lexerAnalyzer.getWarning());

            System.out.println("**************** Analizador Sintáctico ****************");
            System.out.println("Reglas analizador sintáctico");
            System.out.println(parser.getRules());
            System.out.println("Errores analizador sintáctico");
            System.out.println(parser.getErrors());


            /*for (int i = 0; i < 25; i++) {
                Token token = lexerAnalyzer.getNextToken();
                if(token.getId() == 0){
                    i = 24;
                }else{
                    System.out.println("Id: " + token.getId() + " Lexema: " + token.getLexema());
                }
            }*/

        }
    }

