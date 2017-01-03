package com.siv.exceptions;

public final class AllPropertyRequiredException extends RuntimeException{
	
	private static final long serialVersionUID = 5861310537366287163L;

    public AllPropertyRequiredException() {
        super();
    }

    public AllPropertyRequiredException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AllPropertyRequiredException(final String message) {
        super(message);
    }

    public AllPropertyRequiredException(final Throwable cause) {
        super(cause);
    }

}
