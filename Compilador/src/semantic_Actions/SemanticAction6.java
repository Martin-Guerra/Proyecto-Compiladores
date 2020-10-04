package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//Chequea que el punto venga solo
public class SemanticAction6 implements SemanticAction{
	
	public void execute( char character, LexerAnalyzer la) {

		if(la.getLexeme().length() == 1 && la.getLexeme().equals(".")) {
			String error = "Linea: " + la.getNroLinea() + " Error: " + "Ingresó el caracter punto (.) solo";
			la.addError(error);
			la.setActualState(0);
		}
		else{
			String lexeme = la.getLexeme();
			la.addSymbolTable(lexeme, "NRO_DOUBLE");
			int idNumber = la.getNumberId(lexeme);
			la.setToken(idNumber,lexeme);
			State state = la.getState(la.getActualState(), la.getColumn(character));
			la.setActualState(state.getNextstate());
		}
		

	}
}