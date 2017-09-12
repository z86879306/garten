package com.garten.model.other;

public class Comment {
	
	private Integer infoId;
	private String commentName;
	private String commentContent;
	private Integer replyCommentId;
	private Integer commentId;
	private String replyName;
	private String job;
	private Integer jobId;
	
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public Comment(Integer infoId, String commentName, String commentContent, Integer replyCommentId, Integer commentId,
			String replyName, String job, Integer jobId) {
		super();
		this.infoId = infoId;
		this.commentName = commentName;
		this.commentContent = commentContent;
		this.replyCommentId = replyCommentId;
		this.commentId = commentId;
		this.replyName = replyName;
		this.job = job;
		this.jobId = jobId;
	}
	public String getReplyName() {
		return replyName;
	}
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	public Comment(Integer infoId, String commentName, String commentContent, Integer replyCommentId, Integer commentId,
			String replyName) {
		super();
		this.infoId = infoId;
		this.commentName = commentName;
		this.commentContent = commentContent;
		this.replyCommentId = replyCommentId;
		this.commentId = commentId;
		this.replyName = replyName;
	}
	@Override
	public String toString() {
		return "Comment [infoId=" + infoId + ", commentName=" + commentName + ", commentContent=" + commentContent
				+ ", replyCommentId=" + replyCommentId + ", commentId=" + commentId + "]";
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(Integer infoId, String commentName, String commentContent, Integer replyCommentId,
			Integer commentId) {
		super();
		this.infoId = infoId;
		this.commentName = commentName;
		this.commentContent = commentContent;
		this.replyCommentId = replyCommentId;
		this.commentId = commentId;
	}
	public Integer getInfoId() {
		return infoId;
	}
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
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
	public Integer getReplyCommentId() {
		return replyCommentId;
	}
	public void setReplyCommentId(Integer replyCommentId) {
		this.replyCommentId = replyCommentId;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	
	
}
