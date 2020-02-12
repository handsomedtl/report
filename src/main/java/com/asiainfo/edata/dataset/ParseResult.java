package com.asiainfo.edata.dataset;

import java.util.ArrayList;
import java.util.List;

import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.edata.meta.FieldMeta;

public class ParseResult {
	/**
	 * 根据sql语句语法自动生成查询条件
	 */
	private List<ConditionMeta> conditions;
	/**
	 * 根据sql语句自动生成的查询列信息
	 */
	private List<FieldMeta> fields;
	/**
	 * 自动解析以后的sql语句
	 */
	private String finalsql;
	/**
	 * 数据集id
	 */
	private String datasetid;
	
	private List<String> multiselectFields=new ArrayList<String>();

	public List<ConditionMeta> getConditions() {
		return conditions;
	}

	public void setConditions(List<ConditionMeta> conditions) {
		this.conditions = conditions;
	}

	public List<FieldMeta> getFields() {
		return fields;
	}

	public void setFields(List<FieldMeta> fields) {
		this.fields = fields;
	}

	public String getFinalsql() {
		return finalsql;
	}

	public void setFinalsql(String finalsql) {
		this.finalsql = finalsql;
	}

	public String getDatasetid() {
		return datasetid;
	}

	public void setDatasetid(String datasetid) {
		this.datasetid = datasetid;
	}

	public List<String> getMultiselectFields() {
		return multiselectFields;
	}

	public void setMultiselectFields(List<String> multiselectFields) {
		this.multiselectFields = multiselectFields;
	}
	
}
