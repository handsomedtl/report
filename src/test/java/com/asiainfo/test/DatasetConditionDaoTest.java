package com.asiainfo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.edata.EData;
import com.asiainfo.edata.EDataset;
import com.asiainfo.edata.dataset.ParseResult;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.eframe.entity.UserInfo;
import com.asiainfo.ereport.service.CustomReportService;
import com.asiainfo.ereport.service.DepartLevelComparator;
import com.asiainfo.ereport.service.ReportService;

public class DatasetConditionDaoTest extends BaseTest {
	@Resource(name = "datasetFactory")
	private DatasetFactory datasetFactory;
	@Resource(name = "reportService")
	private ReportService reportService;
	@Resource(name = "departLevelComparator")
	private DepartLevelComparator departLevelComparator;
	@Resource(name = "customReportService")
	CustomReportService customReportService;

	@Test
	public void testsql() throws Exception {
		String matchString="select name from user where id={id}";
		matchString=matchString.replaceAll("\\{\\w+\\}", "aaa");
		System.out.println(matchString);
		
		EDatasetMeta datasetMeta=new EDatasetMeta();
		File file = new File("E:/reportsql.txt");
		FileInputStream stream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(stream);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(reader);
		String line;
		while (null != (line = br.readLine())) {
			sb.append(line);
		}
		br.close();
		datasetMeta.setContent(sb.toString());
		  EDataset<EData<String, Object>, Map<String, Object>> dataset =
		  datasetFactory.createDataset(datasetMeta);
		  
		  ParseResult parseResult=dataset.parseAndPreeview();
		  parseResult.getConditions();
		 

	}

	public void testprocedure() throws Exception {
		Map<String, Integer> stringIntegerMap = new HashMap<String, Integer>();
		stringIntegerMap.put("one", 1);
		stringIntegerMap.put("two", 2);
		stringIntegerMap.put("three", 3);
		stringIntegerMap.put("tooMany", 30);

		EvaluationContext evaluationContext = new StandardEvaluationContext();
		evaluationContext.setVariable("map", stringIntegerMap);
		UserInfo userinfo = new UserInfo();
		userinfo.setDepartid("111");
		evaluationContext.setVariable("user", userinfo);

		ExpressionParser expressionParser = new SpelExpressionParser();
		Map<String, Integer> resultStringIntegerMap = expressionParser.parseExpression("#map.$[value<27]")
				.getValue(evaluationContext, Map.class);
		String obj = expressionParser.parseExpression("#user.departid").getValue(evaluationContext, String.class);
		String obj1 = expressionParser.parseExpression("aaaa").getValue(evaluationContext, String.class);
		System.out.println("input: " + stringIntegerMap);
		System.out.println("result: " + resultStringIntegerMap);

	}

}
