package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

public class SemanticAction10 implements SemanticAction{


//Reconoce operadores aritmeticos
	public SemanticAction10() { }

	@Override
	public void execute(char character, LexerAnalyzer la) {
		la.setLexeme(la.getLexeme()+character);
		String lexeme = la.getLexeme();
		int idnumber = la.getIdReservedWord(lexeme);

		switch(lexeme){
			case "<=": idnumber = la.getIdReservedWord("MENOR_IGUAL");
			case ">=": idnumber = la.getIdReservedWord("MAYOR_IGUAL");
			case "!=": idnumber = la.getIdReservedWord("DISTINTO");
			case "==": idnumber = la.getIdReservedWord("IGUAL");
			case "::": idnumber = la.getIdReservedWord("PUNTO_PUNTO");
		}

		la.setToken(idnumber, lexeme);
		la.setPos(la.getPos()+1);
		State state = la.getState(la.getActualState(), la.getColumn(character));
		la.setActualState(state.getNextstate());

	}

}
