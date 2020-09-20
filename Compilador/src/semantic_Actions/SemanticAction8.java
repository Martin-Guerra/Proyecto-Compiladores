package semantic_Actions;

import Lexer.LexerAnalyzer;
//sabemos que viene una "/" y seteamos el estado 0 porque descartamos y guardamos el comentario y descartamos token
public class SemanticAction8 implements SemanticAction{
	
	public SemanticAction8() {
		
	}

	@Override
	public void execute(char character, LexerAnalyzer la) {
		String comment="Linea: "+ la.getNroLinea() + "Comentario: "+ la.getLexeme();
		la.addComments(comment);
		la.setLexeme("");
		la.setNextState(0);
		la.setToken(0, "");
		
	}
	
	

}
