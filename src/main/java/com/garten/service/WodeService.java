package com.garten.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garten.dao.WodeDao;
import com.garten.model.agent.AgentBusinessInfo;
import com.garten.util.page.MyPage;


@Service
public class WodeService {

	@Autowired
	private WodeDao wodeDao;
	
	public Map<String,Object> find(HttpServletRequest request,Integer pageNo ) {//服务器端接收客户端的一个页码  
		Map<String,Object> map=new HashMap<String,Object>();
		List<AgentBusinessInfo> list=(List<AgentBusinessInfo>) wodeDao.find();
		map.put("result", MyPage.listPage(list, pageNo));
		return map;
	}
	

	


}
