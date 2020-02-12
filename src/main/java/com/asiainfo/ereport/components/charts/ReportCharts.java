package com.asiainfo.ereport.components.charts;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.echarts.ECharts;
import com.asiainfo.echarts.meta.EChartsMeta;
import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.eframe.dataset.EDataList;
import com.asiainfo.ereport.components.impl.AbstractReportElement;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ewebframe.ui.UIElementTypes;
import com.asiainfo.ewebframe.ui.exception.InvalidConfigException;

public class ReportCharts extends AbstractReportElement  {
	private String datasetid;
	private EChartsMeta chartsMeta;
	/**
	 * 内置charts 对象
	 */
	private ECharts chart;
	public ReportCharts(ReportComponentMeta componentMeta,EChartsMeta chartsMeta, ECharts chart) {
		super(componentMeta);
		//this.chartsConfig = chartsConfig;
		this.datasetid = componentMeta.getDatasetId();
		this.chartsMeta=chartsMeta;
		this.chart = new ECharts(this.chartsMeta);
	}

	public ReportCharts(ReportComponentMeta componentMeta,EChartsMeta chartsMeta, DatasetFactory datasetfactory) {
		super(componentMeta, datasetfactory);
		this.datasetid = componentMeta.getDatasetId();
		this.chartsMeta=chartsMeta;		
		this.chart = new ECharts(this.chartsMeta);
	}

	

	public String getDatasetid() {
		return datasetid;
	}

	@Override
	public Object render(Map params) throws InvalidConfigException {
		if (params == null) {
			params = new HashMap();
		}
		params.put("reportid", this.getComponentMeta().getReportId());
		params.put("componentid", this.getComponentid());
		return this.chart.render(params);
		//return null;
	}


	@Override
	public Object renderview(Map params) throws InvalidConfigException {
		if (params == null) {
			params = new HashMap();
		}
		params.put("reportid", this.getComponentMeta().getReportId());
		return this.chart.render(params);
	}

	@Override
	public String getComponentid() {
		
		return this.compMeta.getCompId();
	}

	@Override
	public String getComponentType() {

		return UIElementTypes.UI_ELEMENT_TYPE_CHARTS;
	}

	@Override
	public String getTypeString() {
		return UIElementTypes.UI_ELEMENT_TYPE_CHARTS;
	}

	@Override
	public Object handleData(EDataList data, Map param) {
		return this.chart.handleData(data);
	}

	
}
