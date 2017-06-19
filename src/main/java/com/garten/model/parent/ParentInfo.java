package com.garten.model.parent;

import java.sql.Timestamp;

public class ParentInfo {
	
	private String babyName;
	private Integer babyId;
	private String identity;
	private String parentName;
	private String phoneNumber;
	private String parentHead;
	private String address;
	private Integer parentId;
	private String pwd;
	private Integer monitorState;
	private Long monitorTime;
	private Integer attendanceState;
	private Long attendanceTime;
	private Integer flower;
	private String token;
	private Long tokenTime;
	@Override
	public String toString() {
		return "ParentInfo [babyName=" + babyName + ", babyId=" + babyId + ", identity=" + identity + ", parentName="
				+ parentName + ", phoneNumber=" + phoneNumber + ", parentHead=" + parentHead + ", address=" + address
				+ ", parentId=" + parentId + ", pwd=" + pwd + ", monitorState=" + monitorState + ", monitorTime="
				+ monitorTime + ", attendanceState=" + attendanceState + ", attendanceTime=" + attendanceTime
				+ ", flower=" + flower + ", token=" + token + ", tokenTime=" + tokenTime + "]";
	}
	public ParentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParentInfo(String babyName, Integer babyId, String identity, String parentName, String phoneNumber,
			String parentHead, String address, Integer parentId, String pwd, Integer monitorState, Long monitorTime,
			Integer attendanceState, Long attendanceTime, Integer flower, String token, Long tokenTime) {
		super();
		this.babyName = babyName;
		this.babyId = babyId;
		this.identity = identity;
		this.parentName = parentName;
		this.phoneNumber = phoneNumber;
		this.parentHead = parentHead;
		this.address = address;
		this.parentId = parentId;
		this.pwd = pwd;
		this.monitorState = monitorState;
		this.monitorTime = monitorTime;
		this.attendanceState = attendanceState;
		this.attendanceTime = attendanceTime;
		this.flower = flower;
		this.token = token;
		this.tokenTime = tokenTime;
	}
	public String getBabyName() {
		return babyName;
	}
	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
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
	public String getParentHead() {
		return parentHead;
	}
	public void setParentHead(String parentHead) {
		this.parentHead = parentHead;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Integer getMonitorState() {
		return monitorState;
	}
	public void setMonitorState(Integer monitorState) {
		this.monitorState = monitorState;
	}
	public Long getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(Timestamp monitorTime) {
		this.monitorTime = monitorTime.getTime();
	}
	public Integer getAttendanceState() {
		return attendanceState;
	}
	public void setAttendanceState(Integer attendanceState) {
		this.attendanceState = attendanceState;
	}
	public Long getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(Timestamp attendanceTime) {
		this.attendanceTime = attendanceTime.getTime();
	}
	public Integer getFlower() {
		return flower;
	}
	public void setFlower(Integer flower) {
		this.flower = flower;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getTokenTime() {
		return tokenTime;
	}
	public void setTokenTime(Timestamp tokenTime) {
		this.tokenTime = tokenTime.getTime();
	}
	

}
