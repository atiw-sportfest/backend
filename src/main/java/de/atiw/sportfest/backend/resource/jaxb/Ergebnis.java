package de.atiw.sportfest.backend.resource.jaxb;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import de.atiw.sportfest.backend.rules.Variable;

@XmlRootElement
public class Ergebnis {

    @XmlElement
    private Integer lid;

    @XmlElement
    private String wert;

    @XmlElement
    private Variable var;

    public Ergebnis(){}

    public static List<Ergebnis> getAll(Connection con, int lid, boolean close) throws SQLException {

        PreparedStatement prep;
        ResultSet rs;
        List<Ergebnis> ergebnisse = new ArrayList<>();

        try {

            prep = con.prepareStatement("CALL ErgebnisseAnzeigen(?)"); // lid
            prep.setInt(1, lid);

            rs = prep.executeQuery();

            while(rs.next())
                ergebnisse.add(fromResultSet(con, rs));

            return ergebnisse;

        } finally { if(close) con.close(); }
    }

    public static List<Ergebnis> getAll(Connection con, String lid, boolean close) throws SQLException {
        return getAll(con, Integer.parseInt(lid), close);
    }

    public static void updateErgebnisse(Connection con, int lid, List<Ergebnis> nevv, boolean close) throws SQLException, NotFoundException, InternalServerErrorException {

        List<Ergebnis> existing = getAll(con, lid, false);

        // deleted = existing - new
        List<Ergebnis> deleted = new ArrayList<>(existing);
        deleted.removeAll(nevv);

        // (real) new = new - existing
        nevv.removeAll(existing);

        delete(con, deleted, false);

        for(Ergebnis add : nevv)
            Ergebnis.create(con, lid, add, false);

    }

    public static Ergebnis create(Connection con, String lid, Ergebnis ergebnis, boolean close) throws SQLException, NotFoundException, InternalServerErrorException {
        return create(con, Integer.parseInt(lid), ergebnis, close);
    }

    public static Ergebnis create(Connection con, int lid, Ergebnis ergebnis, boolean close) throws SQLException, NotFoundException, InternalServerErrorException {

        PreparedStatement prep;
        ResultSet rs;
        int i = 1, var_id;
        
        try {

            if(ergebnis.var.getVarId() == null)
                throw new NotFoundException("Es muss eine Variablen-ID angegeben werden!");

            var_id = Variable.getOne(con, Integer.toString(ergebnis.var.getVarId()), false).getVarId();

            prep = con.prepareStatement("CALL ErgebnisAnlegen(?, ?, ?)"); // lid, wert, var_id

            prep.setInt(i++, lid);
            prep.setString(i++, ergebnis.wert);
            prep.setInt(i++, var_id);

            rs = prep.executeQuery();

            if(rs.next())
                return fromResultSet(con, rs);
            else
                throw new InternalServerErrorException("Die Datenbank hat das erstellte Objekt nicht zurückgegenen ?!");


        } finally { if(close) con.close(); }

    }

    public static void delete(Connection con, List<Ergebnis> ergebnisse, boolean close) throws SQLException, InternalServerErrorException {

        PreparedStatement prep;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL ErgebnisEntfernen(?, ?)"); // lid, vid

            for(Ergebnis ergebnis : ergebnisse){

                if(ergebnis.lid == null || ergebnis.var == null || ergebnis.var.getVarId() == null)
                    throw new InternalServerErrorException("Kann Ergebnis nicht löschen, braucht immer eine LeistungsID und eine Variable mit Variablen-ID!");
                prep.setInt(i++, ergebnis.lid);
                prep.setInt(i++, ergebnis.var.getVarId());

                prep.execute();
                i = 1;

            }

        } finally { if(close) con.close(); }

    }

    private static Ergebnis fromResultSet(Connection con, ResultSet rs) throws SQLException {

        Ergebnis e = new Ergebnis();
        int i = 1;

        e.lid = rs.getInt(i++);
        e.wert = rs.getString(i++);

        e.var = Variable.getOne(con, rs.getString(i++), false);

        return e;

    }

    @XmlTransient
    public Variable getVariable(){
        return var;
    }

    @XmlTransient
    public Object getWert() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return var.getTyp().getValue(wert);
    }
}
