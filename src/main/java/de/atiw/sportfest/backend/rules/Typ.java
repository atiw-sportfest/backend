package de.atiw.sportfest.backend.rules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@XmlRootElement
public class Typ {

    @XmlElement
    private Integer tid;

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

    @XmlTransient
    public Integer getTypID(){
        return tid;
    }

    public static Typ getOne(Connection con, int tid, boolean close) throws SQLException, NotFoundException, InternalServerErrorException {

        PreparedStatement prep;
        ResultSet rs;

        try {

            prep = con.prepareStatement("CALL TypAnzeigen(?)");
            prep.setInt(1, tid);

            rs = prep.executeQuery();

            if(rs.next())
                return fromResultSet(con, rs);
            else
                throw new NotFoundException(String.format("Typ \"%d\" konnte nicht gefunden werden!", tid));

        } finally { if(close) con.close(); }

    }

    public static Typ getOne(Connection con, String tid) throws SQLException, NotFoundException, InternalServerErrorException {
        return getOne(con, Integer.parseInt(tid), true);
    }

    public static List<Typ> getAll(Connection con, boolean close) throws SQLException {

        List<Typ> typen = new ArrayList<>();
        ResultSet rs;

        try {

            rs = con.prepareStatement("CALL TypenAnzeigen()").executeQuery();

            while(rs.next())
                typen.add(fromResultSet(con, rs));

            return typen;

        } finally { if(close) con.close(); }

    }

    public static List<Typ> getAll(Connection con) throws SQLException {
        return getAll(con, true);
    }

    public static Typ create(Connection con, Typ typ, boolean close) throws SQLException, InternalServerErrorException, BadRequestException {

        if(typ.name == null || typ.typ == null)
            throw new BadRequestException("Name und Typ müssen immer gesetzt sein!");

        try {
            Class.forName(typ.typ.getName());
        } catch (ClassNotFoundException e){
            throw new BadRequestException(String.format("Laufzeitklasse für Typ \"%s\" konnte nicht gefunden werden!", typ.typ.getName()));
        }

        PreparedStatement prep;
        ResultSet rs;
        Typ created;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL TypAnlegen(?, ?, ?)"); // typ_name, typ_descr, typ_typ

            prep.setString(i++, typ.name);
            prep.setString(i++, typ.desc);
            prep.setString(i++, typ.typ.getName());

            rs = prep.executeQuery();

            if(rs.next())
                created = fromResultSet(con, rs);
            else
                throw new InternalServerErrorException("Die Datenbank hat den erstellten Typ nicht zurückgegeben ?!");

            return getOne(con, created.tid, false);

        } finally { if(close) con.close(); }

    }

    public static Typ create(Connection con, Typ typ) throws SQLException {
        return create(con, typ, true);
    }

    public static Typ edit(Connection con, String tid, Typ typ, boolean close) throws SQLException, NotFoundException {

        Typ orig, changed;
        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            orig = getOne(con, Integer.parseInt(tid), false);

            prep = con.prepareStatement("CALL TypBearbeiten(?, ?, ?, ?)"); // typ_id, typ_name, typ_desc, typ_typ

            prep.setInt(i++, orig.tid); // never change id

            prep.setString(i++, typ.name != null ? typ.name : orig.name);
            prep.setString(i++, typ.desc != null ? typ.desc : orig.desc);
            prep.setString(i++, typ.typ != null ? typ.typ.getName() : orig.typ.getName());

            rs = prep.executeQuery();

            if(rs.next())
                changed = fromResultSet(con, rs);
            else
                throw new InternalServerErrorException("Die Datenbank hat den veränderten Typ nichtzurückgegen ?!");

            return getOne(con, orig.tid, false);


        } finally { if(close) con.close(); }
    }

    public static Typ edit(Connection con, String tid, Typ typ) throws SQLException, NotFoundException {
        return edit(con, tid, typ, true);
    }

    public static void delete(Connection con, String tid, boolean close) throws SQLException, WebApplicationException {

        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            getOne(con, Integer.parseInt(tid), false);

            prep = con.prepareStatement("SELECT var_id FROM variable WHERE typ_id = ?");
            prep.setInt(i++, Integer.parseInt(tid));

            rs = prep.executeQuery();

            if(rs.next()){

                StringBuilder sb = new StringBuilder("Der Typ wird noch in folgenden/r Variablen verwendet: ").append(rs.getInt(1));

                while(rs.next())
                    sb.append(", ").append(rs.getInt(1));

                throw new WebApplicationException(sb.toString(), Response.Status.CONFLICT);

            } // else continue

            i = 1;

            prep = con.prepareStatement("CALL TypEntfernen(?)");
            prep.setInt(i++, Integer.parseInt(tid));

            prep.execute();

        } finally { if(close) con.close(); }
    }

    public static void delete(Connection con, String tid) throws SQLException, WebApplicationException {
        delete(con, tid, true);
    }

    private static Typ fromResultSet(Connection con, ResultSet rs) throws SQLException, InternalServerErrorException {

        try {

            Typ typ = new Typ();
            int i = 1;

            typ.tid = rs.getInt(i++);
            typ.name = rs.getString(i++);
            typ.desc = rs.getString(i++);
            typ.typ = resolveJavaType(rs.getString(i++));

            typ.zustaende = Zustand.getAll(con, typ.tid, false);

            return typ;

        } catch (ClassNotFoundException e){
            throw new InternalServerErrorException("Laufzeitklasse für Typ konnte nicht gefunden werden!", e);
        }

    }
}

