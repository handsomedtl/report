package com.asiainfo.ereport.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.ereport.service.ReportService;
import com.asiainfo.ewebframe.uitl.ContextHolderUtils;

@Controller
@RequestMapping("reportexp")
public class EReportExpController {
	@Resource(name = "reportService")
	private ReportService reportservice;

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("{reportId}/{componentid}")
	public ResponseEntity<byte[]> getExcelData(@PathVariable String reportId, @PathVariable String componentid,
			HttpServletResponse response) throws IOException {
		Map<String, Object> params = getRequestParams();
		byte[] reader=reportservice.export(reportId, componentid,params);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentLength(reader.length);
		responseHeaders.add("Content-Type","application/zip");
		responseHeaders.add("Content-disposition", "attachment; filename=" +  new String( reportId.getBytes("gb2312"), "ISO8859-1" )+".zip" );
		responseHeaders.setCacheControl("no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
		responseHeaders.setPragma("no-cache");
		return new ResponseEntity<byte[]>(reader,responseHeaders, HttpStatus.OK);
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

}
