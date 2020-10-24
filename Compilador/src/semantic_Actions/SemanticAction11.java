package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;
import SymbolTable.Attribute;

//Se reconoce la cadena de string
public class SemanticAction11 implements SemanticAction{

    @Override
    public void execute(char character, LexerAnalyzer la) {

        la.setLexeme(la.getLexeme() + character);
        la.setPos(la.getPos() + 1);
        String lexeme = la.getLexeme();
        Attribute attribute = new Attribute(lexeme,"CADENA");
        la.addSymbolTable(lexeme, attribute);
        int idNumber = la.getNumberId(lexeme);
        la.setToken(idNumber, lexeme);
		la.addRecognizedTokens("Cadena de String : " + lexeme);
        State state = la.getState(la.getActualState(), la.getColumn(character));
        la.setActualState(state.getNextstate());

    }
}
