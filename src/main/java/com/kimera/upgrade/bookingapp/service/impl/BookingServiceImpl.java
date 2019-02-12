package com.kimera.upgrade.bookingapp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kimera.upgrade.bookingapp.entity.Booking;
import com.kimera.upgrade.bookingapp.entity.LockedBooking;
import com.kimera.upgrade.bookingapp.exception.InvalidLockedBookingCodeException;
import com.kimera.upgrade.bookingapp.exception.InvalidParameterException;
import com.kimera.upgrade.bookingapp.exception.TimeoutExpiredException;
import com.kimera.upgrade.bookingapp.repository.BookingRepository;
import com.kimera.upgrade.bookingapp.service.BookingService;
import com.kimera.upgrade.bookingapp.service.LockedBookingService;

/**
 * @author jlondono
 */
@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private LockedBookingService lockedBookingService;

	@Override
	@Transactional
	public Booking saveBooking(Booking booking) {
		String bookingCode = booking.getCode();
		LockedBooking lb = lockedBookingService.findLockedBookingByCode(bookingCode);
		checkLockedBooking(lb);
		Booking newBooking = bookingRepository.save(booking);
		lockedBookingService.deleteLockedBooking(lb);
		return newBooking;
	}

	@Override
	@Transactional
	public void updateBooking(String code, Map<String, Object> updates) {

		boolean isCancelOperation = false;
		boolean isUpdateOperation = false;
		LockedBooking lb = null;

		Booking booking = findBookingByCode(code);

		if (booking == null) {
			throw new InvalidLockedBookingCodeException(
					"The provided booking code does not exist.  Please verify if your code is correct");
		}

		if (updates.containsKey("code")) {
			String newCode = (String) updates.get("code");
			lb = lockedBookingService.findLockedBookingByCode(newCode);
			checkLockedBooking(lb);

			booking.setCode(newCode);
			booking.setStartDate(lb.getStartDate());
			booking.setEndDate(lb.getEndDate());
			isUpdateOperation = true;
		}

		if (updates.containsKey("active")) {

			String value = updates.get("active").toString();

			if (!"false".equals(value)) {
				throw new InvalidParameterException("Invalid value for active parameter");
			}

			booking.setActive(Boolean.FALSE);
			isCancelOperation = true;
		}

		if (isUpdateOperation || isCancelOperation) {
			bookingRepository.save(booking);
		}

		if (isUpdateOperation) {
			lockedBookingService.deleteLockedBooking(lb);
		}
	}

	private void checkLockedBooking(LockedBooking lb) {
		if (lb == null) {
			throw new InvalidLockedBookingCodeException(
					"The provided booking code does not exist.  Please verify if your code is correct");
		}

		Date date = new Date();

		if (date.getTime() > lb.getTimeout()) {
			throw new TimeoutExpiredException(
					"Timeout has expired. The campsite is not available for the period you choosed. Please search a new time period");
		}
	}

	private Booking findBookingByCode(String code) {
		Booking example = new Booking();
		example.setCode(code);
		Example<Booking> ex = Example.of(example);
		List<Booking> list = bookingRepository.findAll(ex);
		return !list.isEmpty() ? list.get(0) : null;
	}

	@Override
	public List<Booking> getBookingByTimePeriod(Date startDate, Date endDate) {

		if (startDate.equals(endDate)) {
			throw new InvalidParameterException("Start date and end date must be different");
		}

		if (startDate.after(endDate)) {
			throw new InvalidParameterException("End date must be greater than end date");
		}

		if (daysBetween(startDate, endDate) > 30) {
			throw new InvalidParameterException("The requested period time must not be greater that 30 days");
		}

		return bookingRepository.getBookingByTimePeriod(startDate, endDate, new Date().getTime() / 1000l);
	}

	private int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}
