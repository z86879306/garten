package com.garten.vo.smallcontrol;

import com.garten.model.other.AttendanceNo;

public class CardNoDetail {

	private String name;
	private String cardNo1;
	private String cardNo2;
	private String cardNo3;
	private String job;
	private Integer jobId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public CardNoDetail(String name, String cardNo1, String cardNo2, String cardNo3, String job, Integer jobId) {
		super();
		this.name = name;
		this.cardNo1 = cardNo1;
		this.cardNo2 = cardNo2;
		this.cardNo3 = cardNo3;
		this.job = job;
		this.jobId = jobId;
	}
	public CardNoDetail() {
		super();
	}
	
	
}
