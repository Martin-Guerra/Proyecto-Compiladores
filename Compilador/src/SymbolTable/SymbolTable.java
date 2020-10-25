package SymbolTable;

import Lexer.State;

import java.util.Hashtable;


public class SymbolTable {
	private Hashtable<String,Attribute> symbolTable;
	private ReservedWord reservedword; 
	
	
	public SymbolTable() {
		super();
		this.symbolTable = new Hashtable<String,Attribute>();
		this.reservedword = new ReservedWord();
	}


	public int getNumberId(String lexeme) {
		String aux = this.symbolTable.get(lexeme).getId();
		return this.reservedword.getReservedId(aux);
	}
	
	
	public void add(String lexeme, Attribute attribute) {
		if(!symbolTable.containsKey(lexeme)) {
			symbolTable.put(lexeme, attribute);
		}else{
			symbolTable.get(lexeme).increaseAmount();
		}
	}

	public Hashtable<String,Attribute> getSymbolTable(){
		Hashtable<String,Attribute> symbolTable = new Hashtable<>(this.symbolTable);
		return symbolTable;
	}

	public String printSymbolTable(){
		String salida="";
		for(String key : this.symbolTable.keySet()){
			 salida+="Lexema: " + key + " Identificador: " + this.symbolTable.get(key).getId() +
					 " - Uso: " + this.symbolTable.get(key).getUse() + " - Tipo: " + this.symbolTable.get(key).getType() +
					 " - Amount: " + this.symbolTable.get(key).getAmount() + "\n";
		}
		return salida;
	}

	public void deleteSymbolTableEntry(String lexeme){
		Attribute removedAttribute = this.symbolTable.remove(lexeme);
	}



}
