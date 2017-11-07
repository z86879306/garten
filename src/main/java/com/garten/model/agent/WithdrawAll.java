package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class WithdrawAll extends Withdraw{
	
	private AgentInfo agentInfo;

	@Override
	public String toString() {
		return "WithdrawAll [agentInfo=" + agentInfo + "]";
	}

	public WithdrawAll() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public WithdrawAll(BigDecimal price, Long registTime, Integer withdrawId, Integer agentId, Integer receiveType,
			String card, String cardName, Integer state, String mark, Integer employeeNo) {
		super(price, registTime, withdrawId, agentId, receiveType, card, cardName, state, mark, employeeNo);
		// TODO Auto-generated constructor stub
	}

	public WithdrawAll(AgentInfo agentInfo) {
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
