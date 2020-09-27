package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//Caracteres que pasan directo
public class SemanticAction1 implements SemanticAction{

	public SemanticAction1() {
	}

	@Override
	public void execute( char character, LexerAnalyzer la) {
		la.setLexeme(la.getLexeme() + character);
		la.setPos(la.getPos() + 1);
		int idNumber = la.getIdReservedWord(la.getLexeme());//obtengo el id del lexema de la tabla de palabra reservada
		la.setToken(idNumber,"");
		State state=la.getState(la.getNextState(), la.getColumn(character));
		la.setNextState(state.getNextstate());

	}

}
