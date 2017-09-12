package com.garten.model.worker;

import java.sql.Timestamp;

public class WorkerCheckLog {
	
	private Integer gartenId;
	private Integer workerId;
	private Long arriveTime;
	private Long leaveTime;
	private Long time;
	private Integer checkId;
	private String amFileId;
	private String pmFileId;
	private String macId;
	private String  arriveImg;
	private String  leaveImg;
	@Override
	public String toString() {
		return "WorkerCheckLog [gartenId=" + gartenId + ", workerId=" + workerId + ", arriveTime=" + arriveTime
				+ ", leaveTime=" + leaveTime + ", time=" + time + ", checkId=" + checkId + ", amFileId=" + amFileId
				+ ", pmFileId=" + pmFileId + ", macId=" + macId + ", arriveImg=" + arriveImg + ", leaveImg=" + leaveImg
				+ "]";
	}
	public WorkerCheckLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkerCheckLog(Integer gartenId, Integer workerId, Long arriveTime, Long leaveTime, Long time,
			Integer checkId, String amFileId, String pmFileId, String macId, String arriveImg, String leaveImg) {
		super();
		this.gartenId = gartenId;
		this.workerId = workerId;
		this.arriveTime = arriveTime;
		this.leaveTime = leaveTime;
		this.time = time;
		this.checkId = checkId;
		this.amFileId = amFileId;
		this.pmFileId = pmFileId;
		this.macId = macId;
		this.arriveImg = arriveImg;
		this.leaveImg = leaveImg;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public Long getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Timestamp arriveTime) {
		this.arriveTime = arriveTime.getTime()/1000;
	}
	public Long getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime.getTime()/1000;
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
	public String getArriveImg() {
		return arriveImg;
	}
	public void setArriveImg(String arriveImg) {
		this.arriveImg = arriveImg;
	}
	public String getLeaveImg() {
		return leaveImg;
	}
	public void setLeaveImg(String leaveImg) {
		this.leaveImg = leaveImg;
	}
	
	
	
}
