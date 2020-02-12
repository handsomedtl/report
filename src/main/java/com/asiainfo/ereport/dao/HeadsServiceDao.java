package com.asiainfo.ereport.dao;

import java.util.List;

import com.asiainfo.eframe.dao.BaseDao;
import com.asiainfo.ereport.meta.HeadsTransMeta;
import com.asiainfo.ereport.meta.ReportComponentMeta;

public interface HeadsServiceDao extends BaseDao {
	/**
	 * 根据报表id获取所有报表组件
	 * @param reportid
	 * @return
	 */
	public List<HeadsTransMeta> getHeadsTrans();
	public void updateHeadsTrans(String rptid,String comheads,String froheads);
}
