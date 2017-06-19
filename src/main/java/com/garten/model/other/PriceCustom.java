package com.garten.model.other;

import java.math.BigDecimal;

public class PriceCustom {
	
	private Integer id;
	private BigDecimal attendancePrice;
	private Integer attendanceApply;
	private BigDecimal monitoringPrice;
	private Integer monitoringApply;
	public PriceCustom() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PriceCustom(Integer id, BigDecimal attendancePrice, Integer attendanceApply, BigDecimal monitoringPrice,
			Integer monitoringApply) {
		super();
		this.id = id;
		this.attendancePrice = attendancePrice;
		this.attendanceApply = attendanceApply;
		this.monitoringPrice = monitoringPrice;
		this.monitoringApply = monitoringApply;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getAttendancePrice() {
		return attendancePrice;
	}
	public void setAttendancePrice(BigDecimal attendancePrice) {
		this.attendancePrice = attendancePrice;
	}
	public Integer getAttendanceApply() {
		return attendanceApply;
	}
	public void setAttendanceApply(Integer attendanceApply) {
		this.attendanceApply = attendanceApply;
	}
	public BigDecimal getMonitoringPrice() {
		return monitoringPrice;
	}
	public void setMonitoringPrice(BigDecimal monitoringPrice) {
		this.monitoringPrice = monitoringPrice;
	}
	public Integer getMonitoringApply() {
		return monitoringApply;
	}
	public void setMonitoringApply(Integer monitoringApply) {
		this.monitoringApply = monitoringApply;
	}
		
	
}
