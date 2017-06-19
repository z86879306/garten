package com.garten.model.other;

public class SlideShow {
	
	private Integer gartenId;
	private String slidesImg;
	private String slidesNumber;
	@Override
	public String toString() {
		return "SlideShow [gartenId=" + gartenId + ", slidesImg=" + slidesImg + ", slidesNumber=" + slidesNumber + "]";
	}
	public SlideShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SlideShow(Integer gartenId, String slidesImg, String slidesNumber) {
		super();
		this.gartenId = gartenId;
		this.slidesImg = slidesImg;
		this.slidesNumber = slidesNumber;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getSlidesImg() {
		return slidesImg;
	}
	public void setSlidesImg(String slidesImg) {
		this.slidesImg = slidesImg;
	}
	public String getSlidesNumber() {
		return slidesNumber;
	}
	public void setSlidesNumber(String slidesNumber) {
		this.slidesNumber = slidesNumber;
	}
	
}
