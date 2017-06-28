package de.atiw.sportfest.backend.error;

public class AppException extends Exception{

	private static final long serialVersionUID = 2926424672103981121L;
	
	private Integer status;
	private int code; 
	private String link;

	public AppException() { }
	
	public AppException(int status, int code, String message, String developerMessage, String link) {
		super(message);
		this.status = status;
		this.code = code;
		this.developerMessage = developerMessage;
		this.link = link;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	private String developerMessage;	
	

}
