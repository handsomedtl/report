package com.asiainfo.ereport.controller;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.edata.EData;
import com.asiainfo.edata.meta.EDatasetCategory;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.edata.meta.FieldMeta;
import com.asiainfo.ereport.meta.CustRptDesc;
import com.asiainfo.ereport.meta.CustomReportBindField;
import com.asiainfo.ereport.meta.CustomReportCondition;
import com.asiainfo.ereport.meta.CustomReportGroupField;
import com.asiainfo.ereport.meta.CustomReportMeta;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ereport.meta.ReportMeta;
import com.asiainfo.ereport.meta.RptCategory;
import com.asiainfo.ereport.report.EReport;
import com.asiainfo.ereport.service.CustomReportService;
import com.asiainfo.ereport.service.ReportService;
import com.asiainfo.ewebframe.ui.form.def.FormElement;
import com.asiainfo.ewebframe.uitl.ContextHolderUtils;

import jodd.bean.BeanUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("custreport")
public class CustomReportDefContraller {

	@Resource(name = "customReportService")
	private CustomReportService customreportService;
	@Resource(name = "datasetFactory")
	private DatasetFactory datasetFactory;
	@Resource(name = "reportService")
	private ReportService reportService;
	private static Logger logger = LoggerFactory.getLogger(CustomReportDefContraller.class);

	@Value("${system.templatetextid}")
	private String templateTextId;

