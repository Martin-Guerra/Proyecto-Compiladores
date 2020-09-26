package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

public class SemanticAction10 implements SemanticAction{


//Reconoce operadores aritmeticos
	public SemanticAction10() { }

	@Override
	public void execute(char character, LexerAnalyzer la) {

		la.setLexeme(la.getLexeme()+character);
		String lexeme = la.getLexeme();
		int idnumber = la.getIdReservedWord(la.getLexeme());
		la.setToken(idnumber, lexeme);
		la.setPos(la.getPos()+1);
		State state = la.getState(la.getNextState(), la.getColumn(character));
		la.setNextState(state.getNextstate());

	}

}
