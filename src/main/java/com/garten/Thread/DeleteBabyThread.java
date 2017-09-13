package com.garten.Thread;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.SmallcontrolService;

public class DeleteBabyThread implements Runnable {
	
	private Integer babyId;

	public DeleteBabyThread(Integer babyId) {
		this.babyId = babyId;
	}

	@Override
	public void run() {
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		SmallcontrolService  smallcontrolService = (SmallcontrolService)context.getBean("smallcontrolService");
		smallcontrolService.deleteBabyById(babyId);
	}
	
	
}
