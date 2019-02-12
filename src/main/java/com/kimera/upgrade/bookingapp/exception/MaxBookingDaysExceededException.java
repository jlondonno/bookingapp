package com.kimera.upgrade.bookingapp.exception;

/**
 * @author jlondono
 */
public class MaxBookingDaysExceededException extends RuntimeException {

	public MaxBookingDaysExceededException() {
        super();
    }
    public MaxBookingDaysExceededException(String s) {
        super(s);
    }
    public MaxBookingDaysExceededException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public MaxBookingDaysExceededException(Throwable throwable) {
        super(throwable);
    }
}
