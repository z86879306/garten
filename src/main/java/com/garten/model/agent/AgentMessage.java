package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

public class AgentMessage {
	
	private Long registTime;
	private String title;
	private String message;
	private Integer employeeNo;
	private Integer agentId;
	private Integer agentMessageId;
	private Integer isread;
	
	public Integer getIsread() {
		return isread;
	}
	public void setIsread(Integer isread) {
		this.isread = isread;
	}
	public AgentMessage(Long registTime, String title, String message, Integer employeeNo, Integer agentId,
			Integer agentMessageId, Integer isread) {
		super();
		this.registTime = registTime;
		this.title = title;
		this.message = message;
		this.employeeNo = employeeNo;
		this.agentId = agentId;
		this.agentMessageId = agentMessageId;
		this.isread = isread;
	}
	@Override
	public String toString() {
		return "AgentMessage [registTime=" + registTime + ", title=" + title + ", message=" + message + ", employeeNo="
				+ employeeNo + ", agentId=" + agentId + ", agentMessageId=" + agentMessageId + "]";
	}
	public AgentMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AgentMessage(Long registTime, String title, String message, Integer employeeNo, Integer agentId,
			Integer agentMessageId) {
		super();
		this.registTime = registTime;
		this.title = title;
		this.message = message;
		this.employeeNo = employeeNo;
		this.agentId = agentId;
		this.agentMessageId = agentMessageId;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(Integer employeeNo) {
		this.employeeNo = employeeNo;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getAgentMessageId() {
		return agentMessageId;
	}
	public void setAgentMessageId(Integer agentMessageId) {
		this.agentMessageId = agentMessageId;
	}
	
	
}
