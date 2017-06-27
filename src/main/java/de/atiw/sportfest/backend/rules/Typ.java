package de.atiw.sportfest.backend.rules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

@XmlRootElement
public class Typ {

    @XmlElement
    private int tid;

    @XmlElement
    private String name;

    @XmlElement
    private String desc;

    @XmlElement
    private List<Zustand> zustaende;

    @XmlElement
    private Class<?> typ;

    public Typ() {
    }

    public Typ(String typ) throws ClassNotFoundException {
        this("", "", typ, null);
    }

    public Typ(Class<?> typ){
        this("", "", typ, null);
    }
    public Typ(String name, String desc, String typ, List<Zustand> zustaende) throws ClassNotFoundException {
        this(name, desc, resolveJavaType(typ), zustaende);
    }

    public Typ(String name, String desc, Class<?> typ, List<Zustand> zustaende) {
        this.name = name;
        this.desc = desc;
        this.zustaende = zustaende;
        this.typ = typ;
    }

    public static Class<?> resolveJavaType(String typ) throws ClassNotFoundException {

        switch(typ){
            case "String":
                return String.class;
            case "int":
                return int.class;
        }

        return Class.forName(typ);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public List<Zustand> getZustaende() {
        return zustaende;
    }

    public Class<?> getTyp() {
        return typ;
    }

    public void setTyp(Class<?> typ) {
        this.typ = typ;
    }

    public static Typ getOne(Connection con, int tid, boolean close) throws SQLException, NotFoundException, InternalServerErrorException {

        Typ typ = null;
        PreparedStatement prep;
        ResultSet rs;

        try {

            prep = con.prepareStatement("CALL TypAnzeigen(?)");
            prep.setInt(1, tid);

            rs = prep.executeQuery();

            if(rs.next())
                typ = fromResultSet(con, rs);


        } catch( SQLException e){
            throw new InternalServerErrorException(e);
        } finally{

            if(close)
                con.close();

        }

        if(typ == null)
            throw new NotFoundException(String.format("Typ \"%d\" konnte nicht gefunden werden!", tid));

        return typ;
    }

    private static Typ fromResultSet(Connection con, ResultSet rs) throws SQLException, InternalServerErrorException {

        try {

            Typ typ = new Typ();
            int i = 1;

            typ.tid = rs.getInt(i++);
            typ.name = rs.getString(i++);
            typ.desc = rs.getString(i++);
            typ.typ = resolveJavaType(rs.getString(i++));

            return typ;

        } catch (ClassNotFoundException e){
            throw new InternalServerErrorException("Laufzeitklasse für Typ konnte nicht gefunden werden!", e);
        }

    }
}

