package com.garten.model.garten;

public class RecipeBase64 {

	private String foodName;
	private String foodContent;
	private String foodImg;
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
	public String getFoodImg() {
		return foodImg;
	}
	public void setFoodImg(String foodImg) {
		this.foodImg = foodImg;
	}
	public RecipeBase64(String foodName, String foodContent, String foodImg) {
		super();
		this.foodName = foodName;
		this.foodContent = foodContent;
		this.foodImg = foodImg;
	}
	public RecipeBase64() {
		super();
	}
	
	
}
