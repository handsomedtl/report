package com.asiainfo.ereport.meta;

/**
 * @author bgr
 * 报表组成
 */
public class ReportComponentMeta {
	private String reportId;
	private String type;
	private String compId;
	private String datasetId;
	private Integer displayOrder;
	private String layoutVar;
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getDatasetId() {
		return datasetId;
	}
	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getLayoutVar() {
		return layoutVar;
	}
	public void setLayoutVar(String layoutVar) {
		this.layoutVar = layoutVar;
	}
	
}
