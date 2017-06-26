package de.atiw.sportfest.backend.resource.jaxb;

public class Ergebnis {

	private Integer eid;
	private Integer did;
	private String name;
	private String beschreibung;
	private String variablenname;
	private Integer bewid;
	private Integer istzustand;
	
	
	
	public Ergebnis(Integer eid, Integer did, String name, String beschreibung, String variablenname, Integer bewid, Integer istzustand){
		this.eid = eid;
		this.did = did;
		this.name = name;
		this.beschreibung = beschreibung;
		this.variablenname = variablenname;
		this.bewid = bewid;
		this.istzustand = istzustand;
	}
	
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
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
	public Integer getBewid() {
		return bewid;
	}
	public void setBewid(Integer bewid) {
		this.bewid = bewid;
	}
	public Integer getIstzustand() {
		return istzustand;
	}
	public void setIstzustand(Integer istzustand) {
		this.istzustand = istzustand;
	}	
}
