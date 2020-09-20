package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

public class SemanticAction5 implements SemanticAction{
//Ddevuelve a la entrada el ultimo cracter 
// chequea la longitud
	//ir a la tabla a buscar el puntero
	
	
	private static final int LENGTH=20;
	public SemanticAction5() {}

@Override
public void execute(char character, LexerAnalyzer la) {
	// TODO Auto-generated method stub
	//devolver a la entrada ultimo caracter leido lo hicimos en lexerAnalyzer
	
	//chequea largo de identificador < 25 (elimina/descarta)
			String lexeme = la.getLexeme();
			if(lexeme.length()>LENGTH){
				String warning="Linea: "+ la.getNroLinea() + "Warning: "+"La longitud del identificador es mayor a 20";
				la.addWarning(warning);
				lexeme=lexeme.substring(0, LENGTH);
				
			}
				la.addSymbolTable(lexeme, "ID");//agrego a la tabla de simbolos el nuevo lexema con ID
				int idNumber=la.getNumberId(lexeme);//obtengo el id del lexema
				la.setToken(idNumber,lexeme);
				State state=la.getState(la.getNextState(), la.getColumn(character));
				la.setNextState(state.getNextstate());
				
			}
	
}
