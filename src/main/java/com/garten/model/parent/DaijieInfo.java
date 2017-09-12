package com.garten.model.parent;

import java.sql.Timestamp;

public class DaijieInfo {
	
	private Integer parentId;
	private String phoneNumber;
	private String daijieName;
	private String relation;
	private String daijieHead;
	private String daijiePhoneNumber;
	private Long arrivedTime;
	private Long time;
	private String remark;
	private Integer daijieState;
	private Integer daijieId;
	private Integer babyId;
	
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
	}
	public DaijieInfo(Integer parentId, String phoneNumber, String daijieName, String relation, String daijieHead,
			String daijiePhoneNumber, Long arrivedTime, Long time, String remark, Integer daijieState, Integer daijieId,
			Integer babyId) {
		super();
		this.parentId = parentId;
		this.phoneNumber = phoneNumber;
		this.daijieName = daijieName;
		this.relation = relation;
		this.daijieHead = daijieHead;
		this.daijiePhoneNumber = daijiePhoneNumber;
		this.arrivedTime = arrivedTime;
		this.time = time;
		this.remark = remark;
		this.daijieState = daijieState;
		this.daijieId = daijieId;
		this.babyId = babyId;
	}
	public Integer getDaijieId() {
		return daijieId;
	}
	public void setDaijieId(Integer daijieId) {
		this.daijieId = daijieId;
	}
	public DaijieInfo(Integer parentId, String phoneNumber, String daijieName, String relation, String daijieHead,
			String daijiePhoneNumber, Long arrivedTime, Long time, String remark, Integer daijieState, Integer daijieId) {
		super();
		this.parentId = parentId;
		this.phoneNumber = phoneNumber;
		this.daijieName = daijieName;
		this.relation = relation;
		this.daijieHead = daijieHead;
		this.daijiePhoneNumber = daijiePhoneNumber;
		this.arrivedTime = arrivedTime;
		this.time = time;
		this.remark = remark;
		this.daijieState = daijieState;
		this.daijieId = daijieId;
	}
	@Override
	public String toString() {
		return "DaijieInfo [parentId=" + parentId + ", phoneNumber=" + phoneNumber + ", daijieName=" + daijieName
				+ ", relation=" + relation + ", daijieHead=" + daijieHead + ", daijiePhoneNumber=" + daijiePhoneNumber
				+ ", arrivedTime=" + arrivedTime + ", time=" + time + ", remark=" + remark + ", daijieState="
				+ daijieState + "]";
	}
	public DaijieInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DaijieInfo(Integer parentId, String phoneNumber, String daijieName, String relation, String daijieHead,
			String daijiePhoneNumber, Long arrivedTime, Long time, String remark, Integer daijieState) {
		super();
		this.parentId = parentId;
		this.phoneNumber = phoneNumber;
		this.daijieName = daijieName;
		this.relation = relation;
		this.daijieHead = daijieHead;
		this.daijiePhoneNumber = daijiePhoneNumber;
		this.arrivedTime = arrivedTime;
		this.time = time;
		this.remark = remark;
		this.daijieState = daijieState;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDaijieName() {
		return daijieName;
	}
	public void setDaijieName(String daijieName) {
		this.daijieName = daijieName;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getDaijieHead() {
		return daijieHead;
	}
	public void setDaijieHead(String daijieHead) {
		this.daijieHead = daijieHead;
	}
	public String getDaijiePhoneNumber() {
		return daijiePhoneNumber;
	}
	public void setDaijiePhoneNumber(String daijiePhoneNumber) {
		this.daijiePhoneNumber = daijiePhoneNumber;
	}
	public Long getArrivedTime() {
		return arrivedTime;
	}
	public void setArrivedTime(Timestamp arrivedTime) {
		this.arrivedTime = arrivedTime.getTime()/1000;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDaijieState() {
		return daijieState;
	}
	public void setDaijieState(Integer daijieState) {
		this.daijieState = daijieState;
	}
	
	
	

	
}
