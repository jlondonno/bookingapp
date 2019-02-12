package com.kimera.upgrade.bookingapp.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kimera.upgrade.bookingapp.entity.Booking;
import com.kimera.upgrade.bookingapp.entity.LockedBooking;
import com.kimera.upgrade.bookingapp.exception.InvalidLockedBookingCodeException;
import com.kimera.upgrade.bookingapp.exception.InvalidParameterException;
import com.kimera.upgrade.bookingapp.exception.TimeoutExpiredException;
import com.kimera.upgrade.bookingapp.repository.BookingRepository;
import com.kimera.upgrade.bookingapp.repository.LockedBookingRepository;
import com.kimera.upgrade.bookingapp.service.impl.BookingServiceImpl;
import com.kimera.upgrade.bookingapp.service.impl.LockedBookingServiceImpl;

/**
 * @author jlondono
 */
@RunWith(SpringRunner.class)
public class BookingServiceTest {

	@TestConfiguration
	static class BookingServiceImplTestContextConfiguration {
		@Bean
		public BookingService BookingService() {
			return new BookingServiceImpl();
		}

		@Bean
		public LockedBookingService lockedBookingService() {
			return new LockedBookingServiceImpl();
		}
	}

	@Autowired
	private BookingService bookingService;

	@MockBean
	private BookingRepository bookingRepository;

	@Autowired
	private LockedBookingService lockedBookingService;

	@MockBean
	private LockedBookingRepository lockedBookingRepository;

	Date startDate = null;
	Date endDate = null;

	private static final int ONE_MIN = 1 * 60 * 1000;

	@Before
	public void setUp() {
	}

	@Test(expected = InvalidLockedBookingCodeException.class)
	public void saveBookingNoExistCodeTest() {
		Mockito.when(lockedBookingService.findLockedBookingByCode(any(String.class))).thenReturn(null);
		Booking booking = new Booking();
		bookingService.saveBooking(booking);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = TimeoutExpiredException.class)
	public void saveBookingExpiredTimeoutTest() {
		LockedBooking lockedBooking = new LockedBooking();
		lockedBooking.setTimeout(new Date(System.currentTimeMillis() - ONE_MIN).getTime());
		List<LockedBooking> lbList = new ArrayList<>();
		lbList.add(lockedBooking);
		Mockito.when(lockedBookingRepository.findAll(any(org.springframework.data.domain.Example.class)))
				.thenReturn(lbList);

		Booking booking = new Booking();
		booking.setCode("1233DFG");
		bookingService.saveBooking(booking);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = InvalidLockedBookingCodeException.class)
	public void updateBookingCodeNoExistTest() {
		List<Booking> emptyList = new ArrayList<>();
		Mockito.when(bookingRepository.findAll(any(org.springframework.data.domain.Example.class)))
				.thenReturn(emptyList);
		Map<String, Object> params = new HashMap<>();
		bookingService.updateBooking("23eDFDR", params);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = InvalidParameterException.class)
	public void updateBookingInvalidActiveParameterTest() {
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(new Booking());
		Mockito.when(bookingRepository.findAll(any(org.springframework.data.domain.Example.class)))
				.thenReturn(bookingList);
		
		Map<String, Object> params = new HashMap<>();
		params.put("active", "error");
		bookingService.updateBooking("23eDFDR", params);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = InvalidLockedBookingCodeException.class)
	public void updateBookingInvalidNewCodeTest() {
		List<Booking> bookingList = new ArrayList<>();
		bookingList.add(new Booking());
		Mockito.when(bookingRepository.findAll(any(org.springframework.data.domain.Example.class)))
				.thenReturn(bookingList);
		
		List<LockedBooking> emptyList = new ArrayList<>();
		Mockito.when(lockedBookingRepository.findAll(any(org.springframework.data.domain.Example.class)))
				.thenReturn(emptyList);
		
		Map<String, Object> params = new HashMap<>();
		params.put("code", "RFD2fG4");
		bookingService.updateBooking("23eDFDR", params);
	}
	
}
