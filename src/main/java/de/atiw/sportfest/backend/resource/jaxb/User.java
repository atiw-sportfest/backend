package de.atiw.sportfest.backend.resource.jaxb;

public class User {
	
	
	
	private String name;
	private String passwort;
	private Integer berid;
	
	
	public User(String name, String passwort, Integer berid){
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
	public Integer getBerid() {
		return berid;
	}
	public void setBerid(Integer berid) {
		this.berid = berid;
	}	
}
