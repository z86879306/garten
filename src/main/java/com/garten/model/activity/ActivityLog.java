package com.garten.model.activity;

import java.sql.Timestamp;

public class ActivityLog {
	
	private Integer babyId;
	private String parentName;
	private String phoneNumber;
	private Integer activityId;
	private Integer id;
	@Override
	public String toString() {
		return "ActivityLog [babyId=" + babyId + ", parentName=" + parentName + ", phoneNumber=" + phoneNumber
				+ ", activityId=" + activityId + ", id=" + id + "]";
	}
	public ActivityLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActivityLog(Integer babyId, String parentName, String phoneNumber, Integer activityId, Integer id) {
		super();
		this.babyId = babyId;
		this.parentName = parentName;
		this.phoneNumber = phoneNumber;
		this.activityId = activityId;
		this.id = id;
	}
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
