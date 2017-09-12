package com.garten.vo.teacher;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BabyInfoShort {
	
	private String babyName;
	private Integer babyId;
	private String classStr;
	private String gartenName;
	private String babyHead;
	public String getBabyHead() {
		return babyHead;
	}
	public void setBabyHead(String babyHead) {
		this.babyHead = babyHead;
	}
	public BabyInfoShort(String babyName, Integer babyId, String classStr, String gartenName, String babyHead) {
		super();
		this.babyName = babyName;
		this.babyId = babyId;
		this.classStr = classStr;
		this.gartenName = gartenName;
		this.babyHead = babyHead;
	}
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	public BabyInfoShort(String babyName, Integer babyId, String classStr, String gartenName) {
		super();
		this.babyName = babyName;
		this.babyId = babyId;
		this.classStr = classStr;
		this.gartenName = gartenName;
	}
	@Override
	public String toString() {
		return "BabyInfoShort [babyName=" + babyName + ", babyId=" + babyId + ", classStr=" + classStr + "]";
	}
	public BabyInfoShort() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyInfoShort(String babyName, Integer babyId, String classStr) {
		super();
		this.babyName = babyName;
		this.babyId = babyId;
		this.classStr = classStr;
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
	public String getClassStr() {
		return classStr;
	}
	public void setClassStr(String classStr) {
		this.classStr = classStr;
	}
	
}
