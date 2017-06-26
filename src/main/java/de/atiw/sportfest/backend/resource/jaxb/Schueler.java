package de.atiw.sportfest.backend.resource.jaxb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Schueler {

    @XmlElement
	private Integer sid;

    @XmlElement
	private String vorname;

    @XmlElement
	private String name;

    @XmlElement
	private Integer kid;

    @XmlElement
	private Integer gid;

	public Schueler() {}
	
	public Schueler(String vorname, String name, Integer kid, Integer gid) {
		this.vorname = vorname;
		this.name = name;
		this.kid = kid;
		this.gid = gid;
	}

	public Schueler(Integer sid, String vorname, String name, Integer kid, Integer gid) {
		this.sid = sid;
		this.vorname = vorname;
		this.name = name;
		this.kid = kid;
		this.gid = gid;
	}

	public Integer getSid() {
		return sid;
	}

	public String getVorname() {
		return vorname;
	}

	public String getName() {
		return name;
	}

	public Integer getKid() {
		return kid;
	}

	public Integer getGid() {
		return gid;
	}

	public static ResultSet getRSgetAll(Connection conn) throws SQLException {
		return conn.prepareStatement("Call SchuelerPAnzeigen();").executeQuery();

	}

	public static ResultSet getRSgetOne(Connection conn, String sid) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerAnzeigen(?);");
		ps.setString(1, sid);
		return ps.executeQuery();
	}

	public static ResultSet getRSgetAllOfKlasse(Connection conn, String kid) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerEinerKlasseAnzeigen(?);");
		ps.setString(1, kid);
		return ps.executeQuery();
	}
	
	public static ResultSet getRSgetAllOfKlasseAndDisziplin(Connection conn, String kid, String did) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerEinerDisziplinUndKlasseAnzeigen(?, ?);");
		ps.setString(1, kid);
		ps.setString(2, did);
		return ps.executeQuery();
	}

	public static void getRSput(Connection conn, Schueler schueler) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerAnlegen(?, ?, ?, ?);");
		ps.setString(1, schueler.vorname);
		ps.setString(2, schueler.name);
		ps.setInt(3, schueler.kid);
		ps.setInt(4, schueler.gid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			schueler.sid = rs.getInt(1);
		}
	}

	public static void getRSdelete(Connection conn, String sid) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerLoeschen(?);");
		ps.setString(1, sid);
		ps.execute();
	}
}
