package de.atiw.sportfest.backend.resource.jaxb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.atiw.sportfest.backend.resource.jaxb.Disziplin.NotFoundException;
import de.atiw.sportfest.backend.rules.Regel;

@XmlRootElement
public class Klasse {

	@XmlElement
	private Integer kid;

	@XmlElement
	private String name;

	public Klasse() {}
	
	public Klasse(String name){
		this.name = name;
	}

	public Klasse(Integer kid, String name){
		this.kid = kid;
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
	

	
	
    /**
     * Ruft eine Klasse aus der Datenbank ab.
     *
     * Die Connection wird <em>von der Methode geschlossen</em>.
     *
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param did die ID der abzurufenden Klasse
     * @return die gefundene Klasse
     * @throws Klasse.NotFoundException wenn keine Klasse mit dieser ID gefunden wurde.
     */
	public static Klasse getOne(Connection conn, String kid) throws SQLException, NotFoundException {
        return getOne(conn, kid, true);
    }
	
	
	
	
    /**
     * Ruft eine Klasse aus der Datenbank ab.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param kid die ID der abzurufenden Klasse
     * @param close ob die Connection geschlossen werden soll
     * @return die gefundene Klasse
     * @throws Klasse.NotFoundException wenn keine Klasse mit dieser ID gefunden wurde.
     */
	public static Klasse getOne(Connection conn, String kid, boolean close) throws SQLException, NotFoundException {

		ResultSet rs = getRSgetOne(conn, kid);
        Klasse one = null;

		if(rs.next())
            one = new Klasse(rs.getInt(1), rs.getString(2));

        if(close)
            conn.close();

        if(one != null)
            return one;
        else
            throw new NotFoundException(String.format("Keine Klasse zu ID \"%s\" gefunden!", kid));
	}
	
	
	
	
    /**
     * Ruft alle Klassen aus der Datenbank ab.
     *
     * Die Connection wird <em>von der Methode geschlossen</em>.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @return die gefundenen Klassen
     */
	public static ArrayList<Klasse> getAll(Connection conn) throws SQLException{

		ArrayList<Klasse> returner = new ArrayList<>();
		ResultSet rs = getRSgetAll(conn);

		while(rs.next())
            returner.add(new Klasse(rs.getInt(1), rs.getString(2)));

        conn.close();

		return returner;
	}
	
	
	
	
	
	public static void getRSput(Connection conn, Klasse klasse) throws SQLException{	

		PreparedStatement ps = conn.prepareStatement("Call KlasseAnlegen(?)");
		ps.setString(1, klasse.name);

		ResultSet rs = ps.executeQuery();

		if(rs.next()){
			klasse.kid = rs.getInt(1);
		}
	}
	
	public static void getRSputAnmeldung(Connection conn, int sid, int did) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call AnmeldungAnlegen(?, ?);");
		ps.setInt(1, sid);
		ps.setInt(2, did);
		ps.execute();
	}
	
	public static void getRSdelete(Connection conn, String kid) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call KlasseLoeschen(?)");
		ps.setString(1,kid);
		ps.execute();
    }	

    public Integer getKid() {
        return kid;
    }

    public String getName() {
        return name;
    }
}
