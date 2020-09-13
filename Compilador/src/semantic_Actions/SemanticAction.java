package semantic_Actions;

import Lexer.LexerAnalyzer;

public interface SemanticAction {

	public void execute(String token, char character, LexerAnalyzer la) ;
}
