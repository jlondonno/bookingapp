package com.kimera.upgrade.bookingapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.kimera.upgrade.bookingapp.exception.InvalidLockedBookingCodeException;
import com.kimera.upgrade.bookingapp.exception.InvalidParameterException;
import com.kimera.upgrade.bookingapp.exception.LockedBookingNoAvailableException;
import com.kimera.upgrade.bookingapp.exception.MaxBookingDaysExceededException;
import com.kimera.upgrade.bookingapp.exception.TimeoutExpiredException;

/**
 * @author jlondono
 */
@ControllerAdvice
public class BookingAppExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException ex, WebRequest request) {
		ErrorMessage em = new ErrorMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<ErrorMessage>(em, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidLockedBookingCodeException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleTeaException(InvalidLockedBookingCodeException ex, WebRequest request) {
		ErrorMessage em = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorMessage>(em, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LockedBookingNoAvailableException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleTeaExceptionManager(LockedBookingNoAvailableException ex, WebRequest request) {
		ErrorMessage em = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorMessage>(em, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MaxBookingDaysExceededException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleTeaExceptionManager(MaxBookingDaysExceededException ex, WebRequest request) {
		ErrorMessage em = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorMessage>(em, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TimeoutExpiredException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleTeaExceptionManager(TimeoutExpiredException ex, WebRequest request) {
		ErrorMessage em = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorMessage>(em, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleTeaExceptionManager(InvalidParameterException ex, WebRequest request) {
		ErrorMessage em = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorMessage>(em, HttpStatus.BAD_REQUEST);
	}
}
