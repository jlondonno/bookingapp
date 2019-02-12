package com.kimera.upgrade.bookingapp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jlondono
 */
@Entity
@Table(name = "BOOKING")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "id", "customerFullName", "customerEmail", "active" }, allowGetters = true)
public class Booking implements java.io.Serializable {

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

	@NotBlank
	private String customerFullName;

	@NotBlank
	private String customerEmail;
	
	private boolean active = Boolean.TRUE;

	public Booking() {
	}

	public Booking(Integer id, @NotBlank String code, @NotBlank Date startDate, @NotBlank Date endDate,
			@NotBlank String customerFullName, @NotBlank String customerEmail, boolean active) {
		this.id = id;
		this.code = code;
		this.startDate = startDate;
		this.endDate = endDate;
		this.customerFullName = customerFullName;
		this.customerEmail = customerEmail;
		this.active = active;
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

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
