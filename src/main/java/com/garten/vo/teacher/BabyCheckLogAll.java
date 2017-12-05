package com.garten.vo.teacher;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.garten.model.baby.BabyCheckLog;

public class BabyCheckLogAll extends BabyCheckLog{
	
	
	
	private String babyHead ;
	private String babyName ;
	private String leadClass ;
	private String leadGrade ;
	
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
	public String getBabyHead() {
		return babyHead;
	}
	public void setBabyHead(String babyHead) {
		this.babyHead = babyHead;
	}
	public String getBabyName() {
		return babyName;
	}
	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	@Override
	public String toString() {
		return "BabyCheckLogAll [babyHead=" + babyHead + ", babyName=" + babyName + ", leadClass=" + leadClass
				+ ", leadGrade=" + leadGrade + "]";
	}
	public BabyCheckLogAll(String babyHead, String babyName, String leadClass, String leadGrade) {
		super();
		this.babyHead = babyHead;
		this.babyName = babyName;
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
	}
	public BabyCheckLogAll() {
		super();
	}
	public BabyCheckLogAll(Integer gartenId, Integer babyId, BigDecimal temperature, String remark, Long time,
			Integer checkId, Long amArriveTime, String amArriveImg, String amArriveFileId, String amArriveMacId,
			Long amLeaveTime, String amLeaveImg, String amLeaveFileId, String amLeaveMacId, Long pmArriveTime,
			String pmArriveImg, String pmArriveFileId, String pmArriveMacId, Long pmLeaveTime, String pmLeaveImg,
			String pmLeaveFileId, String pmLeaveMacId) {
		super(gartenId, babyId, temperature, remark, time, checkId, amArriveTime, amArriveImg, amArriveFileId, amArriveMacId,
				amLeaveTime, amLeaveImg, amLeaveFileId, amLeaveMacId, pmArriveTime, pmArriveImg, pmArriveFileId, pmArriveMacId,
				pmLeaveTime, pmLeaveImg, pmLeaveFileId, pmLeaveMacId);
		// TODO Auto-generated constructor stub
	}
	
}
