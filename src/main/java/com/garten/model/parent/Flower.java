package com.garten.model.parent;

import java.sql.Timestamp;

public class Flower {
	
	private Integer parentId;
	private Integer flowerId;
	private Long time;
	private Integer workerId;
	
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Flower(Integer parentId, Integer flowerId, Long time, Integer workerId) {
		super();
		this.parentId = parentId;
		this.flowerId = flowerId;
		this.time = time;
		this.workerId = workerId;
	}
	@Override
	public String toString() {
		return "Flower [parentId=" + parentId + ", flowerId=" + flowerId + ", time=" + time + "]";
	}
	public Flower() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Flower(Integer parentId, Integer flowerId, Long time) {
		super();
		this.parentId = parentId;
		this.flowerId = flowerId;
		this.time = time;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getFlowerId() {
		return flowerId;
	}
	public void setFlowerId(Integer flowerId) {
		this.flowerId = flowerId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	


}
