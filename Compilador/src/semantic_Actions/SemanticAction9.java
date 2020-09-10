package semantic_Actions;

public class SemanticAction9 implements SemanticAction{
	
	public SemanticAction9() {
		
	}
	
	public boolean checkRange(String s) {

		int value = Integer.parseInt(s);
		if(value <= Math.pow(2,32) - 1)
			return true;
		else
			return false;
		
	}

}
