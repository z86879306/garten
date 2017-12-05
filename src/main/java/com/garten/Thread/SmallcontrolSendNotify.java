package com.garten.Thread;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.model.worker.WorkerInfo;
import com.garten.service.SmallcontrolService;

public class SmallcontrolSendNotify implements Runnable{

	
	private Integer classId;
	private Integer gradeId ;
	private Integer type ;
	private String title;
	private String content;
	private WorkerInfo workerInfo;
	
	
	public SmallcontrolSendNotify(Integer classId, Integer gradeId, Integer type, String title, String content,
			WorkerInfo workerInfo) {
		super();
		this.classId = classId;
		this.gradeId = gradeId;
		this.type = type;
		this.title = title;
		this.content = content;
		this.workerInfo = workerInfo;
	}


	@Override
	public void run() {
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		SmallcontrolService  smallcontrolService = (SmallcontrolService)context.getBean("smallcontrolService");
		smallcontrolService.sendNotifySmall(classId, gradeId, type, title, content, workerInfo);		
	}

}
