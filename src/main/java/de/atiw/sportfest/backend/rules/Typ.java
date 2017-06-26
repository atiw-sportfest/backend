package de.atiw.sportfest.backend.rules;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Typ {
    
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
}

