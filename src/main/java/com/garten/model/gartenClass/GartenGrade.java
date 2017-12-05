package com.garten.model.gartenClass;

import java.sql.Timestamp;

public class GartenGrade {
	private String leadGrade;
	private Integer gartenId;
	private String mark;
	private Long registTime;
	private Integer gradeId;
	private Integer no;
	@Override
	public String toString() {
		return "GartenGrade [leadGrade=" + leadGrade + ", gartenId=" + gartenId + ", mark=" + mark + ", registTime="
				+ registTime + ", gradeId=" + gradeId + ", no=" + no + "]";
	}
	public GartenGrade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenGrade(String leadGrade, Integer gartenId, String mark, Long registTime, Integer gradeId, Integer no) {
		super();
		this.leadGrade = leadGrade;
		this.gartenId = gartenId;
		this.mark = mark;
		this.registTime = registTime;
		this.gradeId = gradeId;
		this.no = no;
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
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	
}
