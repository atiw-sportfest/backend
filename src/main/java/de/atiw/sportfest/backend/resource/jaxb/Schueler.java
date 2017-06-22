package de.atiw.sportfest.backend.resource.jaxb;

public class Schueler {
	
	
	
	private int sid;
	private String vorname;
	private String name;
	private int kid;
	private int gid;
	
	
	public Schueler(int sid, String vorname, String name, int kid, int gid){
		this.sid = sid;
		this.vorname = vorname;
		this.name = name;
		this.kid = kid;
		this.gid = gid;
	}
	
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
}
