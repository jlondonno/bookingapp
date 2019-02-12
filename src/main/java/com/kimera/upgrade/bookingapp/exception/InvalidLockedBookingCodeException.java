package com.kimera.upgrade.bookingapp.exception;

/**
 * @author jlondono
 */
public class InvalidLockedBookingCodeException extends RuntimeException {

	public InvalidLockedBookingCodeException() {
        super();
    }
    public InvalidLockedBookingCodeException(String s) {
        super(s);
    }
    public InvalidLockedBookingCodeException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public InvalidLockedBookingCodeException(Throwable throwable) {
        super(throwable);
    }
}
