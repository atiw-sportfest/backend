package de.atiw.sportfest.backend.resource.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

    @XmlElement
	private String name;

    @XmlElement
	private String passwort;

    @XmlElement
	private Integer berid;
	
	
	public User(String name, String passwort, Integer berid){
		this.name = name;
		this.passwort = passwort;
		this.berid = berid;
	}


	public String getName() {
		return name;
	}

	public String getPasswort() {
		return passwort;
	}

	public Integer getBerid() {
		return berid;
	}

}
