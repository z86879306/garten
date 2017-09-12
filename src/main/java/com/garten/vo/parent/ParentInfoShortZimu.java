package com.garten.vo.parent;

import java.sql.Timestamp;
import java.util.Arrays;

public class ParentInfoShortZimu {
	
	private String babyName;
	private String babyId;
	private String identity;
	private String parentName;
	private String phoneNumber;
	private String parentHead;
	private Integer parentId;
	private String address;
	private String zimu;

	public String getZimu() {
		return zimu;
	}
	public void setZimu(String zimu) {
		this.zimu = zimu;
	}
	public ParentInfoShortZimu(String babyName, String babyId, String identity, String parentName, String phoneNumber,
			String parentHead, Integer parentId, String address, String zimu) {
		super();
		this.babyName = babyName;
		this.babyId = babyId;
		this.identity = identity;
		this.parentName = parentName;
		this.phoneNumber = phoneNumber;
		this.parentHead = parentHead;
		this.parentId = parentId;
		this.address = address;
		this.zimu = zimu;
	}
	@Override
	public String toString() {
		return "ParentInfoShort [babyName=" + babyName + ", babyId=" + babyId + ", identity=" + identity
				+ ", parentName=" + parentName + ", phoneNumber=" + phoneNumber + ", parentHead=" + parentHead
				+ ", parentId=" + parentId + ", address=" + address + "]";
	}
	public ParentInfoShortZimu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParentInfoShortZimu(String babyName, String babyId, String identity, String parentName, String phoneNumber,
			String parentHead, Integer parentId, String address) {
		super();
		this.babyName = babyName;
		this.babyId = babyId;
		this.identity = identity;
		this.parentName = parentName;
		this.phoneNumber = phoneNumber;
		this.parentHead = parentHead;
		this.parentId = parentId;
		this.address = address;
	}
	public String getBabyName() {
		return babyName;
	}
	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	public String getBabyId() {
		return babyId;
	}
	public void setBabyId(String babyId) {
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
