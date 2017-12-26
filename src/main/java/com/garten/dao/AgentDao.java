package com.garten.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.garten.model.agent.AgentAudit;
import com.garten.model.agent.AgentAuditDetail;
import com.garten.model.agent.AgentInfo;
import com.garten.model.agent.AgentOrder;
import com.garten.model.agent.SaleService;
import com.garten.model.agent.Withdraw;
import com.garten.model.agent.WithdrawAll;
import com.garten.model.agent.WuliaoOrder;
import com.garten.model.garten.GartenInfo;
import com.garten.vo.agent.AgentInfoAndGarten;
import com.garten.vo.agent.AgentVisitDetail;
import com.garten.vo.agent.GartenCountTongJi;
import com.garten.vo.agent.GartenSimple;

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
			@Param("contractNumber") String contractNumber,@Param("contractStart")String contractStart,@Param("contractEnd") String contractEnd,
			@Param("province")String province,@Param("city")String city,@Param("countries")String countries ,@Param("address")String address, @Param("charge")Double charge);

	List<AgentAudit> findAgentAudit(Integer agentId);

	void cancelApply(Map<String, Object> param);

	void addApplyGarten(@Param("resource")Integer resource,@Param("gartenName")String gartenName, @Param("name")String name, @Param("phoneNumber")String phoneNumber,@Param("contractNumber") String contractNumber,@Param("province") String province,
			@Param("city")String city,@Param("countries") String countries,@Param("workerCount")Integer workerCount, @Param("babyCount")Integer babyCount, @Param("gradeCount")Integer gradeCount, @Param("classCount")Integer classCount,@Param("money1") Double money1,
			@Param("equipment")String equipment,@Param("agentId")Integer agentId,@Param("remark")String remark,@Param("gartenType")Integer gartenType);

	List<AgentVisitDetail> getAgentVisit(@Param("agentId")Integer agentId,@Param("gartenId") Integer gartenId);

	List<AgentAuditDetail> getChildAgentBusiness(Integer agentId);

	void addVisit(@Param("agentId")Integer agentId,@Param("gartenId") Integer gartenId, @Param("title")String title,@Param("content") String content);

	List<GartenInfo> findGartenById(Integer agentId);

	List<GartenSimple> getGartenNameAndId(Integer agentId);

	List<GartenCountTongJi> FindParentByGartenId(Integer gartenId);

	void deleteVisit(Integer visitId);

	AgentInfo findAgentById(Integer agentId);
	
	List<WuliaoOrder> findWuliaoOrder(Map<String, Object> param);

	void addWuliaoOrder(WuliaoOrder wuliaoOrder);

	void deleteWuliaoOrder(Integer wuliaoId);

	SaleService findSaleServiceByOne(Map<String, Object> param);

	void addSaleService(Map<String, Object> param);

	AgentInfo findAgentByAgentId(Integer agentId);

	void deleteSaleService(Long saleServiceId);
	
	void addWithdraw(Map<String, Object> param);

	List<WithdrawAll> findWithdraw(Map<String, Object> param);

	void updateAgentCard(Map<String, Object> param);
	
	void addAgentOrder(AgentOrder a);

	void updateAgentOrder(String orderNumber);

	AgentOrder findAgentOrderByOrderNumber(Long orderNumber);
	
	AgentOrder findAgentOrderByOrderNumber(String orderNumber);

	void updateAgentCredit(Map<String, Object> param);

	List<AgentOrder> findAgentOrderByAgentId(Integer agentId);

	void deleteAgentOrderByOrderNumber(Long orderNumber);
	
	Withdraw findWithdrawById(Integer withdrawId);

	AgentInfo findAgentbyPhoneNumber(String phoneNumber);


}
