package com.asiainfo.ereport.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
import com.asiainfo.ereport.dao.ReportComponentDao;
import com.asiainfo.ereport.meta.ReportComponentMeta;
@Repository("reportComponentDao")
public class ReportComponentDaoImpl extends MybatisBaseDao implements ReportComponentDao {

	@Override
	public List<ReportComponentMeta> getReportComponents(String reportid) {
		return this.getData(ReportComponentMeta.class, reportid);
	}

	@Override
	public ReportComponentMeta getReportComponent(String reportid, String componentId) {
		Map param = new HashMap();
		param.put("reportId", reportid);
		param.put("compId", componentId);
		return this.getDataSingle(ReportComponentMeta.class, param);
	}
	@Override
	public int saveReportComponentMeta(ReportComponentMeta reportComponentMeta){
		return this.insert(ReportComponentMeta.class, reportComponentMeta);
	}

}
