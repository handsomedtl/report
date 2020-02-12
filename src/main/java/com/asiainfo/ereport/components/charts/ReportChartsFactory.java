package com.asiainfo.ereport.components.charts;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.echarts.dao.EChartsMetaDao;
import com.asiainfo.echarts.meta.EChartsMeta;
import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.ereport.components.impl.AbstractComponentFactory;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ewebframe.ui.UIElementTypes;

@Service("reportChartsFactory")
public class ReportChartsFactory extends AbstractComponentFactory<ReportCharts> {
	@Resource(name = "echartsMetaDao")
	private EChartsMetaDao chartsMetaDao;

	@Override
	public String getfactoryTypeName() {

		return UIElementTypes.UI_ELEMENT_TYPE_CHARTS;
	}

	@Override
	public ReportCharts createComponent(ReportComponentMeta meta,
			DatasetFactory datafactory) {
		EChartsMeta chartsMeta = chartsMetaDao.getEChartsMeta(meta.getCompId());
		;
		return new ReportCharts(meta, chartsMeta, datafactory);
	}

}
