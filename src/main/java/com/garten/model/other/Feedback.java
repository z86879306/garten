package com.garten.model.other;

import java.sql.Timestamp;

public class Feedback {
	
	private Integer gartenId;
	private String job;
	private Long time;
	private Integer jobId;
	private String content;
	private String remark;
	private String name;
	private String phoneNumber;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Feedback(Integer gartenId, String job, Long time, Integer jobId, String content, String remark, String name,
			String phoneNumber) {
		super();
		this.gartenId = gartenId;
		this.job = job;
		this.time = time;
		this.jobId = jobId;
		this.content = content;
		this.remark = remark;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "Feedback [gartenId=" + gartenId + ", job=" + job + ", time=" + time + ", jobId=" + jobId + ", content="
				+ content + ", remark=" + remark + "]";
	}
	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Feedback(Integer gartenId, String job, Long time, Integer jobId, String content, String remark) {
		super();
		this.gartenId = gartenId;
		this.job = job;
		this.time = time;
		this.jobId = jobId;
		this.content = content;
		this.remark = remark;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/10000;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
