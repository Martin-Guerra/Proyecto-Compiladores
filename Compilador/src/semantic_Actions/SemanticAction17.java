package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

// caso especial de la barra sola
public class SemanticAction17 implements SemanticAction{

	@Override
	public void execute(char character, LexerAnalyzer la) {
		// TODO Auto-generated method stub
		String lexeme = la.getLexeme();
		int idNumber = la.getIdReservedWord(lexeme);//obtengo el id del lexema
		la.setToken(idNumber, "");
		State state = la.getState(la.getActualState(), la.getColumn(character));
		la.setActualState(state.getNextstate());
	}

}
