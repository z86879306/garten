package com.garten.vo.garent;

import java.math.BigDecimal;

import com.garten.model.agent.AgentInfo;
import com.garten.model.company.Employee;
import com.garten.model.garten.GartenInfo;

public class GartenAndAgent extends GartenInfo{
 
	private AgentInfo agentInfo;
	private Employee employee;
	public AgentInfo getAgentInfo() {
		return agentInfo;
	}
	public void setAgentInfo(AgentInfo agentInfo) {
		this.agentInfo = agentInfo;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public GartenAndAgent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenAndAgent(AgentInfo agentInfo, Employee employee) {
		super();
		this.agentInfo = agentInfo;
		this.employee = employee;
	}
	public GartenAndAgent(Integer gartenId, String phoneNumber, Long registTime, Integer gartenGrade, String pwd,
			Integer accountState, String gartenName, Integer applicationState, String address, Long attendanceTime,
			Long monitorTime, BigDecimal charge, Integer agent, String token, Long tokenTime, String introduceHtml,
			String introduceImgs, String province, String city, String countries, String contractNumber,
			Long contractStart, Long contractEnd, String organizationCode, String name, Integer agentType,
			Integer gartenType) {
		super(gartenId, phoneNumber, registTime, gartenGrade, pwd, accountState, gartenName, applicationState, address,
				attendanceTime, monitorTime, charge, agent, token, introduceHtml, introduceImgs, province, city,
				countries, contractNumber, contractStart, contractEnd, organizationCode, agentType, gartenType);
		// TODO Auto-generated constructor stub
	}
	
	
}
