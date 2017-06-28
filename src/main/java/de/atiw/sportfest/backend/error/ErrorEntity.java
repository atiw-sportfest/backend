package de.atiw.sportfest.backend.error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorEntity {

    @XmlElement
    private Boolean error = true;

    @XmlElement
    private String message;

    @XmlElement
    private Response response;

    public ErrorEntity(){}

    public ErrorEntity(WebApplicationException ex){
        this.response = ex.getResponse();
        this.message = ex.getMessage();
    }

    public static Response fromWebAppEx(WebApplicationException ex){
        return Response.fromResponse(ex.getResponse()).entity(new ErrorEntity(ex)).type(MediaType.APPLICATION_JSON).build();
    }

}

