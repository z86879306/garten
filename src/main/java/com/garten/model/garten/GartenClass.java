package com.garten.model.garten;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GartenClass {
	
	private String leadClass;
	private String leadGrade;
	private Integer gartenId;
	private Integer classId;
	private Long registTime;
	@Override
	public String toString() {
		return "GartenClass [leadClass=" + leadClass + ", leadGrade=" + leadGrade + ", gartenId=" + gartenId
				+ ", classId=" + classId + ", registTime=" + registTime + "]";
	}
	public GartenClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenClass(String leadClass, String leadGrade, Integer gartenId, Integer classId, Long registTime) {
		super();
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
		this.gartenId = gartenId;
		this.classId = classId;
		this.registTime = registTime;
	}
	public String getLeadClass() {
		return leadClass;
	}
	public void setLeadClass(String leadClass) {
		this.leadClass = leadClass;
	}
	public String getLeadGrade() {
		return leadGrade;
	}
	public void setLeadGrade(String leadGrade) {
		this.leadGrade = leadGrade;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	
}
