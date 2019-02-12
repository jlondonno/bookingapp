package com.kimera.upgrade.bookingapp.exception;

/**
 * @author jlondono
 */
public class InvalidParameterException extends RuntimeException {

	public InvalidParameterException() {
        super();
    }
    public InvalidParameterException(String s) {
        super(s);
    }
    public InvalidParameterException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public InvalidParameterException(Throwable throwable) {
        super(throwable);
    }
}
