package com.garten.model.garten;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Video {
	
	private Integer videoId;
	private Integer type;
	private Integer gartenId;
	private Integer pointId;
	private Long time;
	private String  url;
	private String  img;
	@Override
	public String toString() {
		return "Video [videoId=" + videoId + ", type=" + type + ", gartenId=" + gartenId + ", pointId=" + pointId
				+ ", time=" + time + ", url=" + url + ", img=" + img + "]";
	}
	public Video() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Video(Integer videoId, Integer type, Integer gartenId, Integer pointId, Long time, String url, String img) {
		super();
		this.videoId = videoId;
		this.type = type;
		this.gartenId = gartenId;
		this.pointId = pointId;
		this.time = time;
		this.url = url;
		this.img = img;
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
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
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
	
	
	
}
