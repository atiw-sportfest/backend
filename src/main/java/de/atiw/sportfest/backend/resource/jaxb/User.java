package de.atiw.sportfest.backend.resource.jaxb;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public void setBerid(int berid) {
		this.berid = berid;
	}	
	
	public static ResultSet getRSgetAll(Connection conn) throws SQLException{		
		return conn.prepareStatement("Call BenutzerAnzeigen();").executeQuery();
		
	}
	
	
	public static void getRSput(Connection conn, User user) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call BenutzerAnlegen(?,?,?)");
		ps.setString(1,user.getName());
		ps.setString(2,user.getPasswort());
		ps.setInt(3,user.getBerid());
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			user.name=rs.getString(1);
		}
	}
	
	public static void getRSdelete(Connection conn, String username) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call UserLoeschen(?)");
		ps.setString(1,username);
		ps.execute();
	}	

}
