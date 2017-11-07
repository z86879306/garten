package com.garten.vo.smallcontrol;
//order+订单人名+订单人手机号
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.garten.model.other.Order;

public class OrderAll  extends Order{
	//家长,或园长
	private String  name;
	private String phoneNumber;
	private String  head;
	//宝宝
	private String  babyName;
	private String  babyHead;
	@Override
	public String toString() {
		return "OrderAll [name=" + name + ", phoneNumber=" + phoneNumber + ", head=" + head + ", babyName=" + babyName
				+ ", babyHead=" + babyHead + "]";
	}
	public OrderAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderAll(Long orderNumber, Long orderTime, Integer orderId, String job, BigDecimal orderMoney,
			String orderDetail, Integer id, Integer type, Integer payType, Integer state, Integer monthCount,
			Integer relateId, Integer gartenId) {
		super(orderNumber, orderTime, orderId, job, orderMoney, orderDetail, id, type, payType, state, monthCount, relateId,
				gartenId);
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
			Integer relateId, Integer gartenId, String name, String phoneNumber, String head, String babyName,
			String babyHead) {
		super(orderNumber, orderTime, orderId, job, orderMoney, orderDetail, id, type, payType, state, monthCount,
				relateId, gartenId);
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.head = head;
		this.babyName = babyName;
		this.babyHead = babyHead;
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
	public String getBabyName() {
		return babyName;
	}
	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	public String getBabyHead() {
		return babyHead;
	}
	public void setBabyHead(String babyHead) {
		this.babyHead = babyHead;
	}
	
	

}

