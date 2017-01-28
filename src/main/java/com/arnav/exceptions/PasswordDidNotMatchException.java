package com.arnav.exceptions;

public final class PasswordDidNotMatchException extends RuntimeException{

	private static final long serialVersionUID = 5861310537366287163L;

    public PasswordDidNotMatchException() {
        super();
    }

    public PasswordDidNotMatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PasswordDidNotMatchException(final String message) {
        super(message);
    }

    public PasswordDidNotMatchException(final Throwable cause) {
        super(cause);
    }
}
