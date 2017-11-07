package com.garten.model.company;

public class Jobs {

	private Long jobsNo;
	private String jobsName;
	private String mark;
	public Long getJobsNo() {
		return jobsNo;
	}
	public void setJobsNo(Long jobsNo) {
		this.jobsNo = jobsNo;
	}
	public String getJobsName() {
		return jobsName;
	}
	public void setJobsName(String jobsName) {
		this.jobsName = jobsName;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Jobs(Long jobsNo, String jobsName, String mark) {
		super();
		this.jobsNo = jobsNo;
		this.jobsName = jobsName;
		this.mark = mark;
	}
	public Jobs() {
		super();
	}
	
	
}
