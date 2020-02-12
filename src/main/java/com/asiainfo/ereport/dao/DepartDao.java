package com.asiainfo.ereport.dao;

import com.asiainfo.eframe.dao.BaseDao;
import com.asiainfo.ereport.entity.DepartInfo;

public interface DepartDao extends BaseDao {
	/**
	 * 
	 * @param departid
	 * @param referencedepartlevel
	 * @return
	 */
	public DepartInfo getDepartInfo(String departid);

}
