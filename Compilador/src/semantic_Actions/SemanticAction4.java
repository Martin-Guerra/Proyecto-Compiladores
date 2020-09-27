package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//chequea las palabras reservadas
public class SemanticAction4 implements SemanticAction{

	@Override
	public void execute(char character, LexerAnalyzer la) {

		String lexeme = la.getLexeme();
		if( ! ((lexeme.equals("IF") || lexeme.equals("THEN") || lexeme.equals("ELSE")
				|| lexeme.equals("END_IF") || lexeme.equals("OUT") || lexeme.equals("FUNC") || lexeme.equals("RETURN")
				|| lexeme.equals("ULONGINT")|| lexeme.equals("DOUBLE")|| lexeme.equals("FOR")))){
				//agregar a la tabla de plabras reservada los ultimos 3
			la.setLexeme("");
			la.setActualState(0);
			String error = "Linea: " + la.getNroLinea() + " Error: " + "Palabra reservada invalida";
			la.addError(error);
		}
		else {
			int idNumber=la.getIdReservedWord(lexeme);//obtengo el id del lexema de la tabla de palabra reservada
			la.setToken(idNumber,lexeme);
			State state=la.getState(la.getActualState(), la.getColumn(character));
			la.setActualState(state.getNextstate());
		}
	}
	

}
