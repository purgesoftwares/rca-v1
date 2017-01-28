package com.arnav.exceptions;

@SuppressWarnings("serial")
public class UsernameIsNotAnEmailException extends Throwable {
	
	public UsernameIsNotAnEmailException(final String message) {
        super(message);
    }

}
