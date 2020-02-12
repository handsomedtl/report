package com.asiainfo.ereport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.echarts.ECharts;


@Controller
@RequestMapping("echartsdemo")
public class EchartsDemo {
	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("demo/echarts");
	}

	@RequestMapping("echarts_option")
	@ResponseBody
	public String sendOption() {
		ECharts reportCharts = new ECharts();
		return reportCharts.handleData(null);
	}
}
