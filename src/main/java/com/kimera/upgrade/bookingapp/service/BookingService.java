package com.kimera.upgrade.bookingapp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kimera.upgrade.bookingapp.entity.Booking;

/**
 * @author jlondono
 */
public interface BookingService {
	 public Booking saveBooking(Booking booking);
	 public void updateBooking(String code, Map<String, Object> updates);
	 public List<Booking> getBookingByTimePeriod(Date startDate, Date endDate);
}
