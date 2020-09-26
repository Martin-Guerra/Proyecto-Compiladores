package semantic_Actions;

import Lexer.LexerAnalyzer;

//Devuelve errores
public class SemanticAction6 implements SemanticAction{
	
	public void execute( char character, LexerAnalyzer la) {
		if(character=='\n')
			System.out.println("Linea: "+ la.getNroLinea()+ " Error: El caracter \\n no puede estar al final de la cadena " + la.getLexeme());
		else
		if (character == '\t')
			System.out.println("Linea: "+ la.getNroLinea()+ " Error: El caracter \\t no puede estar al final de la cadena " + la.getLexeme());
		else
		if(character == ' ')
			System.out.println("Linea: "+ la.getNroLinea()+ " Error: El caracter BLANCO no puede estar al final de la cadena " + la.getLexeme());
		else
			System.out.println("Linea: "+ la.getNroLinea()+ " Error: El caracter " + character + " no puede estar al final de la cadena " + la.getLexeme());

		la.setLexeme("");
		la.setNextState(0);
	}
}
