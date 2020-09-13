package SymbolTable;

import java.util.Hashtable;



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
	
	
	public void add(String lexeme,String id) {
		if(!symbolTable.contains(lexeme)) {
			symbolTable.put(lexeme, id);
		}
	}
	
	
}
