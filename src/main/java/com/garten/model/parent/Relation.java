package com.garten.model.parent;

public class Relation {
	
	private String relation;
	private Integer relationId;
	@Override
	public String toString() {
		return "Relation [relation=" + relation + ", relationId=" + relationId + "]";
	}
	public Relation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Relation(String relation, Integer relationId) {
		super();
		this.relation = relation;
		this.relationId = relationId;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public Integer getRelationId() {
		return relationId;
	}
	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}
	
	
}
