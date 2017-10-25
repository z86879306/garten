package com.garten.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aliyun.oss.OSSClient;
import com.garten.dao.AttendanceDao;
import com.garten.model.baby.BabyCheckLog;
import com.garten.model.garten.GartenInfo;
import com.garten.model.other.PartnerInfo;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.util.LyUtils;
import com.garten.util.lxcutil.MyParamAll;
import com.garten.util.md5.md5Util;
import com.garten.util.myutil.MyUtil;
import com.garten.vo.attendance.AdminInfoLog;
import com.garten.vo.attendance.AttGartenClass;
import com.garten.vo.attendance.BabyAttendanceInfo;
import com.garten.vo.attendance.BabySimpleInfo;
import com.garten.vo.attendance.EquipmentShort;
import com.garten.model.garten.GartenClass;
import com.garten.vo.attendance.PartnerTokenInfo;
import com.garten.vo.attendance.TeacherAndBabyInfo;

@Service("attendanceService")
public class AttendanceService {

	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private BigcontrolService bigcontrolService;
	
	public Map<String,Object> getPartnerToken(String partnerId,String tms,String md5tms, String macId){
		HashMap<String, Object> result = new HashMap<String,Object>();
		HashMap<String, Object> param = new HashMap<String,Object>();
		if(tms==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，tms为空或格式不正确");
		}
		if(md5tms==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，md5tms为空或格式不正确");
		}
		if(partnerId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerId为空或格式不正确");
		}
		if(macId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，macId为空或格式不正确");
		}
		String uuid="error";
		PartnerInfo partnerInfo = attendanceDao.findPartnerById(partnerId);
		if(partnerInfo!=null){
			//合伙商不为空，验证密码md5 加密
			
			System.out.println(md5Util.getMD5(partnerInfo.getPartnerSecret()+tms).toLowerCase());
			String md5 = md5Util.getMD5(partnerInfo.getPartnerSecret()+tms).toLowerCase();
			if(md5tms.equals(md5)){
				uuid=UUID.randomUUID().toString();
				param.put("token", uuid);
				param.put("partnerId", partnerId);
				attendanceDao.updateToken(param);
				PartnerTokenInfo partnerTokenInfo = attendanceDao.findPartnerTokenInfo(partnerId,macId);
				result.put("partnerTokenInfo", partnerTokenInfo);
				result.put("respCode", 0);
				result.put("respDesc", "请求成功");
				return result;
			}else{
				result.put("respCode", 600002);
				result.put("respDesc", "获取身份令牌token失败");
			}
		}else{
			MyUtil.putMapParams(result, "respCode", 600002, "respDesc", "获取身份令牌token失败" );
		}
		return result;
	}

