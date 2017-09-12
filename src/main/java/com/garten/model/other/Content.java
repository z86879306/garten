package com.garten.model.other;

public class Content {
	
	private String content;
	private String img;
	@Override
	public String toString() {
		return "Content [content=" + content + ", img=" + img + "]";
	}
	public Content() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Content(String content, String img) {
		super();
		this.content = content;
		this.img = img;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
}
