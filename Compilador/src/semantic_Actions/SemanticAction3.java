package semantic_Actions;

import Lexer.LexerAnalyzer;

public class SemanticAction3 implements SemanticAction{
	// agregar letra al string
	@Override
	public void execute(String token, char character, LexerAnalyzer la) {
		//StringBuilder sb = new StringBuilder();
		//sb.append(character);
		//token=token+sb.toString();
		token=token+character;
		la.setToken(0,token);
	}
	
	
	

}
