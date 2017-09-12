package com.garten.vo.teacher;

import java.sql.Timestamp;

public class FlowerAll {
	
	private String name;
	private String headimg;
	private Long time;
	private Integer flowerId;
	@Override
	public String toString() {
		return "FlowerAll [name=" + name + ", headimg=" + headimg + ", time=" + time + ", flowerId=" + flowerId + "]";
	}
	public FlowerAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FlowerAll(String name, String headimg, Long time, Integer flowerId) {
		super();
		this.name = name;
		this.headimg = headimg;
		this.time = time;
		this.flowerId = flowerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	public Integer getFlowerId() {
		return flowerId;
	}
	public void setFlowerId(Integer flowerId) {
		this.flowerId = flowerId;
	}
	
	
}
