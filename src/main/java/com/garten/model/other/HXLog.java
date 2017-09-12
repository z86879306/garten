package com.garten.model.other;

import java.sql.Timestamp;

public class HXLog {
	
	private String fromName;
	private String toName;
	private Long time;
	private Integer huanxinId;
	private String content;
	
	public HXLog(String fromName, String toName, String content) {
		super();
		this.fromName = fromName;
		this.toName = toName;
		this.content = content;
	}
	@Override
	public String toString() {
		return "HXLog [fromName=" + fromName + ", toName=" + toName + ", time=" + time + ", huanxinId=" + huanxinId
				+ ", content=" + content + "]";
	}
	public HXLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HXLog(String fromName, String toName, Long time, Integer huanxinId, String content) {
		super();
		this.fromName = fromName;
		this.toName = toName;
		this.time = time;
		this.huanxinId = huanxinId;
		this.content = content;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	public Integer getHuanxinId() {
		return huanxinId;
	}
	public void setHuanxinId(Integer huanxinId) {
		this.huanxinId = huanxinId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
