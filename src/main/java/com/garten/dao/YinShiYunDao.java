package com.garten.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.garten.model.garten.GartenVideo;
import com.garten.model.other.Equipment;

public interface YinShiYunDao {

	void addDakaCamera(@Param("gartenId")Integer gartenId, @Param("cameraIp")String cameraIp,@Param("cameraPort")Integer cameraPort,@Param("cameraUser")String cameraUser,@Param("cameraPwd")String cameraPwd
			 ,@Param("type")Integer type,@Param("macId")Integer macId,@Param("deviceSerial")String deviceSerial,@Param("validateCode")String validateCode);

	void addLiveCamera(@Param("gartenId")Integer gartenId, @Param("cameraIp")String cameraIp,@Param("cameraPort")Integer cameraPort,@Param("cameraUser")String cameraUser,@Param("cameraPwd")String cameraPwd
			 ,@Param("deviceSerial")String deviceSerial,@Param("validateCode")String validateCode);

	void addGartenVideo(@Param("deviceSerial")String deviceSerial,@Param("gartenId") Integer gartenId, @Param("url")String url,@Param("type") Integer type, @Param("pointId")Integer pointId,
			@Param("gartenVideoImg")String gartenVideoImg);

	void deleteCamera(String deviceSerial);

	void deleteGartenVideo(String deviceSerial);

	List<Equipment> findEquipBygartenId(Integer gartenId);

	List<GartenVideo> findVideoByGartenId(Integer gartenId);

	

}
