package com.garten.Thread;

import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.BigcontrolService;
import com.garten.service.WorkerService;

public class XxxThread implements Runnable {
	

	@Override
    public void run() {//删除所有isvalid =1
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		BigcontrolService  bigcontrolService = (BigcontrolService)context.getBean("bigcontrolService");
		bigcontrolService.deleteIsvalid();
    }

	

}
