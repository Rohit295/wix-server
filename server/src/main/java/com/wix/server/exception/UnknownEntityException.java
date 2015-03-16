package com.wix.server.exception;

/**
 * Created by racastur on 16-03-2015.
 */
public class UnknownEntityException extends Exception {

    private static final long serialVersionUID = 1L;

    public UnknownEntityException() {
        super();
    }

    public UnknownEntityException(String message) {
        super(message);
    }

    public UnknownEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownEntityException(Throwable cause) {
        super(cause);
    }

    protected UnknownEntityException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
