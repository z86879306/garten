package com.garten.vo.attendance;

public class AdminInfoLog {
	
	private String teacherName;
	private Integer id;
	private String job;
	private String phoneNumber;
	
	public AdminInfoLog(String teacherName, Integer id, String job, String phoneNumber) {
		super();
		this.teacherName = teacherName;
		this.id = id;
		this.job = job;
		this.phoneNumber = phoneNumber;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
