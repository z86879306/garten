package com.garten.vo.attendance;

public class BabySimpleInfo {
	
	private String babyName;
	private Integer babyId;
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
	public BabySimpleInfo(String babyName, Integer babyId) {
		super();
		this.babyName = babyName;
		this.babyId = babyId;
	}
	
}
