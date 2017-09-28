package com.garten.vo.teacher;

import com.garten.model.worker.WorkerMessageLog;

public class WorkerNameMessage extends WorkerMessageLog{

	private String workerName;

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public WorkerNameMessage(Integer messageId, String title, String info, Long registTime, Integer workerId,
			Integer gartenId, Integer state, String workerName) {
		super(messageId, title, info, registTime, workerId, gartenId, state);
		this.workerName = workerName;
	}

	public WorkerNameMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkerNameMessage(Integer messageId, String title, String info, Long registTime, Integer workerId,
			Integer gartenId, Integer state) {
		super(messageId, title, info, registTime, workerId, gartenId, state);
		// TODO Auto-generated constructor stub
	}
	
	
}
