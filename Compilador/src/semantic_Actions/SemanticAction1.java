package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//Caracteres que pasan directo
public class SemanticAction1 implements SemanticAction{

	public SemanticAction1() {
	}

	@Override
	public void execute(char character, LexerAnalyzer la) {
		//la.setLexeme(la.getLexeme() + character);
		la.setPos(la.getPos() + 1);
		String key = Character.toString(character);
		int idNumber = la.getIdReservedWord(key); //obtengo el id del lexema de la tabla de palabra reservada
		la.setToken(idNumber,"");
		State state=la.getState(la.getActualState(), la.getColumn(character));
		la.setActualState(state.getNextstate());
	}

}
