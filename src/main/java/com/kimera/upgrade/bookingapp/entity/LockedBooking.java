package com.kimera.upgrade.bookingapp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "LOCKED_BOOKING")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "id", allowGetters = true)
public class LockedBooking implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	
	@NotBlank
	private String code;
	
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date startDate;
	
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date endDate;
	
	@NotNull
	private Long timeout;
	
	public LockedBooking() {
	}
	
	public LockedBooking(Integer id, @NotBlank String code, @NotBlank Date startDate, @NotBlank Date endDate,
			@NotNull Long timeout) {
		this.id = id;
		this.code = code;
		this.startDate = startDate;
		this.endDate = endDate;
		this.timeout = timeout;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
}
