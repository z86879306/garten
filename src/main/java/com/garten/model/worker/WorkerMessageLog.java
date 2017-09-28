package com.garten.model.worker;

import java.sql.Timestamp;

public class WorkerMessageLog {

	private Integer messageId;
	private String title;
	private String info;
	private Long registTime;
	private Integer workerId;
	private Integer gartenId;
	private Integer state;
	
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	
	public WorkerMessageLog(Integer messageId, String title, String info, Long registTime, Integer workerId,
			Integer gartenId, Integer state) {
		super();
		this.messageId = messageId;
		this.title = title;
		this.info = info;
		this.registTime = registTime;
		this.workerId = workerId;
		this.gartenId = gartenId;
		this.state = state;
	}
	public WorkerMessageLog() {
		super();
	}
	
}
