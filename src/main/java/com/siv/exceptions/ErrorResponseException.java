package com.siv.exceptions;

public class ErrorResponseException {
	
	private String error;
	
	private String message;
	
	public ErrorResponseException(String error, String message){
		this.error = error;
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

}
