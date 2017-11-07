package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

public class AgentOrder {
	
	private Long orderNumber;
	private BigDecimal price;
	private String orderDetail;
	private Integer payType;
	private Integer state;
	private Integer agentId;
	@Override
	public String toString() {
		return "AgentOrder [orderNumber=" + orderNumber + ", price=" + price + ", orderDetail=" + orderDetail
				+ ", payType=" + payType + ", state=" + state + ", agentId=" + agentId + "]";
	}
	public AgentOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AgentOrder(Long orderNumber, BigDecimal price, String orderDetail, Integer payType, Integer state,
			Integer agentId) {
		super();
		this.orderNumber = orderNumber;
		this.price = price;
		this.orderDetail = orderDetail;
		this.payType = payType;
		this.state = state;
		this.agentId = agentId;
	}
	public Long getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
}