	/**
	 * 进入报表定义主界面
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelMap map) {
		map.put("templateTextId", this.templateTextId);
		return new ModelAndView("ereportdef/index", map);
	}

	@RequestMapping("custreportdesc")
	public ModelAndView custreportdesc() {
		return new ModelAndView("ereportdef/custreportdesc");
	}

	@RequestMapping("rptcategory")
	public ModelAndView rptcategory() {
		return new ModelAndView("ereportdef/rptcategory");
	}

	@RequestMapping("fillrptcategory")
	public ModelAndView fillRptCategory() {
		return new ModelAndView("ereportdef/fillrptcategory");
	}

	@RequestMapping("datasetsel")
	public ModelAndView datasetsel() {
		return new ModelAndView("ereportdef/datasetsel");
	}

	@RequestMapping("expression")
	public ModelAndView expression() {
		return new ModelAndView("ereportdef/expression");
	}

	/**
	 * 获取报表列表
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list() {
		return new ModelAndView("ereportdef/list");
	}

	/**
	 * 获取报表定义所修数据集--树形结构返回
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("datasettree")
	public List<HashMap<String, Object>> datasetgroup(String id, String category, String name) throws Exception {
		List<HashMap<String, Object>> treenodes = new ArrayList<HashMap<String, Object>>();
		if (id == null) {
			List<EDatasetCategory> datasetgroups = customreportService.getDatasetCategoryHaveDataset(category, name);
			HashMap<String, Object> treeInfo = new HashMap<String, Object>();
			treeInfo.put("id", "-1");
			treeInfo.put("text", "所有数据集");
			treeInfo.put("parentId", "");
			Map<String, Object> rootattr = new HashMap<String, Object>();
			rootattr.put("isleaf", false);
			rootattr.put("dataset", false);
			treeInfo.put("attributes", rootattr);
			treeInfo.put("remark", "数据集根节点");
			treenodes.add(treeInfo);
			for (EDatasetCategory datasetCategory : datasetgroups) {
				HashMap<String, Object> treeNode = new HashMap<String, Object>();
				treeNode.put("id", datasetCategory.getId());
				treeNode.put("text", datasetCategory.getName());
				if (datasetCategory.getParentid() == null || datasetCategory.getParentid().equalsIgnoreCase("-1")) {
					treeNode.put("parentId", "-1");
				} else {
					treeNode.put("parentId", datasetCategory.getParentid());
				}
				treeNode.put("state", "closed");
				Map<String, Object> attrmap = beanToMap(datasetCategory);
				attrmap.put("isleaf", false);
				attrmap.put("dataset", false);
				treeNode.put("attributes", attrmap);
				treeNode.put("remark", "分类节点");
				treenodes.add(treeNode);
				if (name != null) {

					List<EDatasetMeta> datasets = customreportService.getDatasets(id, category, name);
					for (EDatasetMeta datasetMeta : datasets) {
						HashMap<String, Object> treeNodeds = new HashMap<String, Object>();
						treeNodeds.put("id", "dataset_" + datasetMeta.getId());
						treeNodeds.put("text", datasetMeta.getName());
						treeNodeds.put("remark", datasetMeta.getRemark());
						treeNodeds.put("parentId", datasetCategory.getId());
						treeNodeds.put("state", "closed");
						Map<String, Object> attrmapds = beanToMap(datasetMeta);
						attrmapds.put("isleaf", false);
						attrmapds.put("dataset", true);
						treeNodeds.put("attributes", attrmapds);
						treenodes.add(treeNodeds);
						if (datasetMeta.getFieldMetaList() == null || datasetMeta.getFieldMetaList().isEmpty()) {
							continue;
						}
						for (FieldMeta fieldMeta : datasetMeta.getFieldMetaList()) {
							HashMap<String, Object> treeNodei = new HashMap<String, Object>();
							treeNodei.put("id", "f_" + fieldMeta.getId());
							treeNodei.put("text", fieldMeta.getName());
							treeNodei.put("remark", fieldMeta.getName());
							treeNodei.put("parentId", "dataset_" + datasetMeta.getId());
							treeNodei.put("state", "open");
							Map<String, Object> attrmapi = beanToMap(fieldMeta);
							attrmapi.put("isleaf", true);
							attrmapi.put("dataset", false);
							treeNodei.put("attributes", attrmapi);
							if (fieldMeta.getDataType() != null && (java.sql.Types.INTEGER == fieldMeta.getDataType()
									|| java.sql.Types.DECIMAL == fieldMeta.getDataType()
									|| java.sql.Types.DOUBLE == fieldMeta.getDataType()
									|| java.sql.Types.FLOAT == fieldMeta.getDataType()
									|| java.sql.Types.NUMERIC == fieldMeta.getDataType())) {

								treeNodei.put("iconCls", "icon-sum");
							} else {
								treeNodei.put("iconCls", "icon-file");

							}
							treenodes.add(treeNodei);
						}
					}
				}
			}

		} else {
			List<EDatasetMeta> datasets = customreportService.getDatasets(id, category, name);
			for (EDatasetMeta datasetMeta : datasets) {
				HashMap<String, Object> treeNode = new HashMap<String, Object>();
				treeNode.put("id", "dataset_" + datasetMeta.getId());
				treeNode.put("text", datasetMeta.getName());
				treeNode.put("remark", datasetMeta.getRemark());
				treeNode.put("parentId", id);
				treeNode.put("state", "closed");
				Map<String, Object> attrmap = beanToMap(datasetMeta);
				attrmap.put("isleaf", false);
				attrmap.put("dataset", true);
				treeNode.put("attributes", attrmap);
				treenodes.add(treeNode);
				if (datasetMeta.getFieldMetaList() == null || datasetMeta.getFieldMetaList().isEmpty()) {
					continue;
				}
				for (FieldMeta fieldMeta : datasetMeta.getFieldMetaList()) {

					HashMap<String, Object> treeNodei = new HashMap<String, Object>();
					treeNodei.put("id", "f_" + fieldMeta.getId());
					treeNodei.put("text", fieldMeta.getName());
					treeNodei.put("remark", fieldMeta.getName());
					treeNodei.put("parentId", "dataset_" + datasetMeta.getId());
					treeNodei.put("state", "open");
					Map<String, Object> attrmapi = beanToMap(fieldMeta);
					attrmapi.put("isleaf", true);
					attrmapi.put("dataset", false);
					treeNodei.put("attributes", attrmapi);
					if (fieldMeta.getDataType() != null && (java.sql.Types.INTEGER == fieldMeta.getDataType()
							|| java.sql.Types.DECIMAL == fieldMeta.getDataType()
							|| java.sql.Types.DOUBLE == fieldMeta.getDataType()
							|| java.sql.Types.FLOAT == fieldMeta.getDataType()
							|| java.sql.Types.NUMERIC == fieldMeta.getDataType())) {

						treeNodei.put("iconCls", "icon-sum");
					} else {
						treeNodei.put("iconCls", "icon-file");

					}
					treenodes.add(treeNodei);
				}

			}
		}
		setChildren(treenodes);
		return treenodes;
	}

	@ResponseBody
	@RequestMapping("myreporttree")
	public List<HashMap<String, Object>> getMyReport(String id, String category, String name) throws Exception {
		List<HashMap<String, Object>> treenodes = new ArrayList<HashMap<String, Object>>();
		String staffId = ContextHolderUtils.getSessionUserInfo().getStaffid();
		if (id == null) {
			List<RptCategory> reportCategory = customreportService.getReportCategory(staffId);
			for (RptCategory reportCategoryi : reportCategory) {
				HashMap<String, Object> treeInfo = new HashMap<String, Object>();
				if (reportCategoryi.getParentId().equals("root")) {
					treeInfo.put("id", "root_" + reportCategoryi.getId());
					treeInfo.put("text", reportCategoryi.getCategoryName());
					treeInfo.put("parentId", "");
					HashMap<String, Object> treeInfoi = new HashMap<String, Object>();
					treeInfoi.put("id", reportCategoryi.getId());
					treeInfoi.put("isLeaf", false);
					treeInfoi.put("isReport", false);
					treeInfoi.put("categoryNode", false);
					treeInfo.put("attributes", treeInfoi);
				} else {
					treeInfo.put("id", "category_" + reportCategoryi.getId());
					treeInfo.put("text", reportCategoryi.getCategoryName());
					treeInfo.put("parentId", "root_" + reportCategoryi.getParentId());
					treeInfo.put("state", "closed");
					HashMap<String, Object> treeInfoi = new HashMap<String, Object>();
					treeInfoi.put("id", reportCategoryi.getId());
					treeInfoi.put("isLeaf", false);
					treeInfoi.put("isReport", false);
					treeInfoi.put("categoryNode", true);
					treeInfo.put("attributes", treeInfoi);
					List<ReportMeta> rptmetas = customreportService.getReportMetas(reportCategoryi.getId(), staffId);
					for (ReportMeta rptmetasi : rptmetas) {
						HashMap<String, Object> treeInfoii = new HashMap<String, Object>();
						treeInfoii.put("id", "report_" + rptmetasi.getId());
						treeInfoii.put("text", rptmetasi.getName());
						treeInfoii.put("parentId", "category_" + reportCategoryi.getId());
						HashMap<String, Object> treeInfoiii = new HashMap<String, Object>();
						treeInfoiii.put("id", rptmetasi.getId());
						treeInfoiii.put("isLeaf", true);
						treeInfoiii.put("isReport", true);
						treeInfoiii.put("categoryNode", false);
						treeInfoii.put("attributes", treeInfoiii);
						treenodes.add(treeInfoii);
					}
				}
				treenodes.add(treeInfo);
			}
		}
		setChildren(treenodes);
		return treenodes;
	}

	@ResponseBody
	@RequestMapping("savenewrptcategory")
	public String saveNewtprcategory(String categoryName) throws Exception {
		String staffId = ContextHolderUtils.getSessionUserInfo().getStaffid();
		this.customreportService.saveNewRptCategory(categoryName, staffId);
		return "保存成功";
	}

	/**
	 * 获取自定义报表数据集
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getseldefreportattr")
	public JSONObject getRptDataset(String id) throws Exception {
		EReport<EData<String, Object>, Map> rptInfo = this.reportService.getReportInfo(id);
		String datasetId = rptInfo.getReportMeta().getConfig();
		return JSONObject.fromObject(datasetId);
	}

	/**
	 * 获取数据集并构成左侧树
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("dataset")
	public List<HashMap<String, Object>> dataset(String id) throws Exception {
		List<HashMap<String, Object>> treenodes = new ArrayList<HashMap<String, Object>>();
		EDatasetMeta datasetMeta = this.customreportService.getDataset(id);
		HashMap<String, Object> treeNode = new HashMap<String, Object>();
		treeNode.put("id", "dataset_" + datasetMeta.getId());
		treeNode.put("text", datasetMeta.getName());
		treeNode.put("parentId", "");
		// treeNode.put("state", "closed");
		Map<String, Object> attrmap = beanToMap(datasetMeta);
		attrmap.put("isleaf", false);
		treeNode.put("attributes", attrmap);
		treenodes.add(treeNode);
		for (FieldMeta fieldMeta : datasetMeta.getFieldMetaList()) {
			HashMap<String, Object> treeNodei = new HashMap<String, Object>();
			treeNodei.put("id", "f_" + fieldMeta.getId());
			treeNodei.put("text", fieldMeta.getName());
			treeNodei.put("parentId", "dataset_" + datasetMeta.getId());
			treeNodei.put("state", "open");
			Map<String, Object> attrmapi = beanToMap(fieldMeta);
			attrmapi.put("isleaf", true);
			treeNodei.put("attributes", attrmapi);
			//
			if (fieldMeta.getDataType() != null && (java.sql.Types.INTEGER == fieldMeta.getDataType()
					|| java.sql.Types.DECIMAL == fieldMeta.getDataType()
					|| java.sql.Types.DOUBLE == fieldMeta.getDataType()
					|| java.sql.Types.FLOAT == fieldMeta.getDataType()
					|| java.sql.Types.NUMERIC == fieldMeta.getDataType())) {

				treeNodei.put("iconCls", "icon-sum");
			} else {
				treeNodei.put("iconCls", "icon-file");

			}
			//
			treenodes.add(treeNodei);
		}
		setChildren(treenodes);
		return treenodes;
	}

	private void setChildren(List<HashMap<String, Object>> treenodes) {
		List<HashMap<String, Object>> toclean = new ArrayList<HashMap<String, Object>>();
		for (HashMap<String, Object> node : treenodes) {
			List<HashMap<String, Object>> children = new ArrayList<HashMap<String, Object>>();
			for (HashMap<String, Object> nodej : treenodes) {
				String id = node.get("id").toString();
				String parentId = nodej.get("parentId").toString();
				if (id.equalsIgnoreCase(parentId)) {
					children.add(nodej);
					toclean.add(nodej);
				}
			}
			node.put("children", children);
		}
		for (HashMap<String, Object> node : toclean) {
			treenodes.remove(node);
		}
	}

	private Map<String, Object> beanToMap(Object obj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor fieldi : propertyDescriptors) {
			String key = fieldi.getName();

			try {
				Object obji = BeanUtil.getProperty(obj, key);
				if (key != null && (fieldi.getPropertyType().getSimpleName().equalsIgnoreCase("String")
						|| fieldi.getPropertyType().getSimpleName().equalsIgnoreCase("Date")
						|| fieldi.getPropertyType().getSimpleName().equalsIgnoreCase("Integer")
						|| fieldi.getPropertyType().getSimpleName().equalsIgnoreCase("Double")
						|| fieldi.getPropertyType().getSimpleName().equalsIgnoreCase("Boolean")
						|| fieldi.getClass().getSimpleName().equalsIgnoreCase("Long"))) {
					map.put(key, obji);
				}

			} catch (Exception ex) {
				// logger.error("error", ex);
				// ex.printStackTrace();
				// ex.printStackTrace();
			}
		}
		return map;

	}

	/**
	 * 保存报表数据结构
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = { RequestMethod.POST })
	public Map<String, Object> saveReport(String reportMeta) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(reportMeta);
		JSONArray jsonArray = (JSONArray) jsonObject.get("bindfields");
		JSONArray jsonConditions = (JSONArray) jsonObject.get("conditions");
		JSONArray jsonGroups = (JSONArray) jsonObject.get("groupfields");
		CustomReportMeta customReportMeta = (CustomReportMeta) JSONObject.toBean(jsonObject, CustomReportMeta.class);
		CustomReportBindField[] bindfields = (CustomReportBindField[]) JSONArray.toArray(jsonArray,
				CustomReportBindField.class);
		customReportMeta.setBindfields(Arrays.asList(bindfields));
		CustomReportCondition[] conditions = (CustomReportCondition[]) JSONArray.toArray(jsonConditions,
				CustomReportCondition.class);
		customReportMeta.setConditions(Arrays.asList(conditions));
		if (jsonGroups != null) {
			CustomReportGroupField[] groups = (CustomReportGroupField[]) JSONArray.toArray(jsonGroups,
					CustomReportGroupField.class);
			customReportMeta.setGroupfields(Arrays.asList(groups));
		}

		try {
			String reportId = this.customreportService.saveReportConfig(customReportMeta);
			returnMap.put("scuccess", true);
			returnMap.put("reportId", reportId);
			returnMap.put("mess", "保存成功。");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("error", ex);
			returnMap.put("scuccess", false);
			returnMap.put("mess", ex.getMessage());
		}
		return returnMap;
	}

	/**
	 * 获取报表定义信息
	 * 
	 * @param reportid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getreportdef")
	public CustomReportMeta getReportDef(String reportid) {
		return new CustomReportMeta();
	}

	@ResponseBody
	@RequestMapping(value = "getFormElement")
	public FormElement getFormElement(String elementcode) {
		return this.customreportService.getFormElement(elementcode);
	}

	@RequestMapping("formula")
	public ModelAndView formula() {
		return new ModelAndView("ereportdef/formula");
	}

	@RequestMapping("checkExpression")
	@ResponseBody
	public Map<String, Object> checkExpression(String bingfields, String expression, Integer parseType) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		JSONArray array = JSONArray.fromObject(bingfields);
		@SuppressWarnings("unchecked")
		List<CustomReportBindField> fields = (ArrayList<CustomReportBindField>) JSONArray.toCollection(array,
				CustomReportBindField.class);
		try {
			String parseResult = this.customreportService.checkExpression(fields, expression, parseType);
			returnMap.put("success", true);
			returnMap.put("parseResult", parseResult);
		} catch (Exception ex) {
			logger.error("error", ex);
			returnMap.put("success", false);
			returnMap.put("mess", ex.getMessage());
		}
		return returnMap;
	}

	@ResponseBody
	@RequestMapping("deleteall")
	public Map<String, Object> deleteall(String id, boolean categoryorrpt) {
		String formId = null;
		String datasetId = null;
		String gridId = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (categoryorrpt) {
				List<ReportComponentMeta> reportComponentMeta = customreportService.getReportComponents(id);
				for (ReportComponentMeta reportComponentMetai : reportComponentMeta) {
					if (reportComponentMetai.getType().equalsIgnoreCase("form")) {
						formId = reportComponentMetai.getCompId();
					}
					if (reportComponentMetai.getType().equalsIgnoreCase("grid")) {
						datasetId = reportComponentMetai.getReportId();
						gridId = reportComponentMetai.getCompId();
					}
				}
				this.customreportService.deleteReport(id, formId, datasetId, gridId);
			}
			if (!categoryorrpt) {
				this.customreportService.deleteRptCategory(id);
			}
			map.put("success", true);
			map.put("mess", "删除成功！");
		} catch (Exception ex) {
			logger.error("error", ex);
			map.put("success", false);
			map.put("mess", ex.getMessage());
		}
		return map;
	}

	@ResponseBody
	@RequestMapping("custrptdesc")
	public List<CustRptDesc> custrptdesc(String reportName, String condName, String displayFieldName) throws Exception {
		String staffId = ContextHolderUtils.getSessionUserInfo().getStaffid();
		List<CustRptDesc> custRptDesc = this.customreportService.selCustRptDesc(staffId, reportName, condName,
				displayFieldName);
		return custRptDesc;
	}
}
