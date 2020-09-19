package Lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import File.*
;
import SymbolTable.SymbolTable;
import semantic_Actions.SemanticAction;
/* -Recibir cadena de caracteres
 * -analizar caracter por carcater
 * -recorrer la matriz para que estado se va y que accion semantica aplicar a ese caracter
 * -cuando encuentre estado final entrega un token cuando se puede */
public class LexerAnalyzer {
	
	
	private StateMatrix sm;
	private BufferedReader br;
	private List<String> errors=new ArrayList<String>();
	private List<String> warning=new ArrayList<String>();
	private SymbolTable st;
	private State state;
	private Token token;
	private int pos;
	
	// llena matriz y le pasamos por main el buffer con el archivo cargado ya
	public LexerAnalyzer(String path) {
		
			try {
				this.br =new BufferedReader(new FileReader(path));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sm=new StateMatrix();
			this.pos=0;
			this.token=new Token();
		
	}
	
	public String getNextLine() {	
	 /*	String texto;
		try {
			texto =this.br.readLine();
			while(texto != null)
	        {
	            //Hacer lo que sea con la línea leída
	          //  System.out.println("Soy numero:"+texto);
	           // System.out.println("Soy caracter:"+(char)texto);
	            //Leer la siguiente línea
	            texto =this.br.readLine();
	        }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //Repetir mientras no se llegue al final del fichero*/
        
		try {
			return this.br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	///////////////////////pseudo de tincho///////////////////
	public	Token nextToken() {
		Token finalToken = null;
		int col;
		int nextState=0; 
		String lexeme=new String();
		char character ;
		SemanticAction action;
		
		int salida=999999;
		String source;
		try {
			source = this.br.readLine();
		
		while(salida != nextState && this.getPos()<source.length()) {
			if(nextState==-1) {
				nextState=0;
				this.setPos(this.getPos()-1);
			}
		
			character = source.charAt(this.getPos());
			this.setPos(this.getPos()+1);
			col=sm.getColumn(character);
			this.state=sm.getState(nextState, col);
			nextState=this.state.getNextstate();
			action=this.state.getSemanticaction();
			action.execute(this.token.getLexema(), character, this);
		}
		finalToken=new Token(this.token.getId(),this.token.getLexema());
		this.setToken(-1, "");
		return finalToken;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return finalToken;

		
	/*	if(nextState == -1 && token == "") {
			System.out.println("entro en el if 1");
			System.out.println(character);
			sb.append(character);
			token=token+sb.toString();
			 System.out.println(token);
			}
		else
			if (nextState == -1 && token != ""){
				System.out.println("entro en el if 2");
				line=character+line;
				System.out.println(token);
			}
			else {
				
				System.out.println("entro en el if 3");
				System.out.println(character);
				sb.append(character);
				token=token+sb.toString();
				System.out.println(token);
				}
			*/
	
	}
	
	public void setToken(int id,String lexeme) {
		
		
		this.token.setLexema(lexeme);
		this.token.setId(id);
	}
	

	public int getPos() {
		return this.pos;
	}
	
	public void setPos(int i) {
		this.pos=i;
	}
	
	
	public void addError(String error) {
		this.errors.add(error);
	}
	
	public void addWarning(String warning) {
		this.warning.add(warning);
	}
	
	public void addSymbolTable(String token, String id) {
		
		this.st.add(token, id);
	}
	public int getNumberId(String lexeme) {
		return this.st.getNumberId(lexeme);
	}
	
}
