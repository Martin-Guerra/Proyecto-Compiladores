package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//chequea rango ctes
public class SemanticAction7 implements SemanticAction{

	public void execute(char character, LexerAnalyzer la) {

		String lexeme = la.getLexeme();
		long  number = Long.valueOf(lexeme.substring(0, lexeme.length()-2));//porque ya tiene el _
		if ((number < 0) || (number > Math.pow(2,32)-1)){
			number=(long) (Math.pow(2,32)-1);
			String warning="Linea: "+ la.getNroLinea() + " Warning: " + "La constante entera supera el rango";
			la.addWarning(warning);
			la.setLexeme(String.valueOf(number) + '_'+'u' + character);//agrego u y el char leido que es l
		}
		else
			la.setLexeme(lexeme+character);//45_ul

		lexeme.substring(0, lexeme.length()-3);
		la.setPos(la.getPos()+1);
		la.addSymbolTable(lexeme, "CTE");//45,CTE
		la.setToken(la.getIdReservedWord("CTE"),lexeme);
		State state=la.getState(la.getNextState(), la.getColumn(character));
		la.setNextState(state.getNextstate());

		}
}
