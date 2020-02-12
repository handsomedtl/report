package com.asiainfo.ereport.dao.impl;

import org.springframework.stereotype.Repository;

import com.asiainfo.echarts.dao.EChartsMetaDao;
import com.asiainfo.echarts.meta.EChartsMeta;
import com.asiainfo.eframe.dao.impl.MybatisBaseDao;

@Repository("echartsMetaDao")
public class ChartsMetaDaoImpl extends MybatisBaseDao implements EChartsMetaDao{
	
	public EChartsMeta getEChartsMeta(String id) {
		EChartsMeta chartMeta = this.getDataSingle(EChartsMeta.class, id);
		return chartMeta;
		
	}
}
