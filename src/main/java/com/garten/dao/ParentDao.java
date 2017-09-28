package com.garten.dao;


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
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.garten.Video;
import com.garten.model.other.CheckNumber;
import com.garten.model.other.Comment;
import com.garten.model.other.Feedback;
import com.garten.model.other.InfoLog;
import com.garten.model.other.Order;
import com.garten.model.other.Version;
import com.garten.model.other.VisitCount;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerLeaveLog;
import com.garten.vo.baby.UnusualAll;
import com.garten.vo.parent.ParentInfoShort;
import com.garten.vo.smallcontrol.GartenClassName;
import com.garten.vo.teacher.ActivityDetailAll;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.BabyInfoShort;
import com.garten.vo.teacher.BabyPerformanceLogAll;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.Daijie;
import com.garten.vo.teacher.GartenStartEnd;
import com.garten.vo.teacher.Shouye;
import com.garten.vo.teacher.WorkerInfoShort;

public interface ParentDao {

	ParentInfo findParentByPwd(Map<String, Object> param);


	ParentInfo findWorkerByPhoNumber(String phoneNumber);

	void updatePwdToken(Map<String, Object> putMapParams);


	void updateToken(Map<String, Object> param);


	ParentInfo findParentInfoByToken(String token);


	ParentInfoShort findParentShortByToken(String token);


	List<BabyInfoShort> findBabyShortByToken(String token);


	Set<Long> findRecipeCircleCircleByToken(Integer babyId);


	Set<Long> findDaijieCircleCircleByToken(Integer babyId);


	Set<Long> findLessonCircleByToken(Integer babyId);


	Set<Long> findAttendanceCircleByToken(Integer babyId);




	GartenStartEnd findGartenArriveLeaveById(Integer babyId);


	List<BabyLeaveLog> findLeaveLongByToken(Integer babyId);


	List<WorkerLeaveLog> findWLeaveLongByToken(Map<String, Object> putMapParams);


	ParentInfo findWorkerInfoByToken(Map<String, Object> putMapParams);


	List<BabyCheckLogAll> findBabyCheckByToken(Map<String, Object> putMapParams);


	void addRemarkCheck(Map<String, Object> putMapParams);


	Integer findImportantCountByToken(String token);


	List<InfoLog> findNotifiedByToken(Map<String, Object> putMapParams);


	Integer findInfoCount(String token);


	Boolean getParentAttendance(String token);


	List<Daijie> findDaijieById(Integer babyId);


	void deleteDaijieById(Integer daijieId);


	void createDaijieById(Map<String, Object> putMapParams);


	BabyInfo findBabyShortByBabyId(Integer babyId);


	List<GartenLesson> findLessonById(Map<String, Object> map);


	List<GartenRecipe> findRecipeById(Map<String, Object> putMapParams);


	List<BabyPerformanceLogAll> findPerformanceById(Map<String, Object> putMapParams);


	GartenInfo findGartenInfoById(Integer babyId);


	List<ActivityDetailAll> findIntroduceActivityByToken(Map<String, Object> putMapParams);


	ActivityDetail findActivityById(Integer activityId);


	void insertActivity(Map<String, Object> param);


	List<ParentInfoShort> findParentLinkMan(Map<String, Object> map);


	List<WorkerInfoShort> findTeacherLinkMan(Integer babyId);




	void toFlower(Map<String, Object> putMapParams);




	ClassManage findBabyById(Integer babyId);

	void updateBaby(BabyInfo baby);




	void updateBabyHead(Map<String, Object> putMapParams);


	void insertBabyLeave(Map<String, Object> putMapParams);


	void createFeadbackByToken(Map<String, Object> param);


	List<GartenPhotos> findParentPhotoByToken(@Param("babyId")Integer babyId, @Param("gartenId")Integer gartenId);


	List<GartenPhotos> findWorkerPhotoByToken(@Param("babyId")Integer babyId, @Param("gartenId")Integer gartenId);


	void createPhoto(Map<String, Object> param);


	PhotoDianZan findHasdianzan(Map<String, Object> param);


	void createDianzan(Map<String, Object> param);


	void deleteDianzan(Map<String, Object> param);


	void createCommentPhoto(Map<String, Object> param);


	Set<String> findPhoneNameByInfoId(Integer infoId);


	void updateFlower(Integer parentId);


	void upFlower(Integer workerId);




	/*GartenPhotos findPhotoByParentId(Integer parentId);*/


	/*void updatePhoto(Map<String, Object> param);*/


	Set<Long> findYichangLongById(Integer babyId);


	List<UnusualAll> findYichangByToken(Map<String, Object> param);


	/*List<GartenClass> findClasses(String token);*/
	
	List<GartenClassName> findClassesName(Map<String, Object> map);


	List<Video> findVideos(Map<String, Object> map);


	Version findVersion();




	List<ParentInfo> findParentByGartenId(Integer gartenId);


	void zforder(Map<String, Object> param);


	void insertFeedback(Feedback feedback);


	void insertOrdr(Order o);


	Order findOrder(String orderNumber);


	ParentInfo findParentById(Integer id);


	void updateChargeTime(Map<String, Object> putMapParams);




	List<String> findClassName(Integer classId);



	List<BabyInfo> findBaby();
	List<WorkerInfo> getWorker(@Param( value= "teacherId" )String teacherId);

	void updateHonghua();

	List<ParentInfo> getminorParent(Map<String, Object> param);


	VisitCount findVisitCount(Map<String, Object> param);


	void addVisitCount(Map<String, Object> params);


	void updateVisitCount(Map<String, Object> params);

}
