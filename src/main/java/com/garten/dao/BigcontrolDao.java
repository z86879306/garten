package com.garten.dao;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.garten.model.activity.ActivityDetail;
import com.garten.model.activity.ActivityLog;
import com.garten.model.agent.AgentAudit;
import com.garten.model.agent.AgentAuditDetail;
import com.garten.model.agent.AgentInfo;
import com.garten.model.agent.AgentMessageAll;
import com.garten.model.agent.AgentOrderAll;
import com.garten.model.agent.SaleServiceAll;
import com.garten.model.agent.WithdrawAll;
import com.garten.model.agent.WuliaoOrder;
import com.garten.model.baby.BabyInfo;
import com.garten.model.baby.BabyLeaveLog;
import com.garten.model.baby.BabyPerformanceLog;
import com.garten.model.company.CpActivity;
import com.garten.model.company.Department;
import com.garten.model.company.Employee;
import com.garten.model.company.EmployeeAudit;
import com.garten.model.company.Jobs;
import com.garten.model.company.Report;
import com.garten.model.company.ReportAll;
import com.garten.model.garten.GartenCharge;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenLesson;
import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.GartenRecipe;
import com.garten.model.garten.GartenType;
import com.garten.model.garten.GartenVideo;
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.gartenClass.GartenClass;
import com.garten.model.other.AttendanceNo;
import com.garten.model.other.Card;
import com.garten.model.other.CardReturn;
import com.garten.model.other.CheckNumber;
import com.garten.model.other.Comment;
import com.garten.model.other.Equipment;
import com.garten.model.other.EquipmentName;
import com.garten.model.other.Feedback;
import com.garten.model.other.InfoLog;
import com.garten.model.other.Order;
import com.garten.model.other.Version;
import com.garten.model.parent.ParentInfo;
import com.garten.model.parent.Relation;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerLeaveLog;
import com.garten.vo.agent.AgentAuditMessage;
import com.garten.vo.baby.BabyMessageAndParent;
import com.garten.vo.bigcontrol.AddDetail;
import com.garten.vo.bigcontrol.BabyMessage;
import com.garten.vo.bigcontrol.ClassManageBig;
import com.garten.vo.bigcontrol.DakaCamera;
import com.garten.vo.bigcontrol.FeedbackDetail;
import com.garten.vo.bigcontrol.LiveCamera;
import com.garten.vo.bigcontrol.WorkerMessage;
import com.garten.vo.garent.GartenAndAgent;
import com.garten.vo.parent.ParentInfoCharge;
import com.garten.vo.parent.ParentInfoShort;
import com.garten.vo.smallcontrol.CardNoDetail;
import com.garten.vo.smallcontrol.MachineDetail;
import com.garten.vo.smallcontrol.OrderAll;
import com.garten.vo.teacher.ActivityDetailAll;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.BabyInfoShort;
import com.garten.vo.teacher.BabyPerformanceLogAll;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.Daijie;
import com.garten.vo.teacher.GartenStartEnd;
import com.garten.vo.teacher.Shouye;
import com.garten.vo.teacher.WorkerInfoShort;

public interface BigcontrolDao {

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

	List<BabyMessageAndParent> findBabyMessage(Map<String, Object> putMapParams);

	List<WorkerMessage> findWorkerMessage(Map<String, Object> putMapParams);

	List<GartenAndAgent> findGartenMessage(Map<String, Object> putMapParams);

	void updateGarten(Map<String, Object> param);

	List<String> findGartenGrade(Integer gartenId);

	List<GartenClass> findGartenClasses(Map<String, Object> putMapParams);

	List<AgentAuditMessage> findAgentAudit(Integer type);

	void agreeAgentAudit(Integer auditId);

	List<AgentInfo> findAgentMessge(Map<String, Object> map);

	void deleteAgentMessge(Integer agentId);

	void frostAgentMessge(Integer agentId);

	void unfrostAgentMessge(Integer agentId);

	void updateAgentMessge(Map<String, Object> putMapParams);

	List<AgentInfo> findAgentName(Map<String, Object> putMapParams);

	List<AgentAuditDetail> agentPerformance(Map<String, Object> putMapParams);

	List<GartenCharge> gartenCharge(Map<String, Object> putMapParams);

	void addGartenCharge(Map<String, Object> param);

	void changeGartenDredge(Map<String, Object> putMapParams);

	void insertInfoLog(InfoLog infoLog);

	void insertAgent(Map<String, Object> param);

	AgentInfo findAgentMessgeOne(Map<String, Object> putMapParams);

	List<OrderAll> findOrder(Map<String, Object> param);

	void insertGartenInfo(Map<String, Object> param);

	void insertWorkerInfo(Map<String, Object> param);

	Integer fingMaxGartenId();

	void insertClass(Map<String, Object> param);

	AgentAudit findAgentAuditOne(Integer auditId);


	void insertAttendanceNo(Map<String, Object> param);

