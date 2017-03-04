package com.arnav.controllers.coupon;

/**
 * Created by Shankar on 1/8/2017.
 */
@SuppressWarnings("serial")
public class CustomNotFoundException extends Throwable {
    private static final long serialVersionUID = 5861310537366287163L;

    public CustomNotFoundException() {
        super();
    }

    public CustomNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CustomNotFoundException(final String message) {
        super(message);
    }

    public CustomNotFoundException(final Throwable cause) {
        super(cause);
    }
}
