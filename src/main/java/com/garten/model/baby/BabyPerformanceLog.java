package com.garten.model.baby;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BabyPerformanceLog {
	
	private Integer gartenId;
	private Integer babyId;
	private Long time;
	private BigDecimal learn;
	private BigDecimal play;
	private BigDecimal eat;
	private BigDecimal sleep;
	private String remark;
	@Override
	public String toString() {
		return "BabyPerformanceLog [gartenId=" + gartenId + ", babyId=" + babyId + ", time=" + time + ", learn=" + learn
				+ ", play=" + play + ", eat=" + eat + ", sleep=" + sleep + ", remark=" + remark + "]";
	}
	public BabyPerformanceLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyPerformanceLog(Integer gartenId, Integer babyId, Long time, BigDecimal learn, BigDecimal play,
			BigDecimal eat, BigDecimal sleep, String remark) {
		super();
		this.gartenId = gartenId;
		this.babyId = babyId;
		this.time = time;
		this.learn = learn;
		this.play = play;
		this.eat = eat;
		this.sleep = sleep;
		this.remark = remark;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime();
	}
	public BigDecimal getLearn() {
		return learn;
	}
	public void setLearn(BigDecimal learn) {
		this.learn = learn;
	}
	public BigDecimal getPlay() {
		return play;
	}
	public void setPlay(BigDecimal play) {
		this.play = play;
	}
	public BigDecimal getEat() {
		return eat;
	}
	public void setEat(BigDecimal eat) {
		this.eat = eat;
	}
	public BigDecimal getSleep() {
		return sleep;
	}
	public void setSleep(BigDecimal sleep) {
		this.sleep = sleep;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	
	
	

}
