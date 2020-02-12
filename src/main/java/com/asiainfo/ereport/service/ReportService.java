package com.asiainfo.ereport.service;

import java.util.Map;

import com.asiainfo.edata.EData;
import com.asiainfo.ereport.components.impl.AbstractReportElement;
import com.asiainfo.ereport.report.EReport;

/**
 * 报表服务
 * 
 * @author baowzh
 *
 */
public interface ReportService {
	/**
	 * 渲染表格界面
	 * 
	 * @param reportid
	 * @param params
	 * @return
	 */
	public Object render(String reportid, Map params);

	/**
	 * 查询报表
	 * 
	 * @param componentid
	 * @param params
	 * @return
	 */
	public Object dataForUpdate(String reportid, String componentid, Map params);
    /**
     * 
     * @param reportid
     * @param componentid
     * @param pagesize
     * @param pageindex
     * @param params
     * @return
     */
	public Object pageDataForUpdate(String reportid, String componentid, Integer pagesize, Integer pageindex,
			Map params);

	/**
	 * 加载下级节点或者子报表数据
	 * 
	 * @param componentid
	 * @param params
	 * @return
	 */
	public Object loadInnerReportData(String reportid, String componentid,String parentid, Map params);

	/**
	 * 加载图表数据
	 * 
	 * @param componentid
	 * @param params
	 * @return
	 */
	public Object loadChartData(String reportid, String componentid, Map params);
	/**
	 * 
	 * @param reportid
	 * @param componentid
	 * @param params
	 * @return
	 */
	public byte[] export(String reportid, String componentid, Map params);
	/**
	 * 获取报表配置
	 * @param reportid
	 * @return
	 */
	public EReport<EData<String, Object>, Map> getReportInfo(String reportid);
	
	/**
	 * 获取报表组件
	 * @param reportId
	 * 报表ID
	 * @param componentId
	 * 获取的组件ID
	 * 
	 * @return
	 * 成功创建则返回组件实例否则 null
	 * 
	 */
	public AbstractReportElement getReportComponent(String reportId,String componentId);
}
