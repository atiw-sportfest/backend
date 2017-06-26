package de.atiw.sportfest.backend.resource.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ergebnis {

    @XmlElement
	private Integer eid;

    @XmlElement
	private Integer did;

    @XmlElement
	private String name;

    @XmlElement
	private String beschreibung;

    @XmlElement
	private String variablenname;

    @XmlElement
	private Integer bewid;

    @XmlElement
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
	
}
