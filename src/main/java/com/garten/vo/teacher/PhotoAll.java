package com.garten.vo.teacher;

import java.sql.Timestamp;
import java.util.List;

import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.other.Comment;

public class PhotoAll {
	
	private GartenPhotos photo;
	private List<Comment> evaluation;
	private List<PhotoDianZan> dianzan;
	private Integer isDianzan;

	public Integer getIsDianzan() {
		return isDianzan;
	}
	public void setIsDianzan(Integer isDianzan) {
		this.isDianzan = isDianzan;
	}
	public PhotoAll(GartenPhotos photo, List<Comment> evaluation, List<PhotoDianZan> dianzan, Integer isDianzan) {
		super();
		this.photo = photo;
		this.evaluation = evaluation;
		this.dianzan = dianzan;
		this.isDianzan = isDianzan;
	}
	@Override
	public String toString() {
		return "PhotoAll [photo=" + photo + ", evaluation=" + evaluation + ", dianzan=" + dianzan + "]";
	}
	public PhotoAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PhotoAll(GartenPhotos photo, List<Comment> evaluation, List<PhotoDianZan> dianzan) {
		super();
		this.photo = photo;
		this.evaluation = evaluation;
		this.dianzan = dianzan;
	}
	public GartenPhotos getPhoto() {
		return photo;
	}
	public void setPhoto(GartenPhotos photo) {
		this.photo = photo;
	}
	public List<Comment> getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(List<Comment> evaluation) {
		this.evaluation = evaluation;
	}
	public List<PhotoDianZan> getDianzan() {
		return dianzan;
	}
	public void setDianzan(List<PhotoDianZan> dianzan) {
		this.dianzan = dianzan;
	}
	
	
	
}
