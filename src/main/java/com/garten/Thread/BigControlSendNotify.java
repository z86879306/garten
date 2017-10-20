package com.garten.Thread;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.model.company.Employee;
import com.garten.model.worker.WorkerInfo;
import com.garten.service.BigcontrolService;

public class BigControlSendNotify  implements Runnable {

	private Integer[] gartenIds;
	private Integer type;
	private String title;
	private String info;
	private Employee employee;
	


	public BigControlSendNotify(Integer[] gartenIds, Integer type, String title, String info, Employee employee) {
		super();
		this.gartenIds = gartenIds;
		this.type = type;
		this.title = title;
		this.info = info;
		this.employee = employee;
	}



	@Override
	public void run() {
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		BigcontrolService  bigcontrolService = (BigcontrolService)context.getBean("bigcontrolService");
		bigcontrolService.bigControlSendNotify(gartenIds, type, title, info,employee);
		
	}

}
