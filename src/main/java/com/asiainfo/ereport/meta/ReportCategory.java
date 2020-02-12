package com.asiainfo.ereport.meta;

import java.util.Date;

public class ReportCategory {
	private String categoryId;
	private String name;
	private String type;
	private String parentCat;
	private String showOrder;
	private Date updateDate;
	private String updateStaff;
	private String updateDepart;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentCat() {
		return parentCat;
	}

	public void setParentCat(String parentCat) {
		this.parentCat = parentCat;
	}

	public String getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateStaff() {
		return updateStaff;
	}

	public void setUpdateStaff(String updateStaff) {
		this.updateStaff = updateStaff;
	}

	public String getUpdateDepart() {
		return updateDepart;
	}

	public void setUpdateDepart(String updateDepart) {
		this.updateDepart = updateDepart;
	}

}
