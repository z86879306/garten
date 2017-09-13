package com.garten.Thread;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.SmallcontrolService;

public class DeleteParentThread implements Runnable{

	
	private Integer parentId;
	

	public DeleteParentThread(Integer parentId) {
		this.parentId = parentId;
	}


	@Override
	public void run() {
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		SmallcontrolService  smallcontrolService = (SmallcontrolService)context.getBean("smallcontrolService");
		smallcontrolService.deleteParentById(parentId);
	}

}
