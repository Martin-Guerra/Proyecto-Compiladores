package SymbolTable;

import Lexer.State;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class SymbolTable {
	private Hashtable<String, List<Attribute>> symbolTable;
	private ReservedWord reservedword; 
	
	
	public SymbolTable() {
		super();
		this.symbolTable = new Hashtable<String,List<Attribute>>();
		this.reservedword = new ReservedWord();
	}


	public int getNumberId(String lexeme) {
		String aux = this.symbolTable.get(lexeme).get(0).getId();
		return this.reservedword.getReservedId(aux);
	}

	//if(attributes.get(count).getUse().equals(attribute.getUse()))
	//if(attributes.get(count).getUse() == Use.nombre_procedimiento ||
	//		attributes.get(count).getUse() == Use.llamado_procedimiento)
	//	this.symbolTable.get(lexeme).add(attribute);
	/*	public void add(String lexeme, Attribute attribute) {
		List<Attribute> attributes = new ArrayList<>();
		if(!this.symbolTable.containsKey(lexeme)) {
			attributes.add(attribute);
			this.symbolTable.put(lexeme, attributes);
		}else{
			attributes = this.symbolTable.get(lexeme);
			boolean found = false;
			int count = 0;
			while(!found && attributes.size() > count){
				//if(attributes.get(count).getScope().equals(attribute.getScope())) {
					found = true;
					this.symbolTable.get(lexeme).get(count).increaseAmount();
				//}
				count++;
			}
			if(!found){
				this.symbolTable.get(lexeme).add(attribute);
			}

		}
	}*/
	public void add(String lexeme, Attribute attribute) {
		List<Attribute> attributes = new ArrayList<>();
		if(!this.symbolTable.containsKey(lexeme)) {
			attributes.add(attribute);
			this.symbolTable.put(lexeme, attributes);
		}else{
			this.symbolTable.get(lexeme).add(attribute);
		}
	}

	public Hashtable<String,List<Attribute>> getSymbolTable(){
		Hashtable<String,List<Attribute>> symbolTable = new Hashtable<>(this.symbolTable);
		return symbolTable;
	}

	public String printSymbolTable(){
		String salida="";
		for(String key : this.symbolTable.keySet()){
			salida += "Lexema: " + key;
			for(Attribute a : this.symbolTable.get(key)) {
				salida += 	" Ambito: " + a.getScope() +
							" - Identificador: " + a.getId() +
							" - Uso: " + a.getUse() + " - Tipo: " + a.getType() +
							" - Amount: " + a.getAmount() +
							" - isDeclared? " + a.isDeclared() + "\n";
			}
		}
		return salida;
	}

	public void deleteSymbolTableEntry(String lexeme){
		List<Attribute> removedAttribute = this.symbolTable.remove(lexeme);
	}

	public void deleteLastElement(String lexeme){
		List<Attribute> removedAttribute = this.symbolTable.get(lexeme);
		removedAttribute.remove(removedAttribute.size()-1);
		//this.symbolTable.replace(lexeme, removedAttribute);
	}

}
