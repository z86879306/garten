package com.garten.model.activity;

import java.sql.Timestamp;

public class ActivituLog {
	
	private Integer gartenId;
	private String activityImg;
	private String activityContent;
	private Long activityStart;
	private Long activityEnd;
	private String activityAddress;
	private String babyName;
	private String parent;
	private String phoneNumber;
	@Override
	public String toString() {
		return "ActivituLog [gartenId=" + gartenId + ", activityImg=" + activityImg + ", activityContent="
				+ activityContent + ", activityStart=" + activityStart + ", activityEnd=" + activityEnd
				+ ", activityAddress=" + activityAddress + ", babyName=" + babyName + ", parent=" + parent
				+ ", phoneNumber=" + phoneNumber + "]";
	}
	public ActivituLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActivituLog(Integer gartenId, String activityImg, String activityContent, Long activityStart,
			Long activityEnd, String activityAddress, String babyName, String parent, String phoneNumber) {
		super();
		this.gartenId = gartenId;
		this.activityImg = activityImg;
		this.activityContent = activityContent;
		this.activityStart = activityStart;
		this.activityEnd = activityEnd;
		this.activityAddress = activityAddress;
		this.babyName = babyName;
		this.parent = parent;
		this.phoneNumber = phoneNumber;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getActivityImg() {
		return activityImg;
	}
	public void setActivityImg(String activityImg) {
		this.activityImg = activityImg;
	}
	public String getActivityContent() {
		return activityContent;
	}
	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}
	public Long getActivityStart() {
		return activityStart;
	}
	public void setActivityStart(Timestamp activityStart) {
		this.activityStart = activityStart.getTime();
	}
	public Long getActivityEnd() {
		return activityEnd;
	}
	public void setActivityEnd(Timestamp activityEnd) {
		this.activityEnd = activityEnd.getTime();
	}
	public String getActivityAddress() {
		return activityAddress;
	}
	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}
	public String getBabyName() {
		return babyName;
	}
	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
	

	
}
