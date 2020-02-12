package com.asiainfo.ereport.components;

import java.util.Map;

import com.asiainfo.edata.EDataset;
import com.asiainfo.eframe.dataset.EDataList;
import com.asiainfo.ewebframe.ui.UIElement;

public interface ReportUIElement extends UIElement {
	/**
	 * 获取组件id
	 * @return
	 */
	public String getComponentid();
	/**
	 * 获取组件类型
	 * @return
	 */
	public String getComponentType();
	
	
	/**
	 * 处理给定数据转换成自己刷新需要的格式
	 * 
	 * @param data
	 * @param param 
	 * 外部参数，查询参数等
	 * @return
	 */
	public Object handleData(EDataList data, Map param);
	
//	
//	/**
//	 * 根据参数自己从数据源获取数据并处理成自己需要的格式
//	 * 
//	 * 
//	 * @param param
//	 * 数据获取参数
//	 * @return
//	 * 如果没有设置数据源则返回默认
//	 * 
//	 */
//	public Object handleData(Map param);
	
	/**
	 * 设置数据源
	 * @param dataset
	 */
	public void setDataset(EDataset dataset);
	
	
	public String getLayoutVar();
	
	
	public Object dataForUpdate(Map params);
	
	public Object pageDataForUpdate( Map params, Integer pageindex, Integer pagesize); 
	
}
