package com.asiainfo.ereport.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
import com.asiainfo.ereport.dao.ReportComponentDao;
import com.asiainfo.ereport.dao.ReportMetaDao;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ereport.meta.ReportMeta;
import com.asiainfo.ewebframe.exception.ResourceNotFoundException;

@Repository("reportMetaDao")
public class ReportMetaDaoImpl extends MybatisBaseDao implements ReportMetaDao {
	@Resource(name = "reportComponentDao")
	private ReportComponentDao reportComponentDao;

	@Override
	public ReportMeta getReportMeta(String reportid) {
		ReportMeta reportMeta = this.getDataSingle(ReportMeta.class, reportid);
		if(reportMeta==null){
			throw new ResourceNotFoundException("您访问的报表不存在！请确认地址是否正确。");
		}
		List<ReportComponentMeta> components = reportComponentDao.getReportComponents(reportid);
		reportMeta.setComponentconfigs(components);
		return reportMeta;
	}

	@Override
	public void saveReportMeta(ReportMeta reportMeta) {
		this.reportComponentDao.del(ReportComponentMeta.class, reportMeta);
		if (this.exists(ReportMeta.class, reportMeta)) {
			this.update(ReportMeta.class, reportMeta);
		} else {
			this.insert(ReportMeta.class, reportMeta);
		}
		for (ReportComponentMeta componentMeta : reportMeta.getComponentconfigs()) {
			this.reportComponentDao.insert(ReportComponentMeta.class, componentMeta);
		}

	}

	@Override
	public List<ReportMeta> getReportMetas(String category,String staffId) {
		Map map=new HashMap();
		map.put("category", category);
		map.put("staffId", staffId);
		return this.getData(ReportMeta.class, map);

	}

	@Override
	public ReportMeta getReportMetaOnly(String reportid) {
		return this.getDataSingle(ReportMeta.class, reportid);
	}
	@Override
	public void delReportMeta(String id){
		this.del(ReportComponentMeta.class, id);
		this.del(ReportMeta.class, id);
	}
	@Override
	public int getCountReportMetas(String category){
		return this.getCount(ReportMeta.class, category);
	}
}
