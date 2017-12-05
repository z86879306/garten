package com.garten.model.baby;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BabyCheckLog {
	
	private Integer gartenId;
	private Integer babyId;
	private BigDecimal temperature;
	private String remark;
	private Long time;
	private Integer checkId;
	private Long amArriveTime;
	private String amArriveImg;
	private String amArriveFileId;
	private String amArriveMacId;
	private Long amLeaveTime;
	private String amLeaveImg;
	private String amLeaveFileId;
	private String amLeaveMacId;
	private Long pmArriveTime;
	private String pmArriveImg;
	private String pmArriveFileId;
	private String pmArriveMacId;
	private Long pmLeaveTime;
	private String pmLeaveImg;
	private String pmLeaveFileId;
	private String pmLeaveMacId;
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
	public Long getAmArriveTime() {
		return amArriveTime;
	}
	public void setAmArriveTime(Timestamp amArriveTime) {
		this.amArriveTime = amArriveTime.getTime()/1000;
	}
	public String getAmArriveImg() {
		return amArriveImg;
	}
	public void setAmArriveImg(String amArriveImg) {
		this.amArriveImg = amArriveImg;
	}
	public String getAmArriveFileId() {
		return amArriveFileId;
	}
	public void setAmArriveFileId(String amArriveFileId) {
		this.amArriveFileId = amArriveFileId;
	}
	public String getAmArriveMacId() {
		return amArriveMacId;
	}
	public void setAmArriveMacId(String amArriveMacId) {
		this.amArriveMacId = amArriveMacId;
	}
	public Long getAmLeaveTime() {
		return amLeaveTime;
	}
	public void setAmLeaveTime(Timestamp amLeaveTime) {
		this.amLeaveTime = amLeaveTime.getTime()/1000;
	}
	public String getAmLeaveImg() {
		return amLeaveImg;
	}
	public void setAmLeaveImg(String amLeaveImg) {
		this.amLeaveImg = amLeaveImg;
	}
	public String getAmLeaveFileId() {
		return amLeaveFileId;
	}
	public void setAmLeaveFileId(String amLeaveFileId) {
		this.amLeaveFileId = amLeaveFileId;
	}
	public String getAmLeaveMacId() {
		return amLeaveMacId;
	}
	public void setAmLeaveMacId(String amLeaveMacId) {
		this.amLeaveMacId = amLeaveMacId;
	}
	public Long getPmArriveTime() {
		return pmArriveTime;
	}
	public void setPmArriveTime(Timestamp pmArriveTime) {
		this.pmArriveTime = pmArriveTime.getTime()/1000;
	}
	public String getPmArriveImg() {
		return pmArriveImg;
	}
	public void setPmArriveImg(String pmArriveImg) {
		this.pmArriveImg = pmArriveImg;
	}
	public String getPmArriveFileId() {
		return pmArriveFileId;
	}
	public void setPmArriveFileId(String pmArriveFileId) {
		this.pmArriveFileId = pmArriveFileId;
	}
	public String getPmArriveMacId() {
		return pmArriveMacId;
	}
	public void setPmArriveMacId(String pmArriveMacId) {
		this.pmArriveMacId = pmArriveMacId;
	}
	public Long getPmLeaveTime() {
		return pmLeaveTime;
	}
	public void setPmLeaveTime(Timestamp pmLeaveTime) {
		this.pmLeaveTime = pmLeaveTime.getTime()/1000;
	}
	public String getPmLeaveImg() {
		return pmLeaveImg;
	}
	public void setPmLeaveImg(String pmLeaveImg) {
		this.pmLeaveImg = pmLeaveImg;
	}
	public String getPmLeaveFileId() {
		return pmLeaveFileId;
	}
	public void setPmLeaveFileId(String pmLeaveFileId) {
		this.pmLeaveFileId = pmLeaveFileId;
	}
	public String getPmLeaveMacId() {
		return pmLeaveMacId;
	}
	public void setPmLeaveMacId(String pmLeaveMacId) {
		this.pmLeaveMacId = pmLeaveMacId;
	}
	public BabyCheckLog() {
		super();
	}
	public BabyCheckLog(Integer gartenId, Integer babyId, BigDecimal temperature, String remark, Long time,
			Integer checkId, Long amArriveTime, String amArriveImg, String amArriveFileId, String amArriveMacId,
			Long amLeaveTime, String amLeaveImg, String amLeaveFileId, String amLeaveMacId, Long pmArriveTime,
			String pmArriveImg, String pmArriveFileId, String pmArriveMacId, Long pmLeaveTime, String pmLeaveImg,
			String pmLeaveFileId, String pmLeaveMacId) {
		super();
		this.gartenId = gartenId;
		this.babyId = babyId;
		this.temperature = temperature;
		this.remark = remark;
		this.time = time;
		this.checkId = checkId;
		this.amArriveTime = amArriveTime;
		this.amArriveImg = amArriveImg;
		this.amArriveFileId = amArriveFileId;
		this.amArriveMacId = amArriveMacId;
		this.amLeaveTime = amLeaveTime;
		this.amLeaveImg = amLeaveImg;
		this.amLeaveFileId = amLeaveFileId;
		this.amLeaveMacId = amLeaveMacId;
		this.pmArriveTime = pmArriveTime;
		this.pmArriveImg = pmArriveImg;
		this.pmArriveFileId = pmArriveFileId;
		this.pmArriveMacId = pmArriveMacId;
		this.pmLeaveTime = pmLeaveTime;
		this.pmLeaveImg = pmLeaveImg;
		this.pmLeaveFileId = pmLeaveFileId;
		this.pmLeaveMacId = pmLeaveMacId;
	}
	
	

}
