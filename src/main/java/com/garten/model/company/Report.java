package com.garten.model.company;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Report {
	
	private Long startTime;
	private Long endTime;
	private String workContent;
	private String workSummary;
	private String harmonizeContent;
	private String plan;
	private Integer employeeNo;
	private Integer reportId;
	private Long registTime;
	private Integer type;
	@Override
	public String toString() {
		return "Repor [startTime=" + startTime + ", endTime=" + endTime + ", workContent=" + workContent
				+ ", workSummary=" + workSummary + ", harmonizeContent=" + harmonizeContent + ", plan=" + plan
				+ ", employeeNo=" + employeeNo + ", reportId=" + reportId + ", registTime=" + registTime + ", type="
				+ type + "]";
	}
	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Report(Long startTime, Long endTime, String workContent, String workSummary, String harmonizeContent,
			String plan, Integer employeeNo, Integer reportId, Long registTime, Integer type) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.workContent = workContent;
		this.workSummary = workSummary;
		this.harmonizeContent = harmonizeContent;
		this.plan = plan;
		this.employeeNo = employeeNo;
		this.reportId = reportId;
		this.registTime = registTime;
		this.type = type;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getWorkContent() {
		return workContent;
	}
	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}
	public String getWorkSummary() {
		return workSummary;
	}
	public void setWorkSummary(String workSummary) {
		this.workSummary = workSummary;
	}
	public String getHarmonizeContent() {
		return harmonizeContent;
	}
	public void setHarmonizeContent(String harmonizeContent) {
		this.harmonizeContent = harmonizeContent;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public Integer getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(Integer employeeNo) {
		this.employeeNo = employeeNo;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
