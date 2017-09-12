package com.garten.model.other;

import java.util.List;

public class GartenMsg {
	
	private List<String> img;
	private List<String> content;
	@Override
	public String toString() {
		return "GartenMsg [img=" + img + ", content=" + content + "]";
	}
	public GartenMsg() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenMsg(List<String> img, List<String> content) {
		super();
		this.img = img;
		this.content = content;
	}
	public List<String> getImg() {
		return img;
	}
	public void setImg(List<String> img) {
		this.img = img;
	}
	public List<String> getContent() {
		return content;
	}
	public void setContent(List<String> content) {
		this.content = content;
	}

	
	
}
