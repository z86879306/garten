package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

public class AgentOrderAll extends AgentOrder{
	
	private AgentInfo agentInfo;

	@Override
	public String toString() {
		return "AgentOrderAll [agentInfo=" + agentInfo + "]";
	}

	public AgentOrderAll() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgentOrderAll(Long orderNumber, BigDecimal price, String orderDetail, Integer payType, Integer state,
			Integer agentId) {
		super(orderNumber, price, orderDetail, payType, state, agentId);
		// TODO Auto-generated constructor stub
	}

	public AgentOrderAll(AgentInfo agentInfo) {
		super();
		this.agentInfo = agentInfo;
	}

	public AgentInfo getAgentInfo() {
		return agentInfo;
	}

	public void setAgentInfo(AgentInfo agentInfo) {
		this.agentInfo = agentInfo;
	}
	
	
	
}
