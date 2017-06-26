package de.atiw.sportfest.backend.resource.jaxb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Klasse {

	private Integer kid;
	private String name;
	
	public Klasse() {}
	
    public Klasse(String name){
        this.name = name;
    }

	public Klasse(Integer kid, String name){
		this.kid = kid;
		this.name = name;
	}

	public Integer getKid() {
		return kid;
	}
	public void setKid(Integer kid) {
		this.kid = kid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static ResultSet getRSgetAll(Connection conn) throws SQLException{		
		return conn.prepareStatement("Call KlassenAnzeigen();").executeQuery();
		
	}
	
	public static ResultSet getRSgetOne(Connection conn, String did) throws SQLException{			 
		PreparedStatement ps = conn.prepareStatement("Call KlasseAnzeigen(?)");
		ps.setString(1, did);
		return ps.executeQuery();
	}
	
	public static void getRSput(Connection conn, Klasse klasse) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call KlasseAnlegen(?)");
		ps.setString(1, klasse.name);

		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			klasse.kid = rs.getInt(1);
		}
	}
	
	public static void getRSdelete(Connection conn, String kid) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call KlasseLoeschen(?)");
		ps.setString(1,kid);
		ps.execute();
	}	
}