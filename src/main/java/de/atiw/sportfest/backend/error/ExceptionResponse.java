package de.atiw.sportfest.backend.error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExceptionResponse {

    @XmlElement
    private Boolean error = true;

    @XmlElement
    private String exName;

    @XmlElement
    private Exception exception;

    public ExceptionResponse(){}

    public ExceptionResponse(Exception ex){
        this.exName = ex.getClass().getName();
        this.exception = ex;
    }
}

