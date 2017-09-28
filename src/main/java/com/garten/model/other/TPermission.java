package com.garten.model.other;

public class TPermission {

	private Integer id;
	private String permissionName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public TPermission(Integer id, String permissionName) {
		super();
		this.id = id;
		this.permissionName = permissionName;
	}
	public TPermission() {
		super();
	}
	
	
}
