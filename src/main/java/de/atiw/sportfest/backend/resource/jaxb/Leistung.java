package de.atiw.sportfest.backend.resource.jaxb;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.codehaus.commons.compiler.CompileException;

import de.atiw.sportfest.backend.rules.Variable;

@XmlRootElement
public class Leistung {

    @XmlElement
	private Integer lid;

    @XmlElement
	private Integer did;

    @XmlElement
	private Integer kid;

    @XmlElement
	private Integer sid;

    @XmlElement
    private List<Ergebnis> ergebnisse;

    @XmlElement
    private Integer punkte;

    @XmlElement
    private Integer versus;

	public Leistung() {}
	
    public Leistung(Integer lid, Integer did, Integer kid, Integer sid){
    	this.lid = lid;
    	this.did = did;
    	this.kid = kid;
    	this.sid = sid;
    }

	public static ResultSet getRSgetAll(Connection conn) throws SQLException{		
		return conn.prepareStatement("Call LeistungenAnzeigen();").executeQuery();
		
	}
	
	public static ResultSet getRSgetOne(Connection conn, String lid) throws SQLException{
		PreparedStatement ps = conn.prepareStatement("Call LeistungAnzeigen(?)");
		ps.setString(1, lid);
		return ps.executeQuery();
	}
	
