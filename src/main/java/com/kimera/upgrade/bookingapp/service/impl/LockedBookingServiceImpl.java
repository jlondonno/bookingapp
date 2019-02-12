package com.kimera.upgrade.bookingapp.service.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kimera.upgrade.bookingapp.entity.LockedBooking;
import com.kimera.upgrade.bookingapp.exception.InvalidParameterException;
import com.kimera.upgrade.bookingapp.exception.LockedBookingNoAvailableException;
import com.kimera.upgrade.bookingapp.repository.LockedBookingRepository;
import com.kimera.upgrade.bookingapp.service.LockedBookingService;

/**
 * @author jlondono
 */
@Service
public class LockedBookingServiceImpl implements LockedBookingService {

	private static final int FIVE_MINS = 5 * 60 * 1000;
	@Autowired
	private LockedBookingRepository lockedBookingRepository;

	@Override
	@Transactional
	public LockedBooking retrieveAvailableBooking(Date startDate, Date endDate) {

		if (startDate.equals(endDate)) {
			throw new InvalidParameterException("start date and end date must be different");
		}

		if (startDate.after(endDate)) {
			throw new InvalidParameterException("end date must be greater than end date");
		}

		int dateMargin = startDate.compareTo(addOneDayToCurrentDate());

		if (dateMargin == -1) {
			throw new InvalidParameterException(
					"start date is invalid.  You have to book at least one day before arriving");
		}

		if (daysBetween(new Date(), startDate) > 30) {
			throw new InvalidParameterException(
					"Invalid start date.  You can only book up to one month before arrival");
		}

		if (daysBetween(startDate, endDate) > 3) {
			throw new InvalidParameterException(
					"Invalid requested period time.  You can only reserve for a maximum of 3 days");
		}

		Date currentTime = new Date();
		Collection<LockedBooking> listLockedBooking = lockedBookingRepository.findAvailableLockedBooking(startDate,
				endDate, currentTime.getTime() / 1000l);

		if (!listLockedBooking.isEmpty()) {
			throw new LockedBookingNoAvailableException("No available campsite for the requested time period");
		}

		LockedBooking lb = new LockedBooking();
		lb.setStartDate(startDate);
		lb.setEndDate(endDate);
		// Set timeout to five mins. It means the campsite is going to be
		// locked for five mins for that specific time period
		lb.setTimeout((new java.util.Date().getTime() + (FIVE_MINS)) / 1000l);
		lb.setCode(getRandomHexString());
		lb = lockedBookingRepository.save(lb);
		return lb;
	}

	private int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	private Date addOneDayToCurrentDate() {
		Date dt = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		dt = cal.getTime();
		return dt;
	}

	private String getRandomHexString() {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		while (sb.length() < 7) {
			sb.append(Integer.toHexString(r.nextInt()));
		}
		return sb.toString().substring(0, 7);
	}

	@Override
	public LockedBooking findLockedBookingByCode(String code) {
		LockedBooking example = new LockedBooking();
		example.setCode(code);
		Example<LockedBooking> ex = Example.of(example);
		List<LockedBooking> list = lockedBookingRepository.findAll(ex);
		return !list.isEmpty() ? list.get(0) : null;
	}

	@Override
	public void deleteLockedBooking(LockedBooking lockedBooking) {
		lockedBookingRepository.delete(lockedBooking);
	}
}
