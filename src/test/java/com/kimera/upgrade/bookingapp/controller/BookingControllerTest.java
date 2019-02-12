package com.kimera.upgrade.bookingapp.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimera.upgrade.bookingapp.entity.Booking;
import com.kimera.upgrade.bookingapp.service.BookingService;

/**
 * @author jlondono
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean(name = "bookingService")
	private BookingService bookingService;

	@Test
	public void testSaveBooking() throws Exception {
		Booking booking = new Booking();
		booking.setCustomerEmail("jlondono@gmail.com");
		booking.setCustomerFullName("jlondono");
		booking.setStartDate(new Date());
		booking.setEndDate(new Date());
		booking.setCode("4GJ6DWt6");
		given(bookingService.saveBooking(booking)).willReturn(booking);

		mvc.perform(post("/api/booking").contentType(MediaType.APPLICATION_JSON).content(asJsonString(booking)))
				.andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
