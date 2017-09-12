package com.garten.Thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.garten.service.WorkerService;
import com.garten.util.lxcutil.MyParamAll;

public class HuanXinThread implements Runnable{
	
	private Integer type;
	private Integer jobId;
	
	public HuanXinThread(Integer type, Integer jobId) {
		this.type = type;
		this.jobId = jobId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
		WorkerService  workerService = (WorkerService)context.getBean("workerService");
		
		try {
			workerService.regist(MyParamAll.ACCESS_TOKEN, type, jobId);
			workerService.addFriend(MyParamAll.ACCESS_TOKEN, type, jobId);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
