package com.arnav.exceptions;

public final class RequestedIdIsNotExists  extends RuntimeException {
	
	private static final long serialVersionUID = 5861310537366287163L;

    public RequestedIdIsNotExists() {
        super();
    }

    public RequestedIdIsNotExists(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RequestedIdIsNotExists(final String message) {
        super(message);
    }

    public RequestedIdIsNotExists(final Throwable cause) {
        super(cause);
    }

}
