package com.garten.model.garten;

import java.sql.Timestamp;

public class GartenRecipe  {
	
	private Integer gartenId;
	private Long time;
	private String foodImg;
	private String foodName;
	private String foodContent;
	private Integer recipeId;
	@Override
	public String toString() {
		return "GartenRecipe [gartenId=" + gartenId + ", time=" + time + ", foodImg=" + foodImg + ", foodName="
				+ foodName + ", foodContent=" + foodContent + ", recipeId=" + recipeId + "]";
	}
	public GartenRecipe() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenRecipe(Integer gartenId, Long time, String foodImg, String foodName, String foodContent,
			Integer recipeId) {
		super();
		this.gartenId = gartenId;
		this.time = time;
		this.foodImg = foodImg;
		this.foodName = foodName;
		this.foodContent = foodContent;
		this.recipeId = recipeId;
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
		this.time = time.getTime()/1000;
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
	public Integer getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}

	

}
