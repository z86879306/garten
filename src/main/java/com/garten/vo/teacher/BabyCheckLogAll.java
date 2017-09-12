package com.garten.vo.teacher;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BabyCheckLogAll {
	
	private Integer gartenId;
	private Integer babyId;
	private Long arriveTime;
	private String arriveImg;
	private Long leaveTime;
	private String leaveImg;
	private BigDecimal temperature;
	private String remark;
	private Long time;
	private Integer checkId;
	private String amFileId;
	private String pmFileId;
	private String macId;
	
	
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
	public BabyCheckLogAll(Integer gartenId, Integer babyId, Long arriveTime, String arriveImg, Long leaveTime,
			String leaveImg, BigDecimal temperature, String remark, Long time, Integer checkId, String amFileId,
			String pmFileId, String macId, String babyHead, String babyName, String leadClass, String leadGrade) {
		super();
		this.gartenId = gartenId;
		this.babyId = babyId;
		this.arriveTime = arriveTime;
		this.arriveImg = arriveImg;
		this.leaveTime = leaveTime;
		this.leaveImg = leaveImg;
		this.temperature = temperature;
		this.remark = remark;
		this.time = time;
		this.checkId = checkId;
		this.amFileId = amFileId;
		this.pmFileId = pmFileId;
		this.macId = macId;
		this.babyHead = babyHead;
		this.babyName = babyName;
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
	}
	@Override
	public String toString() {
		return "BabyCheckLogAll [gartenId=" + gartenId + ", babyId=" + babyId + ", arriveTime=" + arriveTime
				+ ", arriveImg=" + arriveImg + ", leaveTime=" + leaveTime + ", leaveImg=" + leaveImg + ", temperature="
				+ temperature + ", remark=" + remark + ", time=" + time + ", checkId=" + checkId + ", amFileId="
				+ amFileId + ", pmFileId=" + pmFileId + ", macId=" + macId + ", babyHead=" + babyHead + ", babyName="
				+ babyName + "]";
	}
	public BabyCheckLogAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyCheckLogAll(Integer gartenId, Integer babyId, Long arriveTime, String arriveImg, Long leaveTime,
			String leaveImg, BigDecimal temperature, String remark, Long time, Integer checkId, String amFileId,
			String pmFileId, String macId, String babyHead, String babyName) {
		super();
		this.gartenId = gartenId;
		this.babyId = babyId;
		this.arriveTime = arriveTime;
		this.arriveImg = arriveImg;
		this.leaveTime = leaveTime;
		this.leaveImg = leaveImg;
		this.temperature = temperature;
		this.remark = remark;
		this.time = time;
		this.checkId = checkId;
		this.amFileId = amFileId;
		this.pmFileId = pmFileId;
		this.macId = macId;
		this.babyHead = babyHead;
		this.babyName = babyName;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
	}
	public Long getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Timestamp arriveTime) {
		this.arriveTime = arriveTime.getTime()/1000;
	}
	public String getArriveImg() {
		return arriveImg;
	}
	public void setArriveImg(String arriveImg) {
		this.arriveImg = arriveImg;
	}
	public Long getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime.getTime()/1000;
	}
	public String getLeaveImg() {
		return leaveImg;
	}
	public void setLeaveImg(String leaveImg) {
		this.leaveImg = leaveImg;
	}
	public BigDecimal getTemperature() {
		return temperature;
	}
	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	public Integer getCheckId() {
		return checkId;
	}
	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	public String getAmFileId() {
		return amFileId;
	}
	public void setAmFileId(String amFileId) {
		this.amFileId = amFileId;
	}
	public String getPmFileId() {
		return pmFileId;
	}
	public void setPmFileId(String pmFileId) {
		this.pmFileId = pmFileId;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
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
	
}
