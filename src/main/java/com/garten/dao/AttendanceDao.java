package com.garten.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.garten.model.baby.BabyCheckLog;
import com.garten.model.baby.BabyInfo;
import com.garten.model.daka.Dakalog;
import com.garten.model.garten.GartenAttendance;
import com.garten.model.garten.GartenInfo;
import com.garten.model.other.AttendanceNo;
import com.garten.model.other.PartnerInfo;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.model.worker.WorkerInfo;
import com.garten.vo.attendance.BabySimpleInfo;
import com.garten.vo.attendance.EquipmentShort;
import com.garten.vo.attendance.AttGartenClass;
import com.garten.vo.attendance.PartnerTokenInfo;
import com.garten.vo.attendance.TeacherAndBabyInfo;
import com.garten.vo.attendance.AdminInfoLog;

public interface AttendanceDao {

	
	public PartnerInfo findPartnerById(String partnerId);

	public void updateToken(Map<String, Object> param);
	
	public PartnerTokenInfo findPartnerTokenInfo(@Param("partnerId")String partnerId, @Param("macId")String macId);

	public GartenInfo findGartenByToken(String schoolToken);

	public PartnerInfo findPartnerByTokenAndId(@Param("partnerToken")String partnerToken,@Param("partnerId")String partnerId);

	public List<AttGartenClass> findGartenClass(@Param("schoolToken")String schoolToken);

	public List<BabySimpleInfo> findBabyByClassId(Integer classId);

	public void updateBabyAmCheckLog(@Param("babyId")Integer babyId,  @Param("attendanceDate")String attendanceDate,@Param("examDate") String examDate, @Param("examValue")Double examValue,
			@Param("fileId")String fileId, @Param("macId")String macId,@Param("gartenId") Integer gartenId,@Param("imgUrl") String imgUrl);

	public List<TeacherAndBabyInfo> findTeachers(Integer gartenId);
	
	public List<TeacherAndBabyInfo> findBabys(Integer gartenId);

	public List<EquipmentShort> findEquipmentByMacId(@Param("gartenId")Integer gartenId,@Param("macId")String macId);

	public Map<String,Object> findGartenAttendanceFlag(Integer gartenId);

	public void updateBabyPmCheckLog(@Param("babyId")Integer babyId,@Param("attendanceDate")String attendanceDate,@Param("examDate") String examDate,@Param("examValue") Double examValue,
			@Param("fileId")String fileId, @Param("macId")String macId, @Param("gartenId")Integer gartenId,@Param("imgUrl") String imgUrl);

	public Map<String,Object> findParmByFileIdFromDakaLog(String fileId);

	public void updateBabyArriveImg(@Param("imgPath")String imgPath,@Param("fileId")String fileId);

	public void updateBabyLeaveImg(@Param("imgPath")String imgPath,@Param("fileId")String fileId);

	public Map<String,Object> findPlayTimeByGartenToken(String schoolToken);

	public BabyCheckLog findBabyCheckByTime(@Param("time")String time,@Param("babyId")Integer babayId);

	public Map<String,Object> findArriveLeaveTimeByToken(String schoolToken);

	public void addUnusual(@Param("babyId")Integer babyId, @Param("unusualTime")String unusualTime,@Param("time") String time, @Param("fileId")String fileId, @Param("gartenId")Integer gartenId,@Param("stage") Integer stage,@Param("unusualType")Integer unusualType,@Param("cardNo")String cardNo, @Param("imgUrl")String imgUrl,@Param("job")String job);

	public Integer fingBabyIdByFileIdFromUnusual(String fileId);

	public void updateUnusualImg(@Param("imgPath")String imgPath, @Param("fileId")String fileId);

	public void addAttendanceLog(@Param("babyId")Integer babyId,@Param("cardNo") String cardNo, @Param("attendanceTime")String attendanceTime,@Param("time") String time, @Param("fileId")String fileId,
			@Param("macId")String macId,@Param("gartenId") Integer gartenId, @Param("job")String job,@Param("imgUrl")String imgUrl);

	public String findJobById(Integer jobId);

	public WorkerCheckLog findWorkerCheckByTime(@Param("time")String time,@Param("workerId")Integer workerId);

	public void updateWorkerAmCheckLog(@Param("babyId")Integer babyId,  @Param("attendanceDate")String attendanceDate,@Param("examDate") String examDate,
			@Param("fileId")String fileId, @Param("macId")String macId,@Param("gartenId") Integer gartenId,@Param("imgUrl") String imgUrl);

	public void updateWorkerPmCheckLog(@Param("workerId")Integer workerId, @Param("attendanceDate")String attendanceDate,@Param("examDate") String examDate,
			@Param("fileId")String fileId, @Param("macId")String macId, @Param("gartenId")Integer gartenId,@Param("imgUrl") String imgUrl);

	public Map<String,Object> findNameAndParentAndTeachersByBabyId(Integer babyId);

	public void addInfoLog(@Param("gartenId")Integer gartenId, @Param("info")String info, @Param("time")String time, @Param("job")String job, @Param("parentId")Integer parentId,@Param("babyName")String babyName);

	public List<AdminInfoLog> findAdminByGartenId(Integer gartenId);

	public String findTeacherNameById(Integer workerId);

	public void updateDakaLogImg(@Param("imgPath")String imgPath, @Param("fileId")String fileId);

	public String findBabyArriveImgByFileId(String  fileId);

	public String findWorkerArriveImgByFileId(String fileId);
	
	public void updateWorkerArriveImg(@Param("imgPath")String imgPath,@Param("fileId")String fileId);

	public void updateWorkerLeaveImg(@Param("imgPath")String imgPath,@Param("fileId")String fileId);

	public Integer findGartenRebootFlag(String macId);

	public void updateRebootFlag(String macId);

	public String findCardIsExist(Integer jobId);

	public void updateCardNoById(@Param("jobId")Integer jobId, @Param("cardNo")Integer cardNo);

	public String findParentPhoneById(Integer parentId);

	public String getTeacherPhoneById(String id);

	public List<String> findIgnoreTimeByGartenToken(String schoolToken);

	public Integer findAttendanceUseful(String schoolToken);

	public void addCheckImg(@Param("imgPath")String imgPath, @Param("fileId")String fileId);

	public Dakalog findDakaLogIsRepeat(@Param("babyId")Integer babyId, @Param("attendanceTime")Long attendanceTime);

	public GartenAttendance findGartenAttendanceByGartenId(Integer gartenId);

	public void updateBabyCheckLog(Map<String, Object> params);

	public void insertUnusual(Map<String, Object> params);

	public AttendanceNo findAttendanceByJobId(Integer jobId);

	public BabyInfo findBabyById(Integer babyId);

	public void updateWorkerCheckLog(Map<String, Object> params);

	public List<WorkerInfo> findWorkerByClassId(Integer classId);
}
