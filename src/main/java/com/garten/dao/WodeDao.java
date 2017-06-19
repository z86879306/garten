package com.garten.dao;

import java.util.List;

import com.garten.model.User;
import com.garten.model.agent.AgentBusinessInfo;

public interface WodeDao {

	List<AgentBusinessInfo> find();

}
