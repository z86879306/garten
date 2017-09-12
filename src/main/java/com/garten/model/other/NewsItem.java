package com.garten.model.other;

public class NewsItem {
	
	private String title;
	private String content;
	@Override
	public String toString() {
		return "NewsItem [title=" + title + ", content=" + content + "]";
	}
	public NewsItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NewsItem(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

	
}
