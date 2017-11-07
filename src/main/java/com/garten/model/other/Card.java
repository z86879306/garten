package com.garten.model.other;

public class Card {

	private Integer id;
	private String inCard;
	private String outCard;
	private Integer agentId;
	private Integer gartenId;
	private Integer agentType;
	private Integer returnMoney;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInCard() {
		return inCard;
	}
	public void setInCard(String inCard) {
		this.inCard = inCard;
	}
	public String getOutCard() {
		return outCard;
	}
	public void setOutCard(String outCard) {
		this.outCard = outCard;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getAgentType() {
		return agentType;
	}
	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}
	public Integer getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(Integer returnMoney) {
		this.returnMoney = returnMoney;
	}
	
	public Card(Integer id, String inCard, String outCard, Integer agentId, Integer gartenId, Integer agentType,
			Integer returnMoney) {
		super();
		this.id = id;
		this.inCard = inCard;
		this.outCard = outCard;
		this.agentId = agentId;
		this.gartenId = gartenId;
		this.agentType = agentType;
		this.returnMoney = returnMoney;
	}
	public Card() {
		super();
	}
	
	
	
	
	
	
	
}