	Integer fingMaxJobId();

	GartenCharge gartenChargeOne(Map<String, Object> putMapParams);

	List<FeedbackDetail> findFeedback();

	GartenCharge gartenChargeReal(Map<String, Object> putMapParams);

	void deleteGartenCharge(Integer chargeId);

	void updateGartenTime(Map<String, Object> putMapParams);

	List<ParentInfoCharge> findParentInfoCharge(Integer gartenId);

	void insertIgnore(Map<String, Object> putMapParams);

	void deleteIgnore(Map<String, Object> putMapParams);

	void deleteClass(Map<String, Object> param);

	List<MachineDetail> getMachine(@Param("type")Integer type, @Param("province")String province, @Param("city")String city, @Param("countries")String countries,@Param("gartenId")Integer gartenId);

	void addMachine(@Param("type")Integer type,@Param("macId") String macId, @Param("gartenId")Integer gartenId);

	void updateMachine(@Param("machineId")Integer machineId,@Param("macId") String macId);

	MachineDetail findMachineById(Integer machineId);

	void updateEquipMac(@Param("macId")String macId,@Param("oldMacId") String oldMacId);

	void deleteMachine(Integer machineId);

	void updateEquipIsValid(@Param("machineId")Integer machineId, @Param("oldMacId")String oldMacId);

	List<DakaCamera> findEquipBygartenId(@Param("gartenId")Integer gartenId, @Param("province")String province, @Param("city")String city, @Param("countries")String countries);
	
	List<LiveCamera> findVideoByGartenId(@Param("gartenId")Integer gartenId, @Param("province")String province, @Param("city")String city, @Param("countries")String countries);

	void addDakaCamera(@Param("gartenId")Integer gartenId,@Param("cameraIp") String cameraIp, @Param("cameraPort")String cameraPort, @Param("cameraPwd")String cameraPwd, @Param("cameraUser")String cameraUser,
			@Param("type")Integer type, @Param("macId")String macId, @Param("deviceSerial")String deviceSerial, @Param("validateCode")String validateCode);

	String getDeviceSerialById(Integer cameraId);

	void deleteDakaCamera(Integer cameraId);

	void updateDakaCamera(@Param("cameraId")Integer cameraId, @Param("cameraIp")String cameraIp, @Param("cameraPort")String cameraPort, @Param("cameraUser")String cameraUser, @Param("cameraPwd")String cameraPwd,
			@Param("type")Integer type, @Param("macId")String macId);

	void addLiveCamera(@Param("gartenId")Integer gartenId, @Param("cameraIp")String cameraIp, @Param("cameraPort")String cameraPort, @Param("cameraUser")String cameraUser, @Param("cameraPwd")String cameraPwd,
			@Param("deviceSerial")String deviceSerial, @Param("validateCode")String validateCode);

	void addLiveGartenVideo(@Param("type")Integer type, @Param("pointId")Integer pointId, @Param("gartenId")Integer gartenId, @Param("url")String url, @Param("img")String img,@Param("deviceSerial")String deviceSerial);

	void deleteLiveCamera(Integer cameraId);

	void deleteGartenVideo(Integer cameraId);

	void updateLiveCamera(@Param("cameraId")Integer cameraId, @Param("cameraIp")String cameraIp,@Param("cameraPort") String cameraPort,@Param("cameraPwd") String cameraPwd, @Param("cameraUser")String cameraUser);

	void updateLiveVideo(@Param("cameraId")Integer cameraId, @Param("type")Integer type,@Param("pointId") Integer pointId, @Param("img")String img,@Param("url") String url);

	void updateAgentFinance(Map<String, Object> params);

	void addPartner(@Param("partnerID")String partnerID,@Param("partnerSecret") String partnerSecret,@Param("gartenId") Integer gartenId,@Param("machineId") Integer machineId);

	List<Equipment> findEquipmentByDeviceSerial(String deviceSerial);

	List<GartenInfo> getAllGarten();

	Integer findMaxMachineId(Integer gartenId);

	void deletePartner(Integer machineId);
	
	void updateAgentCredits(Map<String, Object> m);

	Integer findParentMessageCount(Map<String, Object> params);

	void deleteOrderNoPay();
	
	List<Relation> relation();

	void addrelation(String relation);

	void deleterelation(Integer relationId);

	void deleteIsvalid();

	void deleteGartenActivityDetail(Integer gartenId);

	void deleteGartenAllCharge(Integer gartenId);

	void deleteGartenAllClass(Integer gartenId);

	void deleteGartenInfo(Integer gartenId);

	void deleteGartenAllLesson(Integer gartenId);

	void deleteGartenAllPhoto(Integer gartenId);

	void deleteGartenAllRecipe(Integer gartenId);

	void deleteGartenAllVideo(Integer gartenId);

	void deleteGartenAllIgnoreTime(Integer gartenId);

	void deleteGartenAllInfoLog(Integer gartenId);

