package de.atiw.sportfest.backend.rules;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Typ {
    
    private String name, desc;
    private List<Zustand> zustaende;

    private Class<?> typ;

    public Typ() {
    }

    public Typ(String name, String desc, Class<?> typ, List<Zustand> zustaende) {
        this.name = name;
        this.desc = desc;
        this.zustaende = zustaende;
        this.typ = typ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Zustand> getZustaende() {
        return zustaende;
    }

    public void setZustaende(List<Zustand> zustaende) {
        this.zustaende = zustaende;
    }

    public Class<?> getTyp() {
        return typ;
    }

    public void setTyp(Class<?> typ) {
        this.typ = typ;
    }
}

