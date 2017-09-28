package com.garten.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garten.dao.BigWorkerDao;
import com.garten.dao.WorkerDao;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerMessageLog;
import com.garten.util.md5.CryptographyUtil;
import com.garten.util.myutil.MyUtil;
import com.garten.util.page.MyPage;

@Service("bigWorkerService")
public class BigWorkerService {

	@Autowired
	private WorkerDao workerDao;
	@Autowired
	private BigWorkerDao bigWorkerDao;
	
	public Map<String,Object> login(String phoneNumber, String pwd){
		
		Map<String, Object> params = MyUtil.putMapParams("phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc"));
		WorkerInfo workerInfo = workerDao.findWorkerByPwd(params);
		Map<String, Object> result = MyUtil.putMapParams("state", 0);
		if(null!=workerInfo){
			MyUtil.putMapParams(result, "state", 1, "info", workerInfo);
		}
		return result;
	}
	
	public Map<String,Object> applySendMessage(String token,String title, String info){
		Map<String, Object> result = MyUtil.putMapParams("state", 0);
		WorkerInfo workerInfo = workerDao.findWorkerInfoByToken(token);		//根据token获取老师信息
		if(null!=workerInfo){	
			Map<String, Object> params = MyUtil.putMapParams("title", title,"info",info,"workerId",workerInfo.getWorkerId(),"gartenId",workerInfo.getGartenId());
			bigWorkerDao.addWorkerMessage(params);
			MyUtil.putMapParams(result, "state", 1);
		}
		return result;
	}
	
	
	public Map<String,Object> cancelApplyMessage(String token,Integer messageId){
		Map<String, Object> result = MyUtil.putMapParams("state", 0);
		WorkerInfo workerInfo = workerDao.findWorkerInfoByToken(token);		//根据token获取老师信息
		if(null!=workerInfo){	
			bigWorkerDao.cancelApplyMessage(messageId);
			MyUtil.putMapParams(result, "state", 1);
		}
		return result;
	}
	
	public Map<String,Object> applyMessageList(String token,Integer pageNo){
		Map<String, Object> result = MyUtil.putMapParams("state", 0);
		WorkerInfo workerInfo = workerDao.findWorkerInfoByToken(token);		//根据token获取老师信息
		if(null!=workerInfo){	
			List<WorkerMessageLog> list = bigWorkerDao.findApplyMessage(workerInfo.getWorkerId());
			return MyUtil.putMapParams(result, "state", 1, "info", MyPage.listPage16(list, pageNo));
		}
		return result;
	}
}
