package com.garten.vo.bigcontrol;

import com.garten.model.other.Feedback;

public class FeedbackDetail extends Feedback{

	private String gartenName;

	public String getGartenName() {
		return gartenName;
	}

	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}

	public FeedbackDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FeedbackDetail(Integer gartenId, String job, Long time, Integer jobId, String content, String remark,
			String name, String phoneNumber) {
		super(gartenId, job, time, jobId, content, remark, name, phoneNumber);
		// TODO Auto-generated constructor stub
	}

	public FeedbackDetail(Integer gartenId, String job, Long time, Integer jobId, String content, String remark) {
		super(gartenId, job, time, jobId, content, remark);
		// TODO Auto-generated constructor stub
	}
	
	
}
