package com.arnav.exceptions;

@SuppressWarnings("serial")
public class UsernameIsNotAnEmailException extends Throwable {
	
	public UsernameIsNotAnEmailException(String message) {
        super(message);
    }

}
