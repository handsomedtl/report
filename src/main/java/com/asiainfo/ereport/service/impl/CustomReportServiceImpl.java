package com.asiainfo.ereport.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.edata.EData;
import com.asiainfo.edata.EDataset;
import com.asiainfo.edata.EStaticConstant;
import com.asiainfo.edata.dao.DatasetCategoryDao;
import com.asiainfo.edata.dao.EDatasetMetaDao;
import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.edata.meta.DatasetMetaService;
import com.asiainfo.edata.meta.EDatasetCategory;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.edata.meta.FieldMeta;
import com.asiainfo.eframe.dao.exception.DaoException;
import com.asiainfo.eframe.entity.UserInfo;
import com.asiainfo.eframe.service.exception.ServiceException;
import com.asiainfo.ereport.EReportStatics;
import com.asiainfo.ereport.dao.CustRptDescDao;
import com.asiainfo.ereport.dao.ReportComponentDao;
import com.asiainfo.ereport.dao.ReportMetaDao;
import com.asiainfo.ereport.dao.RptCategoryDao;
import com.asiainfo.ereport.meta.CustRptDesc;
import com.asiainfo.ereport.meta.CustomReportBindField;
import com.asiainfo.ereport.meta.CustomReportCondition;
import com.asiainfo.ereport.meta.CustomReportGroupField;
import com.asiainfo.ereport.meta.CustomReportMeta;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ereport.meta.ReportDesc;
import com.asiainfo.ereport.meta.ReportMeta;
import com.asiainfo.ereport.meta.RptCategory;
import com.asiainfo.ereport.service.CustomReportService;
import com.asiainfo.ereport.util.FunctionExpressionParse;
import com.asiainfo.ewebframe.dao.FormDefDao;
import com.asiainfo.ewebframe.dao.UIElementDao;
import com.asiainfo.ewebframe.dao.UIGridMetaDao;
import com.asiainfo.ewebframe.ui.UIElementTypes;
import com.asiainfo.ewebframe.ui.form.UIFormElement;
import com.asiainfo.ewebframe.ui.form.def.FormDef;
import com.asiainfo.ewebframe.ui.form.def.FormElement;
import com.asiainfo.ewebframe.ui.form.def.TreeFormElementDef;
import com.asiainfo.ewebframe.ui.form.element.DateUIElement;
import com.asiainfo.ewebframe.ui.form.element.FiledListenerDef;
import com.asiainfo.ewebframe.ui.form.element.FilterFieldDef;
import com.asiainfo.ewebframe.ui.form.element.NumberUIElement;
import com.asiainfo.ewebframe.ui.form.element.SelectUIElement;
import com.asiainfo.ewebframe.ui.form.element.UIElementFactory;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridColMeta;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridMeta;
import com.asiainfo.ewebframe.uitl.ContextHolderUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("customReportService")
public class CustomReportServiceImpl implements CustomReportService, Comparator<FormElement> {
	@Resource(name = "datasetMetaService")
	private DatasetMetaService datasetMetaService;
	@Resource(name = "datasetCategoryDao")
	private DatasetCategoryDao datasetCategoryDao;
	@Resource(name = "UIElementDao")
	private UIElementDao uiElementDao;
	@Value("${system.templateformid}")
	private String templateformid;
	@Resource(name = "datasetFactory")
	private DatasetFactory datasetFactory;
	@Resource(name = "rptCategoryDao")
	private RptCategoryDao rptCategoryDao;
	@Resource(name = "reportMetaDao")
	private ReportMetaDao reportMetaDao;
	@Autowired
	private FormDefDao formDefDao;
	@Resource(name = "uiGridMetaDao")
	private UIGridMetaDao uiGridMetaDao;
	@Resource(name = "datasetMetaDao")
	private EDatasetMetaDao datasetMetaDao;
	@Resource(name = "reportComponentDao")
	private ReportComponentDao reportComponentDao;
	@Resource(name = "custRptDescDao")
	private CustRptDescDao custRptDescDao;
	private static Logger logger = LoggerFactory.getLogger(CustomReportServiceImpl.class);

