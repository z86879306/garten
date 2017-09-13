package com.garten.Thread;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.SmallcontrolService;

public class DeleteTeacherThread implements Runnable {

	private Integer workerId;

	public DeleteTeacherThread(Integer workerId) {
		this.workerId = workerId;
	}

	@Override
	public void run() {
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		SmallcontrolService  smallcontrolService = (SmallcontrolService)context.getBean("smallcontrolService");
		smallcontrolService.deleteTeacherById(workerId);
	}
	
	
}
