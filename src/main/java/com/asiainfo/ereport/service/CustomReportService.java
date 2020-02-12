package com.asiainfo.ereport.service;

import java.util.List;

import com.asiainfo.edata.meta.EDatasetCategory;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.eframe.service.exception.ServiceException;
import com.asiainfo.ereport.meta.CustRptDesc;
import com.asiainfo.ereport.meta.CustomReportBindField;
import com.asiainfo.ereport.meta.CustomReportMeta;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ereport.meta.ReportDesc;
import com.asiainfo.ereport.meta.ReportMeta;
import com.asiainfo.ereport.meta.RptCategory;
import com.asiainfo.ewebframe.ui.form.def.FormElement;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridMeta;

/**
 * 自定义报表定制服务
 * 
 * @author baowzh
 *
 */
public interface CustomReportService {
	/**
	 * 获取所有数据集分组
	 * 
	 * @return
	 */
	public List<EDatasetCategory> getDatasetCategoryHaveDataset(String category, String name);
    /**
     * 
     * @param staffId
     * @return
     */
	public List<EDatasetCategory> getDatasetCategoryHaveDataset(String staffId);

	/**
	 * 获取数据集分组信息
	 * 
	 * @param groupid
	 * @return
	 */
	public EDatasetCategory getDatasetCategory(String categoryid);

	/**
	 * 保存报表配置
	 * 
	 * @param reportMeta
	 */
	public String saveReportConfig(CustomReportMeta reportMeta) throws ServiceException;

	/**
	 * 删除自定义报表，如果已经授权给用户则不能删除
	 * 
	 * @param reportid
	 */

	public void deleteReport(String reportid, String formId, String datasetId, String gridId) throws ServiceException;

	/**
	 * 
	 * @param categoryName
	 */
	public void saveNewRptCategory(String categoryName, String staffId);

	/**
	 * 获取指定条件下的所有数据集
	 * 
	 * @param groupid
	 * @return
	 */
	public List<EDatasetMeta> getDatasets(String categoryid, String category, String name);
    /**
     * 
     * @param id
     * @param staffId
     * @return
     */
	public List<EDatasetMeta> getDatasets(String id, String staffId);

	/**
	 * 获取数据集
	 * 
	 * @param datasetid
	 * @return
	 */
	public EDatasetMeta getDataset(String datasetid);

	/**
	 * 从模板表单获取组件元素定义
	 * 
	 * @param elementid
	 * @return
	 */
	public FormElement getFormElement(String elementcode);

	/**
	 * 验证用户填写表达式的正确性
	 * 
	 * @param expression
	 * @throws Exception
	 */
	public String checkExpression(List<CustomReportBindField> fields, String expression,Integer parseType) throws Exception;

	/**
	 * 
	 * @param expression
	 * @throws Exception
	 */
	public List<RptCategory> getReportCategory(String staffId) throws Exception;
	/**
	 * 
	 * @param category
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public List<ReportMeta> getReportMetas(String category, String staffId) throws Exception;
    /**
     * 
     * @param reportId
     * @return
     */
	public List<ReportComponentMeta> getReportComponents(String reportId);
    /**
     * 
     * @param id
     * @throws ServiceException
     */
	public void deleteRptCategory(String id) throws ServiceException;
	
	public List<CustRptDesc> selCustRptDesc(String staffid,String rptname,String condname,String fieldname) throws ServiceException;
    
	public List selCustRptDesc(String rptname) throws ServiceException;
	
	public List selCustRptCompDesc(String reportid) throws ServiceException;

	public List<ReportDesc> selDisplayDataset(String datasetid) throws ServiceException;
	public UIGridMeta selGridMeta(String gridid) throws ServiceException;
}
