package com.garten.Thread;

import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.WorkerService;

public class XxxThread implements Runnable {
	

	@Override
    public void run() {
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		WorkerService  workerService = (WorkerService)context.getBean("workerService");
		//  List<String> a= workerService.getPhoneNumber();
      // System.err.println(a.get(0)+"恭喜你成功!");
       for(int i=0;i<1000;i++){
    	   System.err.println("我是"+i);
    	   try {
			Thread.sleep(10L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
    }

	

}