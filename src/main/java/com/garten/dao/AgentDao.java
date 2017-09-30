package com.garten.dao;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.garten.model.activity.ActivityDetail;
import com.garten.model.activity.ActivityLog;
import com.garten.model.agent.AgentAudit;
import com.garten.model.agent.AgentInfo;
import com.garten.model.agent.SaleService;
import com.garten.model.agent.WuliaoOrder;
import com.garten.model.baby.BabyInfo;
import com.garten.model.baby.BabyLeaveLog;
import com.garten.model.baby.BabyPerformanceLog;
import com.garten.model.garten.GartenCharge;
import com.garten.model.garten.GartenClass;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenLesson;
import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.GartenRecipe;
import com.garten.model.garten.PhotoDianZan;
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
import com.garten.vo.agent.AgentInfoAndGarten;
import com.garten.vo.agent.AgentVisitDetail;
import com.garten.vo.agent.GartenCountTongJi;
import com.garten.vo.agent.GartenSimple;
import com.garten.vo.bigcontrol.AddDetail;
import com.garten.vo.bigcontrol.BabyMessage;
import com.garten.vo.bigcontrol.ClassManageBig;
import com.garten.vo.bigcontrol.WorkerMessage;
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

public interface AgentDao {


	AgentInfo findAgentByPwd(Map<String, Object> param);

	String findToken(Map<String, Object> param);

	AgentInfo findAgentByAccount(String phoneNumber);

	void updatePwdToken(Map<String, Object> putMapParams);

	AgentInfo findAgentInfoByToken(String token);

	List<AgentInfoAndGarten> findProChildAgent(String province);

	List<AgentInfoAndGarten> findCityChildAgent(String city);

	List<GartenInfo> findGartenByAgent(@Param("name")String name,@Param("phoneNumber") String phoneNumber,@Param("agentId")Integer agentId);

	void frostGarten(Integer gartenId);

	void recoverGarten(Integer gartenId);

	void updateGarten(@Param("gartenId")Integer gartenId, @Param("gartenName")String gartenName,@Param("name") String name, @Param("phoneNumber")String phoneNumber,
			@Param("contractNumber") String contractNumber,@Param("contractStart")String contractStart,@Param("contractEnd") String contractEnd,@Param("organizationCode") String organizationCode,
			@Param("province")String province,@Param("city")String city,@Param("countries")String countries ,@Param("address")String address, @Param("charge")Double charge);

	List<AgentAudit> findAgentAudit(Integer agentId);

	void cancelApply(String auditId);

	void addApplyGarten(@Param("gartenName")String gartenName, @Param("name")String name, @Param("phoneNumber")String phoneNumber,@Param("contractNumber") String contractNumber,@Param("province") String province,
			@Param("city")String city,@Param("countries") String countries, @Param("count")Integer count,@Param("money") Double money, @Param("equipment")String equipment,@Param("agentId")Integer agentId);


	List<AgentVisitDetail> getAgentVisit(@Param("agentId")Integer agentId,@Param("gartenId") Integer gartenId);

	List<AgentAudit> getChildAgentBusiness(Integer agentId);

	void addVisit(@Param("agentId")Integer agentId,@Param("gartenId") Integer gartenId, @Param("title")String title,@Param("content") String content);

	List<GartenInfo> findGartenById(Integer agentId);

	List<GartenSimple> getGartenNameAndId(Integer agentId);

	List<GartenCountTongJi> FindParentByGartenId(Integer gartenId);

	void deleteVisit(Integer visitId);

	AgentInfo findAgentById(Integer agent);
	
	List<WuliaoOrder> findWuliaoOrder(Map<String, Object> param);

	void addWuliaoOrder(Map<String, Object> param);

	void deleteWuliaoOrder(Integer wuliaoId);

	SaleService findSaleServiceByOne(Map<String, Object> param);

	void addSaleService(Map<String, Object> param);

	AgentInfo findAgentByAgentId(Integer agentId);
	
}
