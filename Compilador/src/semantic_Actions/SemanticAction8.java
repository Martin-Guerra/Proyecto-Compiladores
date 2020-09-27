package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//Reconocemos saltos de lineas \n en el comentario
public class SemanticAction8 implements SemanticAction{
	
	public SemanticAction8() { }

	@Override
	public void execute(char character, LexerAnalyzer la) {

		la.setNroLinea(la.getNroLinea()+1);
		la.setPos(0);
		State state = la.getState(la.getActualState(), la.getColumn(character));
		la.setActualState(state.getNextstate());

	}

}
