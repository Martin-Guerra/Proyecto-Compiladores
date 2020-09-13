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
/* -Recibir cadena de caracteres
 * -analizar caracter por carcater
 * -recorrer la matriz para que estado se va y que accion semantica aplicar a ese caracter
 * -cuando encuentre estado final entrega un token cuando se puede */
public class LexerAnalyzer {
	
	
	private StateMatrix sm;
	private String token;
	private BufferedReader br;
	private List<String> errors=new ArrayList<String>();
	private List<String> warning=new ArrayList<String>();
	private SymbolTable st;
	
	// llena matriz y le pasamos por main el buffer con el archivo cargado ya
	public LexerAnalyzer(String path) {
		
			try {
				this.br =new BufferedReader(new FileReader(path));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sm=new StateMatrix();
		
		
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
	public String nextToken(String line) {
		int col;
		State state;
		int nextState=0;
		String token="";
		char character;
	while(line != null) {
		character = line.charAt(0);
		line.substring(1);
		col=sm.getColumn(character);
		state=sm.getState(nextState, col);
		nextState=state.getNextstate();
		//ejecutar accion semantica
		if(nextState == -1 && token == "") {
			return token+character;
			}
		else
			if (nextState == -1 && token != ""){
				line=character+line;
				return  token;
			}
			else
				token=token+character;
		}
		return  null;
	}
	
	/*public void getNextToken(String line) {
		//String line=this.getNextLine();
		System.out.println("texto es:"+line);
		
		while(line!= null) {
		//for(int i=0; i<texto.length();i++) {
			//while(line.length()>0) {
			 character = line.charAt(0); 
			 line.substring(1);
			col=sm.getColumn(character);
			System.out.println("la letra es:"+line.charAt(0));
			System.out.println("La columna es :"+col+"La fila es:" +nextState);
			state=sm.getState(nextState, col);
			nextState=state.getNextstate();
			state.getSemanticaction();
			if(nextState==-1 && token.equals("")) {
					line=character+line;// agregar carcatr al token
					
			}
			else
				if(nextState!=-1) {
					token=token+character;
				}
				else
				{
					line=character+line;
					return token;
				}
				
			System.out.println("es el estado:"+ state.getNextstate());
			// ejecutar accion semantica
			//mover estado
			
			}
			texto=this.getNextLine();
		}
	}*/
	
	public void addError(String error) {
		this.errors.add(error);
	}
	
	public void addWarning(String warning) {
		this.warning.add(warning);
	}
	
	public void addSymbolTable(String token, String id) {
		this.st.add(token, id);
	}
	
	public int 
	
}
