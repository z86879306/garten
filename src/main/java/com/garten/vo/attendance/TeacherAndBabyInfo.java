package com.garten.vo.attendance;

public class TeacherAndBabyInfo {

	
	private Integer babyId;
	private String name;
	private Integer classId;
	private String classesName;
	private String cardNo1;
	private String cardNo2;
	private String cardNo3;
	private String fileUrl;
	private Integer type;
	private String identity;
	
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getClassesName() {
		return classesName;
	}
	public void setClassesName(String classesName) {
		this.classesName = classesName;
	}
	public String getCardNo1() {
		return cardNo1;
	}
	public void setCardNo1(String cardNo1) {
		this.cardNo1 = cardNo1;
	}
	public Integer getType() {
		return type;
	}
	public void setType(String identity) {
		this.type = "宝宝".equals(identity)?0:1;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getCardNo2() {
		return cardNo2;
	}
	public void setCardNo2(String cardNo2) {
		this.cardNo2 = cardNo2;
	}
	public String getCardNo3() {
		return cardNo3;
	}
	public void setCardNo3(String cardNo3) {
		this.cardNo3 = cardNo3;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public TeacherAndBabyInfo(Integer babyId, String name, Integer classId, String classesName, String cardNo1,
			String cardNo2, String cardNo3, String fileUrl, String identity) {
		super();
		this.babyId = babyId;
		this.name = name;
		this.classId = classId;
		this.classesName = classesName;
		this.cardNo1 = cardNo1;
		this.cardNo2 = cardNo2;
		this.cardNo3 = cardNo3;
		this.fileUrl = fileUrl;
		this.identity = identity;
	}
	public TeacherAndBabyInfo(Integer babyId, String name, String cardNo1, String cardNo2, String cardNo3,
			String fileUrl, String identity) {
		super();
		this.babyId = babyId;
		this.name = name;
		this.cardNo1 = cardNo1;
		this.cardNo2 = cardNo2;
		this.cardNo3 = cardNo3;
		this.fileUrl = fileUrl;
		this.identity = identity;
	}

	
	
}
