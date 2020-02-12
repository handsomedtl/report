package com.asiainfo.ereport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.ereport.dao.DepartDao;
import com.asiainfo.ereport.entity.DepartInfo;
import com.asiainfo.ereport.service.DepartLevelComparator;

@Service("departLevelComparator")
public class DepartLevelComparatorImpl implements DepartLevelComparator {
	@Autowired
	private DepartDao departDao;

	@Override
	public boolean isHigherLevelDept(String departid, Integer referencedepartlevel) {

		DepartInfo departInfo = departDao.getDepartInfo(departid);
		return referencedepartlevel.compareTo(departInfo.getDepartlevel()) >= 0 ? true : false;
	}

	@Override
	public boolean isLowerLevelDept(String departid, Integer referencedepartlevel) {
		DepartInfo departInfo = departDao.getDepartInfo(departid);
		return departInfo.getDepartlevel().compareTo(referencedepartlevel) > 0 ? true : false;

	}

	@Override
	public boolean isHigherLevelDept(String deptid, String referencedeptid) {
		DepartInfo referencedept = departDao.getDepartInfo(referencedeptid);
		DepartInfo depart = departDao.getDepartInfo(deptid);
		return referencedept.getDepartlevel().compareTo(depart.getDepartlevel()) >= 0 ? true : false;

	}

	@Override
	public boolean isLowerLevelDept(String deptid, String referencedeptid) {
		DepartInfo referencedept = departDao.getDepartInfo(referencedeptid);
		DepartInfo depart = departDao.getDepartInfo(deptid);
		return depart.getDepartlevel().compareTo(referencedept.getDepartlevel()) > 0 ? true : false;
	}

}
