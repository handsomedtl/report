package com.asiainfo.ereport.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.edata.ArrayEDataList;
import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.edata.EData;
import com.asiainfo.edata.EDataset;
import com.asiainfo.eframe.dataset.EDataList;
import com.asiainfo.eframe.excel.SinggleFieldData;
import com.asiainfo.eframe.service.exception.ServiceException;
import com.asiainfo.ereport.EReportStatics;
import com.asiainfo.ereport.components.ComponentFactory;
import com.asiainfo.ereport.components.ReportUIElement;
import com.asiainfo.ereport.components.impl.AbstractReportElement;
import com.asiainfo.ereport.excel.ExcelExportHelper;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ereport.meta.ReportMeta;
import com.asiainfo.ewebframe.ui.CommonUIElement;
import com.asiainfo.ewebframe.ui.UIElement;
import com.asiainfo.ewebframe.ui.UIElementTypes;
import com.asiainfo.ewebframe.ui.exception.InvalidConfigException;
import com.asiainfo.ewebframe.ui.form.DynamicForm;
import com.asiainfo.ewebframe.ui.form.DynamicFormEngine;
import com.asiainfo.ewebframe.ui.form.UIFormElement;
import com.asiainfo.ewebframe.ui.form.UITemplateEngine;
import com.asiainfo.ewebframe.uitl.ContextHolderUtils;

import freemarker.template.TemplateNotFoundException;
import jodd.util.StringUtil;

public class EReport<E, P> extends CommonUIElement implements UIElement {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 报表配置
	 */
	private ReportMeta reportMeta;

	private Map<String, ComponentFactory<? extends AbstractReportElement>> factories = new HashMap<String, ComponentFactory<? extends AbstractReportElement>>();
	/**
	 * 所有页面元素
	 */
	private List<ReportUIElement> reportElements = new ArrayList<ReportUIElement>();
	private Map<String, ReportUIElement> compMap = new HashMap<String, ReportUIElement>();

	private DatasetFactory datasetFactory;

	private ExcelExportHelper exportHelper;
	/**
	 * 动态表单服务
	 */
	private DynamicFormEngine dynamicFormEngine;
	private boolean init = false;

	public EReport(ReportMeta reportMeta, List<ComponentFactory<? extends AbstractReportElement>> compFactories) {
		this.reportMeta = reportMeta;
		if (compFactories != null) {
			this.factories.clear();
			for (ComponentFactory<? extends AbstractReportElement> f : compFactories) {
				this.factories.put(f.getfactoryTypeName().toUpperCase(), f);
			}
		}
	}

	private void init() {
		if (init) {
			return;
		}
		List<ReportComponentMeta> componentconfigs = this.reportMeta.getComponentconfigs();

		for (ReportComponentMeta reportComponentMeta : componentconfigs) {

			if (this.factories.containsKey(reportComponentMeta.getType().toUpperCase())) {
				ReportUIElement uielement;
				uielement = this.factories.get(reportComponentMeta.getType().toUpperCase())
						.getComponent(reportComponentMeta);

				if (uielement == null) {
					throw new RuntimeException(String.format("无效的组件配置! report:[%s] component:[%s] type:[%s]",
							reportComponentMeta.getReportId(), reportComponentMeta.getCompId(),
							reportComponentMeta.getType()));
				}
				reportElements.add(uielement);
				compMap.put(uielement.getComponentid(), uielement);
			}
		}
		init = true;
	}

