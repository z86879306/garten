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
			Integer count, String equipment, BigDecimal money1, BigDecimal money2, BigDecimal money3, BigDecimal money4,
			Integer auditId, Integer resourceId, Long registTime, Integer state, String gartenName, String name,
			String phoneNumber) {
		super(resource, contractNumber, province, city, countries, count, equipment, money1, money2, money3, money4, auditId,
				resourceId, registTime, state, gartenName, name, phoneNumber);
		// TODO Auto-generated constructor stub
	}

	public AgentAuditMessage(Integer resource, String contractNumber, String province, String city, String countries,
			Integer count, String equipment, BigDecimal money1, BigDecimal money2, BigDecimal money3, BigDecimal money4,
			Integer auditId, Integer resourceId, Long registTime, Integer state, String gartenName) {
		super(resource, contractNumber, province, city, countries, count, equipment, money1, money2, money3, money4, auditId,
				resourceId, registTime, state, gartenName);
		// TODO Auto-generated constructor stub
	}

	public AgentAuditMessage(Integer resource, String contractNumber, String province, String city, String countries,
			Integer count, String equipment, BigDecimal money1, BigDecimal money2, BigDecimal money3, BigDecimal money4,
			Integer auditId, Integer resourceId, Long registTime, Integer state) {
		super(resource, contractNumber, province, city, countries, count, equipment, money1, money2, money3, money4, auditId,
				resourceId, registTime, state);
		// TODO Auto-generated constructor stub
	}
	
	
}
