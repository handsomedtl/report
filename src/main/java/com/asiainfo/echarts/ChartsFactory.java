package com.asiainfo.echarts;

import com.asiainfo.echarts.meta.ChartsConfig;

public interface ChartsFactory {
	/**
	 * 获取charts 实例
	 * @param chartsid
	 * @return
	 */
	public ECharts createChartsFromConfig(String chartsid);

	/**
	 * 获取图表实例
	 * 
	 * @param chartsConfig
	 * @return
	 */
	public ECharts createCharts(ChartsConfig chartsConfig);
}
