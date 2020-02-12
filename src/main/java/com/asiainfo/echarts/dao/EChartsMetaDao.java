package com.asiainfo.echarts.dao;
import com.asiainfo.eframe.dao.BaseDao;
import com.asiainfo.echarts.meta.EChartsMeta;
public interface EChartsMetaDao extends BaseDao{
	public EChartsMeta getEChartsMeta(String id);
}

