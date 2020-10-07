package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

// Caso especial de la barra sola
public class SemanticAction17 implements SemanticAction{

	@Override
	public void execute(char character, LexerAnalyzer la) {
		// TODO Auto-generated method stub
		String lexeme = la.getLexeme();
		int idNumber = la.getIdReservedWord(lexeme);
		la.setToken(idNumber, "");
		la.addRecognizedTokens(lexeme);
		State state = la.getState(la.getActualState(), la.getColumn(character));
		la.setActualState(state.getNextstate());
	}

}
