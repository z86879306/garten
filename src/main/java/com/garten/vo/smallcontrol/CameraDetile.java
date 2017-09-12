package com.garten.vo.smallcontrol;

public class CameraDetile {
	
	private String deviceSerial;
	private Integer channelNo;
	private String deviceName;
	private String rtmp;
	private String rtmpHd;
	private String hls;
	private String hlsHd;
	
	
	public String getHls() {
		return hls;
	}
	public void setHls(String hls) {
		this.hls = hls;
	}
	public String getHlsHd() {
		return hlsHd;
	}
	public void setHlsHd(String hlsHd) {
		this.hlsHd = hlsHd;
	}
	public String getDeviceSerial() {
		return deviceSerial;
	}
	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	public Integer getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(Integer channelNo) {
		this.channelNo = channelNo;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	

	public String getRtmp() {
		return rtmp;
	}
	public void setRtmp(String rtmp) {
		this.rtmp = rtmp;
	}
	public String getRtmpHd() {
		return rtmpHd;
	}
	public void setRtmpHd(String rtmpHd) {
		this.rtmpHd = rtmpHd;
	}
	public CameraDetile() {
		super();
	}
	public CameraDetile(String deviceSerial, Integer channelNo, String deviceName, String rtmp, String rtmpHd,
			String hls, String hlsHd) {
		super();
		this.deviceSerial = deviceSerial;
		this.channelNo = channelNo;
		this.deviceName = deviceName;
		this.rtmp = rtmp;
		this.rtmpHd = rtmpHd;
		this.hls = hls;
		this.hlsHd = hlsHd;
	}

	
}
