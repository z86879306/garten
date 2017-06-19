package com.garten.dao;


import com.garten.model.worker.WorkerInfo;

public interface TeacherDao {

	public WorkerInfo findUserByToken(String token);
}