	public Map<String, Object> getSchClassList(String schoolToken, String partnerToken, String partnerId) {
		HashMap<String, Object> result = new HashMap<String,Object>();
		//缺少参数  直接返回 错误
		if(schoolToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，schoolToken为空或格式不正确");
		}
		if(partnerToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerToken为空或格式不正确");
		}
		if(partnerId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerId为空或格式不正确");
		}
		
		GartenInfo gartenInfo = attendanceDao.findGartenByToken(schoolToken);
		PartnerInfo partnerInfo = attendanceDao.findPartnerByTokenAndId(partnerToken, partnerId);
		if(null==gartenInfo||null==partnerInfo){
			result.put("respCode", 600001);
			result.put("respDesc", "无效合作商或者无效schoolToken");
			if(null==partnerInfo){
				result.put("respCode", 600003);
				result.put("respDesc", "无效的访问令牌或者令牌已过期");
			}
			return result;
		}
		if(attendanceDao.findAttendanceUseful(schoolToken)==0){
			result.put("respCode", 600009);
			result.put("respDesc", "考勤服务到期，请联系成长记忆续费");
			return result;
		}
		//幼儿园   合伙商不为空
		List<AttGartenClass> list = attendanceDao.findGartenClass(schoolToken);
		result.put("list", list);
		result.put("respCode", 0);
		result.put("respDesc", "请求成功");
		return result;
	}
	
	
	public Map<String,Object> getStudentList(Integer classId,String schoolToken,String partnerToken,String partnerId){
		HashMap<String, Object> result = new HashMap<String,Object>();
		//缺少参数  直接返回 错误
		if(classId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，classId为空或格式不正确");
		}
		if(schoolToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，schoolToken为空或格式不正确");
		}
		if(partnerToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerToken为空或格式不正确");
		}
		if(partnerId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerId为空或格式不正确");
		}
		
		GartenInfo gartenInfo = attendanceDao.findGartenByToken(schoolToken);
		PartnerInfo partnerInfo = attendanceDao.findPartnerByTokenAndId(partnerToken, partnerId);
		if(null==gartenInfo||null==partnerInfo){
			result.put("respCode", 600001);
			result.put("respDesc", "无效合作商或者无效schoolToken");
			if(null==partnerInfo){
				result.put("respCode", 600003);
				result.put("respDesc", "无效的访问令牌或者令牌已过期");
			}
			return result;
		}
		if(attendanceDao.findAttendanceUseful(schoolToken)==0){
			result.put("respCode", 600009);
			result.put("respDesc", "考勤服务到期，请联系成长记忆续费");
			return result;
		}
			//幼儿园   合伙商不为空
			List<BabySimpleInfo> babyList = attendanceDao.findBabyByClassId(classId);
			result.put("babyList", babyList);
			result.put("respCode", 0);
			result.put("respDesc", "请求成功");
		return result;		
	}

	
	public Map<String,Object> sendAttendanceData(List<BabyAttendanceInfo> attendanceInfoList,String schoolToken,String partnerToken,String partnerId){
		HashMap<String, Object> result = new HashMap<String,Object>();
		//缺少参数  直接返回 错误
		if(attendanceInfoList==null){
			result.put("respCode", 1);
			result.put("respDesc", "请求参数错误，attendanceInfoList为空或格式不正确");
			return result;
		}
		if(schoolToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，schoolToken为空或格式不正确");
		}
		if(partnerToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerToken为空或格式不正确");
		}
		if(partnerId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerId为空或格式不正确");
		}
		GartenInfo gartenInfo = attendanceDao.findGartenByToken(schoolToken);
		PartnerInfo partnerInfo = attendanceDao.findPartnerByTokenAndId(partnerToken, partnerId);
		if(null==gartenInfo||null==partnerInfo){
			result.put("respCode", 600001);
			result.put("respDesc", "无效合作商或者无效schoolToken");
			if(null==partnerInfo){
				result.put("respCode", 600003);
				result.put("respDesc", "无效的访问令牌或者令牌已过期");
			}
			return result;
		}
		if(attendanceDao.findAttendanceUseful(schoolToken)==0){
			result.put("respCode", 600009);
			result.put("respDesc", "考勤服务到期，请联系成长记忆续费");
			if(1==gartenInfo.getAccountState()){
				MyUtil.putMapParams(result, "respCode", 600011, "respDesc", "幼儿园已被冻结");
			}
			return result;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
			//身份完整
			try {
				//获取学校 签到 签退  时间
				String arriveEndTime = sdf3.format(new Date())+" "+gartenInfo.getArriveEndTime();
				String leaveStartTime = sdf3.format(new Date())+" "+gartenInfo.getLeaveStartTime();
				List<String> ignoreList = attendanceDao.findIgnoreTimeByGartenToken(schoolToken);
				String content =null;
				for(BabyAttendanceInfo bai :attendanceInfoList){
					//遍历忽略打卡表，当天打卡在忽略时间段内  return 返回
					for(String ignorTime : ignoreList){
						if(ignorTime.substring(0, ignorTime.length()-2).equals(sdf3.format(new Date(bai.getExamDate()))+" 00:00:00")){
							System.out.println(sdf3.format(new Date(bai.getExamDate()))+" 00:00:00");
							return result;
						}
					}
					String imgUrl = MyParamAll.MYOSS_ADDRESS+bai.getFileId();
					//先全部在打卡表记录
					String job = attendanceDao.findJobById(bai.getBabyId());
					attendanceDao.addAttendanceLog(bai.getBabyId(),bai.getCardNo(),sdf.format(new Date(bai.getExamDate())),sdf3.format(new Date(bai.getAttendanceDate())),bai.getFileId(),bai.getMacId(),gartenInfo.getGartenId(),job,imgUrl);
					//签到时间大于规定到园时间
					Boolean arriveflag = LyUtils.strChangeToLong(arriveEndTime)-bai.getAttendanceDate()<0;
					//签退时间小于规定离园时间
					Boolean leaveflag = LyUtils.strChangeToLong(leaveStartTime)-bai.getAttendanceDate()>0;
					//上传的是宝宝的考勤信息
					if("宝宝".equals(job)){
						//babycheck表查找当天签到是否已经有记录
						BabyCheckLog babyCheckLog = attendanceDao.findBabyCheckByTime(sdf3.format(new Date(bai.getExamDate()))+" 00:00:00",bai.getBabyId());
						if(babyCheckLog.getArriveTime()==null){
							attendanceDao.updateBabyAmCheckLog(bai.getBabyId(),sdf.format(new Date(bai.getExamDate())),sdf3.format(new Date(bai.getAttendanceDate())),bai.getExamValue(),bai.getFileId(),bai.getMacId(),gartenInfo.getGartenId(),imgUrl);
							if(arriveflag){
								//在异常表添加数据
								attendanceDao.addUnusual(bai.getBabyId(),sdf.format(new Date(bai.getExamDate())),sdf3.format(new Date(bai.getAttendanceDate())),bai.getFileId(),gartenInfo.getGartenId(),0,0,bai.getCardNo(),imgUrl,job);
								//在通知表记录异常通知  生成多条关于该宝宝的异常通知   包括 监护人 ，老师
								Map<String, Object> param = attendanceDao.findNameAndParentAndTeachersByBabyId(bai.getBabyId());
								//添加主监护人通知记录
								attendanceDao.addInfoLog(gartenInfo.getGartenId(),"迟到",sdf.format(new Date(bai.getExamDate())),"家长",(Integer)param.get("parentId"),(String)param.get("babyName"));
								String parentPhone = attendanceDao.findParentPhoneById((Integer)param.get("parentId"));
								content = "您的孩子"+(String)param.get("babyName")+"于"+sdf.format(new Date(bai.getExamDate()))+"迟到";
							
									bigcontrolService.pushOne(MyParamAll.JIGUANG_PARENT_APP,MyParamAll.JIGUANG_PARENT_MASTER,content,parentPhone);
								
								//添加老师通知记录
								String teacherIds =(String)param.get("teacherId");
								String[] teacherId = teacherIds.split(",");
								content = "您的学生"+(String)param.get("babyName")+"于"+sdf.format(new Date(bai.getExamDate()))+"迟到";
								for(String id:teacherId){
									attendanceDao.addInfoLog(gartenInfo.getGartenId(),"迟到",sdf.format(new Date(bai.getExamDate())),"老师",Integer.valueOf(id),(String)param.get("babyName"));
									//添加通知的同时推送通知
									String teacherPhone = attendanceDao.getTeacherPhoneById(id);
									
										bigcontrolService.pushOne(MyParamAll.JIGUANG_WORKER_APP,MyParamAll.JIGUANG_WORKER_MASTER,content,teacherPhone);
									
								}
							}else{		//正常签到发送推送
								Map<String, Object> param = attendanceDao.findNameAndParentAndTeachersByBabyId(bai.getBabyId());
								String parentPhone = attendanceDao.findParentPhoneById((Integer)param.get("parentId"));
								content = "您的孩子"+(String)param.get("babyName")+"于"+sdf.format(new Date(bai.getExamDate()))+"入园签到";
								
									bigcontrolService.pushOne(MyParamAll.JIGUANG_PARENT_APP,MyParamAll.JIGUANG_PARENT_MASTER,content,parentPhone);
								
								String teacherIds =(String)param.get("teacherId");
								String[] teacherId = teacherIds.split(",");
								content = "您的学生"+(String)param.get("babyName")+"于"+sdf.format(new Date(bai.getExamDate()))+"入园签到";
								for(String id:teacherId){
									String teacherPhone = attendanceDao.getTeacherPhoneById(id);
									
									bigcontrolService.pushOne(MyParamAll.JIGUANG_WORKER_APP,MyParamAll.JIGUANG_WORKER_MASTER,content,teacherPhone);
									
								}	
							}
						}else{	//下午
							attendanceDao.updateBabyPmCheckLog(bai.getBabyId(),sdf.format(new Date(bai.getExamDate())),sdf3.format(new Date(bai.getAttendanceDate())),bai.getExamValue(),bai.getFileId(),bai.getMacId(),gartenInfo.getGartenId(),imgUrl);
							if(leaveflag){
								attendanceDao.addUnusual(bai.getBabyId(),sdf.format(new Date(bai.getExamDate())),sdf3.format(new Date(bai.getAttendanceDate())),bai.getFileId(),gartenInfo.getGartenId(),0,1,bai.getCardNo(),imgUrl,job);
								Map<String, Object> param =attendanceDao.findNameAndParentAndTeachersByBabyId(bai.getBabyId());
								attendanceDao.addInfoLog(gartenInfo.getGartenId(),"早退",sdf.format(new Date(bai.getExamDate())),"家长",(Integer)param.get("parentId"),(String)param.get("babyName"));
								String parentPhone = attendanceDao.findParentPhoneById((Integer)param.get("parentId"));
								content = "您的孩子"+(String)param.get("babyName")+"于"+sdf.format(new Date(bai.getExamDate()))+"早退";
								
									bigcontrolService.pushOne(MyParamAll.JIGUANG_PARENT_APP,MyParamAll.JIGUANG_PARENT_MASTER,content,parentPhone);
								
								//添加老师通知记录
								String teacherIds =(String)param.get("teacherId");
								String[] teacherId = teacherIds.split(",");
								content = "您的学生"+(String)param.get("babyName")+"于"+sdf.format(new Date(bai.getExamDate()))+"早退";
								for(String id:teacherId){
									attendanceDao.addInfoLog(gartenInfo.getGartenId(),"早退",sdf.format(new Date(bai.getExamDate())),"老师",Integer.valueOf(id),(String)param.get("babyName"));
									String teacherPhone = attendanceDao.getTeacherPhoneById(id);
									
									bigcontrolService.pushOne(MyParamAll.JIGUANG_WORKER_APP,MyParamAll.JIGUANG_WORKER_MASTER,content,teacherPhone);
									
								}
							}else{		//正常签到
								Map<String, Object> param = attendanceDao.findNameAndParentAndTeachersByBabyId(bai.getBabyId());
								String parentPhone = attendanceDao.findParentPhoneById((Integer)param.get("parentId"));
								content = "您的孩子"+(String)param.get("babyName")+"于"+sdf.format(new Date(bai.getExamDate()))+"离园签退";
								
									bigcontrolService.pushOne(MyParamAll.JIGUANG_PARENT_APP,MyParamAll.JIGUANG_PARENT_MASTER,content,parentPhone);
								
								String teacherIds =(String)param.get("teacherId");
								String[] teacherId = teacherIds.split(",");
								content = "您的学生"+(String)param.get("babyName")+"于"+sdf.format(new Date(bai.getExamDate()))+"离园签退";
								for(String id:teacherId){
									String teacherPhone = attendanceDao.getTeacherPhoneById(id);
									
										bigcontrolService.pushOne(MyParamAll.JIGUANG_WORKER_APP,MyParamAll.JIGUANG_WORKER_MASTER,content,teacherPhone);
									
								}	
							}
						}
					}else if("老师".equals(job)){	//上传的是老师的考勤信息
						//先查询worker check 是否已经有当天的记录
						WorkerCheckLog workerCheckLog = attendanceDao.findWorkerCheckByTime(sdf3.format(new Date(bai.getExamDate()))+" 00:00:00",bai.getBabyId());
						//签到时间大于规定到园时间
						if(workerCheckLog.getArriveTime()==null){
							attendanceDao.updateWorkerAmCheckLog(bai.getBabyId(),sdf.format(new Date(bai.getExamDate())),sdf3.format(new Date(bai.getAttendanceDate())),bai.getFileId(),bai.getMacId(),gartenInfo.getGartenId(),imgUrl);
							if(arriveflag){
								attendanceDao.addUnusual(bai.getBabyId(),sdf.format(new Date(bai.getExamDate())),sdf3.format(new Date(bai.getAttendanceDate())),bai.getFileId(),gartenInfo.getGartenId(),0,0,bai.getCardNo(),imgUrl,job);
								List<AdminInfoLog> adminList = attendanceDao.findAdminByGartenId(gartenInfo.getGartenId());
								String teacherName = attendanceDao.findTeacherNameById(bai.getBabyId());
								for(AdminInfoLog admin : adminList){
									attendanceDao.addInfoLog(gartenInfo.getGartenId(),"迟到",sdf.format(new Date(bai.getExamDate())),admin.getJob(),admin.getId(),teacherName);
//									LyUtils.pushToOne("201730daa413a52420cf1da1","9ba4d827c9d05b7d8c4ced94", teacherName+"老师于"+sdf.format(new Date(bai.getExamDate()))+"迟到", admin.getPhoneNumber());
								}
							}
						}else{
							attendanceDao.updateWorkerPmCheckLog(bai.getBabyId(),sdf.format(new Date(bai.getExamDate())),sdf3.format(new Date(bai.getAttendanceDate())),bai.getFileId(),bai.getMacId(),gartenInfo.getGartenId(),imgUrl);
							if(leaveflag){
								attendanceDao.addUnusual(bai.getBabyId(),sdf.format(new Date(bai.getExamDate())),sdf3.format(new Date(bai.getAttendanceDate())),bai.getFileId(),gartenInfo.getGartenId(),0,1,bai.getCardNo(),imgUrl,job);
								List<AdminInfoLog> adminList = attendanceDao.findAdminByGartenId(gartenInfo.getGartenId());
								String teacherName = attendanceDao.findTeacherNameById(bai.getBabyId());
								for(AdminInfoLog admin : adminList){
									attendanceDao.addInfoLog(gartenInfo.getGartenId(),"早退",sdf.format(new Date(bai.getExamDate())),admin.getJob(),admin.getId(),teacherName);
//									LyUtils.pushToOne("201730daa413a52420cf1da1","9ba4d827c9d05b7d8c4ced94", teacherName+"老师于"+sdf.format(new Date(bai.getExamDate()))+"早退", admin.getPhoneNumber());
								}
							}
						}
					}else{
						result.put("respCode", 600008);
						result.put("respDesc", "宝宝或老师卡号错误");
					}
				}
				result.put("respCode", 0);
				result.put("respDesc", "请求成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("respCode", 600004);
				result.put("respDesc", "晨检信息上传失败");
			}
		
		return result;
	}
	
	
	public Map<String,Object> getTeacherAndStudentList(String schoolToken,String partnerToken,String partnerId){
		HashMap<String, Object> result = new HashMap<String,Object>();
		//缺少参数  直接返回 错误
		if(schoolToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，schoolToken为空或格式不正确");
		}
		if(partnerToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerToken为空或格式不正确");
		}
		if(partnerId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerId为空或格式不正确");
		}
		GartenInfo gartenInfo = attendanceDao.findGartenByToken(schoolToken);
		PartnerInfo partnerInfo = attendanceDao.findPartnerByTokenAndId(partnerToken, partnerId);
		if(null==gartenInfo||null==partnerInfo){
			result.put("respCode", 600001);
			result.put("respDesc", "无效合作商或者无效schoolToken");
			if(null==partnerInfo){
				result.put("respCode", 600003);
				result.put("respDesc", "无效的访问令牌或者令牌已过期");
			}
			return result;
		}
		if(attendanceDao.findAttendanceUseful(schoolToken)==0){
			result.put("respCode", 600009);
			result.put("respDesc", "考勤服务到期，请联系成长记忆续费");
			return result;
		}
			List<TeacherAndBabyInfo> teacherList = attendanceDao.findTeachers(gartenInfo.getGartenId());
			for(TeacherAndBabyInfo teacher :teacherList){
				teacher.setType("1");
			}
			List<TeacherAndBabyInfo> babyList = attendanceDao.findBabys(gartenInfo.getGartenId());
			for(TeacherAndBabyInfo baby : babyList){
				baby.setType("0");
			}
			ArrayList<TeacherAndBabyInfo> list = new ArrayList<TeacherAndBabyInfo>();
			list.addAll(teacherList);
			list.addAll(babyList);
			result.put("respCode", 0);
			result.put("respDesc", "请求成功");
			result.put("list", list);
		return result;
	}

	
	//心跳测试
	public Map<String,Object> sendHeartbeat(String schoolToken,String partnerToken,String partnerId){
		HashMap<String, Object> result = new HashMap<String,Object>();
		//缺少参数  直接返回 错误
		if(schoolToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，schoolToken为空或格式不正确");
		}
		if(partnerToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerToken为空或格式不正确");
		}
		if(partnerId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerId为空或格式不正确");
		}
		
		GartenInfo gartenInfo = attendanceDao.findGartenByToken(schoolToken);
		PartnerInfo partnerInfo = attendanceDao.findPartnerByTokenAndId(partnerToken, partnerId);
		if(null==gartenInfo||null==partnerInfo){
			result.put("respCode", 600001);
			result.put("respDesc", "无效合作商或者无效schoolToken");
			if(null==partnerInfo){
				result.put("respCode", 600003);
				result.put("respDesc", "无效的访问令牌或者令牌已过期");
			}
			return result;
		}
		if(attendanceDao.findAttendanceUseful(schoolToken)==0){
			result.put("respCode", 600009);
			result.put("respDesc", "考勤服务到期，请联系成长记忆续费");
			return result;
		}
		if(gartenInfo==null||partnerInfo==null){
			result.put("respCode", 600006);
			result.put("respDesc", "心跳接口调用失败");
		}else{
			result.put("respCode", 0);
			result.put("respDesc", "请求成功");
		}
		return result;
		
	}
	
	
	//附件上传接口
	public Map<String,Object> attendanceFileUpload(String schoolToken,String partnerToken,String partnerId,String macId,MultipartFile[] files,String[] fileIds){
		HashMap<String, Object> result = new HashMap<String,Object>();
		//缺少参数  直接返回 错误
		if(files==null||schoolToken==null||partnerToken==null||partnerId==null||macId==null||fileIds==null){
			result.put("respCode", 1);
			result.put("respDesc", "请求参数错误");
			return result;
		}
		GartenInfo gartenInfo = attendanceDao.findGartenByToken(schoolToken);
		PartnerInfo partnerInfo = attendanceDao.findPartnerByTokenAndId(partnerToken, partnerId);
		if(attendanceDao.findAttendanceUseful(schoolToken)==0){
			result.put("respCode", 600009);
			result.put("respDesc", "考勤服务到期，请联系成长记忆续费");
			
			if(1==gartenInfo.getAccountState()){
				MyUtil.putMapParams(result, "respCode", 600011, "respDesc", "幼儿园已被冻结");
			}
			return result;
		}
		if(gartenInfo==null||partnerInfo==null){
			result.put("respCode", 600001);
			result.put("respDesc", "无效合作商或者无效schoolToken");
			//合作商信息为空，600003  无效的访问令牌或者令牌已过期
			if(partnerInfo==null){
				result.put("respCode", 600003);
				result.put("respDesc", "无效的访问令牌或者令牌已过期");
			}
			return result;
		}else{
			//图片文件不为空
			if(files!=null&&files.length>0){  
				// endpoint以杭州为例，其它region请按实际情况填写
				String endpoint = MyParamAll.MYOSS_ENDPOINT;
				// accessKey请登录https://ak-console.aliyun.com/#/查看
				String accessKeyId = MyParamAll.MYOSS_ACCESSKEYID;
				String accessKeySecret = MyParamAll.MYOSS_ACCESSKEYSECRET;
				String bucket = MyParamAll.MYOSS_BUCKET;
				String bucketLujin = MyParamAll.MYOSS_BUCKETLUJIN;
				// 创建OSSClient实例
				OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
				//循环获取file数组中得文件  
//				String fileName=null;
				for(int i = 0;i<files.length;i++){  
	                try {
						MultipartFile file = files[i];  
						// 上传文件
						CommonsMultipartFile cf= (CommonsMultipartFile)file; 
				        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
				        InputStream fileContent = fi.getInputStream(); 
				        String imgUrl= MyParamAll.MYOSS_ADDRESS+file.getOriginalFilename();//获取到刚上传的图片路劲
				        attendanceDao.addCheckImg(imgUrl,fileIds[i]);
				        //把JSP上的图片上传到OSS
				        ossClient.putObject(bucket, bucketLujin+file.getOriginalFilename(), fileContent);
				        
					} catch (Exception e) {
						e.printStackTrace();
						result.put("respCode", 600005);
						result.put("respDesc", "考勤图片上传失败");
						return result;
					}
	            }  
				// 关闭client
				ossClient.shutdown();
	        }  
			result.put("respCode", 0);
			result.put("respDesc", "请求成功");
		}
		return result;
	}

	//拉取幼儿园考勤配置接口
	public Map<String,Object> getAttendanceConf(String schoolToken,String partnerToken,String partnerId,String macId){
		HashMap<String, Object> result = new HashMap<String,Object>();
		//缺少参数  直接返回 错误
		if(schoolToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，schoolToken为空或格式不正确");
		}
		if(partnerToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerToken为空或格式不正确");
		}
		if(partnerId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerId为空或格式不正确");
		}
		if(macId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，macId为空或格式不正确");
		}
		GartenInfo gartenInfo = attendanceDao.findGartenByToken(schoolToken);
		PartnerInfo partnerInfo = attendanceDao.findPartnerByTokenAndId(partnerToken, partnerId);
		if(null==gartenInfo||null==partnerInfo){
			result.put("respCode", 600001);
			result.put("respDesc", "无效合作商或者无效schoolToken");
			if(null==partnerInfo){
				result.put("respCode", 600003);
				result.put("respDesc", "无效的访问令牌或者令牌已过期");
			}
			return result;
		}
		if(attendanceDao.findAttendanceUseful(schoolToken)==0){
			result.put("respCode", 600009);
			result.put("respDesc", "考勤服务到期，请联系成长记忆续费");
			return result;
		}
		HashMap<String, Object> param = new HashMap<String,Object>();
		List<EquipmentShort> equipments = attendanceDao.findEquipmentByMacId(gartenInfo.getGartenId(), macId);
		Map<String, Object> map = attendanceDao.findGartenAttendanceFlag(gartenInfo.getGartenId());
		param.put("teacherAttendanceFlag", map.get("teacherAttendanceFlag"));
		param.put("studentAttendanceFlag", map.get("studentAttendanceFlag"));
		param.put("list", equipments);
		result.put("conf", param);
		result.put("respCode", 0);
		result.put("respDesc", "请求成功");
		return result;
		
	}

	public Map<String, Object> getPlayTime(String schoolToken, String partnerToken, String partnerId) {
		HashMap<String, Object> result = new HashMap<String,Object>();
		//缺少参数  直接返回 错误
		if(schoolToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，schoolToken为空或格式不正确");
		}
		if(partnerToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerToken为空或格式不正确");
		}
		if(partnerId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerId为空或格式不正确");
		}
		
		GartenInfo gartenInfo = attendanceDao.findGartenByToken(schoolToken);
		PartnerInfo partnerInfo = attendanceDao.findPartnerByTokenAndId(partnerToken, partnerId);
		if(null==gartenInfo||null==partnerInfo){
			result.put("respCode", 600001);
			result.put("respDesc", "无效合作商或者无效schoolToken");
			if(null==partnerInfo){
				result.put("respCode", 600003);
				result.put("respDesc", "无效的访问令牌或者令牌已过期");
			}
			return result;
		}
		if(attendanceDao.findAttendanceUseful(schoolToken)==0){
			result.put("respCode", 600009);
			result.put("respDesc", "考勤服务到期，请联系成长记忆续费");
			return result;
		}
		//身份、参数正常
		Map<String, Object> map = attendanceDao.findPlayTimeByGartenToken(schoolToken);
		String arriveStartTime = (String)map.get("arriveStartTime");
		String arriveEndTime = (String)map.get("arriveEndTime");
		String leaveStartTime = (String)map.get("leaveStartTime");
		String leaveEndTime = (String)map.get("leaveEndTime");
		if(null==arriveStartTime||null==arriveEndTime||null==leaveStartTime||null==leaveEndTime){
			return MyUtil.putMapParams(result, "respCode", 600010, "respDesc","未设置考勤时间及是否允许出入园");
		}
		result.put("arriveStartTime", map.get("arriveStartTime"));
		result.put("arriveEndTime", map.get("arriveEndTime"));
		result.put("leaveStartTime", map.get("leaveStartTime"));
		result.put("leaveEndTime", map.get("leaveEndTime"));
		result.put("respCode", 0);
		result.put("respDesc", "请求成功");
		return result;
	}
	
	
	public Map<String,Object> reboot(String schoolToken,String partnerToken,String partnerId, String macId){
		
		HashMap<String, Object> result = new HashMap<String,Object>();
		//缺少参数  直接返回 错误
		if(schoolToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，schoolToken为空或格式不正确");
		}
		if(partnerToken==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerToken为空或格式不正确");
		}
		if(partnerId==null){
			return MyUtil.putMapParams(result,"respCode", 1, "respDesc", "请求参数错误，partnerId为空或格式不正确");
		}
		GartenInfo gartenInfo = attendanceDao.findGartenByToken(schoolToken);
		PartnerInfo partnerInfo = attendanceDao.findPartnerByTokenAndId(partnerToken, partnerId);
		if(null==gartenInfo||null==partnerInfo){
			result.put("respCode", 600001);
			result.put("respDesc", "无效合作商或者无效schoolToken");
			if(null==partnerInfo){
				result.put("respCode", 600003);
				result.put("respDesc", "无效的访问令牌或者令牌已过期");
			}
			return result;
		}
		if(attendanceDao.findAttendanceUseful(schoolToken)==0){
			result.put("respCode", 600009);
			result.put("respDesc", "考勤服务到期，请联系成长记忆续费");
			return result;
		}
		Integer rebootFlag = attendanceDao.findGartenRebootFlag(macId);
		if(rebootFlag==1){
			attendanceDao.updateRebootFlag(macId);
		}
		result.put("rebootFlag",rebootFlag);
		result.put("respCode", 0);
		result.put("respDesc", "请求成功");
		
		return result;
		
	}
}