	@Transactional(rollbackFor = { ServiceException.class, Exception.class })
	@Override
	public String saveReportConfig(CustomReportMeta reportMeta) throws ServiceException {
		// 1.校验数据的完整性
		Integer tableAliasIndex = 0;
		Map<String, Integer> tableNameAndIndexMap = new HashMap<String, Integer>();
		Map<String, String> fieldAndDatasetMap = new HashMap<String, String>();
		if (reportMeta.getBindfields() == null || reportMeta.getBindfields().isEmpty()) {
			throw new ServiceException("请添加表格绑定字段。");
		}
		if (reportMeta.getConditions() == null || reportMeta.getConditions().isEmpty()) {
			throw new ServiceException("请添加查询条件。");
		}
		if (reportMeta.getReportName() == null || reportMeta.getReportName().isEmpty()) {
			throw new ServiceException("请输入报表名称。");
		}
		Integer formulaCount = 0;
		for (CustomReportBindField reportBindField : reportMeta.getBindfields()) {
			if (reportBindField.getDatasetId() == null || reportBindField.getDatasetId().equalsIgnoreCase("")) {
				throw new ServiceException("请选择数据集。");
			}
			if (reportBindField.getFieldName() == null || reportBindField.getFieldName().equalsIgnoreCase("")) {
				throw new ServiceException("请设置列 " + reportBindField.getColIndexName() + "的绑定字段。");
			}
			if (reportBindField.getTitle() == null || reportBindField.getTitle().equalsIgnoreCase("")) {
				throw new ServiceException("请设置列 " + reportBindField.getColIndexName() + "的标题。");
			}
			Integer occarcount = 0;
			for (CustomReportBindField reportBindFieldj : reportMeta.getBindfields()) {
				if (reportBindFieldj.getDatasetId().equalsIgnoreCase(reportBindField.getFieldName())
						&& reportBindFieldj.getFieldName().equalsIgnoreCase(reportBindField.getFormat())) {
					occarcount++;
				}
			}
			if (occarcount > 1) {
				throw new ServiceException("字段 " + reportBindField.getName() + "存在过个绑定。");
			}
			if (!tableNameAndIndexMap.containsKey(reportBindField.getDatasetId())) {
				tableNameAndIndexMap.put(reportBindField.getDatasetId(), tableAliasIndex);
				tableAliasIndex++;
			}
			fieldAndDatasetMap.put(reportBindField.getFieldName(), reportBindField.getDatasetId());
			if (reportBindField.isFormula()) {
				reportBindField.setAlias("bodyFormula" + formulaCount);
				formulaCount++;
			} else {
				reportBindField.setAlias(reportBindField.getFieldName());
			}

		}
		// 校验分组行设置的正确性
		Map<String, String> formulaAndTransMap = new HashMap<String, String>();
		for (int i = 0; i < reportMeta.getBindfields().size(); i++) {
			CustomReportBindField bindField = reportMeta.getBindfields().get(i);
			if (bindField.isFormula()) {
				if (bindField.getFieldName() == null || bindField.getFieldName().equalsIgnoreCase("")) {
					throw new ServiceException("请填写合计行分组表达式。");
				}
				try {
					String expression = bindField.getFieldName().substring(1, bindField.getFieldName().length());
					expression = this.checkExpression(reportMeta.getBindfields(), expression,
							FunctionExpressionParse.PARASETYPE_BODY);
					bindField.setFinalFieldName(expression);
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new ServiceException(ex.getMessage());
				}

			} else {
				bindField.setFinalFieldName(bindField.getFieldName());
			}
		}
		for (int i = 0; i < reportMeta.getGroupfields().size(); i++) {
			CustomReportGroupField reportGroupField = reportMeta.getGroupfields().get(i);
			if (reportGroupField.getExpression() == null || reportGroupField.getExpression().equalsIgnoreCase("")) {
				throw new ServiceException("请填写合计行分组表达式。");
			}
			if (reportGroupField.isIsformula()) {
				try {
					String expression = reportGroupField.getExpression().substring(1,
							reportGroupField.getExpression().length());
					String transF = this.checkExpression(reportMeta.getBindfields(), expression,
							FunctionExpressionParse.PARASETYPE_TAIL);
					formulaAndTransMap.put(reportGroupField.getExpression(), transF);
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new ServiceException(ex.getMessage());
				}
			}
		}
		EDatasetMeta oldDatasetMeta = this.datasetFactory.createDatasetFromConfig(reportMeta.getDatasetId()).getMeta();
		// 2.生成dataset 配置
		try {
			String selectSql = this.orgSelectSql(reportMeta, tableNameAndIndexMap);
			EDatasetMeta datasetMeta = new EDatasetMeta();
			datasetMeta.setCategory(EStaticConstant.DEFAULT_DATASET_CATEGOTY);
			datasetMeta.setContent(selectSql);
			if (reportMeta.getReportId() == null || reportMeta.getReportId().isEmpty()) {
				SimpleDateFormat dataformater = new SimpleDateFormat("yyyyMMddhhmm");
				String reportId = dataformater.format(new Date());
				reportMeta.setReportId(reportId);
			}
			if (reportMeta.getReportDatasetId() == null || reportMeta.getReportDatasetId().isEmpty()) {
				datasetMeta.setId(reportMeta.getReportId());
			} else {
				datasetMeta.setId(reportMeta.getReportDatasetId());
			}
			datasetMeta.setName(reportMeta.getReportName() + "_数据集");
			datasetMeta.setType(EStaticConstant.DATASET_TYPE_SQL);
			Set<String> datasetIds = tableNameAndIndexMap.keySet();
			Iterator<String> iterator = datasetIds.iterator();
			String datasetId = "";
			while (iterator.hasNext()) {
				datasetId = iterator.next();
			}
			EDataset<EData<String, Object>, Map<String, Object>> dataset = this.datasetFactory
					.createDatasetFromConfig(datasetId);
			UserInfo userInfo = ContextHolderUtils.getSessionUserInfo();
			datasetMeta.setUpdateStaff(userInfo.getStaffid());
			datasetMeta.setUpdateDepart(userInfo.getDepartid());
			datasetMeta.setUpdateDate(new Date());
			datasetMeta.setResourceType(EDatasetMeta.DATASET_TYPE_CUSTOM);
			datasetMeta.setDataRoute(dataset.getMeta().getDataRoute());
			List<FieldMeta> fieldMetas = new ArrayList<FieldMeta>();
			if (reportMeta.getGroupfields() != null && !reportMeta.getGroupfields().isEmpty()) {
				String summaryConfig = "";
				for (int i = 0; i < reportMeta.getGroupfields().size(); i++) {
					CustomReportGroupField groupField = reportMeta.getGroupfields().get(i);
					String exp = groupField.getExpression();
					String formula = formulaAndTransMap.get(exp);
					summaryConfig = summaryConfig + formula + " formula_" + i + " ,";
					groupField.setAlias(" formula_" + i);
				}
				if (!summaryConfig.isEmpty()) {
					summaryConfig = summaryConfig.substring(0, summaryConfig.length() - 1);
				}
				datasetMeta.setSummaryConfig(summaryConfig);
			}
			for (CustomReportBindField reportBindFieldj : reportMeta.getBindfields()) {
				FieldMeta fieldMeta = new FieldMeta();
				fieldMetas.add(fieldMeta);
				fieldMeta.setId(reportBindFieldj.getAlias());
				fieldMeta.setName(reportBindFieldj.getTitle());
				fieldMeta.setDatasetId(datasetMeta.getId());
				fieldMeta.setDataType(reportBindFieldj.getDataType());
				EDatasetMeta copyDateMeta = dataset.getMeta();
				for (FieldMeta fieldMetai : copyDateMeta.getFieldMetaList()) {
					if (fieldMetai.getId().equalsIgnoreCase(reportBindFieldj.getAlias())) {
						fieldMeta.setConverterSqlexp(fieldMetai.getConverterSqlexp());
					}
				}
			}
			datasetMeta.setFieldMetaList(fieldMetas);
			List<ConditionMeta> conditionMetas = new ArrayList<ConditionMeta>();
			for (CustomReportCondition reportCondition : reportMeta.getConditions()) {
				ConditionMeta conditionMeta = new ConditionMeta();
				conditionMeta.setDatasetId(datasetMeta.getId());
				conditionMeta.setId(reportCondition.getParamName());
				conditionMeta.setName(reportCondition.getLable());
				conditionMeta.setRequired(reportCondition.isRequired());
				conditionMeta.setSrcType(EStaticConstant.PARAM_SOURCE_TYPE_FORM);
				conditionMetas.add(conditionMeta);
			}
			// 拷贝旧数据集中的session条件

			for (ConditionMeta conditionMeta : oldDatasetMeta.getConditionMetaList()) {
				if (EStaticConstant.PARAM_SOURCE_TYPE_SYSTEM == conditionMeta.getSrcType()) {
					ConditionMeta conditionMetai = new ConditionMeta();
					BeanUtils.copyProperties(conditionMeta, conditionMetai);
					conditionMetai.setDatasetId(datasetMeta.getId());
					conditionMetas.add(conditionMetai);
				}
			}
			datasetMeta.setConditionMetaList(conditionMetas);
			// 3.生成表单
			List<FormElement> formElements = new ArrayList<FormElement>();
			FormDef newForm = this.createForm(reportMeta, formElements);
			// 设置行列索引
			FormElement[] sortElements = new FormElement[formElements.size()];
			formElements.toArray(sortElements);
			Arrays.sort(sortElements, this);
			int rowIndex = 1;
			formElements.clear();
			for (int i = 0; i < sortElements.length; i++) {
				if (i % 3 == 0) {
					rowIndex++;
				}
				sortElements[i].setRowindex(rowIndex);
				formElements.add(sortElements[i]);
			}

			// 添加查询按钮
			FormElement query_button = new FormElement();
			query_button.setTemplateid(newForm.getTemplateId());
			query_button.setElementcode("query");
			query_button.setElementid(Long.valueOf(EStaticConstant.QUERY_BUTTON_ELEMENT_ID));
			query_button.setLable("查询");
			query_button.setOrder(formElements.size() + 1);
			query_button.setRowindex(rowIndex + 1);
			query_button.setColindex(1);
			query_button.setColspan(2);
			query_button.setRequired("0");
			formElements.add(query_button);
			newForm.setTemplateType("2");
			newForm.setEditTemplate("<from></from>");
			newForm.setVersionNum(1);
			// <FROM></FROM>
			// 4.生成gird配置
			List<ReportComponentMeta> componentconfigs = new ArrayList<ReportComponentMeta>();
			// UIGridMeta
			UIGridMeta uiGridMeta = this.createGridConfig(reportMeta);
			uiGridMeta.setName(reportMeta.getReportName() + "_grid");
			uiGridMeta.setDataSrc(datasetMeta.getId());
			// min-height:500px
			uiGridMeta.setGridCss("min-height:500px");
			if (reportMeta.getGroupfields() != null && reportMeta.getGroupfields().size() > 0) {
				uiGridMeta.setRowSummary(EStaticConstant.SHOW_SUMMARY_ROLE_LASTPAGE);
			} else {
				uiGridMeta.setRowSummary(EStaticConstant.SHOW_SUMMARY_ROLE_NONE);
			}
			// 设置合计行列属性
			// 5.根据以上4步的结果生成报表配置
			if (reportMeta.getCategory() == null || reportMeta.getCategory().isEmpty()) {
				reportMeta.setCategory(EStaticConstant.DEFAULT_REPORT_CATEGORY_ID);
			}
			reportMeta.setFormId(newForm.getTemplateId());
			reportMeta.setGridId(uiGridMeta.getId());
			reportMeta.setReportDatasetId(datasetMeta.getId());
			ReportMeta newreportMeta = new ReportMeta();
			newreportMeta.setName(reportMeta.getReportName());
			newreportMeta.setId(reportMeta.getReportId());
			newreportMeta.setCategory(reportMeta.getCategory());
			newreportMeta.setLayoutId(EReportStatics.DEFAULT_REPORT_TEMPLATE);
			newreportMeta.setDescription(reportMeta.getReportName());
			newreportMeta.setStatus(1);
			newreportMeta.setUpdateDate(new Date());
			newreportMeta.setUpdateDepart(userInfo.getDepartid());
			newreportMeta.setUpdateStaff(userInfo.getStaffid());
			newreportMeta.setOwnerid(userInfo.getStaffid());
			newreportMeta.setType(EReportStatics.REPORT_CRF_TYPE_CUSTOM);
			reportMeta.setReportId(newreportMeta.getId());
			JSONObject configObject = JSONObject.fromObject(reportMeta);

			newreportMeta.setConfig(configObject.toString());
			ReportComponentMeta componentGrid = new ReportComponentMeta();
			componentGrid.setType(UIElementTypes.UI_ELEMENT_TYPE_GRID);
			componentGrid.setDatasetId(datasetMeta.getId());
			componentGrid.setCompId(uiGridMeta.getId());
			componentGrid.setDisplayOrder(1);
			componentGrid.setLayoutVar(EStaticConstant.DEFAULT_LYAOUT_GRID_VAR);
			componentGrid.setReportId(newreportMeta.getId());
			componentconfigs.add(componentGrid);

			ReportComponentMeta componentForm = new ReportComponentMeta();
			componentForm.setType(UIElementTypes.UI_ELEMENT_TYPE_FORM);
			componentForm.setCompId(newForm.getTemplateId());
			componentForm.setDisplayOrder(1);
			componentForm.setLayoutVar(EStaticConstant.DEFAULT_LYAOUT_FORM_VAR);
			componentconfigs.add(componentForm);
			componentForm.setReportId(newreportMeta.getId());
			newreportMeta.setComponentconfigs(componentconfigs);
			// 开始保存
			// 1.保存数据集
			this.datasetMetaService.save(datasetMeta);
			// 2.保存表单
			this.formDefDao.saveFormDef(newForm);
			this.uiElementDao.del(FormElement.class, newForm.getTemplateId());
			for (FormElement formElement : formElements) {
				this.uiElementDao.saveFormElement(formElement);
			}
			// 3.保存grid
			uiGridMetaDao.saveUiGrid(uiGridMeta);
			// 4.保存报表
			reportMetaDao.saveReportMeta(newreportMeta);
			return newreportMeta.getId();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("error", ex);
			throw new ServiceException(ex.getMessage());
		}

	}

