package semantic_Actions;

import Lexer.LexerAnalyzer;

public class SemanticAction1 implements SemanticAction{
	
	private static final String INCORRECTSIMBOL = "Caracter ingresado incorrecto";
	
	public SemanticAction1() {
		
	}

	@Override
	public void execute( char character, LexerAnalyzer la) {
		// TODO Auto-generated method stub
		la.addError(INCORRECTSIMBOL);
	}

	

	

}
