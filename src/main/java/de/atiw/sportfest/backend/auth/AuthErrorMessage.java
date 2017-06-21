package de.atiw.sportfest.backend.auth;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthErrorMessage {

    private boolean error = true;
    private String message;
    private String exception;

    public AuthErrorMessage(){}

    public AuthErrorMessage(Exception e){
        this.message = e.getMessage();
        this.exception = e.getClass().getName();
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