	private FormDef createForm(CustomReportMeta reportMeta, List<FormElement> formElements)
			throws ServiceException, DaoException {
		// 获取模板表单
		FormDef formDef = formDefDao.getFormDef(this.templateformid);
		// 设置表单元素的渲染模板，设置级联关系
		FormDef newForm = new FormDef();
		if (reportMeta.getFormId() == null || reportMeta.getFormId().isEmpty()) {
			newForm.setTemplateId(reportMeta.getReportId() + "_form");
		} else {
			newForm.setTemplateId(reportMeta.getFormId());
		}
		for (CustomReportCondition reportCondition : reportMeta.getConditions()) {
			FormElement formElement = new FormElement();
			FormElement copyElement = new FormElement();
			for (UIFormElement uiFormElement : formDef.getElements()) {
				if (uiFormElement.getFormElement().getElementcode()
						.equalsIgnoreCase(reportCondition.getRenderElementCode())) {
					formElement.setElementid(uiFormElement.getFormElement().getElementid());
					copyElement = uiFormElement.getFormElement();
				}
			}
			formElements.add(formElement);
			BeanUtils.copyProperties(copyElement, formElement);
			formElement.setTemplateid(newForm.getTemplateId());
			formElement.setElementcode(reportCondition.getParamName());
			if (reportCondition.isRequired()) {
				formElement.setRequired("1");
			} else {
				formElement.setRequired("0");
			}
			formElement.setOrder(reportCondition.getOrder());
			formElement.setRowindex(1);
			formElement.setColindex(reportCondition.getOrder());
			formElement.setColspan(EStaticConstant.DEFAULT_ELEMENT_SPAN);
			if (reportCondition.getOper() != null && !reportCondition.getOper().equalsIgnoreCase("")) {

				if (reportCondition.getOper().equalsIgnoreCase(">=") || reportCondition.getOper().equalsIgnoreCase("<=")
						|| reportCondition.getOper().equalsIgnoreCase(">")
						|| reportCondition.getOper().equalsIgnoreCase("<")) {
					
					formElement.setLable(reportCondition.getLable()+"("+reportCondition.getOper()+")");
				}else{
					formElement.setLable(reportCondition.getLable());	
				}
			}else{
				formElement.setLable(reportCondition.getLable());
			}
			
			// 设置listener 和filter
			if (copyElement.getListeners() != null && copyElement.getListeners().length > 0) {
				FiledListenerDef[] newListeners = getListners(reportMeta, copyElement);
				for (FiledListenerDef filedListenerDef : newListeners) {
					for (CustomReportCondition reportConditioni : reportMeta.getConditions()) {
						if (filedListenerDef.getSelector()
								.equalsIgnoreCase("#" + reportConditioni.getRenderElementCode())) {
							filedListenerDef.setSelector("#" + reportConditioni.getParamName());
							break;
						}
					}
				}
				formElement.setListeners(newListeners);
			}
			if (copyElement.getFilterfields() != null && copyElement.getFilterfields().length > 0) {
				FilterFieldDef[] filters = getFilters(reportMeta, copyElement);
				for (FilterFieldDef filterFieldDef : filters) {
					for (CustomReportCondition reportConditioni : reportMeta.getConditions()) {
						if (filterFieldDef.getFiledname()
								.equalsIgnoreCase("#" + reportConditioni.getRenderElementCode())) {
							filterFieldDef.setFiledname("#" + reportConditioni.getParamName());
							filterFieldDef.setSqlfieldname(filterFieldDef.getSqlfieldname());
							break;
						}
					}
				}
				formElement.setFilterfields(filters);
			}
			Map<String, Object> extrparamMap = new HashMap<String, Object>();
			if (formElement.getListeners() != null && formElement.getListeners().length > 0) {
				extrparamMap.put("listeners", formElement.getListeners());
			}
			if (formElement.getFilterfields() != null && formElement.getFilterfields().length > 0) {
				extrparamMap.put("filterfields", formElement.getFilterfields());
			}
			if ((formElement.getListeners() != null && formElement.getListeners().length > 0)
					|| (formElement.getFilterfields() != null && formElement.getFilterfields().length > 0)) {
				JSONObject jsonObject = JSONObject.fromObject(extrparamMap);
				formElement.setExtraparam(jsonObject.toString());
			}

		}
		return newForm;
	}

