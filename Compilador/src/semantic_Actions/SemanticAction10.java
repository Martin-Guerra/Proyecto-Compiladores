package semantic_Actions;

public class SemanticAction10 implements SemanticAction{
	
	private static final double LOWRANGE = 1.7976931348623157;
	private static final double TOPRANGE = 2.2250738585072014;
	private static final int POWER = 10 * 308;
	
	
	
	public SemanticAction10() {
		
	}
	
	public boolean checkRange(String s) {
		
		double value = Double.parseDouble(s);
		if((Math.pow(TOPRANGE, (1/POWER)) < value) && (value < Math.pow(LOWRANGE, POWER))){
			if((value >	Math.pow(-1 * LOWRANGE, POWER)) && (value < Math.pow(-1 * TOPRANGE, 1/POWER)) 
					|| value == 0) {
				return true;
			}
		}
		return false;
	}

}
