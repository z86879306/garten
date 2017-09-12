package com.garten.model.baby;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BabyInfo {
	
	private String babyName;
	private Integer babyId;
	private Long birthday;
	private BigDecimal height;
	private String health;
	private String hobby;
	private String specialty;
	private Integer gartenId;
	private String teacherId;
	private Integer attendanceceId;
	private String babyHead;
	private String allergy;
	private Integer parentId;
	private String parentRelation;
	private BigDecimal weight;
	private Integer sex;
	private Long registTime;
	private String cardId;
	@Override
	public String toString() {
		return "BabyInfo [babyName=" + babyName + ", babyId=" + babyId + ", birthday=" + birthday + ", height=" + height
				+ ", health=" + health + ", hobby=" + hobby + ", specialty=" + specialty + ", gartenId=" + gartenId
				+ ", teacherId=" + teacherId + ", attendanceceId=" + attendanceceId + ", babyHead=" + babyHead
				+ ", allergy=" + allergy + ", parentId=" + parentId + ", parentRelation=" + parentRelation + ", weight="
				+ weight + ", sex=" + sex + ", registTime=" + registTime + ", cardId=" + cardId + "]";
	}
	public BabyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyInfo(String babyName, Integer babyId, Long birthday, BigDecimal height, String health, String hobby,
			String specialty, Integer gartenId, String teacherId, Integer attendanceceId, String babyHead,
			String allergy, Integer parentId, String parentRelation, BigDecimal weight, Integer sex, Long registTime,String cardId) {
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
		this.attendanceceId = attendanceceId;
		this.babyHead = babyHead;
		this.allergy = allergy;
		this.parentId = parentId;
		this.parentRelation = parentRelation;
		this.weight = weight;
		this.sex = sex;
		this.registTime = registTime;
		this.cardId = cardId;
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
		this.birthday = birthday.getTime()/1000;
	}
	public BigDecimal getHeight() {
		return height;
	}
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
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
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public Integer getAttendanceceId() {
		return attendanceceId;
	}
	public void setAttendanceceId(Integer attendanceceId) {
		this.attendanceceId = attendanceceId;
	}
	public String getBabyHead() {
		return babyHead;
	}
	public void setBabyHead(String babyHead) {
		this.babyHead = babyHead;
	}
	public String getAllergy() {
		return allergy;
	}
	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getParentRelation() {
		return parentRelation;
	}
	public void setParentRelation(String parentRelation) {
		this.parentRelation = parentRelation;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}



}
