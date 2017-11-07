package com.garten.model.company;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CpActivity {
	
	private Integer employeeNo;
	private Integer cpActivityId;
	private String content;
	private Long registTime;
	private String state;
	private String reason;
	private String title;
	@Override
	public String toString() {
		return "CpActivity [employeeNo=" + employeeNo + ", cpActivityId=" + cpActivityId + ", content=" + content
				+ ", registTime=" + registTime + ", state=" + state + ", reason=" + reason + ", title=" + title + "]";
	}
	public CpActivity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CpActivity(Integer employeeNo, Integer cpActivityId, String content, Long registTime, String state,
			String reason, String title) {
		super();
		this.employeeNo = employeeNo;
		this.cpActivityId = cpActivityId;
		this.content = content;
		this.registTime = registTime;
		this.state = state;
		this.reason = reason;
		this.title = title;
	}
	public Integer getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(Integer employeeNo) {
		this.employeeNo = employeeNo;
	}
	public Integer getCpActivityId() {
		return cpActivityId;
	}
	public void setCpActivityId(Integer cpActivityId) {
		this.cpActivityId = cpActivityId;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
