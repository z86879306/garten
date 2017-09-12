package com.garten.model.other;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
	
	private Long orderNumber;
	private Long orderTime;
	private Integer orderId;
	private String job;
	private BigDecimal orderMoney;
	private String orderDetail;
	private Integer id;
	private Integer type;
	private Integer payType;
	private Integer state;
	private Integer monthCount;
	private Integer relateId;
	private Integer gartenId;
	
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Order(Long orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id, Integer type, Integer payType, Integer state, Integer monthCount,
			Integer relateId, Integer gartenId) {
		super();
		this.orderNumber = orderNumber;
		this.orderTime = orderTime;
		this.orderId = orderId;
		this.job = job;
		this.orderMoney = orderMoney;
		this.orderDetail = orderDetail;
		this.id = id;
		this.type = type;
		this.payType = payType;
		this.state = state;
		this.monthCount = monthCount;
		this.relateId = relateId;
		this.gartenId = gartenId;
	}
	public Integer getRelateId() {
		return relateId;
	}
	public void setRelateId(Integer relateId) {
		this.relateId = relateId;
	}
	public Order(Long orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id, Integer type, Integer payType, Integer state, Integer monthCount,
			Integer relateId) {
		super();
		this.orderNumber = orderNumber;
		this.orderTime = orderTime;
		this.orderId = orderId;
		this.job = job;
		this.orderMoney = orderMoney;
		this.orderDetail = orderDetail;
		this.id = id;
		this.type = type;
		this.payType = payType;
		this.state = state;
		this.monthCount = monthCount;
		this.relateId = relateId;
	}
	public Integer getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}
	public Order(Long orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id, Integer type, Integer payType, Integer state, Integer monthCount) {
		super();
		this.orderNumber = orderNumber;
		this.orderTime = orderTime;
		this.orderId = orderId;
		this.job = job;
		this.orderMoney = orderMoney;
		this.orderDetail = orderDetail;
		this.id = id;
		this.type = type;
		this.payType = payType;
		this.state = state;
		this.monthCount = monthCount;
	}
	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", orderTime=" + orderTime + ", orderId=" + orderId + ", job="
				+ job + ", orderMoney=" + orderMoney + ", orderDetail=" + orderDetail + ", id=" + id + ", type=" + type
				+ ", payType=" + payType + ", state=" + state + "]";
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(Long orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id, Integer type, Integer payType, Integer state) {
		super();
		this.orderNumber = orderNumber;
		this.orderTime = orderTime;
		this.orderId = orderId;
		this.job = job;
		this.orderMoney = orderMoney;
		this.orderDetail = orderDetail;
		this.id = id;
		this.type = type;
		this.payType = payType;
		this.state = state;
	}
	public Long getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Long getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime.getTime()/1000;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public BigDecimal getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	
	
	
	

}

