package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;
import SymbolTable.Attribute;
import SymbolTable.Type;

//Chequea rango de las constantes
public class SemanticAction7 implements SemanticAction{

	public void execute(char character, LexerAnalyzer la) {

		String lexeme = la.getLexeme();
		long  number = Long.valueOf(lexeme.substring(0, lexeme.length()-2));
		if ((number > Math.pow(2,32)-1)){
			String error="Linea: "+ la.getNroLinea() + " Error: " + "La constante entera supera el rango";
			la.addError(error);
			la.setLexeme("");
			la.setActualState(0);
		}
		else{
			la.setLexeme(lexeme);
			lexeme = la.getLexeme().substring(0, la.getLexeme().length()-2);
			Attribute attribute = new Attribute("NRO_ULONGINT", Type.ULONGINT);
			la.addSymbolTable(lexeme, attribute);
			la.setToken(la.getIdReservedWord("NRO_ULONGINT"),lexeme);
			la.addRecognizedTokens("Valor de la constante: " + lexeme);
			State state=la.getState(la.getActualState(), la.getColumn(character));
			la.setActualState(state.getNextstate());
		}
		la.setPos(la.getPos()+1);
		}
}
