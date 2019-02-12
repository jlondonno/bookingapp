package com.kimera.upgrade.bookingapp.exception;

/**
 * @author jlondono
 */
public class TimeoutExpiredException extends RuntimeException {

	public TimeoutExpiredException() {
        super();
    }
    public TimeoutExpiredException(String s) {
        super(s);
    }
    public TimeoutExpiredException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public TimeoutExpiredException(Throwable throwable) {
        super(throwable);
    }
}
