package semantic_Actions;

import Lexer.LexerAnalyzer;

public class SemanticAction3 implements SemanticAction{
	// agregar letra al string
	@Override
	public void execute(String token, char character, LexerAnalyzer la) {
		// TODO Auto-generated method stub
		token=token+character;
	}
	
	
	

}
