package com.garten.model.agent;

import java.sql.Timestamp;

public class AgentVisit {
	private Integer agentId;
	private String title;
	private String content;
	private Integer garentId;
	private Long time;
	private Integer visitId;
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getGarentId() {
		return garentId;
	}
	public void setGarentId(Integer garentId) {
		this.garentId = garentId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime();
	}
	public Integer getVisitId() {
		return visitId;
	}
	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}
	public AgentVisit(Integer agentId, String title, String content, Integer garentId, Long time, Integer visitId) {
		super();
		this.agentId = agentId;
		this.title = title;
		this.content = content;
		this.garentId = garentId;
		this.time = time;
		this.visitId = visitId;
	}
	public AgentVisit() {
		super();
	}
	
}
