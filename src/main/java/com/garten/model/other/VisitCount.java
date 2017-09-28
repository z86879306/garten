package com.garten.model.other;

import java.sql.Date;

public class VisitCount {

	private Integer id;
	private Long gmt_create;
	private Long gmt_modified;
	private Integer count;
	private Integer type;
	private Integer gartenId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create.getTime()/1000;
	}
	public Long getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(Date gmt_modified) {
		this.gmt_modified = gmt_modified.getTime()/1000;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
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
	
	public VisitCount() {
		super();
	}
	public VisitCount(Integer id, Long gmt_create, Long gmt_modified, Integer count, Integer type, Integer gartenId) {
		super();
		this.id = id;
		this.gmt_create = gmt_create;
		this.gmt_modified = gmt_modified;
		this.count = count;
		this.type = type;
		this.gartenId = gartenId;
	}
	
	
}
