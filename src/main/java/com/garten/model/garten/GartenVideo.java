package com.garten.model.garten;

import java.sql.Timestamp;

public class GartenVideo {
	
	private Integer videoId;
	private Integer	type;
	private Integer gartenId;
	private Integer pointId;
	private String url;
	private String img;
	private String deviceSerial;
	private Long time;
	public GartenVideo(Integer videoId, Integer type, Integer gartenId, Integer pointId, String url, String img,
			String deviceSerial, Long time) {
		super();
		this.videoId = videoId;
		this.type = type;
		this.gartenId = gartenId;
		this.pointId = pointId;
		this.url = url;
		this.img = img;
		this.deviceSerial = deviceSerial;
		this.time = time;
	}
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDeviceSerial() {
		return deviceSerial;
	}
	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime();
	}
	
}