	private UIGridMeta createGridConfig(CustomReportMeta reportMeta) {
		UserInfo userInfo = ContextHolderUtils.getSessionUserInfo();
		List<UIGridColMeta> gridCols = new ArrayList<UIGridColMeta>();
		UIGridMeta uiGridMeta = new UIGridMeta();
		if (reportMeta.getGridId() == null || reportMeta.getGridId().isEmpty()) {
			uiGridMeta.setId(reportMeta.getReportId() + "_grid");
		} else {
			uiGridMeta.setId(reportMeta.getGridId());
		}
		Integer order = 0;
		for (CustomReportBindField reportBindFieldj : reportMeta.getBindfields()) {
			UIGridColMeta gridColMeta = new UIGridColMeta();
			gridColMeta.setAlign(reportBindFieldj.getAlign());
			gridColMeta.setTitle(reportBindFieldj.getTitle());
			gridColMeta.setField(reportBindFieldj.getAlias().toUpperCase());
			gridColMeta.setResizeable(true);
			gridColMeta.setGridId(uiGridMeta.getId());
			gridColMeta.setShowOrder(order);
			String cellStyle = "";
			if (reportBindFieldj.getStyle() != null && reportBindFieldj.getStyle().length != 0) {
				for (String style : reportBindFieldj.getStyle()) {
					if ("bold".equalsIgnoreCase(style)) {
						cellStyle = cellStyle + "font-weight:bold;";
					} else if ("italic".equalsIgnoreCase(style)) {
						cellStyle = cellStyle + "font-style:italic;";
					} else if ("underline".equalsIgnoreCase(style)) {
						cellStyle = cellStyle + "text-decoration:underline;";
					}
				}
			}
			if (reportBindFieldj.getBackground() != null) {
				cellStyle = cellStyle + "background-color:" + reportBindFieldj.getBackground() + ";";
			}
			gridColMeta.setCellStyle(cellStyle);
			if (reportBindFieldj.getWidth() == null) {
				gridColMeta.setWidth(EStaticConstant.DEFAULT_COL_WIDTH);
			} else {
				gridColMeta.setWidth(reportBindFieldj.getWidth());
			}
			// FOOTER_ALIGN
			if (reportBindFieldj.getAlign() != null && !reportBindFieldj.getAlign().isEmpty()) {
				gridColMeta.setFooterAlign(reportBindFieldj.getAlign());
			} else {
				gridColMeta.setFooterAlign(EStaticConstant.ALIGN_RIGHT);
			}
			if (reportMeta.getGroupfields() != null && !reportMeta.getGroupfields().isEmpty()) {
				for (CustomReportGroupField reportGroupField : reportMeta.getGroupfields()) {
					if (reportGroupField.getColIndex().equalsIgnoreCase(reportBindFieldj.getColIndexName())) {
						gridColMeta.setFooterAlign(reportGroupField.getAlign());
						gridColMeta.setFooterFormatter(reportGroupField.getFormat());
						String footStyle = "";
						if (reportGroupField.getStyle() != null && reportGroupField.getStyle().length != 0) {
							for (String style : reportGroupField.getStyle()) {
								if ("bold".equalsIgnoreCase(style)) {
									footStyle = footStyle + "font-weight:bold;";
								} else if ("italic".equalsIgnoreCase(style)) {
									footStyle = footStyle + "font-style:italic;";
								} else if ("underline".equalsIgnoreCase(style)) {
									footStyle = footStyle + "text-decoration:underline;";
								}
							}
						}
						if (reportGroupField.getBackground() != null && !reportGroupField.getBackground().isEmpty()) {
							footStyle = footStyle + "background-color:" + reportGroupField.getBackground() + ";";
						} else {
							footStyle = footStyle + "background-color:#fff;";
						}
						if (gridColMeta.getFooterAlign() != null && !gridColMeta.getFooterAlign().isEmpty()) {
							footStyle = footStyle + "text-align-last:" + gridColMeta.getFooterAlign() + ";";
						}
						gridColMeta.setFooterStyle(footStyle);

					}
				}
			}
			gridCols.add(gridColMeta);
			order++;
		}
		HashMap<String, String> fieldAndExpMap = new HashMap<String, String>();
		for (CustomReportGroupField reportGroupField : reportMeta.getGroupfields()) {
			String colIndex = reportGroupField.getColIndex();
			for (CustomReportBindField reportBindField : reportMeta.getBindfields()) {
				if (reportBindField.getColIndexName().equalsIgnoreCase(colIndex)) {
					if (reportGroupField.isIsformula()) {
						fieldAndExpMap.put(reportBindField.getAlias().toUpperCase(), reportGroupField.getAlias());
					} else {
						fieldAndExpMap.put(reportBindField.getAlias(), reportGroupField.getExpression());
					}
				}
			}
		}
		JSONArray jsonObject = JSONArray.fromObject(fieldAndExpMap);
		uiGridMeta.setRowSummaryConfig(jsonObject.toString());
		uiGridMeta.setCols(gridCols);
		uiGridMeta.setType(UIElementTypes.GRID_TYPE_DATAGRID);
		uiGridMeta.setPaging(reportMeta.isPaging());
		uiGridMeta.setRowSummary(EReportStatics.ROW_SUMMARY_TYPE_LASTPAGE);
		uiGridMeta.setFunctionList(EReportStatics.GRID_FUNCTION_EXPORT);
		uiGridMeta.setUpdateDate(new Date());
		uiGridMeta.setUpdateDepart(userInfo.getDepartid());
		uiGridMeta.setUpdateStaff(userInfo.getStaffid());
		String headStr = this.createGridHeader(reportMeta);
		uiGridMeta.setHeader(headStr);
		return uiGridMeta;
	}

