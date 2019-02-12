package com.kimera.upgrade.bookingapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kimera.upgrade.bookingapp.entity.LockedBooking;
import com.kimera.upgrade.bookingapp.exception.InvalidParameterException;
import com.kimera.upgrade.bookingapp.exception.LockedBookingNoAvailableException;
import com.kimera.upgrade.bookingapp.repository.LockedBookingRepository;
import com.kimera.upgrade.bookingapp.service.impl.LockedBookingServiceImpl;

/**
 * @author jlondono
 */
@RunWith(SpringRunner.class)
public class LockedBookingServiceTest {

	@TestConfiguration
	static class LockedBookingServiceImplTestContextConfiguration {
		@Bean
		public LockedBookingService lockedBookingService() {
			return new LockedBookingServiceImpl();
		}
	}

	@Autowired
	private LockedBookingService lockedBookingService;

	@MockBean
	private LockedBookingRepository lockedBookingRepository;
	
	Date startDate = null;
	Date endDate = null;

	@Before
	public void setUp() {
		startDate = addDays(new Date(), 1);
		endDate = addDays(new Date(), 4);
		
		LockedBooking lockedBooking = new LockedBooking();
		lockedBooking.setStartDate(startDate);
		lockedBooking.setEndDate(endDate);
		Mockito.when(lockedBookingRepository.save(any(LockedBooking.class))).thenReturn(lockedBooking);
	}

	@Test(expected = InvalidParameterException.class)
	public void retrieveAvailableBookingSameParametersTest() {
		lockedBookingService.retrieveAvailableBooking(new Date(), new Date());
	}

	@Test(expected = InvalidParameterException.class)
	public void retrieveAvailableBookingStartDateGreaterThatEndDateTest() {
		lockedBookingService.retrieveAvailableBooking(addDays(new Date(), 1), new Date());
	}

	@Test(expected = InvalidParameterException.class)
	public void retrieveAvailableBookingOneDayBeforeArrivingTest() {
		lockedBookingService.retrieveAvailableBooking(new Date(), addDays(new Date(), 2));
	}

	@Test(expected = InvalidParameterException.class)
	public void retrieveAvailableBookingUpto30DaysTest() {
		lockedBookingService.retrieveAvailableBooking(new Date(), addDays(new Date(), 31));
	}

	@Test(expected = InvalidParameterException.class)
	public void retrieveAvailableBookingMax3Days() {
		lockedBookingService.retrieveAvailableBooking(addDays(new Date(), 1), addDays(new Date(), 5));
	}

	@Test(expected = LockedBookingNoAvailableException.class)
	public void retrieveAvailableBookingNoAvailableTest() {
		
		List<LockedBooking> lockedBookingList = new ArrayList<>();
		LockedBooking myBooking = new LockedBooking(1, "ASDF1122", new Date(), new Date(), 1549762484586L);
		lockedBookingList.add(myBooking);

		Mockito.when(lockedBookingRepository.findAvailableLockedBooking(any(Date.class), any(Date.class), anyLong()))
				.thenReturn(lockedBookingList);
		
		lockedBookingService.retrieveAvailableBooking(addDays(new Date(), 3), addDays(new Date(), 6));
	}

	@Test
	public void retrieveAvailableBookingTest() {
		List<LockedBooking> emptyLockedBookingList = new ArrayList<>();
		Mockito.when(lockedBookingRepository.findAvailableLockedBooking(any(Date.class), any(Date.class),
				anyLong())).thenReturn(emptyLockedBookingList);
		
		LockedBooking lb = lockedBookingService.retrieveAvailableBooking(startDate, endDate);
		assertNotNull(lb);
		assertEquals(lb.getStartDate(), startDate);
		assertEquals(lb.getEndDate(), endDate);
	}

	private Date addDays(Date dt, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, days);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		dt = cal.getTime();
		return dt;
	}
}
