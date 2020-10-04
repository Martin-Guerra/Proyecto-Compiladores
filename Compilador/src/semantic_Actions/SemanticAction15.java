package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//Fin de archivo
public class SemanticAction15 implements SemanticAction{

    public SemanticAction15(){ }

    @Override
    public void execute(char character, LexerAnalyzer la) {
        la.setToken(0,"EOF");
        State state=la.getState(la.getActualState(), la.getColumn(character));
        la.setActualState(state.getNextstate());
    }
}