	public static void putSchuelerleistung(Connection conn, Leistung leistung) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call LeistungSchuelerAnlegen(?,?,?)");
		ps.setInt(1, leistung.did);
		ps.setInt(2, leistung.sid);
        ps.setNull(3, Types.TIMESTAMP);
		ps.executeQuery();
	}
	
	public static void putKlassenleistung(Connection conn, Leistung leistung) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call LeistungKlasseAnlegen(?,?,?)");
		ps.setInt(1, leistung.did);
		ps.setInt(2, leistung.kid);
        ps.setNull(3, Types.TIMESTAMP);
		ps.executeQuery();
	}
	
	public static void post(Connection conn, Leistung leistung) throws SQLException {	
		PreparedStatement ps = conn.prepareStatement("Call LeistungAendern(?,?,?,?,?,?)");
		ps.setInt(1, leistung.lid);
		ps.setInt(2, leistung.did);
		ps.setInt(3, leistung.kid);
		ps.setInt(4, leistung.sid);
        ps.setNull(5, Types.TIMESTAMP);
		ps.executeQuery();
	}
	
	public static Leistung getOne(Connection conn, String lid, boolean close) throws SQLException, NotFoundException {
        return getOne(conn, Integer.parseInt(lid), close);
    }

	public static Leistung getOne(Connection conn, int lid, boolean close) throws SQLException, NotFoundException {

        PreparedStatement prep;
		ResultSet rs;

        try {

            prep = conn.prepareStatement("CALL LeistungAnzeigen(?)"); // lid
            prep.setInt(1, lid);

            rs = prep.executeQuery();
            
            if(rs.next())
                return fromResultSet(conn, rs);
            else
                throw new NotFoundException(String.format("Keine Leistung zu ID \"%s\" gefunden!", lid));

        } finally { if(close) conn.close(); }

	}

	public static List<Leistung> getAll(Connection conn, boolean close) throws SQLException {
        return getAll(conn, "CALL LeistungenAnzeigen()", close);
	}

    public static List<Leistung> getAllDisziplin(Connection con, String did, boolean close) throws SQLException {
        return getAllDisziplin(con, Integer.parseInt(did), close);
    }
    public static List<Leistung> getAllDisziplin(Connection con, int did, boolean close) throws SQLException {

        PreparedStatement prep = con.prepareStatement("CALL LeistungenEinerDisziplinAnzeigen(?)"); // kid
        prep.setInt(1, did);

        return getAll(con, prep, close);

    }

    public static List<Leistung> getAllKlasse(Connection con, int kid, boolean close) throws SQLException {

        PreparedStatement prep = con.prepareStatement("CALL LeistungenEinerKlasseAnzeigen(?)"); // kid
        prep.setInt(1, kid);

        return getAll(con, prep, close);

    }

    public static List<Leistung> getAllSchueler(Connection con, int sid, boolean close) throws SQLException {

        PreparedStatement prep = con.prepareStatement("CALL LeistungenEinesSchuelersAnzeigen(?)"); // sid
        prep.setInt(1, sid);

        return getAll(con, prep, close);

    }

    public static List<Leistung> getAll(Connection con, String query, boolean close) throws SQLException {
        return getAll(con, con.prepareStatement(query), close);
    }

    public static List<Leistung> getAll(Connection con, PreparedStatement prep) throws SQLException {
        return getAll(con, prep, true);
    }

    public static List<Leistung> getAll(Connection con, PreparedStatement prep, boolean close) throws SQLException {

        ResultSet rs;
        List<Leistung> leistungen = new ArrayList<>();

        try {

            rs = prep.executeQuery();

            while(rs.next())
                leistungen.add(fromResultSet(con, rs));

            return leistungen;

        } finally { if(close) con.close(); }

    }

    public static Leistung edit(Connection con, String lid, Leistung leistung, boolean close) throws SQLException, NotFoundException {
        return edit(con, lid, leistung, getOne(con, lid, false), close);
    }

    public static Leistung edit(Connection con, String lid, Leistung leistung, Leistung orig, boolean close) throws SQLException, NotFoundException {

        PreparedStatement prep;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL LeistungBearbeiten(?, ?, ?, ?, ?)"); // lid, did, kid, sid, tstp

            prep.setInt(i++, orig.lid);
            prep.setInt(i++, leistung.did != null ? leistung.did : orig.did);
            prep.setInt(i++, leistung.kid != null ? leistung.kid : orig.kid);
            prep.setInt(i++, leistung.sid != null ? leistung.sid: orig.sid);
            prep.setNull(i++, Types.TIMESTAMP);
            prep.setInt(i++, leistung.versus != null ? leistung.versus : orig.versus);

            Ergebnis.updateErgebnisse(con, orig.lid, leistung.ergebnisse, false);

            prep.execute();

            return getOne(con, lid, false);

        } finally { if(close) con.close(); }

    }

    public static Leistung create(Connection con, Leistung ls, int versus, boolean close) throws SQLException, InternalServerErrorException, BadRequestException {
        ls.versus = versus;
        return create(con, ls, close);
    }

    public static Leistung create(Connection con, Leistung ls, boolean close) throws SQLException, InternalServerErrorException, BadRequestException {

        PreparedStatement prep;
        ResultSet rs;
        Leistung created = null;
        int i = 1;

        try {

            ls.verify(); // throws BadRequestException

            prep = con.prepareStatement("CALL LeistungAnlegen(?, ?, ?, ?, ?)"); // did, kid, sid, tstp, versus

            prep.setInt(i++, ls.did);
            prep.setInt(i++, ls.kid);
            if(ls.sid == null){
                prep.setNull(i++, Types.INTEGER);
            }else{
                prep.setInt(i++, ls.sid);
            }
            prep.setNull(i++, Types.TIMESTAMP);

            if(ls.versus == null)
                prep.setNull(i++, Types.INTEGER);
            else
                prep.setInt(i++, ls.versus);

            rs = prep.executeQuery();

            if(rs.next())
                created = fromResultSet(con, rs);
            else
                throw new InternalServerErrorException("Die Datenbank hat das erstellte Objekt nicht zurückgegeben ?!");

            for(Ergebnis ergebnis : ls.ergebnisse)
                Ergebnis.create(con, Integer.toString(created.lid), ergebnis, false);

            return getOne(con, created.lid, false);

        } catch (NotFoundException e) {
            throw new InternalServerErrorException("Das erstellte Objekt konnte in der Datenbank nicht gefunden werden!");
        } finally { if(close) con.close(); }

    }

	public static void delete(Connection conn, String lid, boolean close) throws SQLException{

        PreparedStatement ps;

        try {

            ps = conn.prepareStatement("Call LeistungEntfernen(?)");

            ps.setString(1, lid);
            ps.execute();

        } finally { if(close) conn.close(); }
    }

    private static Leistung fromResultSet(Connection con, ResultSet rs) throws SQLException, InternalServerErrorException {

        Leistung l = new Leistung();
        List<Variable> vars = new ArrayList<>();
        List<Object> vals = new ArrayList<>();
        Disziplin d;
        int i = 1;

        l.lid = rs.getInt(i++);
        l.did = rs.getInt(i++);
        l.kid = rs.getInt(i++);

        l.sid = rs.getInt(i++);
        l.sid = rs.wasNull() ? null : l.sid;

        i++; //timestamp

        l.versus = rs.getInt(i++);
        l.versus = rs.wasNull() ? null : l.versus;

        l.ergebnisse = Ergebnis.getAll(con, Integer.toString(l.lid), false);

        vars.add(Variable.Geschlecht);

        if(l.sid != null)
            vals.add(Schueler.getOne(con, Integer.toString(l.sid), false).getGeschlecht());
        else
            vals.add("");

        d = Disziplin.getOne(con, l.did, false);

        try {

            for(Ergebnis ergebnis : l.ergebnisse){
                vals.add(ergebnis.getWert());
                vars.add(ergebnis.getVariable());
            }

            if(d.getErsteRegel() != null && l.ergebnisse.size() > 0)
                l.punkte = d.getErsteRegel().evaluate(vars, vals);

        } catch(NoSuchMethodException | IllegalAccessException e){
            throw new InternalServerErrorException("Konnte Wert nicht übersetzen!", e);
        } catch(CompileException e){
            throw new InternalServerErrorException("Konnte Regel nicht auswerten!", e);
        } catch(InvocationTargetException e){
            throw new InternalServerErrorException(e);
        }

        return l;

    }

    public void verify() throws BadRequestException {
        if(ergebnisse == null && did == null && kid == null)
            throw new BadRequestException("Ergebnisse, Klassen-Id und Disziplin-ID müssen immer gesetzt sein!");
    }

    @XmlTransient
    public Integer getPunkte(){
        return punkte;
    }
}
