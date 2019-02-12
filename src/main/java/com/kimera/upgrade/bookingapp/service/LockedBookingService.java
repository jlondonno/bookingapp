package com.kimera.upgrade.bookingapp.service;

import java.util.Date;

import com.kimera.upgrade.bookingapp.entity.LockedBooking;

/**
 * @author jlondono
 */
public interface LockedBookingService {
	public LockedBooking retrieveAvailableBooking(Date startDate, Date endDate);
	public LockedBooking findLockedBookingByCode(String code);
	public void deleteLockedBooking(LockedBooking lockedBooking);
}
