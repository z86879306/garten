package com.garten.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.garten.model.activity.ActivityDetail;
import com.garten.model.activity.ActivityLog;
import com.garten.model.baby.BabyInfo;
import com.garten.model.baby.BabyLeaveLog;
import com.garten.model.baby.BabyPerformanceLog;
import com.garten.model.garten.GartenClass;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenLesson;
import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.GartenRecipe;
import com.garten.model.garten.IgnoreTime;
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.other.AttendanceNo;
import com.garten.model.other.CheckNumber;
import com.garten.model.other.Comment;
import com.garten.model.other.InfoLog;
import com.garten.model.other.MessageLog;
import com.garten.model.other.TPermission;
import com.garten.model.other.Version;
import com.garten.model.other.VisitCount;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerLeaveLog;
import com.garten.model.worker.WorkerMessageLog;
import com.garten.vo.baby.BabyLeaveLogAll;
import com.garten.vo.baby.BabyMessageAndParent;
import com.garten.vo.baby.UnusualAll;
import com.garten.vo.bigcontrol.BabyMessage;
import com.garten.vo.parent.ParentInfoCharge;
import com.garten.vo.parent.ParentInfoShort;
import com.garten.vo.smallcontrol.BabyCheckSimple;
import com.garten.vo.smallcontrol.CardNoDetail;
import com.garten.vo.smallcontrol.GartenClassName;
import com.garten.vo.smallcontrol.GartenLessonDetail;
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
import com.garten.vo.teacher.WorkerLeaveLogPrin;
import com.garten.vo.teacher.WorkerNameMessage;

public interface SmallcontrolDao {

	WorkerInfo findWorkerByPwd(Map<String, Object> param);

	void createAttendanceNo(Map<String, Object> param);

	AttendanceNo findAttendanceNo(Map<String, Object> param);

	void createWorker(Map<String, Object> param);

	void createBaby(Map<String, Object> param);

	WorkerInfo findWorkerByAccount(String phoneNumber);

	void updatePwdToken(Map<String, Object> putMapParams);

	String findToken(Map<String, Object> param);

	WorkerInfo findWorkerByToken(String token);

	List<ParentInfoCharge> findParentInfoCharge(Map<String, Object> param);

	void updateDaban(Integer gartenId);

	void updateDabanWorker(Integer gartenId);

	void updateZhongban(Integer gartenId);

	void updateXiaoban(Integer gartenId);

	String findMaxLeadClass(Integer gartenId);

	IgnoreTime findIgnoreOne(Map<String, Object> param);

	void insertLesson(Map<String, Object> param);

	void updateDakaTime(Map<String, Object> param);

	List<GartenLessonDetail> findLesson(Map<String, Object> param);

	void deletelesson(Integer lessonId);

	List<GartenRecipe> findRecipe(Map<String, Object> param);

	void addrecipe(Map<String, Object> param);

	List<OrderAll> findOrder(Map<String, Object> param);

	/*
	 * 8.19 交接
	 */
	void updateTeacher(@Param("sex")Integer sex, @Param("age")Integer age,@Param("education") String education, @Param("certificate")String certificate,@Param("chinese") String chinese,@Param("classId") Integer classId,
			@Param("phoneNumber")String phoneNumber, @Param("workerName")String workerName,@Param("workerId") Integer workerId,@Param("jobcall")String jobcall,@Param("permission")String permission);

	void updateRebootFlag(Integer gartenId);

	void updateBaby(@Param("babyId")Integer babyId,@Param("babyName") String babyName,@Param("sex") Integer sex,@Param("birthday") Long birthday, @Param("newTeacher")String newTeacher,@Param("height") Double height, @Param("health")String health, @Param("hobby")String hobby, @Param("specialty")String specialty, @Param("allergy")String allergy, @Param("weight")Double weight);

	String getGartenNameById(Integer gartenId);

	AttendanceNo findCardNo(Integer jobId);

	void bindingCardNo1(@Param("jobId")Integer jobId,@Param("cardNo") String cardNo);

	void bindingCardNo2(@Param("jobId")Integer jobId,@Param("cardNo") String cardNo);
	
	void bindingCardNo3(@Param("jobId")Integer jobId,@Param("cardNo") String cardNo);

	void cancelBindingNo1(@Param("jobId")Integer jobId);
	
	void cancelBindingNo2(@Param("jobId")Integer jobId);
	
	void cancelBindingNo3(@Param("jobId")Integer jobId);

	List<CardNoDetail> getBabyCardNoList(@Param("gartenId")Integer gartenId,@Param("job") String job, @Param("classId")Integer classId);

	List<CardNoDetail> getTeacherCardNoList(@Param("gartenId")Integer gartenId,@Param("job") String job, @Param("classId")Integer classId);

	void addAttendanceNo(AttendanceNo attendanceNo);

	void addWorkerTeacher(@Param("gartenId")Integer gartenId,@Param("jobId") Integer jobId,@Param("pwd") String pwd, @Param("workerName")String workerName,@Param("phoneNumber") String phoneNumber,
			@Param("sex")Integer sex, @Param("age")Integer age, @Param("education")String education, @Param("certificate")String certificate, @Param("chinese")String chinese,@Param("job") String job,
			@Param("jobCall")String jobCall, @Param("classId")Integer classId,@Param("permission")String permission);

