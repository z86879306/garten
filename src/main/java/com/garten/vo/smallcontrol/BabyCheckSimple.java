package com.garten.vo.smallcontrol;

public class BabyCheckSimple {

	private Integer babyId;
	private Integer gartenId;
	private Long time;
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public BabyCheckSimple(Integer babyId, Integer gartenId, Long time) {
		super();
		this.babyId = babyId;
		this.gartenId = gartenId;
		this.time = time;
	}
	
	
}
