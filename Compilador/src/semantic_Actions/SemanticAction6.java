package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;
import SymbolTable.Attribute;

//Chequea que el punto venga solo para los double
public class SemanticAction6 implements SemanticAction{
	private static final double POWERPOSITIVE =  Math.pow(10,308);
	private static final double POWERNEGATIVE =  Math.pow(10,-308);
	private static final double  TOPRANGEPOSITIVE = 1.7976931348623157 * POWERPOSITIVE;
	private static final double LOWRANGEPOSITIVE = 2.2250738585072014 * POWERNEGATIVE;

	public void execute( char character, LexerAnalyzer la) {

		if(la.getLexeme().length() == 1 && la.getLexeme().equals(".")) {
			String error = "Linea: " + la.getNroLinea() + " Error: " + "Ingresó el caracter punto (.) solo";
			la.addError(error);
			la.setActualState(0);
		}
		else{
			double num;
			String lexeme = la.getLexeme();
			String[] p = lexeme.split("\\.");
			double real = Double.valueOf(lexeme);
			String p0 = p[0];
			if(p.length < 2) {
				p0 = p[0] + ".0";
			}else{
				if(p[0].equals(""))
					p0 =  "0." + p[1];
			}

			if(!String.valueOf(real).equals(p0)){
				num = 4.9*Math.pow(10,-324);
			}else{
				num = real;
			}
			if(num < LOWRANGEPOSITIVE || num > TOPRANGEPOSITIVE && num != 0){
				String error = "Linea: " + la.getNroLinea() + " Error: " + "El double se encuentra fuera de rango";
				la.addError(error);
				la.setLexeme("");
				la.setActualState(0);
			}else {
				lexeme = String.valueOf(num);
				Attribute attribute = new Attribute("NRO_DOUBLE", "DOUBLE");
				la.addSymbolTable(lexeme, attribute);
				int idNumber = la.getNumberId(lexeme);
				la.setToken(idNumber, lexeme);
				la.addRecognizedTokens("Valor double: " + lexeme);
				State state = la.getState(la.getActualState(), la.getColumn(character));
				la.setActualState(state.getNextstate());
			}

		}
	}
}