package de.atiw.sportfest.backend.resource.jaxb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Ergebnis {

	private int eid;
	private int did;
	private String name;
	private String beschreibung;
	private String variablenname;
	private int bewid;
	
	
	
	public Ergebnis(int eid, int did, String name, String beschreibung, String variablenname, int bewid){
		this.eid = eid;
		this.did = did;
		this.name = name;
		this.beschreibung = beschreibung;
		this.variablenname = variablenname;
		this.bewid = bewid;
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
	
	public static ResultSet getRSgetAll(Connection conn) throws SQLException{		
		return conn.prepareStatement("Call ErgebnisseAnzeigen(); ").executeQuery();
		
	}
	public static ResultSet getRSgetOne(Connection conn, String eid) throws SQLException{			 
		PreparedStatement ps = conn.prepareStatement("Call ErgebnisAnzeigen(?)");
		ps.setString(1, eid);
		return ps.executeQuery();
	}
	public static void put(Connection conn, Ergebnis ergebnis) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call ErgebnisAnlegen(?,?,?,?,?)");
		ps.setInt(1,ergebnis.getDid() );
		ps.setString(2,ergebnis.getName());
		ps.setString(3,ergebnis.getBeschreibung());
		ps.setString(4,ergebnis.getVariablenname());
		ps.setInt(5,ergebnis.getBewid());
		ps.execute();
	}
	public static void post(Connection conn, Ergebnis ergebnis) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call ErgebnisAendern (?,?,?,?,?,?)");
		ps.setInt(1,ergebnis.getEid() );
		ps.setInt(2,ergebnis.getDid() );
		ps.setString(3,ergebnis.getName());
		ps.setString(4,ergebnis.getBeschreibung());
		ps.setString(5,ergebnis.getVariablenname());
		ps.setInt(6,ergebnis.getBewid());
		ps.execute();
	}
	public static void delete(Connection conn, String eid) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call ErgebnisLoeschen(?)");
		ps.setString(1,eid);
		ps.execute();
	}

	public static Ergebnis getOne(Connection conn, String did) throws SQLException{
		ResultSet rs;
		rs = getRSgetOne(conn, did);
		if(rs.next()){
			return new Ergebnis(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
		}
		return null;
	}
	
	public static ArrayList<Ergebnis> getAll(Connection conn) throws SQLException{
		ResultSet rs;
		rs = getRSgetAll(conn);
		ArrayList<Ergebnis> returner = new ArrayList<>();
		while(rs.next()){
			returner.add(new Ergebnis(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
		}
		return returner;
	}
}
