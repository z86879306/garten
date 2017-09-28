package com.garten.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garten.service.BigWorkerService;

@Controller
@RequestMapping("bigWorker")
public class BigWorkerController {

	@Autowired
	private BigWorkerService bigworkerService;
	
	//登录
	@RequestMapping("login")
	@ResponseBody
	public Map<String,Object> login(String phoneNumber, String pwd){
		Map<String, Object> map = bigworkerService.login(phoneNumber, pwd);
		return map;
	}
	
	//申请发送通知
	@RequestMapping("applySendMessage")
	@ResponseBody
	public Map<String,Object> applySendMessage(String token,String title, String info ){
		Map<String, Object> map = bigworkerService.applySendMessage(token, title, info);
		return map;
	}
	
	//取消申请
	@RequestMapping("cancelApplyMessage")
	@ResponseBody
	public Map<String,Object> cancelApplyMessage(String token,Integer messageId){
		Map<String, Object> map = bigworkerService.cancelApplyMessage(token, messageId);
		return map;
	}
	
	//已申请发送列表
	@RequestMapping("applyMessageList")
	@ResponseBody
	public Map<String,Object> applyMessageList(String token,Integer pageNo){
		Map<String, Object> map = bigworkerService.applyMessageList(token, pageNo);
		return map;
	}
}
