package com.kimera.upgrade.bookingapp.exception;

/**
 * @author jlondono
 */
public class LockedBookingNoAvailableException extends RuntimeException {

	public LockedBookingNoAvailableException() {
        super();
    }
    public LockedBookingNoAvailableException(String s) {
        super(s);
    }
    public LockedBookingNoAvailableException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public LockedBookingNoAvailableException(Throwable throwable) {
        super(throwable);
    }
}
