package com.kimera.upgrade.bookingapp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kimera.upgrade.bookingapp.entity.LockedBooking;
import com.kimera.upgrade.bookingapp.service.LockedBookingService;

/**
 * @author jlondono
 */
@RestController
public class LockedBookingController {

	@Autowired
	private LockedBookingService lockedBookingService;

	@GetMapping("/api/lockedbooking")
	public @ResponseBody LockedBooking retrieveAvailableBooking(
			@RequestParam(name = "stardate", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
			@RequestParam(name = "enddate", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate) {
		LockedBooking lockedBooking = lockedBookingService.retrieveAvailableBooking(startDate, endDate);
		return lockedBooking;
	}
}
