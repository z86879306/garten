package com.garten.model.other;

public class Equipment {
	
	private Integer cameraId;
	private Integer gartenId;
	private String cameraIp;
	private Integer cameraPort;
	private String cameraUser;
	private String cameraPwd;
	private Integer type;
	private String macId;
	private String deviceSerial;
	private String validateCode;
	
	public Integer getCameraId() {
		return cameraId;
	}
	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public Equipment(Integer cameraId, Integer gartenId, String cameraIp, Integer cameraPort, String cameraUser,
			String cameraPwd, Integer type, String macId, String deviceSerial, String validateCode) {
		super();
		this.cameraId = cameraId;
		this.gartenId = gartenId;
		this.cameraIp = cameraIp;
		this.cameraPort = cameraPort;
		this.cameraUser = cameraUser;
		this.cameraPwd = cameraPwd;
		this.type = type;
		this.macId = macId;
		this.deviceSerial = deviceSerial;
		this.validateCode = validateCode;
	}
	public Equipment() {
		super();
	}
	
	
	
}
