package com.arnav.exceptions;

public class NoCurrentProviderException extends RuntimeException{
	private static final long serialVersionUID = 5861310537366287163L;

    public NoCurrentProviderException() {
        super();
    }

    public NoCurrentProviderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoCurrentProviderException(final String message) {
        super(message);
    }

    public NoCurrentProviderException(final Throwable cause) {
        super(cause);
    }

}
