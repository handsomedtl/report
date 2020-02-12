package com.asiainfo.edata.meta;

import java.util.List;

/**
 * 自定义报表分组
 * 
 * @author baowzh
 *
 */
public class ECustDatasetGroup {
	
	private String groupname;
	private String groupid;
	/**
	 * 报表组维度定义
	 */
	private List<EStatisticsDimension> dimensions;
	/**
	 * 模板表单id 此表单中定义每个 dimensionCol 对应的渲染元素和级联关系
	 */
	private String templateformid;
	/**
	 * 属于此组的所有数据集
	 */
	private List<ECustomDatasetMeta> datasets;
	public List<EStatisticsDimension> getDimensions() {
		return dimensions;
	}
	public void setDimensions(List<EStatisticsDimension> dimensions) {
		this.dimensions = dimensions;
	}
	public String getTemplateformid() {
		return templateformid;
	}
	public void setTemplateformid(String templateformid) {
		this.templateformid = templateformid;
	}
	public List<ECustomDatasetMeta> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<ECustomDatasetMeta> datasets) {
		this.datasets = datasets;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
}
