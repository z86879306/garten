package com.garten.model.other;

import java.sql.Timestamp;

public class MessageLog {
	
	private Long registTime;
	private String targetName;
	private String message;
	private Integer messageId;
	private String fromName;
	private String gartenId;
	private String title;
	private String toGartenName;
	private String fromGartenName;
	private Integer fromId;
	private String fromJob;
	private String toclass;
	
	
	public String getFromGartenName() {
		return fromGartenName;
	}
	public void setFromGartenName(String fromGartenName) {
		this.fromGartenName = fromGartenName;
	}
	public String getToGartenName() {
		return toGartenName;
	}
	public void setToGartenName(String toGartenName) {
		this.toGartenName = toGartenName;
	}
	
	
	public String getToclass() {
		return toclass;
	}
	public void setToclass(String toclass) {
		this.toclass = toclass;
	}
	
	public MessageLog(Long registTime, String targetName, String message, Integer messageId, String fromName,
			String gartenId, String title, String toGartenName, String fromGartenName, Integer fromId, String fromJob,
			String toclass) {
		super();
		this.registTime = registTime;
		this.targetName = targetName;
		this.message = message;
		this.messageId = messageId;
		this.fromName = fromName;
		this.gartenId = gartenId;
		this.title = title;
		this.toGartenName = toGartenName;
		this.fromGartenName = fromGartenName;
		this.fromId = fromId;
		this.fromJob = fromJob;
		this.toclass = toclass;
	}
	
	public Integer getFromId() {
		return fromId;
	}
	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}
	public String getFromJob() {
		return fromJob;
	}
	public void setFromJob(String fromJob) {
		this.fromJob = fromJob;
	}
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "MessageLog [registTime=" + registTime + ", targetName=" + targetName + ", message=" + message
				+ ", messageId=" + messageId + ", fromName=" + fromName + ", gartenId=" + gartenId + "]";
	}
	public MessageLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageLog(Long registTime, String targetName, String message, Integer messageId, String fromName,
			String gartenId) {
		super();
		this.registTime = registTime;
		this.targetName = targetName;
		this.message = message;
		this.messageId = messageId;
		this.fromName = fromName;
		this.gartenId = gartenId;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getGartenId() {
		return gartenId;
	}
	public void setGartenId(String gartenId) {
		this.gartenId = gartenId;
	}
	
	
	
	
	
	
	

}
