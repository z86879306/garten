package com.garten.vo.bigcontrol;

public class DakaCamera {
	private String gartenName;
	private String deviceSerial;
	private String validateCode;
	private Integer type;
	private String macId;
	private String cameraIp;
	private Integer cameraPort;
	private String cameraUser;
	private String cameraPwd;
	private Integer cameraId;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
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
	public Integer getCameraId() {
		return cameraId;
	}
	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}
	public DakaCamera(String gartenName, String deviceSerial, String validateCode, Integer type, String macId,
			String cameraIp, Integer cameraPort, String cameraUser, String cameraPwd, Integer cameraId) {
		super();
		this.gartenName = gartenName;
		this.deviceSerial = deviceSerial;
		this.validateCode = validateCode;
		this.type = type;
		this.macId = macId;
		this.cameraIp = cameraIp;
		this.cameraPort = cameraPort;
		this.cameraUser = cameraUser;
		this.cameraPwd = cameraPwd;
		this.cameraId = cameraId;
	}
	public DakaCamera() {
		super();
	}
	
}
