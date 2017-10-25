package com.garten.vo.agent;

import java.math.BigDecimal;

import com.garten.model.agent.AgentAudit;

public class AgentAuditMessage extends AgentAudit{

	private String agentName;

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public AgentAuditMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgentAuditMessage(Integer resource, String contractNumber, String province, String city, String countries,
			Integer workerCount, Integer babyCount, String equipment, BigDecimal money1, BigDecimal money2,
			BigDecimal money3, BigDecimal money4, Integer auditId, Integer resourceId, Long registTime, Integer state,
			String gartenName, String name, String phoneNumber, String remark, Integer gradeCount, Integer classCount,
			String reason, String agentName) {
		super(resource, contractNumber, province, city, countries, workerCount, babyCount, equipment, money1, money2,
				money3, money4, auditId, resourceId, registTime, state, gartenName, name, phoneNumber, remark,
				gradeCount, classCount, reason);
		this.agentName = agentName;
	}

	public AgentAuditMessage(Integer resource, String contractNumber, String province, String city, String countries,
			Integer workerCount, Integer babyCount, String equipment, BigDecimal money1, BigDecimal money2,
			BigDecimal money3, BigDecimal money4, Integer auditId, Integer resourceId, Long registTime, Integer state,
			String gartenName, String name, String phoneNumber, String remark, Integer gradeCount, Integer classCount,
			String reason) {
		super(resource, contractNumber, province, city, countries, workerCount, babyCount, equipment, money1, money2, money3,
				money4, auditId, resourceId, registTime, state, gartenName, name, phoneNumber, remark, gradeCount, classCount,
				reason);
		// TODO Auto-generated constructor stub
	}

	


	
	
	
}
