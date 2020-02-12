package com.asiainfo.ereport.meta;

public class RptCategory {
	private String id;
	private String categoryName;
	private String parentId;
	private String ownStaffId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getOwnStaffId() {
		return ownStaffId;
	}
	public void setOwnStaffId(String ownStaffId) {
		this.ownStaffId = ownStaffId;
	}
	
}
