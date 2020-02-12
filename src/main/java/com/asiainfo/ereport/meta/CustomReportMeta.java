package com.asiainfo.ereport.meta;

import java.util.List;

/**
 * 自定义报表数据结构
 * 
 * @author baowzh
 *
 */
public class CustomReportMeta {
	/**
	 * 表头字符串
	 */
	private String headstr;
	/**
	 * 绑定地段列表
	 */
	private List<CustomReportBindField> bindfields;
	/**
	 * 分组设置
	 */
	private List<CustomReportGroupField> groupfields;
	/**
	 * 是否分页
	 */
	private boolean paging;
	/**
	 * 是否导出
	 */
	private boolean exp;
	/**
	 * 功能列表
	 */
	private List<String> functions;
	/**
	 * 过滤条件定义
	 */
	private List<CustomReportCondition> conditions;
	/**
	 * 模板表单id
	 */
	private String templateformid;
	/**
	 * 报表名称
	 */
	private String reportName;
	/**
	 * 报表id
	 */
	private String reportId;
	/**
	 * 分类id
	 */
	private String category;
	/**
	 * 设置报表时候选择的数据集
	 */
	private String datasetId;
	/**
	 * 报表数据集
	 */
	private String reportDatasetId;
	/**
	 * 
	 */
	private String formId;
	
	private String gridId;

	public String getHeadstr() {
		return headstr;
	}

	public void setHeadstr(String headstr) {
		this.headstr = headstr;
	}

	public List<CustomReportBindField> getBindfields() {
		return bindfields;
	}

	public void setBindfields(List<CustomReportBindField> bindfields) {
		this.bindfields = bindfields;
	}

	public boolean isPaging() {
		return paging;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	public List<String> getFunctions() {
		return functions;
	}

	public void setFunctions(List<String> functions) {
		this.functions = functions;
	}

	public List<CustomReportCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<CustomReportCondition> conditions) {
		this.conditions = conditions;
	}

	public String getTemplateformid() {
		return templateformid;
	}

	public void setTemplateformid(String templateformid) {
		this.templateformid = templateformid;
	}

	public List<CustomReportGroupField> getGroupfields() {
		return groupfields;
	}

	public void setGroupfields(List<CustomReportGroupField> groupfields) {
		this.groupfields = groupfields;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public boolean isExp() {
		return exp;
	}

	public void setExp(boolean exp) {
		this.exp = exp;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getReportDatasetId() {
		return reportDatasetId;
	}

	public void setReportDatasetId(String reportDatasetId) {
		this.reportDatasetId = reportDatasetId;
	}

}
