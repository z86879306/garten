package com.garten.model.other;

public class Comment {
	
	private Integer informId;
	private String commentName;
	private String commentContent;
	private Integer upState;
	@Override
	public String toString() {
		return "Comment [informId=" + informId + ", commentName=" + commentName + ", commentContent=" + commentContent
				+ ", upState=" + upState + "]";
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(Integer informId, String commentName, String commentContent, Integer upState) {
		super();
		this.informId = informId;
		this.commentName = commentName;
		this.commentContent = commentContent;
		this.upState = upState;
	}
	public Integer getInformId() {
		return informId;
	}
	public void setInformId(Integer informId) {
		this.informId = informId;
	}
	public String getCommentName() {
		return commentName;
	}
	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public Integer getUpState() {
		return upState;
	}
	public void setUpState(Integer upState) {
		this.upState = upState;
	}
	
	
	

}
