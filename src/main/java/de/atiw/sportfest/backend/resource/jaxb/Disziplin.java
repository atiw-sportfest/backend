package de.atiw.sportfest.backend.resource.jaxb;

public class Disziplin {

	private int did;
	private String name;
	private String beschreibung;
	private int minTeilnehmer;
	private int maxTeilnehmer;
	private boolean aktiviert;
	private String regeln;
	private boolean teamleistung;
	
	
	public Disziplin(){
		
	}
	
	public Disziplin(int did, String name, String beschreibung, int minTeilnehmer, int maxTeilnehmer, boolean aktiviert, String regeln, boolean temleistung){
		this.did = did;
		this.name = name;
		this.beschreibung = beschreibung;
		this.minTeilnehmer = minTeilnehmer;
		this.maxTeilnehmer = maxTeilnehmer;
		this.aktiviert = aktiviert;
		this.regeln = regeln;
		this.teamleistung = temleistung;
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
	public int getMinTeilnehmer() {
		return minTeilnehmer;
	}
	public void setMinTeilnehmer(int minTeilnehmer) {
		this.minTeilnehmer = minTeilnehmer;
	}
	public int getMaxTeilnehmer() {
		return maxTeilnehmer;
	}
	public void setMaxTeilnehmer(int maxTeilnehmer) {
		this.maxTeilnehmer = maxTeilnehmer;
	}
	public boolean isAktiviert() {
		return aktiviert;
	}
	public void setAktiviert(boolean aktiviert) {
		this.aktiviert = aktiviert;
	}
	public String getRegeln() {
		return regeln;
	}
	public void setRegeln(String regeln) {
		this.regeln = regeln;
	}
	public boolean isTeamleistung() {
		return teamleistung;
	}
	public void setTeamleistung(boolean teamleistung) {
		this.teamleistung = teamleistung;
	}
	
}
