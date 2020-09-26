package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//chequea largo de identificador < 20 (descarta lo que sobra)
public class SemanticAction2 implements SemanticAction{

	private static final int LENGTH = 20;

	@Override
	public void execute( char character, LexerAnalyzer la) {
		//devolver a la entrada ultimo caracter leido lo hicimos en lexerAnalyzer

		String lexeme = la.getLexeme();
		if(lexeme.length() > LENGTH){
			String warning = "Linea: " + la.getNroLinea() + "Warning: " + "La longitud del identificador es mayor a 20";
			la.addWarning(warning);
			lexeme=lexeme.substring(0, LENGTH);
		}

		la.addSymbolTable(lexeme, "ID");//agrego a la tabla de simbolos el nuevo lexema con ID
		int idNumber = la.getNumberId(lexeme);//obtengo el id del lexema
		la.setToken(idNumber, lexeme);
		State state = la.getState(la.getNextState(), la.getColumn(character));
		la.setNextState(state.getNextstate());

	}

}
