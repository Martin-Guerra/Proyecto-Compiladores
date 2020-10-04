package SymbolTable;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;


public class SymbolTable {
	private Hashtable<String,String> symbolTable;
	private ReservedWord reservedword; 
	
	
	public SymbolTable() {
		super();
		this.symbolTable = new Hashtable<String,String>();
		this.reservedword = new ReservedWord();
	}


	public int getNumberId(String lexeme) {
		
		String aux=this.symbolTable.get(lexeme);//
		
		return this.reservedword.getReservedId(aux);
	}
	
	
	public void add(String lexeme, String id) {
		if(!symbolTable.contains(lexeme)) {
			symbolTable.put(lexeme, id);
		}
	}

	public Hashtable<String,String> getSymbolTable(){
		Hashtable<String,String> symbolTable = new Hashtable<>(this.symbolTable);
		return symbolTable;
	}

	public void printSymbolTable(){
		for(String key : this.symbolTable.keySet()){
			System.out.println("Lexema: " + key + " Identificador: " + this.symbolTable.get(key));
		}
	}
	
	
}
