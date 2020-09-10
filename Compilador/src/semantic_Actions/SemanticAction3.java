package semantic_Actions;

public class SemanticAction3 implements SemanticAction{
	
	//Inicializar string y agregar letra al string
	private String token;
	
	public SemanticAction3() {
		token = new String();
	}
	
	public String addChar(Character c) {
		token = c.toString();
		return token;
	}

}
