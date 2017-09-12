package com.garten.Thread;

import java.text.ParseException;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.WorkerService;

public class InsertCheckThread implements Runnable {

	
	private Integer id;
	private Integer gartenId;
	private Integer type;
	
	

	public InsertCheckThread(Integer id, Integer gartenId, Integer type) {
		this.id = id;
		this.gartenId = gartenId;
		this.type = type;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		WorkerService  workerService = (WorkerService)context.getBean("workerService");
		try {
			if(type==1){
				workerService.insertBabyCheckNew(id, gartenId);
			}else if(type==2){
				workerService.insertTeacherCheck(id, gartenId);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
