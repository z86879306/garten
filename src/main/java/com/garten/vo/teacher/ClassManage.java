package com.garten.vo.teacher;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ClassManage {
	
	private String babyName;
	private Integer babyId;
	private Long birthday;
	private BigDecimal height;
	private String health;
	private String hobby;
	private String specialty;
	private Integer gartenId;
	private String teacherId;
	private String babyHead;
	private String allergy;
	private String weight;
	private Integer sex;
	private Long registTime;
	
	private Integer classId;
	private String leadClass;
	private String leadGrade;
	
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public ClassManage(String babyName, Integer babyId, Long birthday, BigDecimal height, String health, String hobby,
			String specialty, Integer gartenId, String teacherId, String babyHead, String allergy, String weight,
			Integer sex, Long registTime, Integer classId, String leadClass, String leadGrade) {
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
		this.babyHead = babyHead;
		this.allergy = allergy;
		this.weight = weight;
		this.sex = sex;
		this.registTime = registTime;
		this.classId = classId;
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
	}
	@Override
	public String toString() {
		return "ClassManage [babyName=" + babyName + ", babyId=" + babyId + ", birthday=" + birthday + ", height="
				+ height + ", health=" + health + ", hobby=" + hobby + ", specialty=" + specialty + ", gartenId="
				+ gartenId + ", teacherId=" + teacherId + ", babyHead=" + babyHead + ", allergy=" + allergy
				+ ", weight=" + weight + ", sex=" + sex + ", registTime=" + registTime + ", leadClass=" + leadClass
				+ ", leadGrade=" + leadGrade + "]";
	}
	public ClassManage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClassManage(String babyName, Integer babyId, Long birthday, BigDecimal height, String health, String hobby,
			String specialty, Integer gartenId, String teacherId, String babyHead, String allergy, String weight,
			Integer sex, Long registTime, String leadClass, String leadGrade) {
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
		this.babyHead = babyHead;
		this.allergy = allergy;
		this.weight = weight;
		this.sex = sex;
		this.registTime = registTime;
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
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
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
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
	public String getLeadClass() {
		return leadClass;
	}
	public void setLeadClass(String leadClass) {
		this.leadClass = leadClass;
	}
	public String getLeadGrade() {
		return leadGrade;
	}
	public void setLeadGrade(String leadGrade) {
		this.leadGrade = leadGrade;
	}
	
}
