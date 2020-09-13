package semantic_Actions;

import Lexer.LexerAnalyzer;

public class SemanticAction5 implements SemanticAction{
//Ddevuelve a la entrada el ultimo cracter 
// chequea la longitud
	//ir a la tabla a buscar el puntero
	
	private static final String WARNING="Identificador truncado";
	private static final int LENGTH=20;
	public SemanticAction5() {}

@Override
public void execute(String token, char character, LexerAnalyzer la) {
	// TODO Auto-generated method stub
	//devolver a la entrada ultimo caracter leido
	if(token.length()>20) {
		 token=token.substring(0, LENGTH);
		la.addWarning(WARNING);	
	}
	
	la.addSymbolTable(token, "ID");
	///////////////////el lexico tendria que devilver un token que sea lexema y id en un string.
	string newToken = "lexema, token(ID)";
	la.addToken(newToken);
	
}
	
	
	
}
