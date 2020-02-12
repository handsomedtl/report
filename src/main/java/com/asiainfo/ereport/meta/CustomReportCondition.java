package com.asiainfo.ereport.meta;

/**
 * 自定义表单数据域定义
 * 
 * @author baowzh
 *
 */
public class CustomReportCondition {

	/**
	 * 对应数据集id
	 */
	private String datasetId;
	/**
	 * 字段名称
	 */
	private String fieldName;
	/**
	 * 渲染元素编码
	 */
	private String renderElementCode;
	/**
	 * 是否必填
	 */
	private boolean required;
	/**
	 * 参数名称
	 */
	private String paramName;
	/**
	 * 标签文本
	 */
	private String lable;
	private Integer order;
	private String commType;
	private String oper;

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getRenderElementCode() {
		return renderElementCode;
	}

	public void setRenderElementCode(String renderElementCode) {
		this.renderElementCode = renderElementCode;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getCommType() {
		return commType;
	}

	public void setCommType(String commType) {
		this.commType = commType;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}
	
}
