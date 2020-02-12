package com.asiainfo.edata.meta;

public class FieldMeta extends MetaEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Integer dataType;
	/**
	 * 
	 */
	private String converterSqlexp;

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getConverterSqlexp() {
		return converterSqlexp;
	}

	public void setConverterSqlexp(String converterSqlexp) {
		this.converterSqlexp = converterSqlexp;
	}

}
