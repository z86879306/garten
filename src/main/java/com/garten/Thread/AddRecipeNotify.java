package com.garten.Thread;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.SmallcontrolService;
import com.garten.service.WorkerService;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

public class AddRecipeNotify implements Runnable{

	private Integer gartenId;

	public AddRecipeNotify(Integer gartenId) {
		this.gartenId = gartenId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		SmallcontrolService  smallcontrolService = (SmallcontrolService)context.getBean("smallcontrolService");
		
		try {
			smallcontrolService.addrecipeNotify(gartenId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
