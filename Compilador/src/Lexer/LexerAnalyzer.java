package Lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import SymbolTable.ReservedWord;
import SymbolTable.SymbolTable;
import semantic_Actions.SemanticAction;

/* -Recibir cadena de caracteres
 * -analizar caracter por carcater
 * -recorrer la matriz para que estado se va y que accion semantica aplicar a ese caracter
 * -cuando encuentre estado final entrega un token cuando se puede */
public class LexerAnalyzer {
	private static final int EOF = 9999999;
	private static final int FINAL_STATE = -1;

	private BufferedReader br;

	//Matriz de transición de estados, de acciones semánticas y mapeo de columnas
	public StateMatrix sm;
	//Estado que contiene la celda de la matriz
	private State state;
	//Tabla de palabras reservadas
	private ReservedWord rw;
	//Tabla de símbolos
	private SymbolTable st;

	//Errores, warnings y comentarios
	private List<String> errors;
	private List<String> warning;
	private List<String> comments;

	//Token compuesto por el par <token,lexema> que se forma recorriendo la entrada
	private Token token;
	//Posición en la que nos encontramos en el recorrido de la entrada
	private int pos;
	//Número de línea que estamos analizando en el recorrido de la entrada
	private int nroLinea;
	//Lexema que formamos en el recorrido de la entrada
	private String lexeme;
	//Próximo estado al que nos debemos dirigir en la matriz de transición de estados
	private int actualState;
	//Entrada
	private String source = "";
	//Longitud recorrida de source
	private int sourceLong;


	//Llenado de la matriz de transición de estados y de acciones semánticas
	//Se pasa en el main el buffer con el archivo ya cargado
	public LexerAnalyzer(Path path) {
			try {
				Charset charset = Charset.forName("US-ASCII");
				this.br = Files.newBufferedReader(path, charset);
				try{
					String line = this.br.readLine();
					while (line != null) {
						this.source+=line+'\n';
						line = this.br.readLine();
					}
				} catch (IOException x) {
					System.err.format("IOException: %s%n", x);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.sm = new StateMatrix();
			this.rw = new ReservedWord();
			this.st = new SymbolTable();

			this.errors = new ArrayList<String>();
			this.warning = new ArrayList<String>();
			this.comments = new ArrayList<String>();

			this.token = new Token();
			this.pos = 0;
			this.nroLinea = 0;
			this.lexeme = new String();
			this.actualState = 0;
			this.sourceLong = 0;
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

	//Devuelve el par <token,lexema> a partir de la entrada
	public Token nextToken() {
		Token finalToken = null;
		SemanticAction action;

		while (actualState != FINAL_STATE && this.getPos() < source.length()) {
			if (actualState == EOF) {
				actualState = 0;
			}
			char character = source.charAt(this.getPos());
			this.sourceLong++;
			int col = this.getColumn(character);        //Columna en relación al caracter en análisis
			this.state = sm.getState(actualState, col);    //Setea estado global
			action = this.state.getSemanticaction();    //Obtiene acción semántica
			action.execute(character, this);            //Ejecuta acción semántica
		}
		//Inicializa el token a devolver con el par <token,lexema> obtenidos
		this.sourceLong--;
		finalToken = new Token(this.token.getId(), this.token.getLexema());
		//Setea el token global para el próximo pedido del consumidor de tokens
		this.setToken(-1, "");
		this.setActualState(0);
		this.setLexeme("");
		return finalToken;
	}
	
	//Devuelve el numero de la columna asociada el caracter
	public int getColumn(char character) {
		return sm.getColumn(character);
	}
	//Devuelve el proximo estado del caracter donde tenemos que ir
	public int getActualState() {
		return this.actualState;
	}

	//Detea el proximo estado del caracter donde tenemos que ir
	public void setActualState(int state) {
		 this.actualState =state;
	}
	//Detea el token que se va a devolver
	public void setToken(int id,String lexeme) {
		this.token.setLexema(lexeme);
		this.token.setId(id);
	}
	
	//Devuelve la posicion actual de la letra de entrada
	public int getPos() {
		return this.pos;
	}

	//Setea la posicion actual de la letra de entrada
	public void setPos(int i) {
		this.pos=i;
	}
	
	//Agrega errores a la lista
	public void addError(String error) {
		this.errors.add(error);
	}

	//Retorna la lista de errores
	public List<String> getErrors(){
		return new ArrayList<>(this.errors);
	}

	//Agrega warning a la lista
	public void addWarning(String warning) {
		this.warning.add(warning);
	}

	//Agrega un <lexema,ID> nuevo a la tabla de simbolos
	public void addSymbolTable(String token, String id) {
		this.st.add(token, id);
	}

	//Devuelve el id de un lexema dado buscado en la tabla de simbolo
	public int getNumberId(String lexeme) {
		return this.st.getNumberId(lexeme);
	}

	//Devuelve el lexema actual que estamos construyendo
	public String getLexeme() {
		return this.lexeme;
	}

	//Construye el lexema
	public void setLexeme(String string) {
		this.lexeme=string;
	}

	//Obtiene la celda(estado) de la matriz de SM
	public State getState(int row,int col) {
		return this.sm.getState(row, col);
	}
	
	//Obtiene el numero de lineas
	public int getNroLinea() {
		return nroLinea;
	}

	//Setea el numero de lineas
	public void setNroLinea(int nroLinea) {
		this.nroLinea = nroLinea;
	}

	//Obtiene el id de la plabra reservada
	public int getIdReservedWord(String key) {
		return rw.getReservedId(key);
	}

	//Agrega comentarios
	public void addComments(String comment) {
		this.comments.add(comment);
	}

	//Devolver buffer
	public BufferedReader getBuffer(){
		return this.br;
	}

	//Devolver source
	public boolean endSource(){
		return (this.source.length() == this.pos);
	}

	//Devolver source
	public String getSource(){
		return this.source;
	}

	//Devolver source
	public int getSourceLong(){
		return this.sourceLong;
	}

	
}
