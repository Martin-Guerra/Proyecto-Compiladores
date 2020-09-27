package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

public class SemanticAction12 implements SemanticAction{
    private static final String INCORRECTSIMBOL = "Caracter ingresado incorrecto: ";

    public SemanticAction12() {

    }

    @Override
    public void execute( char character, LexerAnalyzer la) {
        String error = "Linea: " + la.getNroLinea() + " Error: " + INCORRECTSIMBOL + la.getLexeme();
        la.addError(error);
        la.setLexeme(Character.toString(character));
        State state = la.getState(la.getActualState(), la.getColumn(character));
        la.setActualState(state.getNextstate());
    }

}
