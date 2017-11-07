package com.garten.vo.bigcontrol;

public class CardSimple {

	private String name;
	private Integer agentId;
	private Integer count;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public CardSimple(String name, Integer agentId, Integer count) {
		super();
		this.name = name;
		this.agentId = agentId;
		this.count = count;
	}
	public CardSimple() {
		super();
	}
	
	
	
}
