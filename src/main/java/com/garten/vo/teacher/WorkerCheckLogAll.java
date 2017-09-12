package com.garten.vo.teacher;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class WorkerCheckLogAll {
	
	private Integer gartenId;
	private Integer workerId;
	private Long arriveTime;
	private Long leaveTime;
	private Long time;
	private Integer checkId;
	private String amFileId;
	private String pmFileId;
	private String amMacId;
	private String pmMacId;
	private String  arriveImg;
	private String  leaveImg;
	
	private String workerName;
	private String workerHead;
	@Override
	public String toString() {
		return "WorkerCheckLogAll [gartenId=" + gartenId + ", workerId=" + workerId + ", arriveTime=" + arriveTime
				+ ", leaveTime=" + leaveTime + ", time=" + time + ", checkId=" + checkId + ", amFileId=" + amFileId
				+ ", pmFileId=" + pmFileId + ", amMacId=" + amMacId + ", pmMacId=" + pmMacId + ", arriveImg="
				+ arriveImg + ", leaveImg=" + leaveImg + ", workerName=" + workerName + ", workerHead=" + workerHead
				+ "]";
	}
	public WorkerCheckLogAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkerCheckLogAll(Integer gartenId, Integer workerId, Long arriveTime, Long leaveTime, Long time,
			Integer checkId, String amFileId, String pmFileId, String amMacId,String pmMacID, String arriveImg, String leaveImg,
			String workerName, String workerHead) {
		super();
		this.gartenId = gartenId;
		this.workerId = workerId;
		this.arriveTime = arriveTime;
		this.leaveTime = leaveTime;
		this.time = time;
		this.checkId = checkId;
		this.amFileId = amFileId;
		this.pmFileId = pmFileId;
		this.amMacId = amMacId;
		this.pmMacId = pmMacId;
		this.arriveImg = arriveImg;
		this.leaveImg = leaveImg;
		this.workerName = workerName;
		this.workerHead = workerHead;
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
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getWorkerHead() {
		return workerHead;
	}
	public void setWorkerHead(String workerHead) {
		this.workerHead = workerHead;
	}
	public String getAmMacId() {
		return amMacId;
	}
	public void setAmMacId(String amMacId) {
		this.amMacId = amMacId;
	}
	public String getPmMacId() {
		return pmMacId;
	}
	public void setPmMacId(String pmMacId) {
		this.pmMacId = pmMacId;
	}
	
}
