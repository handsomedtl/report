package com.asiainfo.ereport.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
import com.asiainfo.ereport.dao.CustRptDescDao;
import com.asiainfo.ereport.meta.CustRptDesc;
import com.asiainfo.ereport.meta.ReportDesc;

@Repository("custRptDescDao")
public class CustRptDescImpl extends MybatisBaseDao implements CustRptDescDao {
	
	public List<CustRptDesc> getCustRptDesc(String staffid,String rptname,String condname,String fieldname){
		Map map=new HashMap();
		map.put("staffid", staffid);
		map.put("rptname", rptname);
		map.put("condname", condname);
		map.put("fieldname", fieldname);
		return this.getData(CustRptDesc.class, map);
	}
	public List getCustRptDesc(String rptname){
		Map map=new HashMap();
		map.put("rptname", rptname);
		return this.getData("SelReportDesc", map);
	}
	public List getCustRptCompDesc(String reportid){
		Map map=new HashMap();
		map.put("reportid", reportid);
		return this.getData("SelReportCompDesc", map);
	}
	public List<ReportDesc> getDisplayDataset(String datasetid){
		Map map=new HashMap();
		map.put("datasetid", datasetid);
		return this.getData("SelDisplayDataset", map);
	}
}