	/**
	 * 组织过级联规则--获取监听器
	 * 
	 * @param reportMeta
	 * @param copyElement
	 * @return
	 */
	private FiledListenerDef[] getListners(CustomReportMeta reportMeta, FormElement copyElement) {
		List<FiledListenerDef> newListeners = new ArrayList<FiledListenerDef>();
		for (FiledListenerDef filedListenerDef : copyElement.getListeners()) {
			boolean exists = false;
			for (CustomReportCondition condition : reportMeta.getConditions()) {
				if (filedListenerDef.getSelector().equalsIgnoreCase("#" + condition.getRenderElementCode())) {
					exists = true;
					break;
				}
			}
			if (exists) {
				newListeners.add(filedListenerDef);
			}
		}
		FiledListenerDef[] listners = new FiledListenerDef[newListeners.size()];
		return newListeners.toArray(listners);
	}

	/**
	 * 获取过滤字段列表
	 * 
	 * @param reportMeta
	 * @param copyElement
	 * @return
	 */
	private FilterFieldDef[] getFilters(CustomReportMeta reportMeta, FormElement copyElement) {
		List<FilterFieldDef> newFilters = new ArrayList<FilterFieldDef>();
		for (FilterFieldDef filterFieldDef : copyElement.getFilterfields()) {
			boolean exists = false;
			for (CustomReportCondition condition : reportMeta.getConditions()) {
				if (filterFieldDef.getFiledname().equalsIgnoreCase("#" + condition.getRenderElementCode())) {
					exists = true;
					break;
				}
			}
			if (exists) {
				newFilters.add(filterFieldDef);
			}
		}
		FilterFieldDef[] listners = new FilterFieldDef[newFilters.size()];
		return newFilters.toArray(listners);
	}

