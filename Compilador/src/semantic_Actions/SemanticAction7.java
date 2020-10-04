package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//chequea rango ctes
public class SemanticAction7 implements SemanticAction{

	public void execute(char character, LexerAnalyzer la) {

		String lexeme = la.getLexeme();
		long  number = Long.valueOf(lexeme.substring(0, lexeme.length()-2));//porque ya tiene el _u
		if ((number > Math.pow(2,32)-1)){
			String error="Linea: "+ la.getNroLinea() + " Error: " + "La constante entera supera el rango";
			la.addError(error);
			la.setLexeme("");
			la.setActualState(0);
		}
		else{
			la.setLexeme(lexeme);
			lexeme = la.getLexeme().substring(0, la.getLexeme().length()-2);
			la.addSymbolTable(lexeme, "NRO_ULONGINT");
			la.setToken(la.getIdReservedWord("NRO_ULONGINT"),lexeme);
			State state=la.getState(la.getActualState(), la.getColumn(character));
			la.setActualState(state.getNextstate());
		}
		la.setPos(la.getPos()+1);
		}
}
