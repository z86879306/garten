package com.garten.vo.baby;

import java.math.BigDecimal;

import com.garten.vo.bigcontrol.BabyMessage;

public class BabyMessageAndParent extends BabyMessage {

	private String parentName;
	private String phoneNumber;
	private Integer classId;
	
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
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
	public BabyMessageAndParent(String parentName, String phoneNumber, Integer classId) {
		super();
		this.parentName = parentName;
		this.phoneNumber = phoneNumber;
		this.classId = classId;
	}
	public BabyMessageAndParent() {
		super();
	}
	public BabyMessageAndParent(String babyName, Integer babyId, Long birthday, BigDecimal height, String health,
			String hobby, String specialty, Integer gartenId, String teacherId, Integer attendanceceId, String babyHead,
			String allergy, Integer parentId, String parentRelation, BigDecimal weight, Integer sex, Long registTime,
			String cardId) {
		super(babyName, babyId, birthday, height, health, hobby, specialty, gartenId, teacherId, attendanceceId, babyHead,
				allergy, parentId, parentRelation, weight, sex, registTime, cardId);
		// TODO Auto-generated constructor stub
	}
	public BabyMessageAndParent(String leadClass, String leadGrade, String cardNo) {
		super(leadClass, leadGrade, cardNo);
		// TODO Auto-generated constructor stub
	}
	
	
}
