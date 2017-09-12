package com.garten.vo.agent;

import com.garten.model.agent.AgentVisit;

public class AgentVisitDetail extends AgentVisit{

	private String gartenName;

	public String getGartenName() {
		return gartenName;
	}

	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}

	public AgentVisitDetail(Integer agentId, Integer visitId,String title, String content, Integer garentId, Long time, 
			String gartenName) {
		super(agentId, title, content, garentId, time, visitId);
		this.gartenName = gartenName;
	}

	public AgentVisitDetail(Integer agentId, String title, String content, Integer garentId, Long time,
			Integer visitId) {
		super(agentId, title, content, garentId, time, visitId);
	}
	
}
