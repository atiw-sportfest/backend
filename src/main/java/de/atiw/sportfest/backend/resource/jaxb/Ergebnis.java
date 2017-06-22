package de.atiw.sportfest.backend.resource.jaxb;

public class Ergebnis {

	private int eid;
	private int did;
	private String name;
	private String beschreibung;
	private String variablenname;
	private int bewid;
	private int istzustand;
	
	
	
	public Ergebnis(int eid, int did, String name, String beschreibung, String variablenname, int bewid, int istzustand){
		this.eid = eid;
		this.did = did;
		this.name = name;
		this.beschreibung = beschreibung;
		this.variablenname = variablenname;
		this.bewid = bewid;
		this.istzustand = istzustand;
	}
	
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public String getVariablenname() {
		return variablenname;
	}
	public void setVariablenname(String variablenname) {
		this.variablenname = variablenname;
	}
	public int getBewid() {
		return bewid;
	}
	public void setBewid(int bewid) {
		this.bewid = bewid;
	}
	public int getIstzustand() {
		return istzustand;
	}
	public void setIstzustand(int istzustand) {
		this.istzustand = istzustand;
	}	
}
