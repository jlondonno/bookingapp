package com.kimera.upgrade.bookingapp.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kimera.upgrade.bookingapp.entity.LockedBooking;

/**
 * @author jlondono
 */
@Repository
public interface LockedBookingRepository extends JpaRepository<LockedBooking, Long> {
	
	@Query(value = 
			" SELECT LB.ID AS ID, LB.CODE AS CODE, LB.END_DATE AS END_DATE, LB.START_DATE AS START_DATE, LB.TIMEOUT AS TIMEOUT "
			+ " FROM LOCKED_BOOKING LB WHERE "
			+ " ((?1 >= date(LB.START_DATE) AND ?1 < date(LB.END_DATE)) OR "
			+ " (?2 > date(LB.START_DATE) AND ?2 <= date(LB.END_DATE)) OR "
			+ " (?1 < date(LB.START_DATE) AND ?2 > date(LB.END_DATE))) "
			+ " AND LB.TIMEOUT > ?3 "
			+ " UNION "
			+ " SELECT B.ID AS ID, B.CODE AS CODE, B.END_DATE AS END_DATE, B.START_DATE AS START_DATE, NULL AS TIMEOUT "
			+ " FROM BOOKING B WHERE "
			+ " ((?1 >= date(B.START_DATE) AND ?1 < date(B.END_DATE)) OR "
			+ " (?2 > date(B.START_DATE) AND ?2 <= date(B.END_DATE)) OR "
			+ " (?1 < date(B.START_DATE) AND ?2 > date(B.END_DATE))) "
			+ " AND B.ACTIVE = 'TRUE'", nativeQuery = true)
	public Collection<LockedBooking> findAvailableLockedBooking(Date startDate, Date endDate, long currentTimeStamp);
}
