package com.asiainfo.edata.meta;
/**
 * 数据集查询条件定义
 * @author baowzh
 *
 */
public class ConditionMeta extends MetaEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 是否必填
	 */
	private Boolean required=false;
	/**
	 * 验证逻辑
	 */
	private String validator;
	/**
	 * 定义存储过程调用时候用
	 */
	private Integer datatype;
	/**
	 * 定义存储过程调用时候用
	 */
	private Integer inout;
	private Integer conorder;
	public String getValidator() {
		return validator;
	}
	public void setValidator(String validator) {
		this.validator = validator;
	}
	/**
	 * 参数数据来源
	 */
	private Integer srcType;
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public Integer getSrcType() {
		return srcType;
	}
	public void setSrcType(Integer srcType) {
		this.srcType = srcType;
	}
	public Integer getDatatype() {
		return datatype;
	}
	public void setDatatype(Integer datatype) {
		this.datatype = datatype;
	}
	public Integer getInout() {
		return inout;
	}
	public void setInout(Integer inout) {
		this.inout = inout;
	}
	public Integer getConorder() {
		return conorder;
	}
	public void setConorder(Integer conorder) {
		this.conorder = conorder;
	}
	

}
