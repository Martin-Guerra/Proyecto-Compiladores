package semantic_Actions;

import Lexer.LexerAnalyzer;

public class SemanticAction10 implements SemanticAction{
	private static final double POWERPOSITIVE =  Math.pow(10,308);
	private static final double POWERNEGATIVE =  Math.pow(10,-308);
	private static final double LOWRANGEPOSITIVE = 1.7976931348623157 * POWERPOSITIVE;
	private static final double TOPRANGEPOSITIVE = 2.2250738585072014 * POWERNEGATIVE;
	private static final double LOWRANGENEGATIVE = -1.7976931348623157 * POWERNEGATIVE;
	private static final double TOPRANGENEGATIVE = -2.2250738585072014 * POWERPOSITIVE;
	
	
	
	
	
	
	public SemanticAction10() {
		
	}
	
	/*public boolean checkRange(String s) {
		
		double value = Double.parseDouble(s);
		if((Math.pow(TOPRANGE, (1/POWER)) < value) && (value < Math.pow(LOWRANGE, POWER))){
			if((value >	Math.pow(-1 * LOWRANGE, POWER)) && (value < Math.pow(-1 * TOPRANGE, 1/POWER)) 
					|| value == 0) {
				return true;
			}
		}
		return false;
	}*/

	@Override
	public void execute(char character, LexerAnalyzer la) {//seguir con los double y su rango !!!!!!!!!
		
		
		//controla rango de float (reemplazar)
				double num;
				String lexeme = la.getLexeme();
				if (lexeme.contains("d")){
					String[] d = lexeme.split("d");
					double real = Double.valueOf(d[0]);
					double exponencial;
					if(d[1].equals("+") || d[1].equals("-"))//cheqeuar si es necesario 
						exponencial=0;
					else
						exponencial=Double.valueOf(d[1]);//chequear -5 o +5
					num = (double) (real* Math.pow(10,exponencial));
				}
				else
					num = Double.valueOf(lexeme);
				
				double lim_sup_neg = TOPRANGENEGATIVE;
				double lim_inf_neg = LOWRANGENEGATIVE;
				double lim_inf = LOWRANGEPOSITIVE;
				double lim_sup = TOPRANGEPOSITIVE;
				if((lim_sup_neg<num && num <0) || (num < lim_inf_neg) || (lim_inf > num && num>0) || (num > lim_sup)){//despues lo hago chequear, esto esta mal, num NO tiene que estar antre el mayor de los negativos y el 0.0 o entre el menos de los positios y el 0.0 
					System.out.println("Linea: "+ la.getNroLinea()+" Warning: El float esta fuera de rango y fue reemplazado por un valor frontera "+ "NUMERO  "+num);
					num=lim_sup;
				}
				buffer = String.valueOf(num);
				al.setBuffer(buffer);
				al.token.setToken(al.CTE_SINGLE);
				al.token.setLexema(buffer);
				al.setEst(al.estados[al.getEst()][al.columna(c)]);
				al.agregarATablaDeSimbolos(al.token.getLexema(), al.token.getToken(), al.SINGLE);
			};
		
	}

}
