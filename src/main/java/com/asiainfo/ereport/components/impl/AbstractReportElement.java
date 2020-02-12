package com.asiainfo.ereport.components.impl;

import java.util.Map;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.edata.EDataset;
import com.asiainfo.eframe.dataset.EDataList;
import com.asiainfo.ereport.components.ReportUIElement;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ewebframe.ui.CommonUIElement;

public abstract class AbstractReportElement extends CommonUIElement implements ReportUIElement {

	protected ReportComponentMeta compMeta;
	protected DatasetFactory dataFactory;

	public AbstractReportElement(ReportComponentMeta componentMeta) {
		this.compMeta = componentMeta;
		this.dataFactory = null;
	}

	public AbstractReportElement(ReportComponentMeta componentMeta, DatasetFactory dataFactory) {
		this.compMeta = componentMeta;
		this.dataFactory = dataFactory;
	}

	protected EDataset dataset = null;

	@Override
	public String getComponentid() {
		return this.compMeta.getCompId();
	}

	@Override
	public String getComponentType() {
		return this.compMeta.getType();
	}

	@Override
	public Object handleData(EDataList data, Map param) {

		return data;
	}

	@Override
	public void setDataset(EDataset dataset) {
		this.dataset = dataset;

	}

	public ReportComponentMeta getComponentMeta() {
		return this.compMeta;
	}

	@Override
	public String getLayoutVar() {
		return this.compMeta.getLayoutVar() == null ? this.getComponentid() : this.compMeta.getLayoutVar();
	}

	@Override
	public Object dataForUpdate(Map params) {

		return this.handleData(this.getDataset().load(params), params);
	}

	@Override
	public Object pageDataForUpdate(Map params, Integer pageindex, Integer pagesize) {
		Integer offset = (pageindex - 1) * pagesize;
		return this.handlePageData(this.getDataset().loadPage(params, offset, pagesize), params, pageindex, pagesize);
	}

	protected Object handlePageData(EDataList data, Map param, Integer pageindex, Integer pagesize) {
		return handleData(data, param);
	}

	protected EDataset getDataset() {

		if (this.dataFactory != null && this.dataset == null) {
			this.dataset = dataFactory.createDatasetFromConfig(compMeta.getDatasetId());
		}

		return this.dataset;
	}

}
