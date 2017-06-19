package com.garten.model.baby;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BabyCheckLog {
	
	private Integer gartenId;
	private Integer babyId;
	private Long arriveTime;
	private String arriveImg;
	private Long leaveTime;
	private String leaveImg;
	private BigDecimal temperature;
	private String remark;
	@Override
	public String toString() {
		return "BabyCheckLog [gartenId=" + gartenId + ", babyId=" + babyId + ", arriveTime=" + arriveTime
				+ ", arriveImg=" + arriveImg + ", leaveTime=" + leaveTime + ", leaveImg=" + leaveImg + ", temperature="
				+ temperature + ", remark=" + remark + "]";
	}
	public BabyCheckLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyCheckLog(Integer gartenId, Integer babyId, Long arriveTime, String arriveImg, Long leaveTime,
			String leaveImg, BigDecimal temperature, String remark) {
		super();
		this.gartenId = gartenId;
		this.babyId = babyId;
		this.arriveTime = arriveTime;
		this.arriveImg = arriveImg;
		this.leaveTime = leaveTime;
		this.leaveImg = leaveImg;
		this.temperature = temperature;
		this.remark = remark;
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
		this.arriveTime = arriveTime.getTime();
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
		this.leaveTime = leaveTime.getTime();
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
	
	

}
