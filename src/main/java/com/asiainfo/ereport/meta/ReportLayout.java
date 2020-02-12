package com.asiainfo.ereport.meta;

import java.util.Date;

public class ReportLayout {
	private String id;
	private String name;
	private String content;
	private String varList;
	private Integer versionNum;
	private Date updateDate;
	private String updateStaff;
	private String updateDepart;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVarList() {
		return varList;
	}

	public void setVarList(String varList) {
		this.varList = varList;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
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