	Integer[] getTeacherByClassId(Integer classId);

	void addBaby(@Param("gartenId")Integer gartenId,@Param("babyName") String babyName,@Param("birthday") Long birthday,@Param("height") Double height,@Param("health") String health,@Param("hobby") String hobby,@Param("specialty")String specialty,@Param("teachers") String teachers,@Param("allergy") String allergy,
			@Param("identity") String identity, @Param("weight")Double weight, @Param("sex")Integer sex,@Param("babyId") Integer babyId, @Param("parentId")Integer parentId,@Param("cardId")String cardId);

	ParentInfo findParentByPhone(String phoneNumber);

	void updateParentIsExist(@Param("parentId")Integer parentId,@Param("newBabyId") String newBabyId, @Param("newGarten")String newGarten, @Param("newIdentity")String newIdentity,@Param("newMonitorTime") String newMonitorTime, @Param("newAttendanceTime")String newAttendanceTime);

	void addParent(@Param("babyId")String babyId, @Param("identity")String identity,@Param("parentName") String parentName, @Param("phoneNumber")String phoneNumber, @Param("address")String address,
			@Param("gartenId")String gartenId, @Param("pwd")String pwd, @Param("monitorTime")String monitorTime,@Param("attendanceTime") String attendanceTime);

	void updateBaByTeacher(@Param("teachers")String teachers,@Param("classId") Integer classId);

	void insertBabyCheck(ArrayList<BabyCheckSimple> list);

	void insertBabyPerformance(ArrayList<BabyCheckSimple> list);

	void insertTeacherCheck(ArrayList<BabyCheckSimple> list);

	void updateParentMessage(@Param("parentId")Integer parentId, @Param("parentName")String parentName, @Param("address")String address, @Param("phoneNumber")String phoneNumber);

	WorkerInfo findWorkerByPhoneNumber(String phoneNumber);

	GartenInfo getGartenById(Integer gartenId);

	List<InfoLog> findNotifyHistory(@Param("gartenId")Integer gartenId, @Param("job")String job,@Param("startTime") Long startTime,@Param("endTime") Long endTime);

	List<BabyMessageAndParent> findBabyMessage(Map<String, Object> putMapParams);

	WorkerInfo findworkerById(Integer workerId);

	void deleteTeacher(Integer workerId);

	List<ParentInfo> findParentByBabyId(Integer babyId);

	void deleteBaby(Integer babyId);

	void deleteAttendanceNo(Integer jobId);

	Integer getBabyCountByClassId(Integer classId);

	void deleteRecipe(Integer recipeId);

	CardNoDetail findJobByCardNo(String cardNo);

	List<UnusualAll> findUnusualAllByToken(Map<String, Object> param);

	List<UnusualAll> findUnusualAllByTokenSmall(Map<String, Object> param);

	List<WorkerLeaveLogPrin> findLeaveLogByToken(Map<String, Object> putMapParams);
	List<BabyLeaveLogAll> findLeaveLogByTokenSmall(Map<String, Object> putMapParams);

	BabyInfo findBabyByIdCard(String cardId);

	void deleteActivity(Integer babyId);

	void deleteBabyLeaveLog(Integer babyId);

	void deleteBabyPerformanceLog(Integer babyId);

	void deleteDaijieInfo(Integer babyId);

	void deleteDakaLog(Integer jobId);

	void deleteGartenPhotos(Integer babyId);

	void deleteUnusual(Integer jobId);
	
	void deleteWorkerPhoto(Integer workerId);

	void deleteWorkerFlower(Integer workerId);

	void deleteWorkerCheckLog(Integer workerId);

	void deletePhotoDianzan(@Param("job")String job,@Param("jobId") Integer jobId);

	void deleteInfoLog(@Param("job")String job,@Param("jobId") Integer jobId);

	void deleteFeedback(@Param("job")String job,@Param("jobId") Integer jobId);

	void deleteComment(@Param("job")String job,@Param("jobId") Integer jobId);

	BabyInfo findBabyByParentId(Integer parentId);

	void deleteParentFlower(Integer parentId);

	WorkerInfo findPrincipalByGartenId(Integer gartenId);
	
	void updateMainParent(Map<String, Object> param);
	
	void insertMessageLog(MessageLog ml);
	
	List<MessageLog> findMessageLog(Map<String, Object> param);

	List<VisitCount> findVisitCount(Map<String, Object> params);

	List<TPermission> findTeacherPermisson();

	List<WorkerNameMessage> findWorkerApplyMessage(Map<String, Object> params);

	GartenClass findClassByTeacher(Integer workerId);

	void updateTeacherMessageState(Integer messageId);

	AttendanceNo findAttendanceNoById(Integer jobId);

	void deleteParent(Integer parentId);

	void addWorkerMessage(Map<String, Object> params);

	void cancelApplyMessage(Integer messageId);

	List<WorkerMessageLog> findApplyMessage(Integer workerId);

	WorkerMessageLog findMessageById(Integer messageId);

	List<WorkerNameMessage> findMessageMore(Integer workerId);

	Integer findMostEarlyApply(Integer workerId);
}
