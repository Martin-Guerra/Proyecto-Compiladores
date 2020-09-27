package semantic_Actions;

import Lexer.LexerAnalyzer;

//Mueve y suma en caso de que entre \n, \s, \t
public class SemanticAction13 implements SemanticAction{

    @Override
    public void execute(char character, LexerAnalyzer la) {

        if(character == '\n')
            System.out.println("Linea: "+ la.getNroLinea()+
                    " Error: El caracter \\n no puede estar al final de la cadena " + la.getLexeme());
        else
        if (character == '\t')
            System.out.println("Linea: " + la.getNroLinea() +
                    " Error: El caracter \\t no puede estar al final de la cadena " + la.getLexeme());
        else
        if(character == ' ')
            System.out.println("Linea: "+ la.getNroLinea() +
                    " Error: El caracter BLANCO no puede estar al final de la cadena " + la.getLexeme());
        else
            System.out.println("Linea: " + la.getNroLinea() +
                    " Error: El caracter " + character + " no puede estar al final de la cadena " + la.getLexeme());

        la.setLexeme("");
        la.setActualState(0);
    }

}


/*    String warning = "Linea: " + la.getNroLinea() + "Warning: " + "La longitud del identificador es mayor a 20";
        la.addWarning(warning);*/

