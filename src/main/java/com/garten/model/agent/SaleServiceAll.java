package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.garten.model.garten.GartenInfo;

public class SaleServiceAll extends SaleService{
	
	private  AgentInfo agent;
	private GartenInfo garten;
	@Override
	public String toString() {
		return "SaleServiceAll [agent=" + agent + ", garten=" + garten + "]";
	}
	public SaleServiceAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SaleServiceAll(String title, Integer agentId, Integer gartenId, String content, String mark, Integer state,
			Long saleServiceId, String reply, Long replyTime) {
		super(title, agentId, gartenId, content, mark, state, saleServiceId, reply, replyTime);
		// TODO Auto-generated constructor stub
	}
	public SaleServiceAll(AgentInfo agent, GartenInfo garten) {
		super();
		this.agent = agent;
		this.garten = garten;
	}
	public AgentInfo getAgent() {
		return agent;
	}
	public void setAgent(AgentInfo agent) {
		this.agent = agent;
	}
	public GartenInfo getGarten() {
		return garten;
	}
	public void setGarten(GartenInfo garten) {
		this.garten = garten;
	}
	

}
