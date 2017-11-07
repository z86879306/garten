package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

import com.garten.model.company.Employee;

public class AgentMessageAll extends AgentMessage{
	
	private Employee employee;
	private AgentInfo agentInfo;
	@Override
	public String toString() {
		return "AgentMessageAll [employee=" + employee + ", agentInfo=" + agentInfo + "]";
	}
	public AgentMessageAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AgentMessageAll(Long registTime, String title, String message, Integer employeeNo, Integer agentId,
			Integer agentMessageId) {
		super(registTime, title, message, employeeNo, agentId, agentMessageId);
		// TODO Auto-generated constructor stub
	}
	public AgentMessageAll(Employee employee, AgentInfo agentInfo) {
		super();
		this.employee = employee;
		this.agentInfo = agentInfo;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public AgentInfo getAgentInfo() {
		return agentInfo;
	}
	public void setAgentInfo(AgentInfo agentInfo) {
		this.agentInfo = agentInfo;
	}
	

}
