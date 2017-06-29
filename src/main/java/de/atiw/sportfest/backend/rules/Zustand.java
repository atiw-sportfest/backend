package de.atiw.sportfest.backend.rules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

@XmlRootElement
public class Zustand {

    @XmlElement
    private Integer zid;

    @XmlElement
    private String name;

    @XmlElement
    private String desc;

    @XmlElement
    private String value;

    public Zustand() {
    }

    public Zustand(String name, String desc, String value) {
        this.name = name;
        this.desc = desc;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }

    public static List<Zustand> getAll(Connection con, int tid, boolean close) throws SQLException {

        List<Zustand> zustaende = new ArrayList<>();
        PreparedStatement prep;
        ResultSet rs;

        try {

           prep = con.prepareStatement("CALL ZustaendeEinesTypsAnzeigen(?)");
           prep.setInt(1, tid);

           rs = prep.executeQuery();

           while(rs.next())
               zustaende.add(fromResultSet(rs));

        } catch (SQLException e){
            throw new InternalServerErrorException(e);
        } finally {

            if(close)
                con.close();
        }


        return zustaende;
    }

    public static Zustand getOne(Connection con, int zid, boolean close) throws SQLException, NotFoundException {

        Zustand zustand = null;
        PreparedStatement prep;
        ResultSet rs;

        try {

            prep = con.prepareStatement("CALL ZustandAnzeigen(?)");
            prep.setInt(1, zid);

            rs = prep.executeQuery();

            if(rs.next())
                zustand = fromResultSet(rs);

        } catch (SQLException e){
            throw new InternalServerErrorException(e);
        } finally {

            if(close)
                con.close();
        }

        if(zustand == null)
            throw new NotFoundException(String.format("Zustand %d nicht gefunden.", zid));


        return zustand;
    }

    public static Zustand getOne(Connection con, String zid, boolean close) throws SQLException, NotFoundException {
        return getOne(con, Integer.parseInt(zid), close);
    }

    public static void createOrEdit(Connection con, int tid, List<Zustand> zustaende, boolean close) throws SQLException, InternalServerErrorException {

        try {

            List<Zustand> diff = getAll(con, tid, false);
            diff.removeAll(zustaende);

            // Zustände die nicht mehr vorhanden sind löschen
            for(Zustand zustand : diff)
                delete(con, zustand, false);

            for(Zustand zustand : zustaende)
                createOrEdit(con, tid, zustand, false);

        } finally { if(close) con.close(); }

    }

    public static void createOrEdit(Connection con, int tid, Zustand zustand, boolean close) throws SQLException, InternalServerErrorException {

        Zustand orig;

        if(zustand.zid == null)
            create(con, tid, zustand, close);
        else if ( (orig = getOne(con, zustand.zid, false)) != null)
            edit(con, zustand, orig, close);
        else
            create(con, tid, zustand, close);

    }

    public static void createOrEdit(Connection con, int tid, Zustand zustand) throws SQLException, InternalServerErrorException {
        createOrEdit(con, tid, zustand, true);
    }

    public static Zustand create(Connection con, int tid, Zustand zustand, boolean close) throws SQLException, InternalServerErrorException {

        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL ZustandAnlegen(?, ?, ?, ?)"); // typ_id, tzst_name, tzst_descr, tzst_val

            prep.setInt(i++, tid);
            prep.setString(i++, zustand.name);
            prep.setString(i++, zustand.desc);
            prep.setString(i++, zustand.value);

            rs = prep.executeQuery();

            if(rs.next())
                return fromResultSet(rs);
            else
                throw new InternalServerErrorException("Die Datenbank hat das erstellte Objekt nicht zurückggegeben ?!");

        } finally { if(close) con.close(); }
    }

    public static Zustand create(Connection con, int tid, Zustand zustand) throws SQLException, InternalServerErrorException {
        return create(con, tid, zustand, true);
    }

    public static Zustand edit(Connection con, Zustand zustand, Zustand orig, boolean close) throws SQLException, InternalServerErrorException {
        
        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL ZustandBearbeiten(?, ?, ?, ?)"); // tzst_id, tzst_name, tzst_desc, tzts_value

            prep.setInt(i++, orig.zid); // never change id
            prep.setString(i++, zustand.name != null ? zustand.name : orig.name);
            prep.setString(i++, zustand.desc != null ? zustand.desc : orig.desc);
            prep.setString(i++, zustand.value != null ? zustand.value : orig.value);

            rs = prep.executeQuery();

            if(rs.next())
                return fromResultSet(rs);
            else
                throw new InternalServerErrorException("Die Datenbank hat das geänderte Objekt nicht zurückggegeben ?!");

        } finally { if(close) con.close(); }
    }

    public static void delete(Connection con, Zustand zustand, boolean close) throws SQLException, NotFoundException, WebApplicationException {

        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            if(zustand.zid == null)
                throw new InternalServerErrorException("Zu löschender Zustand hat keine ID ?!");

            getOne(con, zustand.zid, false); // Triggers NotFoundException

            prep = con.prepareStatement("CALL ZustandEntfernen(?)"); // tzst_id
            prep.setInt(i++, zustand.zid);

            prep.execute();

        } finally { if(close) con.close(); }

    }

    public static Zustand fromResultSet(ResultSet rs) throws SQLException {

        Zustand zustand = new Zustand();
        int i = 1;

        zustand.zid = rs.getInt(i++);
        i++; // tid
        zustand.name = rs.getString(i++);
        zustand.desc = rs.getString(i++);
        zustand.value = rs.getString(i++);


        return zustand;
    }

}

