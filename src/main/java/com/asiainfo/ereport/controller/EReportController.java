package com.asiainfo.ereport.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.edata.EData;
import com.asiainfo.ereport.report.EReport;
import com.asiainfo.ereport.service.ReportService;
import com.asiainfo.ewebframe.ui.exception.InvalidConfigException;
import com.asiainfo.ewebframe.uitl.ContextHolderUtils;

@Controller
@RequestMapping("report")
public class EReportController {
	@Resource(name = "reportService")
	private ReportService reportservice;

	/**
	 * 
	 * @param modelMap
	 * @return
	 * @throws InvalidConfigException 
	 */
	@RequestMapping("{reportId}")
	public ModelAndView open(@PathVariable String reportId, ModelMap modelMap) throws InvalidConfigException {
		//Object reportcontent = reportservice.render(reportId, new HashMap<String, Object>());
		EReport<EData<String, Object>, Map> report = reportservice.getReportInfo(reportId);
		modelMap.put("reportcontent", report.render(new HashMap<String, Object>()));
		modelMap.put("report", report);
		return new ModelAndView("ereport/index", modelMap);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("{reportId}/{componentid}/loadData")
	@ResponseBody
	public Object loaddata(@PathVariable String reportId, @PathVariable String componentid) {
		Map<String, Object> params = getRequestParams();
		Object grid = reportservice.dataForUpdate(reportId, componentid, params);
		return grid;
	}

	private Map<String, Object> getRequestParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		Enumeration<String> enums = ContextHolderUtils.getRequest().getParameterNames();
		while (enums.hasMoreElements()) {
			String paramnane = enums.nextElement();
			params.put(paramnane, ContextHolderUtils.getRequest().getParameter(paramnane));
		}
		return params;

	}

	/**
	 * 
	 * @param reportId
	 * @param componentid
	 * @param rows
	 *            每页行数
	 * @param page
	 *            页索引
	 * @return
	 */
	@RequestMapping("{reportId}/{componentid}/pageData")
	@ResponseBody
	public Object loadPageData(@PathVariable String reportId, @PathVariable String componentid, Integer rows,
			Integer page) {
		Map<String, Object> params = getRequestParams();
		Object grid = reportservice.pageDataForUpdate(reportId, componentid, page, rows, params);
		return grid;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("{reportId}/{componentid}/loadChilds")
	@ResponseBody
	public Object loadChilds(@PathVariable String reportId, @PathVariable String componentid, String id) {
		Map<String, Object> params = getRequestParams();
		Object grid = reportservice.loadInnerReportData(reportId, componentid, id, params);
		return grid;
	}

	@RequestMapping("{reportId}/{componentid}/exp")
	public ResponseEntity<byte[]> getExcelData(@PathVariable String reportId, @PathVariable String componentid,
			HttpServletResponse response) throws IOException {
		Map<String, Object> params = getRequestParams();
		Set<String> set=params.keySet();
		Iterator<String> iterator=set.iterator();
		while(iterator.hasNext()){
			String paramName=iterator.next();
			String paramsValue= ContextHolderUtils.getRequest().getParameter(paramName);
			params.put(paramName, java.net.URLDecoder.decode(paramsValue,"utf-8"));
		}

		byte[] reader = reportservice.export(reportId, componentid, params);
		String name=reportservice.getReportInfo(reportId).getReportMeta().getName();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentLength(reader.length);
		responseHeaders.add("Content-Type", "application/zip");
		responseHeaders.add("Content-disposition",
				"attachment;filename=" + java.net.URLEncoder.encode(name.trim(),"utf-8").concat(".zip"));
		responseHeaders.setCacheControl("no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
		responseHeaders.setPragma("no-cache");
		return new ResponseEntity<byte[]>(reader, responseHeaders, HttpStatus.OK);
	}
}
