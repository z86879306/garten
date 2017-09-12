package com.garten.Thread;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.BigcontrolService;
import com.garten.service.SmallcontrolService;

public class GartenRegisteNotify implements Runnable{

	private String phoneNumber;
	private String[] datas;
	
	public GartenRegisteNotify(String phoneNumber, String[] datas) {
		this.phoneNumber = phoneNumber;
		this.datas = datas;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		BigcontrolService  bigcontrolService = (BigcontrolService)context.getBean("bigcontrolService");
		bigcontrolService.GartenRegisterNotify(datas, phoneNumber);
	}

	
	
}
