package com.asiainfo.ereport.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.edata.EData;
import com.asiainfo.ereport.components.ComponentFactory;
import com.asiainfo.ereport.components.ReportSubViewUIElement;
import com.asiainfo.ereport.components.impl.AbstractReportElement;
import com.asiainfo.ereport.dao.ReportComponentDao;
import com.asiainfo.ereport.dao.ReportMetaDao;
import com.asiainfo.ereport.excel.ExcelExportHelper;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ereport.meta.ReportMeta;
import com.asiainfo.ereport.report.EReport;
import com.asiainfo.ereport.service.ReportService;
import com.asiainfo.ewebframe.ui.exception.InvalidConfigException;
import com.asiainfo.ewebframe.ui.form.DynamicFormEngine;

@Service("reportService")
public class ReportServiceImpl implements ReportService {

	@Autowired
	private List<ComponentFactory<? extends AbstractReportElement>> componentFactories;

	@Autowired
	private DatasetFactory datasetFactory;
	@Autowired
	private ExcelExportHelper excelExportHelper;
	@Autowired
	private DynamicFormEngine dynamicFormEngine;
	@Autowired
	private ReportMetaDao reportMetaDao;

	@Autowired
	private ReportComponentDao reportComponentDao;
	
	@Override
	public Object render(String reportid, Map params) {

		try {
			EReport<EData<String, Object>, Map> report = getReportInstance(reportid);
			return report.render(params);
		} catch (InvalidConfigException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ComponentFactory<? extends AbstractReportElement>> getComponentFactories() {
		return componentFactories;
	}

	
	public void setComponentFactories(List<ComponentFactory<? extends AbstractReportElement>> componentFactories) {
		this.componentFactories = componentFactories;
	}

	@Override
	public Object loadChartData(String reportid, String componentid, Map params) {
		EReport<EData<String, Object>, Map> report = getReportInstance(reportid);
		return null;
	}

	private EReport<EData<String, Object>, Map> getReportInstance(String reportid) {
		ReportMeta reportMeta = reportMetaDao.getReportMeta(reportid);
		EReport<EData<String, Object>, Map> report = new EReport<EData<String, Object>, Map>(reportMeta,
				componentFactories);
		report.setDatasetFactory(datasetFactory);
		report.setExportHelper(excelExportHelper);
		report.setDynamicFormEngine(dynamicFormEngine);
		return report;
	}

	@Override
	public Object dataForUpdate(String reportid, String componentid, Map params) {
		AbstractReportElement comp = this.getReportComponent(reportid, componentid);
		return comp.dataForUpdate(params);
	}

	@Override
	public Object loadInnerReportData(String reportid, String componentid, String parentid, Map params) {
		AbstractReportElement comp = this.getReportComponent(reportid, componentid);
		if(comp instanceof ReportSubViewUIElement){
			return ((ReportSubViewUIElement)comp).dataforInnerReport( parentid, params);
		}
		return new ArrayList();
	}

	@Override
	public Object pageDataForUpdate(String reportid, String componentid, Integer pagesize, Integer pageindex,
			Map params) {
		AbstractReportElement comp = this.getReportComponent(reportid, componentid);
		return comp.pageDataForUpdate( params, pagesize, pageindex);
	}

	@Override
	public byte[] export(String reportid, String componentid, Map params) {
		EReport<EData<String, Object>, Map> report = getReportInstance(reportid);
		return report.exportExcel(componentid, params);
	}
	@Override
	public  EReport<EData<String, Object>, Map> getReportInfo(String reportid) {
		EReport<EData<String, Object>, Map> report = getReportInstance(reportid);
		return report;
	}

	@Override
	public AbstractReportElement getReportComponent(String reportId, String componentId) {
		ReportComponentMeta comp = reportComponentDao.getReportComponent(reportId, componentId);
		for(ComponentFactory<? extends AbstractReportElement> f:this.componentFactories){
			if(f.getfactoryTypeName().equalsIgnoreCase(comp.getType())){
				return f.getComponent(comp,datasetFactory);
			}
		}
		return null;
	}

	
}
