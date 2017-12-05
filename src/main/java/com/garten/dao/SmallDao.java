package com.garten.dao;


import java.util.List;
import java.util.Map;
import java.util.Set;

import com.garten.model.activity.ActivityDetail;
import com.garten.model.activity.ActivityLog;
import com.garten.model.agent.AgentAudit;
import com.garten.model.agent.AgentInfo;
import com.garten.model.baby.BabyInfo;
import com.garten.model.baby.BabyLeaveLog;
import com.garten.model.baby.BabyPerformanceLog;
import com.garten.model.garten.GartenCharge;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenLesson;
import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.GartenRecipe;
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.gartenClass.GartenClass;
import com.garten.model.other.CheckNumber;
import com.garten.model.other.Comment;
import com.garten.model.other.Feedback;
import com.garten.model.other.InfoLog;
import com.garten.model.other.Order;
import com.garten.model.other.Version;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerLeaveLog;
import com.garten.vo.bigcontrol.AddDetail;
import com.garten.vo.bigcontrol.BabyMessage;
import com.garten.vo.bigcontrol.ClassManageBig;
import com.garten.vo.bigcontrol.WorkerMessage;
import com.garten.vo.parent.ParentInfoCharge;
import com.garten.vo.parent.ParentInfoShort;
import com.garten.vo.teacher.ActivityDetailAll;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.BabyInfoShort;
import com.garten.vo.teacher.BabyPerformanceLogAll;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.Daijie;
import com.garten.vo.teacher.GartenStartEnd;
import com.garten.vo.teacher.Shouye;
import com.garten.vo.teacher.WorkerInfoShort;

public interface SmallDao {

	WorkerInfo findWorkerByPwd(Map<String, Object> param);

	WorkerInfo findWorkerByAccount(String phoneNumber);

	void updatePwdToken(Map<String, Object> putMapParams);

	WorkerInfo findWorkerByToken(String token);


	Integer addtongjiBaby(Map<String, Object> map);

	Integer addtongjiWorker(Map<String, Object> map);

	Integer addtongjiParent(Map<String, Object> map);

	List<AddDetail> adddetailBaby(Map<String, Object> map);

	List<AddDetail> adddetailWorker(Map<String, Object> map);

	List<AddDetail> adddetailParent(Map<String, Object> map);

	Integer deletetongjiBaby(Map<String, Object> map);

	Integer deletetongjiWorker(Map<String, Object> map);

	Integer deletetongjiParent(Map<String, Object> map);

	List<AddDetail> deletedetailBaby(Map<String, Object> putMapParams);

	List<AddDetail> deletedetailWorker(Map<String, Object> putMapParams);

	List<AddDetail> deletedetailParent(Map<String, Object> putMapParams);

	List<GartenInfo> findGartens();

	Set<Long> findCheckLong(Integer gartenId);

	List<BabyCheckLogAll> findBabyCheckByToken(Map<String, Object> putMapParams);

	List<ParentInfo> findParentMessage(Map<String, Object> putMapParams);

	ClassManageBig findBabyByIdBig(Integer babyId);

	List<BabyMessage> findBabyMessage(Map<String, Object> putMapParams);

	List<WorkerMessage> findWorkerMessage(Map<String, Object> putMapParams);

	List<GartenInfo> findGartenMessage(Map<String, Object> putMapParams);

	void updateGarten(Map<String, Object> param);

	List<String> findGartenGrade(Integer gartenId);

	List<GartenClass> findGartenClasses(Map<String, Object> putMapParams);

	List<AgentAudit> findAgentAudit(Integer type);

	void agreeAgentAudit(Integer auditId);

	List<AgentInfo> findAgentMessge(Map<String, Object> map);

	void deleteAgentMessge(Integer agentId);

	void frostAgentMessge(Integer agentId);

	void unfrostAgentMessge(Integer agentId);

	void updateAgentMessge(Map<String, Object> putMapParams);

	List<AgentInfo> findAgentName(Map<String, Object> putMapParams);

	List<AgentAudit> agentPerformance(Map<String, Object> putMapParams);

	List<GartenCharge> gartenCharge(Map<String, Object> putMapParams);

	void addGartenCharge(Map<String, Object> param);

	void changeGartenDredge(Map<String, Object> putMapParams);

	void insertInfoLog(InfoLog infoLog);

	void insertAgent(Map<String, Object> param);

	AgentInfo findAgentMessgeOne(Map<String, Object> putMapParams);

	List<Order> findOrder();

	void insertGartenInfo(Map<String, Object> param);

	void insertWorkerInfo(Map<String, Object> param);

	Integer fingMaxGartenId();

	void insertClass(Map<String, Object> param);

	AgentAudit findAgentAuditOne(Integer auditId);


	void insertAttendanceNo(Map<String, Object> param);

	Integer fingMaxJobId();

	GartenCharge gartenChargeOne(Map<String, Object> putMapParams);

	List<Feedback> findFeedback();

	GartenCharge gartenChargeReal(Map<String, Object> putMapParams);

	void deleteGartenCharge(Integer chargeId);

	void updateGartenTime(Map<String, Object> putMapParams);

	List<ParentInfoCharge> findParentInfoCharge(Integer gartenId);

	

}
