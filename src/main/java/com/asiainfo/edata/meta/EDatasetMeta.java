package com.asiainfo.edata.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据源描述 meta
 * 
 * 
 * @author tsing
 *
 */
public class EDatasetMeta implements Serializable {
	/**
	 * 系统数据集
	 */
	public static final String DATASET_TYPE_SYSTEM = "system";
	/**
	 * 用户自定义数据集
	 */
	public static final String DATASET_TYPE_CUSTOM = "custom";
	/**
	 * 
	 */
	public static final String DATASET_TYPE_REF = "REF";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String category;
	private String name;
	private Integer type;
	private String remark;
	private String content;
	private String config;
	private String rightCode;
	private Boolean serviceable;
	private Integer dataRoute;
	private String resourceType;
	private String updateStaff;
	private String updateDepart;
	private Date updateDate;
	private String summaryConfig;

	/**
	 * 结果集列描述
	 */
	private Map<String, FieldMeta> fieldMetas = new HashMap<String, FieldMeta>();
	/**
	 * 
	 */
	private List<FieldMeta> fieldMetaList = new ArrayList<FieldMeta>();
	/**
	 * 
	 */
	private List<ConditionMeta> conditionMetaList = new ArrayList<ConditionMeta>();;

	/**
	 * 条件字段描述
	 */
	private Map<String, ConditionMeta> conditionMetas = new HashMap<String, ConditionMeta>();

	/**
	 * 是否可以统计
	 */
	private boolean countable = false;
	/**
	 * 是否可以合计
	 */
	private boolean sumable = false;

	/**
	 * 是否可分页
	 */
	private boolean pageable = false;

	public void clear() {
		this.fieldMetas.clear();
		this.conditionMetas.clear();
	}

	public void setFieldMetas(Map<String, FieldMeta> fieldMetas) {
		this.fieldMetas = fieldMetas;
		this.fieldMetaList.clear();
		Set<String> keyset = this.fieldMetas.keySet();
		Iterator<String> iterator = keyset.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			this.fieldMetaList.add(this.fieldMetas.get(key));
		}
	}

	public void addFieldMeta(FieldMeta meta) {
		this.fieldMetas.put(meta.name, meta);
		this.fieldMetaList.add(meta);
	}

	public void addConditionMeta(ConditionMeta meta) {
		meta.setId(meta.getId());
		this.conditionMetas.put(meta.getId(), meta);
		this.conditionMetaList.add(meta);
	}

	public void setConditionMetas(Map<String, ConditionMeta> conditionMetas) {
		this.conditionMetas = conditionMetas;
		this.conditionMetaList.clear();
		Set<String> keyset = this.conditionMetas.keySet();
		Iterator<String> iterator = keyset.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			this.conditionMetaList.add(this.conditionMetas.get(key));
		}
	}

	public List<FieldMeta> getFieldMetaList() {
		return this.fieldMetaList;
	}

	public List<ConditionMeta> getConditionMetaList() {
		return conditionMetaList;
	}

	public boolean isCountable() {
		return countable;
	}

	public void setCountable(boolean countable) {
		this.countable = countable;
	}

	public boolean isSumable() {
		return sumable;
	}

	public void setSumable(boolean sumable) {
		this.sumable = sumable;
	}

	public boolean isPageable() {
		return pageable;
	}

	public void setPageable(boolean pageable) {
		this.pageable = pageable;
	}

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public Boolean getServiceable() {
		return serviceable;
	}

	public void setServiceable(Boolean serviceable) {
		this.serviceable = serviceable;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public Integer getDataRoute() {
		return dataRoute;
	}

	public void setDataRoute(Integer dataRoute) {
		this.dataRoute = dataRoute;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
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

	public Map<String, FieldMeta> getFieldMetas() {
		return fieldMetas;
	}

	public Map<String, ConditionMeta> getConditionMetas() {
		return conditionMetas;
	}

	public void setFieldMetaList(List<FieldMeta> fieldMetaList) {
		this.fieldMetaList = fieldMetaList;
	}

	public void setConditionMetaList(List<ConditionMeta> conditionMetaList) {
		this.conditionMetaList = conditionMetaList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSummaryConfig() {
		return summaryConfig;
	}

	public void setSummaryConfig(String summaryConfig) {
		this.summaryConfig = summaryConfig;
	}

}
