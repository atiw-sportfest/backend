package de.atiw.sportfest.backend.resource.jaxb;

public class User {
	
	
	
	private String name;
	private String passwort;
	private int berid;
	
	
	public User(String name, String passwort, int berid){
		this.name = name;
		this.passwort = passwort;
		this.berid = berid;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public int getBerid() {
		return berid;
	}
	public void setBerid(int berid) {
		this.berid = berid;
	}	
}
