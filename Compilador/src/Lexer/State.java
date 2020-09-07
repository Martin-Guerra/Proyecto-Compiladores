package Lexer;

import semantic_Actions.SemanticAction;

public class State {

	private int nextstate;
	private SemanticAction semanticaction;
	
	public State(int nextstate) {
		this.nextstate = nextstate;
	}
	
	public void setState(int nextstate) {
		this.nextstate = nextstate;
	}
	
	public void setSemanticAction(SemanticAction s) {
		semanticaction = s;
	}
}
