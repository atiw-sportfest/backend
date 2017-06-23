package de.atiw.sportfest.backend.resource.jaxb;

public class Variable {

	
	
	private String name;
	private String expID;
	private String desc;
	private boolean zustand;
	private boolean istHauptvar;
	
	
	
	public Variable(String name, String expID, String desc, boolean zustand, boolean istHauptvar){
		this.name = name;
		this.expID = expID;
		this.desc = desc;
		this.zustand = zustand;
		this.istHauptvar = istHauptvar;
	}


	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpID() {
		return expID;
	}
	public void setExpID(String expID) {
		this.expID = expID;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isZustand() {
		return zustand;
	}
	public void setZustand(boolean zustand) {
		this.zustand = zustand;
	}
	public boolean isIstHauptvar() {
		return istHauptvar;
	}
	public void setIstHauptvar(boolean istHauptvar) {
		this.istHauptvar = istHauptvar;
	}
}
