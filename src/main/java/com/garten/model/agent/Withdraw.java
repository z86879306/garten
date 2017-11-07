package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Withdraw {
	
	private BigDecimal price;
	private Long registTime;
	private Integer withdrawId;
	private Integer agentId;
	private Integer receiveType;
	private String card;
	private String cardName;
	private Integer state;
	private String mark;
	private Integer employeeNo;
	@Override
	public String toString() {
		return "Withdraw [price=" + price + ", registTime=" + registTime + ", withdrawId=" + withdrawId + ", agentId="
				+ agentId + ", receiveType=" + receiveType + ", card=" + card + ", cardName=" + cardName + ", state="
				+ state + ", mark=" + mark + ", employeeNo=" + employeeNo + "]";
	}
	public Withdraw() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Withdraw(BigDecimal price, Long registTime, Integer withdrawId, Integer agentId, Integer receiveType,
			String card, String cardName, Integer state, String mark, Integer employeeNo) {
		super();
		this.price = price;
		this.registTime = registTime;
		this.withdrawId = withdrawId;
		this.agentId = agentId;
		this.receiveType = receiveType;
		this.card = card;
		this.cardName = cardName;
		this.state = state;
		this.mark = mark;
		this.employeeNo = employeeNo;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public Integer getWithdrawId() {
		return withdrawId;
	}
	public void setWithdrawId(Integer withdrawId) {
		this.withdrawId = withdrawId;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getReceiveType() {
		return receiveType;
	}
	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(Integer employeeNo) {
		this.employeeNo = employeeNo;
	}
	

}
