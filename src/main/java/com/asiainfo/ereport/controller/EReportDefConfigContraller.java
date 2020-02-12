package com.asiainfo.ereport.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.edata.EData;
import com.asiainfo.edata.EDataset;
import com.asiainfo.edata.dataset.ParseResult;
import com.asiainfo.edata.exception.ErrorDatasetDefineException;
import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.edata.meta.FieldMeta;
import com.asiainfo.ereport.meta.ReportDesc;
import com.asiainfo.ereport.service.CustomReportService;
import com.asiainfo.ewebframe.ui.form.UIFormElement;
import com.asiainfo.ewebframe.ui.form.UIFormElementService;
import com.asiainfo.ewebframe.ui.form.def.FormElement;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridMeta;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("ereportdefconfig")
public class EReportDefConfigContraller {
	@Resource(name = "datasetFactory")
	private DatasetFactory datasetFactory;
	@Resource(name = "UIFormElementService")
	private UIFormElementService uiFormElementService;
	@Resource(name = "customReportService")
	private CustomReportService customreportService;
	private List<FormElement> formElement = new ArrayList<FormElement>();

	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("ereportdefconfig/index");
	}

	@ResponseBody
	@RequestMapping("reportdesc")
	public List custrptdesc(String reportName, String condName, String displayFieldName) throws Exception {
		List custRptDesc = this.customreportService.selCustRptDesc(reportName);
		return custRptDesc;
	}

	@ResponseBody
	@RequestMapping("reportcompdesc")
	public List reportcompdesc(String reportid) throws Exception {
		List reportCompDesc = this.customreportService.selCustRptCompDesc(reportid);
		return reportCompDesc;
	}

	@ResponseBody
	@RequestMapping("displaydataset")
	public JSONObject displaydataset(String datasetid) throws Exception {
		List<ReportDesc> displayDataset = this.customreportService.selDisplayDataset(datasetid);
		JSONArray jsonArr = JSONArray.fromObject(displayDataset);
		JSONObject json = jsonArr.getJSONObject(0);
		return json;
	}

	@ResponseBody
	@RequestMapping("rptcomptype")
	public JSONArray rptcomptype() throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "form");
		map.put("type", "form");
		list.add(0, map);
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("id", "grid");
		map1.put("type", "grid");
		list.add(1, map1);
		return JSONArray.fromObject(list);
	}

	// FieldMeta
	@ResponseBody
	@RequestMapping("displaydatasetcol")
	public List<FieldMeta> displaydatasetcol(String datasetid) throws Exception {
		EDatasetMeta datasets = this.customreportService.getDataset(datasetid);
		List<FieldMeta> fieldMeta = datasets.getFieldMetaList();
		return fieldMeta;
	}

	@ResponseBody
	@RequestMapping("displaydatasetcond")
	public List<ConditionMeta> displaydatasetcond(String datasetid) throws Exception {
		EDatasetMeta datasets = this.customreportService.getDataset(datasetid);
		List<ConditionMeta> condMeta = datasets.getConditionMetaList();
		return condMeta;
	}

	// datasetcondgen
	@ResponseBody
	@RequestMapping("datasetcondgen")
	public List<FormElement> datasetcondgen(String templateid) throws Exception {
		List<UIFormElement> datasets = this.uiFormElementService.getElements(templateid);
		for (UIFormElement uIFormElement : datasets) {
			formElement.add(uIFormElement.getFormElement());
		}
		return formElement;
	}

	@ResponseBody
	@RequestMapping("displaygrid")
	public UIGridMeta displaygrid(String gridid) throws Exception {
		UIGridMeta grid = this.customreportService.selGridMeta(gridid);
		return grid;
	}

	@ResponseBody
	@RequestMapping("analyssql")
	public ParseResult analyssql(String sql, String id) throws ErrorDatasetDefineException {
		ParseResult parseResult = null;
		EDatasetMeta datasetMeta = new EDatasetMeta();
		datasetMeta.setContent(sql);
		datasetMeta.setId(id);
		EDataset<EData<String, Object>, Map<String, Object>> dataset = datasetFactory.createDataset(datasetMeta);
		try {
			parseResult = dataset.parseAndPreeview();
		} catch (Exception e) {
			throw new ErrorDatasetDefineException(e.getMessage());
		}
		return parseResult;
	}
}
