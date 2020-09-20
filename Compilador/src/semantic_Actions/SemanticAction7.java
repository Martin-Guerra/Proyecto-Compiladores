package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;
//chequea palabras reservada
public class SemanticAction7 implements SemanticAction{

	public void execute(char character, LexerAnalyzer la) {
		String lexeme = la.getLexeme();
		if(!(lexeme.equals("IF") || lexeme.equals("THEN") || lexeme.equals("ELSE") 
			|| lexeme.equals("END_IF") || lexeme.equals("OUT") || lexeme.equals("FUNC") || lexeme.equals("RETURN")
			|| lexeme.equals("ULONGINT")|| lexeme.equals("DOUBLE")|| lexeme.equals("FOR"))){//agregar a la tabla de plabras reservada los ultimos 3
			la.setLexeme("");
			la.setNextState(0);
		}
		else {
			int idNumber=la.getIdReservedWord(lexeme);//obtengo el id del lexema de la tabla de palabra reservada
			la.setToken(idNumber,lexeme);
			State state=la.getState(la.getNextState(), la.getColumn(character));
			la.setNextState(state.getNextstate());
		}
	}
}
