package com.kimera.upgrade.bookingapp.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kimera.upgrade.bookingapp.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	@Query(value = 
			  " SELECT LB.ID AS ID, LB.CODE AS CODE, LB.END_DATE AS END_DATE, LB.START_DATE AS START_DATE, "
			+ " TRUE AS ACTIVE, NULL AS CUSTOMER_FULL_NAME, NULL AS CUSTOMER_EMAIL "
			+ " FROM LOCKED_BOOKING LB WHERE "
			+ " (date(LB.START_DATE) BETWEEN ?1 AND ?2 OR "
			+ " date(LB.END_DATE) BETWEEN ?1 AND ?2)"
			+ " AND LB.TIMEOUT > ?3 "
			+ " UNION "
			+ " SELECT B.ID AS ID, B.CODE AS CODE, B.END_DATE AS END_DATE, B.START_DATE AS START_DATE, "
			+ " B.ACTIVE AS ACTIVE, B.CUSTOMER_FULL_NAME AS CUSTOMER_FULL_NAME, B.CUSTOMER_EMAIL AS CUSTOMER_EMAIL"
			+ " FROM BOOKING B WHERE "
			+ " (date(B.START_DATE) BETWEEN ?1 AND ?2 OR "
			+ " date(B.END_DATE) BETWEEN ?1 AND ?2) "
			+ " AND B.ACTIVE = 'TRUE'", nativeQuery = true)
	public List<Booking> getBookingByTimePeriod(Date startDate, Date endDate, long currentTimeStamp);
}

