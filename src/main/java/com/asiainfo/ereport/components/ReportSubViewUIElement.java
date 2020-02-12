package com.asiainfo.ereport.components;

import java.util.Map;

import com.asiainfo.eframe.dataset.EDataList;

public interface ReportSubViewUIElement extends ReportUIElement {

	public String getSubDataSrc();
	public Object handleSubData(EDataList data, Map params);
	public Object dataforInnerReport(String parentId, Map params);
}
