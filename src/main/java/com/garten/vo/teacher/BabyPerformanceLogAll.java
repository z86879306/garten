package com.garten.vo.teacher;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BabyPerformanceLogAll {
	
	private Integer gartenId;
	private Integer babyId;
	private Long time;
	private BigDecimal learn;
	private BigDecimal play;
	private BigDecimal eat;
	private BigDecimal sleep;
	private String remark;
	private Integer performanceId;
	private String babyName;
	private String babyHead;

	public String getBabyHead() {
		return babyHead;
	}
	public void setBabyHead(String babyHead) {
		this.babyHead = babyHead;
	}
	public BabyPerformanceLogAll(Integer gartenId, Integer babyId, Long time, BigDecimal learn, BigDecimal play,
			BigDecimal eat, BigDecimal sleep, String remark, Integer performanceId, String babyName, String babyHead) {
		super();
		this.gartenId = gartenId;
		this.babyId = babyId;
		this.time = time;
		this.learn = learn;
		this.play = play;
		this.eat = eat;
		this.sleep = sleep;
		this.remark = remark;
		this.performanceId = performanceId;
		this.babyName = babyName;
		this.babyHead = babyHead;
	}
	public String getBabyName() {
		return babyName;
	}
	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	public BabyPerformanceLogAll(Integer gartenId, Integer babyId, Long time, BigDecimal learn, BigDecimal play,
			BigDecimal eat, BigDecimal sleep, String remark, Integer performanceId, String babyName) {
		super();
		this.gartenId = gartenId;
		this.babyId = babyId;
		this.time = time;
		this.learn = learn;
		this.play = play;
		this.eat = eat;
		this.sleep = sleep;
		this.remark = remark;
		this.performanceId = performanceId;
		this.babyName = babyName;
	}
	public Integer getPerformanceId() {
		return performanceId;
	}
	public void setPerformanceId(Integer performanceId) {
		this.performanceId = performanceId;
	}
	public BabyPerformanceLogAll(Integer gartenId, Integer babyId, Long time, BigDecimal learn, BigDecimal play,
			BigDecimal eat, BigDecimal sleep, String remark, Integer performanceId) {
		super();
		this.gartenId = gartenId;
		this.babyId = babyId;
		this.time = time;
		this.learn = learn;
		this.play = play;
		this.eat = eat;
		this.sleep = sleep;
		this.remark = remark;
		this.performanceId = performanceId;
	}
	@Override
	public String toString() {
		return "BabyPerformanceLog [gartenId=" + gartenId + ", babyId=" + babyId + ", time=" + time + ", learn=" + learn
				+ ", play=" + play + ", eat=" + eat + ", sleep=" + sleep + ", remark=" + remark + "]";
	}
	public BabyPerformanceLogAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyPerformanceLogAll(Integer gartenId, Integer babyId, Long time, BigDecimal learn, BigDecimal play,
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
		this.time = time.getTime()/1000;
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
