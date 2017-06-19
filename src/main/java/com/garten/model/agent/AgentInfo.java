package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AgentInfo {
	
	private Integer agentId;
	private String agentNumber;
	private String agentPwd;
	private String serviceArea;
	private Integer agentGrade;
	private BigDecimal initialFee;
	private BigDecimal creditMoney;
	private Long agentStartTime;
	private Long agentEndTime;
	@Override
	public String toString() {
		return "AgentInfo [agentId=" + agentId + ", agentNumber=" + agentNumber + ", agentPwd=" + agentPwd
				+ ", serviceArea=" + serviceArea + ", agentGrade=" + agentGrade + ", initialFee=" + initialFee
				+ ", creditMoney=" + creditMoney + ", agentStartTime=" + agentStartTime + ", agentEndTime="
				+ agentEndTime + "]";
	}
	public AgentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AgentInfo(Integer agentId, String agentNumber, String agentPwd, String serviceArea, Integer agentGrade,
			BigDecimal initialFee, BigDecimal creditMoney, Long agentStartTime, Long agentEndTime) {
		super();
		this.agentId = agentId;
		this.agentNumber = agentNumber;
		this.agentPwd = agentPwd;
		this.serviceArea = serviceArea;
		this.agentGrade = agentGrade;
		this.initialFee = initialFee;
		this.creditMoney = creditMoney;
		this.agentStartTime = agentStartTime;
		this.agentEndTime = agentEndTime;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public String getAgentNumber() {
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}
	public String getAgentPwd() {
		return agentPwd;
	}
	public void setAgentPwd(String agentPwd) {
		this.agentPwd = agentPwd;
	}
	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
	public Integer getAgentGrade() {
		return agentGrade;
	}
	public void setAgentGrade(Integer agentGrade) {
		this.agentGrade = agentGrade;
	}
	public BigDecimal getInitialFee() {
		return initialFee;
	}
	public void setInitialFee(BigDecimal initialFee) {
		this.initialFee = initialFee;
	}
	public BigDecimal getCreditMoney() {
		return creditMoney;
	}
	public void setCreditMoney(BigDecimal creditMoney) {
		this.creditMoney = creditMoney;
	}
	public Long getAgentStartTime() {
		return agentStartTime;
	}
	public void setAgentStartTime(Timestamp agentStartTime) {
		this.agentStartTime = agentStartTime.getTime();
	}
	public Long getAgentEndTime() {
		return agentEndTime;
	}
	public void setAgentEndTime(Timestamp agentEndTime) {
		this.agentEndTime = agentEndTime.getTime();
	}
	
	
	
	
	
	
	

}
