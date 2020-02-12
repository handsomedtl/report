package com.asiainfo.ereport.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
import com.asiainfo.ereport.dao.RptCategoryDao;
import com.asiainfo.ereport.meta.RptCategory;
@Repository("rptCategoryDao")
public class RptCategoryDaoImpl extends MybatisBaseDao implements RptCategoryDao {

	@Override
	public List<RptCategory> getReportCategory(String staffId) {
		return this.getData(RptCategory.class, staffId);
	}
    @Override
    public void saveNewRptCategory(RptCategory t){
    	this.insert(RptCategory.class,  t);
    }
}
