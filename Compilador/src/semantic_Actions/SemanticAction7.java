package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//chequea rango ctes
public class SemanticAction7 implements SemanticAction{

	public void execute(char character, LexerAnalyzer la) {

		String lexeme = la.getLexeme();
		long  number = Long.valueOf(lexeme.substring(0, lexeme.length()-2));//porque ya tiene el _u
		if ((number > Math.pow(2,32)-1)){
			number=(long) (Math.pow(2,32)-1);
			String error="Linea: "+ la.getNroLinea() + " Error: " + "La constante entera supera el rango";
			la.addError(error);
			la.setLexeme("");

			//la.setLexeme(String.valueOf(number) + '_'+'u');//agrego u y el char leido que es l
		}
		else{
			la.setLexeme(lexeme);
			lexeme = la.getLexeme().substring(0, la.getLexeme().length()-2);
			la.addSymbolTable(lexeme, "ULONGINT");
			la.setToken(la.getIdReservedWord("ULONGINT"),lexeme);
		}

		la.setPos(la.getPos()+1);
		State state=la.getState(la.getActualState(), la.getColumn(character));
		la.setActualState(state.getNextstate());

		}
}
