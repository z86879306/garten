package com.garten.model.other;

import java.sql.Timestamp;

public class CardReturn {

	private Integer returnId;
	private Integer cardId;
	private String outCard;
	private Integer returnMoney;
	private Long returnTime;
	private String name;
	public Integer getReturnId() {
		return returnId;
	}
	public void setReturnId(Integer returnId) {
		this.returnId = returnId;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public String getOutCard() {
		return outCard;
	}
	public void setOutCard(String outCard) {
		this.outCard = outCard;
	}
	public Integer getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(Integer returnMoney) {
		this.returnMoney = returnMoney;
	}
	public Long getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Timestamp returnTime) {
		this.returnTime = returnTime.getTime();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CardReturn(Integer returnId, Integer cardId, String outCard, Integer returnMoney, Long returnTime,
			 String name) {
		super();
		this.returnId = returnId;
		this.cardId = cardId;
		this.outCard = outCard;
		this.returnMoney = returnMoney;
		this.returnTime = returnTime;
		this.name = name;
	}
	public CardReturn() {
		super();
	}
	
	
	
}
