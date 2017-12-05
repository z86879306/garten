package com.garten.vo.teacher;

import com.garten.model.gartenClass.GartenClass;
import com.garten.model.worker.WorkerMessageLog;

public class WorkerNameMessage extends WorkerMessageLog{

	private String workerName;

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}


	public WorkerNameMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkerNameMessage(String workerName) {
		super();
		this.workerName = workerName;
	}

	public WorkerNameMessage(Integer messageId, String title, String info, Long registTime, Integer workerId,
			Integer gartenId, Integer state, GartenClass gartenClass) {
		super(messageId, title, info, registTime, workerId, gartenId, state, gartenClass);
		// TODO Auto-generated constructor stub
	}

	
}
