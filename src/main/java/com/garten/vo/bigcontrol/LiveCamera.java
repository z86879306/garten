package com.garten.vo.bigcontrol;

import com.garten.model.gartenClass.GartenClass;

public class LiveCamera {
	private Integer gartenId;
	private String gartenName;
	private String deviceSerial;
	private String validateCode;
	private String url;
	private String cameraUser;
	private String cameraPwd;
	private String cameraIp;
	private Integer cameraPort;
	private Integer cameraId;
	private Integer type;	//0教室,1操场,2食堂,3公共教室
	private Integer pointId;
	private String img;
	
	private GartenClass gartenClass;
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	public String getDeviceSerial() {
		return deviceSerial;
	}
	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCameraUser() {
		return cameraUser;
	}
	public void setCameraUser(String cameraUser) {
		this.cameraUser = cameraUser;
	}
	public String getCameraPwd() {
		return cameraPwd;
	}
	public void setCameraPwd(String cameraPwd) {
		this.cameraPwd = cameraPwd;
	}
	public String getCameraIp() {
		return cameraIp;
	}
	public void setCameraIp(String cameraIp) {
		this.cameraIp = cameraIp;
	}
	public Integer getCameraPort() {
		return cameraPort;
	}
	public void setCameraPort(Integer cameraPort) {
		this.cameraPort = cameraPort;
	}
	public Integer getCameraId() {
		return cameraId;
	}
	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	public GartenClass getGartenClass() {
		return gartenClass;
	}
	public void setGartenClass(GartenClass gartenClass) {
		this.gartenClass = gartenClass;
	}
	public LiveCamera(Integer gartenId, String gartenName, String deviceSerial, String validateCode, String url,
			String cameraUser, String cameraPwd, String cameraIp, Integer cameraPort, Integer cameraId, Integer type,
			Integer pointId, String img) {
		super();
		this.gartenId = gartenId;
		this.gartenName = gartenName;
		this.deviceSerial = deviceSerial;
		this.validateCode = validateCode;
		this.url = url;
		this.cameraUser = cameraUser;
		this.cameraPwd = cameraPwd;
		this.cameraIp = cameraIp;
		this.cameraPort = cameraPort;
		this.cameraId = cameraId;
		this.type = type;
		this.pointId = pointId;
		this.img = img;
	}
	public LiveCamera() {
		super();
	}
	
	
	
}
