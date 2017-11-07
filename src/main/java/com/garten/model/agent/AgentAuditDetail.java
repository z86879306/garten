package com.garten.model.agent;

import java.math.BigDecimal;

import com.garten.model.company.Employee;

public class AgentAuditDetail extends AgentAudit {

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
	
	public AgentAuditDetail(Integer resource, String contractNumber, String province, String city, String countries,
			Integer workerCount, Integer babyCount, String equipment, BigDecimal money1, BigDecimal money2,
			BigDecimal money3, BigDecimal money4, Integer auditId, Integer resourceId, Long registTime, Integer state,
			String gartenName, String name, String phoneNumber, String remark, Integer gradeCount, Integer classCount,
			String reason, Integer gartenType, AgentInfo agentInfo, Employee employee) {
		super(resource, contractNumber, province, city, countries, workerCount, babyCount, equipment, money1, money2,
				money3, money4, auditId, resourceId, registTime, state, gartenName, name, phoneNumber, remark,
				gradeCount, classCount, reason, gartenType);
		this.agentInfo = agentInfo;
		this.employee = employee;
	}
	public AgentAuditDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
