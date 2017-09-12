package com.garten.model.other;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Version   {
	
	private String version;
	private String url;
	private String info;
	private Long time;
	private Integer type;
	@Override
	public String toString() {
		return "Version [version=" + version + ", url=" + url + ", info=" + info + ", time=" + time + ", type=" + type
				+ "]";
	}
	public Version() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Version(String version, String url, String info, Long time, Integer type) {
		super();
		this.version = version;
		this.url = url;
		this.info = info;
		this.time = time;
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
