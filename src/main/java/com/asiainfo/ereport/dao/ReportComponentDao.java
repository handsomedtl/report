package com.asiainfo.ereport.dao;

import java.util.List;

import com.asiainfo.eframe.dao.BaseDao;
import com.asiainfo.ereport.meta.ReportComponentMeta;

public interface ReportComponentDao extends BaseDao {
	/**
	 * 根据报表id获取所有报表组件
	 * @param reportid
	 * @return
	 */
	public List<ReportComponentMeta> getReportComponents(String reportid);
	
	public ReportComponentMeta getReportComponent(String reportid,String componentId);
     
	public int saveReportComponentMeta(ReportComponentMeta reportComponentMeta);
}
