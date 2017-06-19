package com.garten.model.garten;

import java.sql.Timestamp;

public class GartenRecipe {
	
	private Integer gartenId;
	private Long time;
	private String foodImg;
	private String foodName;
	private String foodContent;
	@Override
	public String toString() {
		return "GartenRecipe [gartenId=" + gartenId + ", time=" + time + ", foodImg=" + foodImg + ", foodName="
				+ foodName + ", foodContent=" + foodContent + "]";
	}
	public GartenRecipe() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenRecipe(Integer gartenId, Long time, String foodImg, String foodName, String foodContent) {
		super();
		this.gartenId = gartenId;
		this.time = time;
		this.foodImg = foodImg;
		this.foodName = foodName;
		this.foodContent = foodContent;
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
	public void setTime(Timestamp time) {
		this.time = time.getTime();
	}
	public String getFoodImg() {
		return foodImg;
	}
	public void setFoodImg(String foodImg) {
		this.foodImg = foodImg;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getFoodContent() {
		return foodContent;
	}
	public void setFoodContent(String foodContent) {
		this.foodContent = foodContent;
	}
	
}
