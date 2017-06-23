package de.atiw.sportfest.backend.resource.jaxb;

public class Regel {
	
	
	
	private String expression;
	private int result;
	
	
	
	public Regel(String expression, int result){
		this.expression = expression;
		this.result = result;
	}

	
	
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}	
}
