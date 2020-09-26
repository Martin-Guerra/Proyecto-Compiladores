package semantic_Actions;

import Lexer.LexerAnalyzer;

public class SemanticAction12 implements SemanticAction{
    private static final String INCORRECTSIMBOL = "Caracter ingresado incorrecto";

    public SemanticAction12() {

    }

    @Override
    public void execute( char character, LexerAnalyzer la) {

        String error = "Linea: " + la.getNroLinea() + "Error: " + INCORRECTSIMBOL + character;
        la.addError(error);
    }

}
