package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class SaleService {
	
	private  String title;
	private Integer agentId;
	private Integer gartenId;
	private String content;
	private String mark;
	private Integer state;
	private Long saleServiceId;
	private String  reply;
	private Long  replyTime;
	@Override
	public String toString() {
		return "SaleService [title=" + title + ", agentId=" + agentId + ", gartenId=" + gartenId + ", content="
				+ content + ", mark=" + mark + ", state=" + state + ", saleServiceId=" + saleServiceId + ", reply="
				+ reply + ", replyTime=" + replyTime + "]";
	}
	public SaleService() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SaleService(String title, Integer agentId, Integer gartenId, String content, String mark, Integer state,
			Long saleServiceId, String reply, Long replyTime) {
		super();
		this.title = title;
		this.agentId = agentId;
		this.gartenId = gartenId;
		this.content = content;
		this.mark = mark;
		this.state = state;
		this.saleServiceId = saleServiceId;
		this.reply = reply;
		this.replyTime = replyTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getSaleServiceId() {
		return saleServiceId;
	}
	public void setSaleServiceId(Long saleServiceId) {
		this.saleServiceId = saleServiceId;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public Long getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Long replyTime) {
		this.replyTime = replyTime;
	}
	
	

	


}
