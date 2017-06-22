package de.atiw.sportfest.backend.resource.jaxb;

public class Klasse {
	
	
	
	private int kid;
	private String name;
	
	public Klasse(int kid, String name){
		this.kid = kid;
		this.name = name;
	}

	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
