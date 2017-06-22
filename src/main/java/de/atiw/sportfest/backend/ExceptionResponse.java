package de.atiw.sportfest.backend;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExceptionResponse {

    private boolean error;
    private String message;
    private String exception;
    private Object data;

    public ExceptionResponse(){}

    public ExceptionResponse(Exception e){
        this(e.getMessage(), e.getClass().getName());
    }

    public ExceptionResponse(String message, String exceptionName){
        this(true, message, exceptionName);
    }

    public ExceptionResponse(boolean error, String message, String exceptionName){
        this.error = error;
        this.message = message;
        this.exception = exceptionName;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

