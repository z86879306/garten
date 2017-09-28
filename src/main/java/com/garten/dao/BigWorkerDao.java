package com.garten.dao;

import java.util.List;
import java.util.Map;

import com.garten.model.worker.WorkerMessageLog;

public interface BigWorkerDao {

	void addWorkerMessage(Map<String, Object> params);

	void cancelApplyMessage(Integer messageId);

	List<WorkerMessageLog> findApplyMessage(Integer workerId);

	WorkerMessageLog findMessageById(Integer messageId);
}