	void deleteGartenAllUnusual(Integer gartenId);

	void deleteGartenAllMachine(Integer gartenId);

	void deleteGartenAllEquipment(Integer gartenId);

	void deleteMessageLog(Integer messageId);

	List<EquipmentName> findEquipmentName();

	void addEquipmentName(Map<String, Object> putMapParams);

	void deleteEquipmentName(Integer equipmentId);

	void resolveWuliaoOrder(Map<String, Object> param);

	void updateBalance(Map<String, Object> param);

	void updateAgentAuditPhone(@Param("oldPhoneNumber")String oldPhoneNumber, @Param("newPhoneNumber")String NewPhoneNumber);

	List<CardNoDetail> getBabyCardNoList(Map<String, Object> params);

	List<CardNoDetail> getTeacherCardNoList(Map<String, Object> params);

	List<SaleServiceAll> findSaleService(Map<String, Object> param);
	
	SaleServiceAll findSaleServiceBySaleServiceId(Long saleServiceId);
	
	void replySaleService(Map<String, Object> param);

	void deleteSaleService(Long saleServiceId);

	EquipmentName findEquipmentByName(String equipmentName);

	GartenClass findClassById(Integer classId);

	MachineDetail findMachineByMacId(String macId);

	List<WuliaoOrder> findWuliaoOrder(Integer state);
	
	List<Employee> findEmployee(Map<String, Object> param);

	Boolean findIfDeleteEmployee(Integer employeeNo);

    void deleteEmployee(Map<String, Object> param);

	void updateEmployee(Map<String, Object> param);

	void addEmployee(Map<String, Object> param);

	List<CpActivity> findCpActivity(Map<String, Object> param);

	void deleteCpActivity(Integer cpActivityId);

	void addCpActivity(Map<String, Object> param);

	void updateCpActivity(Map<String, Object> param);

	Employee findEmployeeByToken(String token);

	List<ReportAll> findDepartmentReport(Map<String, Object> param);

	void deleteReport(Integer reportId);

	void addReport(Map<String, Object> param);
	
	List<Report> findMyReport(Map<String, Object> param);

	List<AgentAudit> findMyApplyGarten(Integer employeeNo);

	Employee findEmployeeByPwd(Map<String, Object> param);

	Employee findEmployeeByPhoneNumber(String phoneNumber);

	void auditCpActivity(Integer cpActivityId);

	Employee findEmployeeById(Integer employeeNo);
	
	List<Department> findDepartment();

	void deleteDepartment(Long departmentno);

	void addDepartment(Map<String, Object> param);

	Boolean ifExistDepartment(String departmentName);

	int ifExistDepartmentEmployee(Long departmentNo);

	List<Jobs> findJobs();

	int ifExistJobsEmployee(Long jobsNo);

	Boolean ifExistJobs(String jobsName);

	void addJobs(Map<String, Object> param);

	Jobs findJobsByEmployeeNo(Integer employeeNo);

	Department findDepartmentByEmployeeNo(Integer employeeNo);
	
	List<CpActivity> findDepartmentCpActivity(Map<String, Object> param);

	String findToken(Map<String, Object> param);

	void refuseAgentAudit(Map<String, Object> params);

	List<WithdrawAll> findWithdraw(Map<String, Object> param);

	void updateWithdraw(Map<String, Object> param);

	void deleteWithdraw(Integer withdrawId);

	List<AgentAuditDetail> findEmployeePerformance(Map<String, Object> params);
	
	List<AgentOrderAll> findAgentOrder(Map<String, Object> param);

	List<WuliaoOrder> findDepartmentWuliaoOrder(Map<String, Object> params);

	void deleteJobs(Long jobsNo);

	void addCard(List<Card> list);

	Integer findCardCount(@Param("agentId")Integer agentId, @Param("agentType")Integer agentType,@Param("gartenId")Integer gartenId);

	List<Card> findCard(@Param("agentId")Integer agentId, @Param("agentType")Integer agentType,@Param("gartenId")Integer gartenId);

	void addGartentype(Map<String, Object> param);

	void deleteGartentype(Integer gartenType);

	List<GartenType> findGartentype();

	void updateAttendanceNoAll(List<AttendanceNo> list);

	Card findCardById(Integer cardId);

	void deleteCard(Integer cardId);

	void addcardReturn(Map<String, Object> params);

	List<CardReturn> findCardReturnByName(String name);

	List<CardReturn> findCardReturn(Map<String, Object> params);

	void deleteCardReturn(Integer returnId);

	List<AgentMessageAll> findAgentMessage(Map<String, Object> param);

	void addAgentMessage(Map<String, Object> param);

	void deleteAgentMessage(Integer agentMessageId);

	AttendanceNo findAttendanceNoByInCard(String inCard);

	void deleteYichangLog(Map<String, Object> params);

	void readAgentMessage(Integer agentMessageId);

	void addGartenAttendance(Integer gartenId);

	void removeRepetitionCard();

}
