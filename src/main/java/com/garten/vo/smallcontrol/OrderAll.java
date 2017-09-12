package com.garten.vo.smallcontrol;
//order+订单人名+订单人手机号
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.garten.model.other.Order;

public class OrderAll  extends Order{
	
	private String  name;
	private String phoneNumber;
	private String  head;
	@Override
	public String toString() {
		return "OrderAll [name=" + name + ", phoneNumber=" + phoneNumber + ", head=" + head + "]";
	}
	public OrderAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderAll(Long orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id, Integer type, Integer payType, Integer state, Integer monthCount,
			Integer relateId) {
		super(orderNumber, orderTime, orderId, job, orderMoney, orderDetail, id, type, payType, state, monthCount, relateId);
		// TODO Auto-generated constructor stub
	}
	public OrderAll(Long orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id, Integer type, Integer payType, Integer state, Integer monthCount) {
		super(orderNumber, orderTime, orderId, job, orderMoney, orderDetail, id, type, payType, state, monthCount);
		// TODO Auto-generated constructor stub
	}
	public OrderAll(Long orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id, Integer type, Integer payType, Integer state) {
		super(orderNumber, orderTime, orderId, job, orderMoney, orderDetail, id, type, payType, state);
		// TODO Auto-generated constructor stub
	}
	public OrderAll(Long orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id, Integer type, Integer payType, Integer state, Integer monthCount,
			Integer relateId, String name, String phoneNumber, String head) {
		super(orderNumber, orderTime, orderId, job, orderMoney, orderDetail, id, type, payType, state, monthCount,
				relateId);
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.head = head;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	
	

}

