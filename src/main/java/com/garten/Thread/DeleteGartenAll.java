package com.garten.Thread;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.BigcontrolService;

public class DeleteGartenAll implements Runnable{

	
	private Integer gartenId;
	
	public DeleteGartenAll(Integer gartenId) {
		super();
		this.gartenId = gartenId;
	}

	@Override
	public void run() {
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		BigcontrolService  bigcontrolService = (BigcontrolService)context.getBean("bigcontrolService");
		bigcontrolService.deleteGartenAll(gartenId);
	}

}
