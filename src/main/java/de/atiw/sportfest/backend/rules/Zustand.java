package de.atiw.sportfest.backend.rules;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Zustand {

    @XmlElement
    String name;

    @XmlElement
    String desc;

    @XmlElement
    String id;

    public Zustand() {
    }

    public Zustand(String name, String desc, String id) {
        this.name = name;
        this.desc = desc;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }
}

