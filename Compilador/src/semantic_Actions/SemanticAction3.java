package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

//Chequea rango double
public class SemanticAction3 implements SemanticAction{

	private static final double POWERPOSITIVE =  Math.pow(10,308);
	private static final double POWERNEGATIVE =  Math.pow(10,-308);
	private static final double  TOPRANGEPOSITIVE = 1.7976931348623157 * POWERPOSITIVE;
	private static final double LOWRANGEPOSITIVE = 2.2250738585072014 * POWERNEGATIVE;
	//private static final double LOWRANGENEGATIVE = -1.7976931348623157 * POWERNEGATIVE;
	//private static final double TOPRANGENEGATIVE = -2.2250738585072014 * POWERPOSITIVE;

	@Override
	public void execute(char character, LexerAnalyzer la) {

		double num;
		String lexeme = la.getLexeme();
		if (lexeme.contains("d")){
			String[] d = lexeme.split("d");
			double real = Double.valueOf(d[0]);
			String d0 = d[0];
			String[] p = d0.split("\\.");
			if(p.length < 2) {
				d0 = d[0] + "0";
			}else{
				if(p[0].equals(""))
					d0 =  "0" + d[0];
			}
			d[0] = d0;
			if(!String.valueOf(real).equals(d[0])){
				num = 4.9*Math.pow(10,-324);
			}else{
				double exponencial;
				exponencial = Double.valueOf(d[1]);
				num = (double) (real * Math.pow(10,exponencial));
			}
		}
		else
			num = Double.valueOf(lexeme);

		//Como el léxico no reconoce numeros negativos no se realizara el chequeo de estos valores.
		if(num < LOWRANGEPOSITIVE || num > TOPRANGEPOSITIVE && num != 0){
			String error = "Linea: " + la.getNroLinea() + " Error: " + "El double se encuentra fuera de rango";
			la.addError(error);
			la.setLexeme("");
			la.setActualState(0);
		}else {
			lexeme = String.valueOf(num);
			la.addSymbolTable(lexeme, "DOUBLE");
			int idNumber = la.getNumberId(lexeme);
			la.setToken(idNumber, lexeme);
			State state = la.getState(la.getActualState(), la.getColumn(character));
			la.setActualState(state.getNextstate());
		}

		la.setPos(la.getPos() + 1);
	}

}