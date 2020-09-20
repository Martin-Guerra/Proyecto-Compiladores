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
import SymbolTable.ReservedWord;
import SymbolTable.SymbolTable;
import semantic_Actions.SemanticAction;
/* -Recibir cadena de caracteres
 * -analizar caracter por carcater
 * -recorrer la matriz para que estado se va y que accion semantica aplicar a ese caracter
 * -cuando encuentre estado final entrega un token cuando se puede */
public class LexerAnalyzer {
	private static final int finalState=-1;
	
	private StateMatrix sm;
	private BufferedReader br;
	private List<String> errors=new ArrayList<String>();
	private List<String> warning=new ArrayList<String>();
	private List<String> comments=new ArrayList<String>();
	private SymbolTable st;
	private ReservedWord rw;
	private State state;
	

	private Token token;
	private int pos;
	private int nroLinea;
	private String lexeme;
	private int nextState; 
	
	

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
			this.nroLinea=0;
			this.lexeme=new String();
			this.nextState=0;
	}
	
	
	
	public String getNextLine() {	
		try {
			return this.br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	
	// devuelve <token,lexema> de una palabra
	public	Token nextToken() {
		Token finalToken = null;
		int col;
	
		char character ;
		SemanticAction action;
		
		
		String source;
		try {
			source = this.br.readLine();
		
			while(  nextState!=finalState && this.getPos()<source.length()) {
				if(nextState==9999999) {
					nextState=0;
				}			
				character = source.charAt(this.getPos());
				//this.setPos(this.getPos()+1);
				col=this.getColumn(character);// saco la posicion por columna
				this.state=sm.getState(nextState, col); // tomo el estado entero
				//nextState=this.state.getNextstate();//proximo estado
				action=this.state.getSemanticaction();//accion semantica
				action.execute( character, this);//ejecuto la accion
			}
			finalToken=new Token(this.token.getId(),this.token.getLexema());
			this.setToken(-1, "");
			return finalToken;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return finalToken;

	}
	
	
	
	//devuelve el numero de la columna asociada el caracter
	public int getColumn(char character) {
		return sm.getColumn(character);
	}
	//devuelve el proximo estado del caracter donde tenemos que ir
	public int getNextState() {
		return this.nextState;
	}
	//seteamos el proximo estado del caracter donde tenemos que ir
	public void setNextState(int state) {
		 this.nextState=state;
	}
	//setea el token que se va a devolver
	public void setToken(int id,String lexeme) {
		this.token.setLexema(lexeme);
		this.token.setId(id);
	}
	
	// devuelve la posicion actual de la letra de entrada 
	public int getPos() {
		return this.pos;
	}
	//seteo la posicion actual de la letra de entrada 
	public void setPos(int i) {
		this.pos=i;
	}
	
	//agrego errores a la lista
	public void addError(String error) {
		this.errors.add(error);
	}
	//agrego warning a la lista
	public void addWarning(String warning) {
		this.warning.add(warning);
	}
	// agrego un <lexema,ID> nuevo a la tabla de simbolos
	public void addSymbolTable(String token, String id) {
		
		this.st.add(token, id);
	}
	//devuelvo el id de un lexema dado buscado en la tabla de simbolo
	public int getNumberId(String lexeme) {
		return this.st.getNumberId(lexeme);
	}
	//devuelvo el lexema actual que estamos construyendo
	public String getLexeme() {
		return this.lexeme;
	}
	//construyo el lexema
	public void setLexeme(String string) {
		this.lexeme=string;
		
	}
	//obtengo la celda(estado) de la matriz de SM
	public State getState(int row,int col) {
		return this.sm.getState(row, col);
	}
	
	//obtengo el numero de lineas
	public int getNroLinea() {
		return nroLinea;
	}
	//seteo el numero de lineas
	public void setNroLinea(int nroLinea) {
		this.nroLinea = nroLinea;
	}
	//obtengo el id de la plabra reservada
	public int getIdReservedWord(String key) {
		return rw.getReservedId(key);
	}
	public void addComments(String comment) {
		this.comments.add(comment);
	}
	
}
