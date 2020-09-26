package SymbolTable;

import java.util.HashMap;
import java.util.Map;

public class ReservedWord {

	
private Map<String, Integer> reserved_table;
	
	public ReservedWord() {
		reserved_table = new HashMap<>();
		
		reserved_table.put("ID",45);
		reserved_table.put("CTE",46);
		reserved_table.put("IF",47);
		reserved_table.put("THEN",48);
		reserved_table.put("ELSE",49);
		reserved_table.put("END_IF",50);
		reserved_table.put("OUT",51);
		reserved_table.put("FUNC",52);
		reserved_table.put("RETURN",53);
		reserved_table.put(";",54);
		reserved_table.put(",",55);
		reserved_table.put(")",56);
		reserved_table.put("(",57);
		reserved_table.put("}",58);
		reserved_table.put("{",59);
		reserved_table.put("<",60);
		reserved_table.put(">",61);
		reserved_table.put("=",62);
		reserved_table.put("/",63);
		reserved_table.put("*",64);
		reserved_table.put("+",65);
		reserved_table.put("-",66);
		reserved_table.put("<=",67);
		reserved_table.put(">=",68);
		reserved_table.put("==",69);
		reserved_table.put("!=",70);
		reserved_table.put("CADENA",71);
	}

	public int getReservedId(String id) {
		return reserved_table.get(id);
	}

	
	
	
	
}
