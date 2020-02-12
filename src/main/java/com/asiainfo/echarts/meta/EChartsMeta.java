package com.asiainfo.echarts.meta;

import java.util.Date;

public class EChartsMeta {
	private String id;
	private String title;
	private String chartsType;
	private String yAxisName;
	private String yAxisUnit;
	private String yAxisMax;
	private String yAxisMin;
	private String dataSrc;
	private String categoryFields;
	private String dataFields;
	private boolean showToolbox;
	private String updateStaff;
	private String updateDepart;
	private Date updateDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChartsType() {
		return chartsType;
	}
	public void setChartsType(String chartsType) {
		this.chartsType = chartsType;
	}
	public String getyAxisName() {
		return yAxisName;
	}
	public void setyAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}
	public String getyAxisUnit() {
		return yAxisUnit;
	}
	public void setyAxisUnit(String yAxisUnit) {
		this.yAxisUnit = yAxisUnit;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public String getCategoryFields() {
		return categoryFields;
	}
	public void setCategoryFields(String categoryFields) {
		this.categoryFields = categoryFields;
	}
	public String getDataFields() {
		return dataFields;
	}
	public void setDataFields(String dataFields) {
		this.dataFields = dataFields;
	}
	public boolean getShowToolbox() {
		return showToolbox;
	}
	public void setShowToolbox(boolean showToolbox) {
		this.showToolbox = showToolbox;
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
	public String getyAxisMax() {
		return yAxisMax;
	}
	public void setyAxisMax(String yAxisMax) {
		this.yAxisMax = yAxisMax;
	}
	public String getyAxisMin() {
		return yAxisMin;
	}
	public void setyAxisMin(String yAxisMin) {
		this.yAxisMin = yAxisMin;
	}

}
