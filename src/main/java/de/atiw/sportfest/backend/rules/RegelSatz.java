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
import javax.ws.rs.NotFoundException;

@XmlRootElement
public class RegelSatz {

    @XmlElement
    private Integer rsatz_id;

    @XmlElement
    private List<Regel> regeln;

    @XmlElement
    private boolean sortiert;

    @XmlTransient
    public List<Regel> getRegeln(){
        return regeln;
    }

    public boolean requiresSorted(){
        return sortiert;
    }

    @XmlTransient
    public Regel getErsteRegel() {

        Regel regel = null;

        // Erste Regel kann entweder am Anfang oder Ende stehen.
        // Erstmal versuchen dort zu finden.
        // Wenn das alles nix bringt, versuchen die erste Regel in der Liste zu finden.

        // Erst am Ende, wie wenn aus der DB,
        // dann am Anfang,
        // dann alles durchsuchen

        if(regeln != null && regeln.size() > 0)
            if( ( regel = regeln.get(regeln.size()-1) ) != null && regel.isFirst())
                ; // regel = regel
            else if( ( regel = regeln.get(0) ) != null && regel.isFirst())
                ; // regel = regel
            else
                for(Regel regel_ : regeln)
                    if(regel_ != null && regel_.isFirst())
                        regel = regel_;

        if(regel != null)
            regel.resetCounters();

        return regel;
    }

    public static List<RegelSatz> getAll(Connection con, int did, boolean close) throws SQLException {

        PreparedStatement prep = con.prepareStatement("CALL RegelsaetzeEinerDisziplinAnzeigen(?)");
        prep.setInt(1, did);

        return getAll(con, prep, close);

    }

    public static List<RegelSatz> getAll(Connection con, PreparedStatement prep, boolean close) throws SQLException {

        List<RegelSatz> ret = new ArrayList<>();
        ResultSet rs;

        try {

            rs = prep.executeQuery();

            while(rs.next())
                ret.add(fromResultSet(con, rs));

            return ret;

        } finally { if(close) con.close(); }

    }

    public static RegelSatz fromResultSet(Connection con, ResultSet rs) throws SQLException {

        RegelSatz rsatz = new RegelSatz();
        int i = 1;

        rsatz.rsatz_id = rs.getInt(i++);
        i++; // disz_id
        rsatz.sortiert = rs.getBoolean(i++);

        rsatz.regeln = Regel.getAll(con, rsatz.rsatz_id, false);

        return rsatz;
    }

}
