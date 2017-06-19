package com.garten.model.baby;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BabyInfo {
	
	private String babyName;
	private Integer babyId;
	private Long birthday;
	private BigDecimal height;
	private BigDecimal health;
	private String hobby;
	private String specialty;
	private Integer gartenId;
	private Integer teacherId;
	@Override
	public String toString() {
		return "BabyInfo [babyName=" + babyName + ", babyId=" + babyId + ", birthday=" + birthday + ", height=" + height
				+ ", health=" + health + ", hobby=" + hobby + ", specialty=" + specialty + ", gartenId=" + gartenId
				+ ", teacherId=" + teacherId + "]";
	}
	public BabyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyInfo(String babyName, Integer babyId, Long birthday, BigDecimal height, BigDecimal health, String hobby,
			String specialty, Integer gartenId, Integer teacherId) {
		super();
		this.babyName = babyName;
		this.babyId = babyId;
		this.birthday = birthday;
		this.height = height;
		this.health = health;
		this.hobby = hobby;
		this.specialty = specialty;
		this.gartenId = gartenId;
		this.teacherId = teacherId;
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
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday.getTime();
	}
	public BigDecimal getHeight() {
		return height;
	}
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	public BigDecimal getHealth() {
		return health;
	}
	public void setHealth(BigDecimal health) {
		this.health = health;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	
	
	
	
	
	

	
}