	@Override
	public Object render(Map params) throws InvalidConfigException {
		init();
		if (params == null)
			params = new HashMap();
		String all = "";
		for (ReportUIElement element : this.reportElements) {
			Object obj = element.render(params);
			System.out.println(obj);
			all += obj.toString();
			params.put(element.getLayoutVar(), obj);
			// return obj;
		}

		// 先看是否个性布局
		try {
			if (StringUtil.isNotBlank(this.reportMeta.getLayoutContent()))
				return UITemplateEngine.renderTemplate(this.reportMeta.getId(), reportMeta.getLayoutContent(), params);
		} catch (ServiceException e) {
			logger.error("render report layout error! reportId:{},layoutId:{},error:{}", reportMeta.getId(),
					reportMeta.getLayoutId(), e);
			throw new RuntimeException("报表渲染异常！请联系管理员");
		}

		// 预设布局渲染，先按布局ID 从模板目录下的layout目录查
		try {
			if (StringUtil.isNotBlank(this.reportMeta.getLayoutId()))
				return UITemplateEngine.renderTemplate(this.reportMeta.getLayoutId(), params, "layout");
		} catch (ServiceException e) {
			logger.error("render report layout error! reportId:{},layoutId:{},error:{}", reportMeta.getId(),
					reportMeta.getLayoutId(), e);
			throw new RuntimeException("报表渲染异常！请联系管理员");
		} catch (TemplateNotFoundException e) {
			// @TODO 去布局数据库中的内容渲染
			e.printStackTrace();
		}

		return all;
	}

	@Override
	public Object renderview(Map params) throws InvalidConfigException {

		return render(params);
	}

	/**
	 * 加载组件数据
	 * 
	 * @param componentid
	 * @param params
	 * @return
	 */
	public EDataList<E> loadData(String componentid, P params) {
		init();
		EDataset<E, P> dataset = getDataSet(componentid);
		if (dataset != null) {
			return dataset.load(params);
		} else {
			return new ArrayEDataList<E>(0);
		}

	}

	/**
	 * 加载grid分页数据
	 * 
	 * @param gridid
	 * @param params
	 * @param offset
	 * @param limit
	 * @return
	 */
	public EDataList<E> pageData(String gridid, P params, Integer pageindex, Integer pagesize) {
		this.init();
		EDataset<E, P> dataset = getDataSet(gridid);
		Integer offset = (pageindex - 1) * pagesize;
		if (dataset != null) {
			EDataList<E> datalist = dataset.loadPage(params, offset, pagesize);
			Long totalcount = dataset.count(params);
			EDataList<E> result = new ArrayEDataList<E>(totalcount, datalist.getFieldmetas());
			result.addAll(datalist);
			return result;
		} else {
			return new ArrayEDataList<E>(0);
		}

	}

	public byte[] exportExcel(String gridid, Map<String, Object> params) {
		List<ReportComponentMeta> componentconfigs = this.reportMeta.getComponentconfigs();
		String datasetid = "";
		for (ReportComponentMeta reportComponentMeta : componentconfigs) {
			if (reportComponentMeta.getCompId().equalsIgnoreCase(gridid)) {
				datasetid = reportComponentMeta.getDatasetId();
			}
		}
		List<SinggleFieldData> fieldDatds = new ArrayList<SinggleFieldData>();
		this.init();
		for (ReportUIElement uiElement : reportElements) {
			if (uiElement.is(UIElementTypes.UI_ELEMENT_TYPE_FORM)) {
				DynamicForm dynamicForm;
				try {
					dynamicForm = this.dynamicFormEngine.getDynamicForm(uiElement.getComponentid());
					List<UIFormElement> formelements = dynamicForm.getFormdef().getElements();
					for (UIFormElement formElement : formelements) {
						SinggleFieldData singgleFieldData = new SinggleFieldData();
						singgleFieldData.setFieldname(formElement.getFormElement().getLable());
						if (formElement.is(UIElementTypes.FORM_ELEMENT_TYPE_SELECT)
								&& !EReportStatics.SELECT_COMP_DEFAULTVALUE.equalsIgnoreCase(
										"" + params.get(formElement.getFormElement().getElementcode() + "_name"))) {
							singgleFieldData
									.setValue(params.get(formElement.getFormElement().getElementcode() + "_name"));
						} else if (formElement.is(UIElementTypes.FORM_ELEMENT_TYPE_TREE)) {
							singgleFieldData
									.setValue(params.get(formElement.getFormElement().getElementcode() + "_pop"));
						} else {
							singgleFieldData.setValue(params.get(formElement.getFormElement().getElementcode()));
						}

						singgleFieldData.setCondition(true);
						if (formElement.is("placeholder") || formElement.is("raw"))
							continue;
						fieldDatds.add(singgleFieldData);
					}

				} catch (ServiceException e) {

					e.printStackTrace();
				}

			}
		}
		long ll = System.currentTimeMillis();
		Date date = new Date(ll);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SinggleFieldData departNameField = new SinggleFieldData();
		departNameField.setValue(ContextHolderUtils.getSessionUserInfo().getDepartname());
		departNameField.setFieldname("制表部门");
		departNameField.setCondition(false);
		SinggleFieldData usernameField = new SinggleFieldData();
		usernameField.setFieldname("制表人员");
		usernameField.setCondition(false);
		usernameField.setValue(ContextHolderUtils.getSessionUserInfo().getStaffname());
		SinggleFieldData dateField = new SinggleFieldData();
		dateField.setFieldname("制表日期");
		dateField.setCondition(false);
		dateField.setValue(dateFormat.format(date));
		fieldDatds.add(departNameField);
		fieldDatds.add(usernameField);
		fieldDatds.add(dateField);
		EDataset<EData<String, Object>, Map<String, Object>> dataset = this.getDatasetFactory()
				.createDatasetFromConfig(datasetid);

		for (int y = 0; y < params.size(); y++) {

		}
		return this.getExportHelper().export(this.reportMeta.getName(), gridid, dataset, params, fieldDatds);
	}

