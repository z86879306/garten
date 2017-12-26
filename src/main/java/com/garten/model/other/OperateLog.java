package com.garten.model.other;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OperateLog {
	private Integer operateId;
	private String fromName;
	private String toName;
	private Integer fromId;
	private String toId;
	private String toJob;
	private Integer type;
	private String content;
	private Long registTime;
	@Override
	public String toString() {
		return "OperateLog [operateId=" + operateId + ", fromName=" + fromName + ", toName=" + toName + ", fromId="
				+ fromId + ", toId=" + toId + ", toJob=" + toJob + ", type=" + type + ", content=" + content
				+ ", registTime=" + registTime + "]";
	}
	public OperateLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OperateLog(Integer operateId, String fromName, String toName, Integer fromId, String toId, String toJob,
			Integer type, String content, Long registTime) {
		super();
		this.operateId = operateId;
		this.fromName = fromName;
		this.toName = toName;
		this.fromId = fromId;
		this.toId = toId;
		this.toJob = toJob;
		this.type = type;
		this.content = content;
		this.registTime = registTime;
	}
	public Integer getOperateId() {
		return operateId;
	}
	public void setOperateId(Integer operateId) {
		this.operateId = operateId;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public Integer getFromId() {
		return fromId;
	}
	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getToJob() {
		return toJob;
	}
	public void setToJob(String toJob) {
		this.toJob = toJob;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	
}
