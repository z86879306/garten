package com.garten.vo.attendance;

public class EquipmentShort {

	
	private String cameraIp;
	private Integer cameraPort;
	private String cameraUser;
	private String cameraPwd;
	private Integer type;
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
	public EquipmentShort(String cameraIp, Integer cameraPort, String cameraUser, String cameraPwd, Integer type) {
		super();
		this.cameraIp = cameraIp;
		this.cameraPort = cameraPort;
		this.cameraUser = cameraUser;
		this.cameraPwd = cameraPwd;
		this.type = type;
	}
	
}