	/**
	 * 获取组件对应的EDataset
	 * 
	 * @param componentid
	 * @return
	 */
	private EDataset<E, P> getDataSet(String componentid) {
		ReportComponentMeta meta = this.reportMeta.getComponentMeta(componentid);
		if (meta == null || StringUtil.isBlank(meta.getDatasetId()))
			return null;
		EDataset<E, P> dataset = this.getDatasetFactory().createDatasetFromConfig(meta.getDatasetId());
		return dataset;
	}

	public DatasetFactory getDatasetFactory() {
		return datasetFactory;
	}

	public void setDatasetFactory(DatasetFactory datasetFactory) {
		this.datasetFactory = datasetFactory;
	}

	public ExcelExportHelper getExportHelper() {
		return exportHelper;
	}

	public void setExportHelper(ExcelExportHelper exportHelper) {
		this.exportHelper = exportHelper;
	}

	public void setDynamicFormEngine(DynamicFormEngine dynamicFormEngine) {
		this.dynamicFormEngine = dynamicFormEngine;
	}

	@Override
	public String getTypeString() {
		return UIElementTypes.UI_ELEMENT_TYPE_REPORT;
	}

	// /**
	// * 更新组件使用的数据
	// *
	// * @param componentid
	// * 组件ID
	// * @param params
	// * 参数
	// * @return 从数据源获取的数据通过组件处理后返回
	// */
	// public Object dataForUpdate(String componentid, P params) {
	// init();
	// return
	// this.compMap.get(componentid).handleData(this.loadData(componentid,
	// params), null);
	// }
	//
	// /**
	// * 获取更新分页数据
	// *
	// * @param componentid
	// * 组件Id
	// * @param params
	// *
	// * @param offset
	// * @param limit
	// * @return
	// */
	// public Object pageDataForUpdate(String componentid, P params, Integer
	// pageindex, Integer pagesize) {
	// this.init();
	//
	// EDataList data = this.pageData(componentid, params, pageindex, pagesize);
	//
	// return this.compMap.get(componentid).handleData(data, null);
	//
	// }
	//
	// /**
	// * 加载字表或者是treegrid下级节点数据
	// *
	// * @param componentid
	// * @param params
	// * @return
	// */
	// public Object dataforInnerReport(String componentid, String parentid, P
	// params) {
	// init();
	// ReportUIElement comp = this.compMap.get(componentid);
	// try {
	// BeanUtils.setProperty(params, "parentId", parentid);
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// }
	// if (comp instanceof ReportSubViewUIElement) {
	// EDataset<E, P> dataset = this.getDatasetFactory()
	// .createDatasetFromConfig(((ReportSubViewUIElement)
	// comp).getSubDataSrc());
	// return ((ReportSubViewUIElement)
	// comp).handleSubData(dataset.load(params));
	// }
	// return
	// this.compMap.get(componentid).handleData(this.loadData(componentid,
	// params), null);
	// }

	public ReportMeta getReportMeta() {
		return reportMeta;
	}
}
