package com.garten.model.gartenClass;

import java.sql.Timestamp;

public class GartenClass {
	private String leadClass;
	private String leadGrade;
	private Integer gartenId;
	private Integer classId;
	private String mark;
	private Long registTime;
	private Integer gradeId;
	@Override
	public String toString() {
		return "GartenClass [leadClass=" + leadClass + ", leadGrade=" + leadGrade + ", gartenId=" + gartenId
				+ ", classId=" + classId + ", mark=" + mark + ", registTime=" + registTime + ", gradeId=" + gradeId
				+ "]";
	}
	public GartenClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenClass(String leadClass, String leadGrade, Integer gartenId, Integer classId, String mark,
			Long registTime, Integer gradeId) {
		super();
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
		this.gartenId = gartenId;
		this.classId = classId;
		this.mark = mark;
		this.registTime = registTime;
		this.gradeId = gradeId;
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
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	
	
}
