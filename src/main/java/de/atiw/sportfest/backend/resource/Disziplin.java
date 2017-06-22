package de.atiw.sportfest.backend.resource;

public class Disziplin {

	
	private String name;
	private String beschreibung;
	private int minTeilnehmer;
	private int maxTeilnehmer;
	private boolean teamleistung;
	
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
	public boolean isTeamleistung() {
		return teamleistung;
	}
	public void setTeamleistung(boolean teamleistung) {
		this.teamleistung = teamleistung;
	}
	
}
