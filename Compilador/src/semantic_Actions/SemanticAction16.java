package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

public class SemanticAction16 implements SemanticAction{
    @Override
    public void execute(char character, LexerAnalyzer la) {
        //la.setLexeme(la.getLexeme()+character);
        String lexeme = la.getLexeme();
        int idNumber = la.getIdReservedWord(lexeme); //obtengo el id del lexema
        la.setToken(idNumber,"");
        State state = la.getState(la.getActualState(), la.getColumn(character));
        la.setActualState(state.getNextstate());
    }
}
