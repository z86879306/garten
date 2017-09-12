package com.garten.model.other;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AttendanceNo {
	private String cardNo1;
	private String cardNo2;
	private String cardNo3;
	private String job;
	private Long registTime;
	private Integer gartenId;
	private Integer jobId;
	public String getCardNo1() {
		return cardNo1;
	}
	public void setCardNo1(String cardNo1) {
		this.cardNo1 = cardNo1;
	}
	public String getCardNo2() {
		return cardNo2;
	}
	public void setCardNo2(String cardNo2) {
		this.cardNo2 = cardNo2;
	}
	public String getCardNo3() {
		return cardNo3;
	}
	public void setCardNo3(String cardNo3) {
		this.cardNo3 = cardNo3;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public AttendanceNo(String cardNo1, String cardNo2, String cardNo3, String job, Long registTime, Integer gartenId,
			Integer jobId) {
		super();
		this.cardNo1 = cardNo1;
		this.cardNo2 = cardNo2;
		this.cardNo3 = cardNo3;
		this.job = job;
		this.registTime = registTime;
		this.gartenId = gartenId;
		this.jobId = jobId;
	}
	public AttendanceNo() {
		super();
	}
	public AttendanceNo(String job, Integer gartenId) {
		super();
		this.job = job;
		this.gartenId = gartenId;
	}
	
	
}
