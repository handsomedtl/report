package com.asiainfo.ereport.meta;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportMeta {
	private List<ReportComponentMeta> componentconfigs;
	private Map<String, ReportComponentMeta> comMetaMap = new HashMap<String, ReportComponentMeta>();
	private String id;
	private String category;
	private String name;
	private String rightCode;
	private String layoutId;
	private String layoutContent;
	private String helpContent;
	private String description;
	private Integer status;
	private String updateStaff;
	private String updateDepart;
	private Date updateDate;
	private String remark;
	private String config;
	private String createStaff;
	private String createDepart;
	private Date createTime;
	private String type;
	private String ownerid;

	public String getCreateStaff() {
		return createStaff;
	}

	public void setCreateStaff(String createStaff) {
		this.createStaff = createStaff;
	}

	public String getCreateDepart() {
		return createDepart;
	}

	public void setCreateDepart(String createDepart) {
		this.createDepart = createDepart;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public String getLayoutContent() {
		return layoutContent;
	}

	public void setLayoutContent(String layoutContent) {
		this.layoutContent = layoutContent;
	}

	public String getHelpContent() {
		return helpContent;
	}

	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ReportComponentMeta> getComponentconfigs() {
		return componentconfigs;
	}

	public void setComponentconfigs(List<ReportComponentMeta> componentconfigs) {
		this.componentconfigs = componentconfigs;
		comMetaMap.clear();
		for (ReportComponentMeta c : componentconfigs) {
			comMetaMap.put(c.getCompId(), c);
		}
	}

	public ReportComponentMeta getComponentMeta(String componentId) {
		return comMetaMap.get(componentId);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

}
