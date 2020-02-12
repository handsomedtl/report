package com.asiainfo.ereport.dao.impl;

import org.springframework.stereotype.Repository;

import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
import com.asiainfo.ereport.dao.DepartDao;
import com.asiainfo.ereport.entity.DepartInfo;

@Repository("departDao")
public class DepartDaoImpl extends MybatisBaseDao implements DepartDao {

	@Override
	public DepartInfo getDepartInfo(String departid) {
		return this.getDataSingle(DepartInfo.class, departid);
	}

}
