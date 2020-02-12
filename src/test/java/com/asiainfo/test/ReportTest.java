package com.asiainfo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.annotation.Resource;

import org.junit.Test;

import com.asiainfo.ereport.meta.CustomReportBindField;
import com.asiainfo.ereport.meta.CustomReportCondition;
import com.asiainfo.ereport.meta.CustomReportGroupField;
import com.asiainfo.ereport.meta.CustomReportMeta;
import com.asiainfo.ereport.service.CustomReportService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ReportTest extends BaseTest {
	@Resource(name = "customReportService")
	CustomReportService customReportService;

	@Test
	public void saveReport() throws Exception {
		File file = new File("E:/report.json");
		FileInputStream stream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(stream);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(reader);
		String line;
		while (null != (line = br.readLine())) {
			sb.append(line);
		}
		br.close();
		JSONObject jsonObject = JSONObject.fromObject(sb.toString());
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
			customReportService.saveReportConfig(customReportMeta);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
