package com.asiainfo.ereport.dao;

import java.util.List;

import com.asiainfo.eframe.dao.BaseDao;
import com.asiainfo.ereport.meta.ReportMeta;

public interface ReportMetaDao extends BaseDao {
	/**
	 * 获取报表配置信息
	 * 
	 * @param reportid
	 * @return
	 */
	public ReportMeta getReportMeta(String reportid);

	/**
	 * 
	 * @param category
	 * @return
	 */
	public List<ReportMeta> getReportMetas(String category,String staffId);

	/**
	 * 
	 * @param reportMeta
	 * @return
	 */
	public void saveReportMeta(ReportMeta reportMeta);

	/**
	 * 
	 * @param reportid
	 * @return
	 */
	public ReportMeta getReportMetaOnly(String reportid);
	
	public void delReportMeta(String reportid);
	public int getCountReportMetas(String category);
}
