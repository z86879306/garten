package com.garten.Thread;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.SmallcontrolService;
import com.garten.service.WorkerService;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

public class PushNotify implements Runnable{

	private Integer gartenId;
	private Integer classId;
	private String content;
	private Integer type;

	public PushNotify(Integer gartenId, Integer classId, String content, Integer type) {
		super();
		this.gartenId = gartenId;
		this.classId = classId;
		this.content = content;
		this.type = type;
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		SmallcontrolService  smallcontrolService = (SmallcontrolService)context.getBean("smallcontrolService");
		
		try {
			smallcontrolService.pushNotify(gartenId,classId,content,type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
