package semantic_Actions;

import Lexer.LexerAnalyzer;

public interface SemanticAction {

	public void execute( char character, LexerAnalyzer la) ;
}
