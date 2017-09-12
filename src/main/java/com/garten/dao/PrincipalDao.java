package com.garten.dao;


import java.util.List;
import java.util.Map;
import java.util.Set;

import com.garten.model.activity.ActivityDetail;
import com.garten.model.activity.ActivityLog;
import com.garten.model.baby.BabyInfo;
import com.garten.model.baby.BabyLeaveLog;
import com.garten.model.baby.BabyPerformanceLog;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenLesson;
import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.GartenRecipe;
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.garten.Video;
import com.garten.model.other.CheckNumber;
import com.garten.model.other.Comment;
import com.garten.model.other.InfoLog;
import com.garten.model.other.Version;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerLeaveLog;
import com.garten.vo.baby.UnusualAll;
import com.garten.vo.parent.ParentInfoShort;
import com.garten.vo.teacher.ActivityDetailAll;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.BabyInfoShort;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.Daijie;
import com.garten.vo.teacher.GartenStartEnd;
import com.garten.vo.teacher.Shouye;
import com.garten.vo.teacher.WorkerCheckLogAll;
import com.garten.vo.teacher.WorkerInfoShort;
import com.garten.vo.teacher.WorkerLeaveLogPrin;

public interface PrincipalDao {

	WorkerInfo findPrincipalByPwd(Map<String, Object> param);

	WorkerInfo findPrincipalByAccount(String account);

	List<WorkerCheckLogAll> findWorkerCheckByToken(Map<String, Object> putMapParams);

	WorkerInfo findPrincipalInfoByToken(String token);

	List<WorkerLeaveLogPrin> findLeaveLogByToken(Map<String, Object> putMapParams);

	/*List<WorkerCheckLogAll> findWorkerCheckAllByToken(String token);*/

	List<WorkerLeaveLog> findLeaveLongByToken(String token);

	void updateAgreeLeaveByCheckId(Integer leaveId);

	void deleteWorkerLeaveByleaveId(Integer leaveId);

	void updateCheckAgreeByCheckId(Map<String, Object> putMapParams);


	List<BabyCheckLogAll> findBabyCheckByClass(Map<String, Object> putMapParams);

	void updatePwdToken(Map<String, Object> putMapParams);

	List<GartenPhotos> findParentPhotoByToken(String token);

	List<GartenPhotos> findWorkerPhotoByToken(String token);

	List<ParentInfoShort> findParentLinkMan(Integer classId);

	List<UnusualAll> findUnusualAllByToken(Map<String, Object> param);

	Set<Long> findYichangCircleByToken(String token);

	void updateToken(Map<String, Object> param);

	void updateTeacherHead(Map<String, Object> putMapParams);

	List<Video> findVideosByToken(Map<String, Object> map);

	Version findVersion();

	WorkerInfo findPrincipalByGartenId(Integer integer);

	List<BabyCheckLogAll> findBabyCheckByClassSmall(Map<String, Object> putMapParams);

	WorkerInfo findPrincipalInfoById(Integer id);



	

	/*List<Video> findVideosByTokenClass(Map<String, Object> putMapParams);*/


	

	

}
