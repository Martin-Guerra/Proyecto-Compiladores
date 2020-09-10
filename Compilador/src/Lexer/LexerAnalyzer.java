package Lexer;

import java.io.BufferedReader;
import java.io.IOException;


/* -Recibir cadena de caracteres
 * -analizar caracter por carcater
 * -recorrer la matriz para que estado se va y que accion semantica aplicar a ese caracter
 * -cuando encuentre estado final entrega un token cuando se puede */
public class LexerAnalyzer {
	
	private  BufferedReader br ;
	private StateMatrix sm;
	private String token;
	
	
	public LexerAnalyzer(BufferedReader br) {
		this.br = br;
		sm=new StateMatrix();
		
	}
	
	public String getNextLine() {	
		try {
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getNextToken() {
		String texto=this.getNextLine();
		int col;
		State state;
		for(int i=0; i<texto.length();i++) {
			col=sm.getColumn(texto.charAt(i));
			state=sm.getState(0, col);
			// ejecutar accion semantica
			//mover estado
		}
		
		
	}
	
	
	
}
