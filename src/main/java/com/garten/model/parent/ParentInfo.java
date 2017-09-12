package com.garten.model.parent;

import java.sql.Timestamp;
import java.util.Arrays;

public class ParentInfo {
	
	private String[] babyId;
	private String[] identity;
	private String parentName;
	private String phoneNumber;
	private String parentHead;
	private String address;
	private Integer parentId;
	private String pwd;
	private String[] monitorTime;
	private String[] attendanceTime;
	private Integer flower;
	private String token;
	private Long tokenTime;
	private Long registTime;
	private String gartenId;
	
	
	public ParentInfo(String[] babyId, String[] identity, String parentName, String phoneNumber, String parentHead,
			String address, Integer parentId, String pwd, String[] monitorTime, String[] attendanceTime, Integer flower,
			String token, Long tokenTime, Long registTime, String gartenId) {
		super();
		this.babyId = babyId;
		this.identity = identity;
		this.parentName = parentName;
		this.phoneNumber = phoneNumber;
		this.parentHead = parentHead;
		this.address = address;
		this.parentId = parentId;
		this.pwd = pwd;
		this.monitorTime = monitorTime;
		this.attendanceTime = attendanceTime;
		this.flower = flower;
		this.token = token;
		this.tokenTime = tokenTime;
		this.registTime = registTime;
		this.gartenId = gartenId;
	}
	
	@Override
	public String toString() {
		return "ParentInfo [babyId=" + Arrays.toString(babyId) + ", identity=" + Arrays.toString(identity)
				+ ", parentName=" + parentName + ", phoneNumber=" + phoneNumber + ", parentHead=" + parentHead
				+ ", address=" + address + ", parentId=" + parentId + ", pwd=" + pwd + ", monitorTime="
				+ Arrays.toString(monitorTime) + ", attendanceTime=" + Arrays.toString(attendanceTime) + ", flower="
				+ flower + ", token=" + token + ", tokenTime=" + tokenTime + ", registTime=" + registTime + "]";
	}
	public ParentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParentInfo(String[] babyId, String[] identity, String parentName, String phoneNumber, String parentHead,
			String address, Integer parentId, String pwd, String[] monitorTime, String[] attendanceTime, Integer flower,
			String token, Long tokenTime, Long registTime) {
		super();
		this.babyId = babyId;
		this.identity = identity;
		this.parentName = parentName;
		this.phoneNumber = phoneNumber;
		this.parentHead = parentHead;
		this.address = address;
		this.parentId = parentId;
		this.pwd = pwd;
		this.monitorTime = monitorTime;
		this.attendanceTime = attendanceTime;
		this.flower = flower;
		this.token = token;
		this.tokenTime = tokenTime;
		this.registTime = registTime;
	}
	public String[] getBabyId() {
		return babyId;
	}
	public void setBabyId(String babyId) {
		this.babyId = babyId.split(",");
	}
	public String[] getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity.split(",");
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
	public String[] getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime.split(",");
	}
	public String[] getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(String attendanceTime) {
		this.attendanceTime = attendanceTime.split(",");
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
		this.tokenTime = tokenTime.getTime()/1000;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	
	public String getGartenId() {
		return gartenId;
	}

	public void setGartenId(String gartenId) {
		this.gartenId = gartenId;
	}
	public ParentInfo(String[] babyId, String[] identity, String parentName, String phoneNumber, String address,
			String gartenId,String pwd) {
		super();
		this.babyId = babyId;
		this.identity = identity;
		this.parentName = parentName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.gartenId = gartenId;
		this.pwd = pwd;
	}
	
	
}
