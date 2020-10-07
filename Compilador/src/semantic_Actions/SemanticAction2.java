package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//chequea largo de identificador < 20 (descarta lo que sobra)
public class SemanticAction2 implements SemanticAction{

	private static final int LENGTH = 20;

	@Override
	public void execute( char character, LexerAnalyzer la) {
		String lexeme = la.getLexeme();
		if(lexeme.length() > LENGTH){
			String warning = "Linea: " + la.getNroLinea() + " Warning: " + "La longitud del identificador es mayor a 20";
			la.addWarning(warning);
			lexeme=lexeme.substring(0, LENGTH);
		}

		la.addSymbolTable(lexeme, "ID");
		int idNumber = la.getNumberId(lexeme);
		la.setToken(idNumber, lexeme);
		la.addRecognizedTokens("Identificador: " + lexeme);
		State state = la.getState(la.getActualState(), la.getColumn(character));
		la.setActualState(state.getNextstate());

	}

}
