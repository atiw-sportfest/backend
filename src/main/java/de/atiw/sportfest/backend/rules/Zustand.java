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

@XmlRootElement
public class Zustand {

    @XmlElement
    private int zid;

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

    public static List<Zustand> getAll(Connection con, int vid, boolean close) throws SQLException {

        List<Zustand> zustaende = new ArrayList<>();
        PreparedStatement prep;
        ResultSet rs;

        try {

           prep = con.prepareStatement("CALL ZustaendeEinesTypsAnzeigen(?)");
           prep.setInt(1, vid);

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

