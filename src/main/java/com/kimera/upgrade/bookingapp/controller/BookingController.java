package com.kimera.upgrade.bookingapp.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kimera.upgrade.bookingapp.entity.Booking;
import com.kimera.upgrade.bookingapp.service.BookingService;

/**
 * @author jlondono
 */
@RestController
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PatchMapping("/api/booking/{code}")
	public ResponseEntity<Void> updateBooking(@PathVariable String code, @RequestBody Map<String, Object> updates) {
		bookingService.updateBooking(code, updates);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/api/booking")
	public ResponseEntity<Booking> saveBooking(Booking booking) {
		Booking createdBooking = bookingService.saveBooking(booking);
		return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
	}
	
	@GetMapping("/api/booking")
	public ResponseEntity<List<Booking>> getBookingByTimePeriod(
			@RequestParam(name = "stardate", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
			@RequestParam(name = "enddate", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate) {
		List<Booking> bookingList = bookingService.getBookingByTimePeriod(startDate, endDate);
		return new ResponseEntity<>(bookingList, HttpStatus.OK);
	}
}
