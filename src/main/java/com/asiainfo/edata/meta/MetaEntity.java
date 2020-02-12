package com.asiainfo.edata.meta;

import java.io.Serializable;

public class MetaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String datasetId;
	protected String id;
	protected String name;
	protected String bizType;
	
	public String getDatasetId() {
		return datasetId;
	}
	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	

	
}
