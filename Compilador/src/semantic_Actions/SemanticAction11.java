package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//Se reconoce la cadena de string
public class SemanticAction11 implements SemanticAction{

    @Override
    public void execute(char character, LexerAnalyzer la) {

        la.setLexeme(la.getLexeme() + character);
        la.setPos(la.getPos() + 1);
        String lexeme = la.getLexeme();
        la.addSymbolTable(lexeme, "CADENA");//agrego a la tabla de simbolos el nuevo lexema con ID
        int idNumber = la.getNumberId(lexeme);//obtengo el id del lexema
        la.setToken(idNumber, lexeme);
        State state = la.getState(la.getNextState(), la.getColumn(character));
        la.setNextState(state.getNextstate());

    }
}