	/**
	 * 判断是否数字字段
	 * 
	 * @param reportBindField
	 * @return
	 */
	private boolean isDigital(CustomReportBindField reportBindField, EDatasetMeta datasetMeta) {
		FieldMeta checkField = null;
		for (FieldMeta fieldMeta : datasetMeta.getFieldMetaList()) {
			if (fieldMeta.getId().equalsIgnoreCase(reportBindField.getFieldName())) {
				checkField = fieldMeta;
			}
		}
		if (checkField == null) {
			return false;
		} else {
			if (checkField.getDataType() != null && (checkField.getDataType().equals(java.sql.Types.INTEGER)
					|| checkField.getDataType().equals(java.sql.Types.DOUBLE)
					|| checkField.getDataType().equals(java.sql.Types.FLOAT)
					|| checkField.getDataType().equals(java.sql.Types.DECIMAL)
					|| checkField.getDataType().equals(java.sql.Types.BIGINT)
					|| checkField.getDataType().equals(java.sql.Types.NUMERIC))) {
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * 组织查询sql
	 * 
	 * @param reportMeta
	 * @param tableNameAndIndexMap
	 * @return
	 */
	private String orgSelectSql(CustomReportMeta reportMeta, Map<String, Integer> tableNameAndIndexMap) {
		// 组织动态执行的sql语句
		// 1.组织select 段
		String dynamicSql_select = "select ";
		Map<String, Boolean> fieldGroupMap = new HashMap<String, Boolean>();
		Map<String, EDatasetMeta> idAndMetaMap = new HashMap<String, EDatasetMeta>();
		// boolean isContainGroupCuntion=false;
		for (CustomReportBindField reportBindFieldj : reportMeta.getBindfields()) {
			String tableName = "" + tableNameAndIndexMap.get(reportBindFieldj.getDatasetId());
			tableName = "tab_" + tableName;
			if (!idAndMetaMap.containsKey(reportBindFieldj.getDatasetId())) {
				idAndMetaMap.put(reportBindFieldj.getDatasetId(),
						this.datasetFactory.createDatasetFromConfig(reportBindFieldj.getDatasetId()).getMeta());
			}
			if (!reportBindFieldj.isFormula() && reportBindFieldj.getGroupoer() != null
					&& !reportBindFieldj.getGroupoer().isEmpty()
					&& !reportBindFieldj.getGroupoer().equalsIgnoreCase("count")
					&& this.isDigital(reportBindFieldj, idAndMetaMap.get(reportBindFieldj.getDatasetId()))) {
				dynamicSql_select = dynamicSql_select + " " + reportBindFieldj.getGroupoer() + "( " + tableName + "."
						+ reportBindFieldj.getFinalFieldName() + ") " + reportBindFieldj.getAlias() + ",";
				fieldGroupMap.put(reportBindFieldj.getFieldName(), new Boolean(true));

			} else if (!reportBindFieldj.isFormula() && reportBindFieldj.getGroupoer() != null
					&& !reportBindFieldj.getGroupoer().isEmpty()
					&& reportBindFieldj.getGroupoer().equalsIgnoreCase(" count")) {
				dynamicSql_select = dynamicSql_select + " " + reportBindFieldj.getGroupoer() + "(" + tableName + "."
						+ reportBindFieldj.getFinalFieldName() + ") " + reportBindFieldj.getAlias() + ",";
				fieldGroupMap.put(reportBindFieldj.getFieldName(), new Boolean(true));

			} else if (!reportBindFieldj.isFormula()
					&& (reportBindFieldj.getGroupoer() == null || reportBindFieldj.getGroupoer().isEmpty())
					&& this.isDigital(reportBindFieldj, idAndMetaMap.get(reportBindFieldj.getDatasetId()))) {// 文本或者是日期
				dynamicSql_select = dynamicSql_select + " sum( " + tableName + "."
						+ reportBindFieldj.getFinalFieldName() + ") " + reportBindFieldj.getAlias() + " ,";
				fieldGroupMap.put(reportBindFieldj.getFieldName(), new Boolean(true));

			} else if (reportBindFieldj.isFormula()) {
				dynamicSql_select = dynamicSql_select + reportBindFieldj.getFinalFieldName() + " "
						+ reportBindFieldj.getAlias() + " ,";
				fieldGroupMap.put(reportBindFieldj.getFieldName(), new Boolean(true));
			} else {
				// 处理转换器
				EDatasetMeta datasetMeta = idAndMetaMap.get(reportBindFieldj.getDatasetId());
				FieldMeta reffield = null;
				for (FieldMeta fieldMeta : datasetMeta.getFieldMetaList()) {
					if (fieldMeta.getId().equalsIgnoreCase(reportBindFieldj.getFinalFieldName())) {
						reffield = fieldMeta;
					}
				}
				if (reffield.getConverterSqlexp() != null && !reffield.getConverterSqlexp().isEmpty()) {
					String conversql = reffield.getConverterSqlexp();
					conversql = conversql.replaceAll("\\{\\w+\\}",
							tableName + "." + reportBindFieldj.getFinalFieldName());
					dynamicSql_select = dynamicSql_select + " (" + conversql + " )  "
							+ reportBindFieldj.getFinalFieldName() + " ,";
				} else {
					dynamicSql_select = dynamicSql_select + " " + tableName + "." + reportBindFieldj.getFinalFieldName()
							+ " ,";
				}

				fieldGroupMap.put(reportBindFieldj.getFinalFieldName(), new Boolean(false));
			}

		}
		dynamicSql_select = dynamicSql_select.substring(0, dynamicSql_select.length() - 1);
		// 2.组织 from 段
		String dynamicSql_from = " from ";
		Set<String> tabNames = tableNameAndIndexMap.keySet();
		Iterator<String> tterator = tabNames.iterator();
		while (tterator.hasNext()) {
			String datasetid = tterator.next();
			EDataset<EData<String, Object>, Map<String, Object>> dataset = datasetFactory
					.createDatasetFromConfig(datasetid);
			String tableName = "" + tableNameAndIndexMap.get(datasetid);
			tableName = "tab_" + tableName;
			dynamicSql_from = dynamicSql_from + "(" + dataset.getMeta().getContent() + ") " + tableName + ",";
		}
		dynamicSql_from = dynamicSql_from.substring(0, dynamicSql_from.length() - 1);
		// 3.组织where 段
		for (int i = 0; i < reportMeta.getConditions().size(); i++) {
			CustomReportCondition reportCondition = reportMeta.getConditions().get(i);
			reportCondition.setParamName("param" + i);
		}
		String dynamicSql_where = "  where 1=1 ";
		for (int i = 0; i < reportMeta.getConditions().size(); i++) {
			CustomReportCondition reportCondition = reportMeta.getConditions().get(i);
			FormElement formElement = uiElementDao.getElement(templateformid, reportCondition.getRenderElementCode());
			UIFormElement uiFormElement = UIElementFactory.getIntance().createUIElement(formElement);
			String tableName = "" + tableNameAndIndexMap.get(reportCondition.getDatasetId());
			tableName = "tab_" + tableName;
			dynamicSql_where = dynamicSql_where + "  and " + tableName + "." + reportCondition.getFieldName();
			if (uiFormElement.is(UIElementTypes.FORM_ELEMENT_TYPE_TREE)) {
				TreeFormElementDef treeFormElementDef = (TreeFormElementDef) formElement.getFormElementDef();
				if (treeFormElementDef.getMultiSelect() == 1) {
					dynamicSql_where = dynamicSql_where + " in (:" + reportCondition.getParamName() + ") ";
				} else {
					String oper = reportCondition.getOper() == null || reportCondition.getOper().equalsIgnoreCase("")
							? "=" : reportCondition.getOper();
					if (oper.equalsIgnoreCase("%")) {
						dynamicSql_where = dynamicSql_where + " like  '%'|| :" + reportCondition.getParamName()
								+ "||'%'";
					} else {
						dynamicSql_where = dynamicSql_where + oper + "  :" + reportCondition.getParamName() + "";
					}
					dynamicSql_where = dynamicSql_where + " = :" + reportCondition.getParamName() + "";
				}
			} else if (uiFormElement instanceof DateUIElement) {
				String oper = reportCondition.getOper() == null || reportCondition.getOper().equalsIgnoreCase("") ? "="
						: reportCondition.getOper();
				dynamicSql_where = dynamicSql_where + oper + "  to_date(:" + reportCondition.getParamName()
						+ ",'yyyy-mm-dd')";
			} else if (uiFormElement instanceof NumberUIElement) {
				String oper = reportCondition.getOper() == null || reportCondition.getOper().equalsIgnoreCase("") ? "="
						: reportCondition.getOper();
				dynamicSql_where = dynamicSql_where + oper + "  to_number(:" + reportCondition.getParamName() + ")";
			} else if (uiFormElement instanceof SelectUIElement) {
				String oper = reportCondition.getOper() == null || reportCondition.getOper().equalsIgnoreCase("") ? "="
						: reportCondition.getOper();
				if (oper.equalsIgnoreCase("%")) {
					dynamicSql_where = dynamicSql_where + " like  '%'|| :" + reportCondition.getParamName() + "||'%'";
				} else {
					dynamicSql_where = dynamicSql_where + oper + "  :" + reportCondition.getParamName() + "";
				}

			} else {
				String oper = reportCondition.getOper() == null || reportCondition.getOper().equalsIgnoreCase("") ? "="
						: reportCondition.getOper();
				if (oper.equalsIgnoreCase("%")) {
					dynamicSql_where = dynamicSql_where + " like  '%'|| :" + reportCondition.getParamName() + "||'%'";
				} else {
					dynamicSql_where = dynamicSql_where + oper + "  :" + reportCondition.getParamName() + "";
				}
			}
		}
		// 4.组织 groupby 段
		String groupbyField = "";
		for (CustomReportBindField reportBindFieldj : reportMeta.getBindfields()) {
			String fieldName = reportBindFieldj.getFieldName();
			if (!fieldGroupMap.get(fieldName).booleanValue()) {
				String tableName = "" + tableNameAndIndexMap.get(reportBindFieldj.getDatasetId());
				tableName = "tab_" + tableName;
				groupbyField = groupbyField + tableName + "." + reportBindFieldj.getFieldName() + ",";
			}
		}
		if (!groupbyField.isEmpty()) {
			groupbyField = groupbyField.substring(0, groupbyField.length() - 1);
			groupbyField = " group by " + groupbyField;
		}
		// 5. 组织sql语句
		String sql = dynamicSql_select + dynamicSql_from + dynamicSql_where + groupbyField;
		return sql;
	}

	@Transactional(rollbackFor = { ServiceException.class, Exception.class })
	@Override
	public void deleteReport(String reportid, String formId, String datasetId, String gridId) throws ServiceException {
		// 1.校验是否已经授权给用户
		// 2.如果没有授权删除 数据集配置、grid配置、报表配置
		try {
			ReportMeta reportMeta = reportMetaDao.getReportMeta(reportid);
			if (!ContextHolderUtils.getSessionUserInfo().getStaffid().equalsIgnoreCase(reportMeta.getOwnerid())) {
				throw new ServiceException("由其他用户定义的报表不能删除。");
			}

			this.reportMetaDao.delReportMeta(reportid);
			this.uiGridMetaDao.delGridMeta(gridId);
			this.datasetMetaDao.delDatasetMeta(datasetId);
			this.uiElementDao.delFormTemplateElement(formId);
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public void deleteRptCategory(String id) throws ServiceException {
		if (this.reportMetaDao.getCountReportMetas(id) > 0) {
			throw new ServiceException("请先删除归属报表!");
		}
		this.datasetCategoryDao.del(RptCategory.class, id);
	}

	@Override
	public List<EDatasetMeta> getDatasets(String categoryid, String category, String name) {
		return datasetMetaService.getDatadetConfigs(categoryid, category, name);
	}

	@Override
	public List<EDatasetMeta> getDatasets(String id, String staffId) {
		return datasetMetaService.getDatadetConfigs(id, staffId);
	}

	@Override
	public EDatasetCategory getDatasetCategory(String categoryid) {
		return null;
	}

	@Override
	public List<EDatasetCategory> getDatasetCategoryHaveDataset(String category, String name) {
		return datasetCategoryDao.getAllCategorysHaveDataset(category, name);
	}

	@Override
	public List<EDatasetCategory> getDatasetCategoryHaveDataset(String staffId) {
		return datasetCategoryDao.getAllCategorysHaveDataset(staffId);
	}

	@Override
	public EDatasetMeta getDataset(String datasetid) {

		return this.datasetMetaService.getDatadetConfig(datasetid);
	}

	@Override
	public FormElement getFormElement(String elementid) {
		FormElement formElement = uiElementDao.getElement(templateformid, elementid);
		UIElementFactory.getIntance().createUIElement(formElement);
		return formElement;
	}

	@Override
	public String checkExpression(List<CustomReportBindField> fields, String expression, Integer parseType)
			throws Exception {
		expression = "select " + expression.toUpperCase() + " from dual";
		Map<String, EDatasetMeta> dataSetIdAndDatasetMap = new HashMap<String, EDatasetMeta>();
		Integer formulaCount = 0;
		for (CustomReportBindField reportBindField : fields) {
			if (!dataSetIdAndDatasetMap.containsKey(reportBindField.getDatasetId())) {
				dataSetIdAndDatasetMap.put(reportBindField.getDatasetId(),
						this.datasetFactory.createDatasetFromConfig(reportBindField.getDatasetId()).getMeta());
			}
			if (reportBindField.getAlias() == null || reportBindField.getAlias().isEmpty()) {
				if (reportBindField.isFormula()) {
					reportBindField.setAlias("bodyFormula" + formulaCount);
					formulaCount++;
				} else {
					reportBindField.setAlias(reportBindField.getFieldName());
				}
			}
			EDatasetMeta satasetMetai = dataSetIdAndDatasetMap.get(reportBindField.getDatasetId());
			for (FieldMeta fieldMeta : satasetMetai.getFieldMetaList()) {
				if (fieldMeta.getId().equalsIgnoreCase(reportBindField.getFieldName())) {
					reportBindField.setDataType(fieldMeta.getDataType());
				}
			}
		}
		FunctionExpressionParse expressionParse = new FunctionExpressionParse(fields, expression, parseType);
		return expressionParse.parse();
	}

	@Override
	public List<RptCategory> getReportCategory(String staffId) {
		return rptCategoryDao.getReportCategory(staffId);
	}

	@Override
	public List<ReportMeta> getReportMetas(String category, String staffId) {
		return reportMetaDao.getReportMetas(category, staffId);
	};

	/**
	 * 创建表头
	 * 
	 * @param reportMeta
	 * @return
	 */
	private String createGridHeader(CustomReportMeta reportMeta) {
		ArrayList<HashMap<String, Object>> heads = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < reportMeta.getBindfields().size(); i++) {
			CustomReportBindField reportBindField = reportMeta.getBindfields().get(i);
			HashMap<String, Object> gridHead = new HashMap<String, Object>();
			gridHead.put("rowindex", 1);
			gridHead.put("rowspan", 1);
			gridHead.put("colspan", 1);
			gridHead.put("title", reportBindField.getTitle());
			gridHead.put("field", reportBindField.getAlias().toUpperCase());
			heads.add(gridHead);
		}
		List<ArrayList<HashMap<String, Object>>> headRows = new ArrayList<ArrayList<HashMap<String, Object>>>();
		headRows.add(heads);
		JSONArray jsonArray = JSONArray.fromObject(headRows);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("head", jsonArray);
		return jsonObject.toString();
	}

	/**
	 * 
	 */
	public void saveNewRptCategory(String categoryName, String staffId) {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);
		RptCategory rptCategory = new RptCategory();
		rptCategory.setCategoryName(categoryName);
		rptCategory.setOwnStaffId(staffId);
		rptCategory.setParentId("ZDY");
		rptCategory.setId(staffId + time);
		rptCategoryDao.saveNewRptCategory(rptCategory);
	}

	public List<ReportComponentMeta> getReportComponents(String reportId) {
		return reportComponentDao.getReportComponents(reportId);
	}

	@Override
	public int compare(FormElement o1, FormElement o2) {
		return o1.getOrder().compareTo(o2.getOrder());
	}

	@Override
	public List<CustRptDesc> selCustRptDesc(String staffid, String rptname, String condname, String fieldname) {
		return custRptDescDao.getCustRptDesc(staffid, rptname, condname, fieldname);
	}
    
	@Override
	public List selCustRptDesc(String rptname) {
		return custRptDescDao.getCustRptDesc(rptname);
	}
	@Override
	public List selCustRptCompDesc(String reportid) {
		return custRptDescDao.getCustRptCompDesc(reportid);
	}
	
	@Override
	public List<ReportDesc> selDisplayDataset(String datasetid){
		return custRptDescDao.getDisplayDataset(datasetid);
	}
	@Override
	public UIGridMeta selGridMeta(String gridid) {
		return uiGridMetaDao.getUiGrid(gridid);
	}
}
