package com.garten.model.other;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
	
	private Integer orderNumber;
	private Long orderTime;
	private Integer orderId;
	private String job;
	private BigDecimal orderMoney;
	private String orderDetail;
	private Integer id;
	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", orderTime=" + orderTime + ", orderId=" + orderId + ", job="
				+ job + ", orderMoney=" + orderMoney + ", orderDetail=" + orderDetail + ", id=" + id + "]";
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(Integer orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id) {
		super();
		this.orderNumber = orderNumber;
		this.orderTime = orderTime;
		this.orderId = orderId;
		this.job = job;
		this.orderMoney = orderMoney;
		this.orderDetail = orderDetail;
		this.id = id;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Long getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime.getTime();
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
	
}

