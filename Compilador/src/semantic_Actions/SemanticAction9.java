package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//Descartar el token porque terminamos de leer un comentario
public class SemanticAction9 implements SemanticAction{
	
	public SemanticAction9() { }

	@Override
	public void execute(char character, LexerAnalyzer la) {

		String comment = "Linea: " + la.getNroLinea() + " Comentario: " + la.getLexeme();
		la.addComments(comment);
		la.setLexeme("");
		State state = la.getState(la.getNextState(), la.getColumn(character));
		la.setNextState(state.getNextstate());

	}

}
