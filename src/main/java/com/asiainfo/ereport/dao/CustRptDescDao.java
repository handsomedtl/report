package com.asiainfo.ereport.dao;

import java.util.List;

import com.asiainfo.eframe.dao.BaseDao;
import com.asiainfo.ereport.meta.CustRptDesc;
import com.asiainfo.ereport.meta.ReportDesc;

public interface CustRptDescDao extends BaseDao {
	public List<CustRptDesc> getCustRptDesc(String staffid,String rptname,String condname,String fieldname);
	public List getCustRptDesc(String rptname);
	public List getCustRptCompDesc(String reportid);
	public List<ReportDesc> getDisplayDataset(String datasetid);
}
