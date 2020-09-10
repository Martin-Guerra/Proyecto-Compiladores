package semantic_Actions;

public class SemanticAction8 implements SemanticAction{
	
	public SemanticAction8() {
		
	}
	
	public boolean deleteComment(String s) {
		if(s.endsWith("/"))
			return true;
		else
			return false;
	}

}
