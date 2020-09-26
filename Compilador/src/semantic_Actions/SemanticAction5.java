package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

public class SemanticAction5 implements SemanticAction{

	public SemanticAction5() {}

	@Override
	public void execute(char character, LexerAnalyzer la) {
		la.setLexeme(la.getLexeme()+character);
		la.setPos(la.getPos()+1);
		State state=la.getState(la.getNextState(), la.getColumn(character));
		la.setNextState(state.getNextstate());

	}
	
}
