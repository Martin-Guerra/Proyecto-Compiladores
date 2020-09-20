package semantic_Actions;

import Lexer.LexerAnalyzer;

//caracter que pasa directo 
public class SemanticAction6 implements SemanticAction{
	
	public void execute( char character, LexerAnalyzer la) {
		char a = la.getLexeme().charAt(0);
		int token= (int) a;
		la.token.setToken(token);
		la.token.setLexema(al.getBuffer());
		la.setEst(al.estados[al.getEst()][al.columna(c)]);
	}
}
