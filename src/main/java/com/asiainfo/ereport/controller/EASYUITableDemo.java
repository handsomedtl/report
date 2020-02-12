package com.asiainfo.ereport.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.ereport.service.ReportService;

@Controller
@RequestMapping("tabledemo")
public class EASYUITableDemo {
	@Resource(name = "reportService")
	private ReportService reportservice;

	@RequestMapping("index")
	public ModelAndView index(ModelMap modelMap) {
		Object grid = reportservice.render("1001", new HashMap<String, Object>());
		modelMap.put("grid", grid);
		return new ModelAndView("demo/easyui", modelMap);
	}

	@RequestMapping("datagrid_data")
	@ResponseBody
	public Map<String, Object> datagrid_data() {
		Map<String, Object> returnData = new HashMap<String, Object>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> colmeta = new HashMap<String, Object>();
		// for (int i = 0; i < 3; i++) {
		Map<String, Object> row1 = new HashMap<String, Object>();
		row1.put("STAFF_ID", "code" + 1);
		colmeta.put("STAFF_ID", "long");
		row1.put("CUST_ID", "name" + 1);
		colmeta.put("CUST_ID", "long");
		row1.put("UPDATE_TIME", "price" + 1);
		row1.put("state", "closed");
		
		//row1.put("_parentId", null);

		colmeta.put("CUST_ID", "date");
		data.add(row1);
		//

		Map<String, Object> row2 = new HashMap<String, Object>();
		row2.put("STAFF_ID", "code" + 2);
		colmeta.put("STAFF_ID", "long");
		row2.put("CUST_ID", "name" + 2);
		colmeta.put("CUST_ID", "long");
		row2.put("UPDATE_TIME", "price" + 2);
		colmeta.put("CUST_ID", "date");
		row2.put("_parentId", "name1");		
		//data.add(row2);

		Map<String, Object> row3 = new HashMap<String, Object>();
		row3.put("STAFF_ID", "code" + 3);
		colmeta.put("STAFF_ID", "long");
		row3.put("CUST_ID", "name" + 3);
		colmeta.put("CUST_ID", "long");
		row3.put("UPDATE_TIME", "price" + 3);
		colmeta.put("CUST_ID", "date");
		row3.put("_parentId", "name2");	
		//data.add(row3);
		// }
		returnData.put("rows", data);
		returnData.put("total", 3);
		returnData.put("colmeta", colmeta);
		return returnData;
	}
}
