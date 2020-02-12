package com.asiainfo.echarts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;


import com.asiainfo.echarts.meta.EChartsMeta;
import com.asiainfo.echarts.option.axis.CategoryAxis;
import com.asiainfo.echarts.option.axis.ValueAxis;
import com.asiainfo.echarts.option.code.Magic;
import com.asiainfo.echarts.option.code.Tool;
import com.asiainfo.echarts.option.data.Data;
import com.asiainfo.echarts.option.feature.MagicType;
import com.asiainfo.echarts.option.series.Bar;
import com.asiainfo.echarts.option.series.Line;
import com.asiainfo.echarts.option.series.Pie;
import com.asiainfo.echarts.option.series.Series;
import com.asiainfo.echarts.util.EnhancedOption;
import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.eframe.dataset.EDataList;
import com.asiainfo.eframe.service.exception.ServiceException;
import com.asiainfo.ewebframe.ui.config.UIContext;
import com.asiainfo.ewebframe.ui.form.UITemplateEngine;

import freemarker.template.TemplateNotFoundException;

public class ECharts {
	/*
	 * Echarts Option 对象
	 */
	private EnhancedOption option;
	/*
	 * 图表标题
	 */
	private String title = "图表";
	/*
	 * 图表类型。 这里一个charts组件只支持一种类型图
	 */
	private String type = "bar";
	/*
	 * 是否要显示工具栏.折线、柱状图有 【保存为图片】【折线柱状切换】【数据显示】【还原】按钮。 饼状图有【保存为图片】【数据显示】【还原】按钮
	 */
	private Boolean toolbox = true;
	/*
	 * y轴名称
	 */
	private String yAxisName = "y轴";
	/*
	 * y轴单位
	 */
	private String yAxisUnit = "";
	/*
	 * y轴最大值
	 */
	private String yAxisMax;
	/*
	 * y轴最小值
	 */
	private String yAxisMin;
	/*
	 * 需要分类用列 例子： {xAxisField："month",categoryField："area"}
	 */
	private Map<String, String> catCol = new HashMap<String, String>();
	/*
	 * 需要输出的数据列 例子： ["fee3G","fee4G"]
	 */
	private List<String> dataCol = new ArrayList<String>();
	
	
	/*
	 * x轴刻度标签
	 */
	private List<String> xAxisData;
	/*
	 * 数据源
	 */
	private List<Map<String, Object>> dataSource;
	/*
	 * 系列名称,图例
	 */
	private List<String> seriesName;
	/*
	 * 列描述 例子： {manth:"月份",fee3G:"3G收入",fee4G:"4G收入",area:"地区"}
	 */
	private Map<String, String> colSet;

	/*
	 * 数据系列。 bar/line 图用数据系列
	 */
	private Map<String, List<Object>> seriesData;

	/*
	 * x轴刻度归类的数据系列。适合用Pie 图
	 */
	private Map<String, HashMap<String, Object>> xAxisCategoryData;


	@Resource(name = "datasetFactory")
	private DatasetFactory datasetFactory;

	public ECharts(EChartsMeta chartsMeta) {
       this.init(chartsMeta);
	}

	public ECharts() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> row = new HashMap<String, Object>();
		row.put("month", "1月");	row.put("area", "呼市");	row.put("fee3G", "11.0");row.put("fee4G", "12.0");
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("month", "1月");	row.put("area", "包头");	row.put("fee3G", "13.0");row.put("fee4G", "14.0");
		data.add(row);

		row = new HashMap<String, Object>();
		row.put("month", "2月");	row.put("area", "呼市");	row.put("fee3G", "23.0");row.put("fee4G", "24.0");
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("month", "2月");	row.put("area", "包头");	row.put("fee3G", "25.0");row.put("fee4G", "26.0");
		data.add(row);

		row = new HashMap<String, Object>();
		row.put("month", "3月");	row.put("area", "呼市");	row.put("fee3G", "33.0");row.put("fee4G", "34.0");
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("month", "3月");	row.put("area", "包头");	row.put("fee3G", "35.0");row.put("fee4G", "36.0");
		data.add(row);

		row = new HashMap<String, Object>();
		row.put("month", "5月"); row.put("area", "呼市");row.put("fee3G", "53.0");row.put("fee4G", "54.0");
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("month", "5月");	row.put("area", "包头");	row.put("fee3G", "55.0");row.put("fee4G", "56.0");
		data.add(row);
		
		this.dataSource = data;

		List<String> _dataCol = new ArrayList<String>();
		_dataCol.add("fee3G|3G费");
		_dataCol.add("fee4G|4G费");
		this.dataCol = _dataCol;

		Map<String, String> _catCol = new HashMap<String, String>();
		_catCol.put("xAxisField", "month|月份");
		_catCol.put("categoryField", "area|地区");
		this.catCol = _catCol;
	}
	private void init(EChartsMeta chartsMeta){
		 this.title=chartsMeta.getTitle();
		 
		 Map<String,String> _colSet = new HashMap<String,String>();
		 String[] arrCategoryFields = chartsMeta.getCategoryFields().split(",");
		 for(Integer i=0;i<arrCategoryFields.length;i++){
			 String[] keyVal = arrCategoryFields[i].split("\\|");
			 _colSet.put(keyVal[0], keyVal[1]);
			 if(i==0){
				 this.catCol.put("xAxisField", keyVal[0]);
			 }else{
				 this.catCol.put("area", keyVal[0]);
			 }
		 }
		 String[] arrDataFields = chartsMeta.getDataFields().split(",");
		 for(Integer i=0;i<arrDataFields.length;i++){
			 String[] keyVal = arrDataFields[i].split("\\|");
			 _colSet.put(keyVal[0], keyVal[1]);
			 this.dataCol.add(keyVal[0]);
		 }
		 this.colSet = _colSet;
		 //setColSet(_colSet);
		 this.toolbox = chartsMeta.getShowToolbox();
		 this.type = chartsMeta.getChartsType();
		 this.yAxisMax = chartsMeta.getyAxisMax();
		 this.yAxisMin = chartsMeta.getyAxisMin();
		 this.yAxisName = chartsMeta.getyAxisName();
		 this.yAxisUnit = chartsMeta.getyAxisUnit();

	}
   

	/*
	 * 根据colSet中指定的列集合，从dataSource解析数据生成 图表用数据系列。 并生成图例 seriesName
	 */
	public Map<String, List<Object>> _handleData( List<Map<String, Object>> dataSource) {
		
		Map<String, HashMap<String, Object>> monthRows = null;
		List<String> fields = new ArrayList<String>();
		List<String> xAxisLabels = new ArrayList<String>();
		if (dataCol.size() > 0 && dataSource.size() > 0) {
			monthRows = new HashMap<String, HashMap<String, Object>>();
			for (Integer i = 0; i < dataSource.size(); i++) {
				Map<String, Object> dataSourceI = dataSource.get(i);// 原数据中的一行
				
				if (!xAxisLabels.contains(dataSourceI.get(catCol.get("xAxisField")))) {
					xAxisLabels.add((String) dataSourceI.get(catCol.get("xAxisField")));
				}
				
				if (null == monthRows.get(dataSourceI.get(catCol.get("xAxisField")))) {
					Map<String, Object> monthRow = new HashMap<String, Object>();
					for (Integer dci = 0; dci < dataCol.size(); dci++) {
						String fieldKey = colSet.get(dataCol.get(dci));
						if (null != catCol.get("categoryField")) {
							fieldKey = dataSourceI.get(catCol.get("categoryField")) + fieldKey;
						}
						if (!fields.contains(fieldKey)) {
							fields.add(fieldKey);
						}
						monthRow.put(fieldKey,dataSourceI.get(dataCol.get(dci)));
					}
					monthRows.put((String) dataSourceI.get(catCol.get("xAxisField")),(HashMap<String, Object>) monthRow);
				} else {
					Map<String, Object> monthRowsI = monthRows.get(dataSourceI.get(catCol.get("xAxisField")));
					for (Integer dci = 0; dci < dataCol.size(); dci++) {
						String fieldKey = colSet.get(dataCol.get(dci));
						if (null != catCol.get("categoryField")) {
							fieldKey = dataSourceI.get(catCol.get("categoryField")) + fieldKey;
						}
						if (!fields.contains(fieldKey)) {
							fields.add(fieldKey);
						}
						monthRowsI.put(fieldKey, dataSourceI.get(dataCol.get(dci)));
					}
				}
			}
		}
		// 保存x轴标签数据
		this.xAxisData = xAxisLabels;
		//setxAxisData(xAxisLabels);
		
		// 保存图例即数据系列名
		//setSeriesName(fields);
		this.seriesName = fields;

		// 保存数据
		//setxAxisCategoryData(monthRows);
		this.xAxisCategoryData = monthRows;

		// 继续处理，行列转换. 生成列为单位的echarts数据系列
		Map<String, List<Object>> _seriesData = new HashMap<String, List<Object>>();
		for (Integer i = 0; i < this.seriesName.size(); i++) {
			_seriesData.put(this.seriesName.get(i), new ArrayList<Object>());
		}
		for(Integer x=0;x<this.xAxisData.size();x++){
			String month = this.xAxisData.get(x);
			HashMap v = (HashMap) monthRows.get(month);
			for (Integer i = 0; i < this.seriesName.size(); i++) {
				String sName = this.seriesName.get(i);
				if (null == v.get(this.seriesName.get(i))) {
					_seriesData.get(sName).add("0");
				} else {
					_seriesData.get(sName).add(v.get(this.seriesName.get(i)));
				}
			}
		}
		this.seriesData = _seriesData;
		//setSeriesData(_seriesData);
		return _seriesData;
	}

	// @SuppressWarnings("null")
	public String handleData(EDataList dDataList) {
		// 数据处理
		Map<String, List<Object>> data = _handleData(dDataList);
		ResourceBundle bundler = ResourceBundle.getBundle("reportConfig");
		String echart_grid_containLabel = bundler.getString("echart_grid_containLabel");
		String echart_axisLabel_rotate = bundler.getString("echart_axisLabel_rotate");
		String echart_axisLabel_interval = bundler.getString("echart_axisLabel_interval");
		
		EnhancedOption option = new EnhancedOption();
		/*
		 * <<< 如有系统默认配置，在其他配置之前配置 >>>
		 */
		if (! "".equals(title) && null != title) {
			option.title().text(title);
		}
		//grid 中是否包含刻度标签
		if(null!=echart_grid_containLabel && !"false".equalsIgnoreCase(echart_grid_containLabel)){
			option.grid().containLabel(true);
		}
		
		// 设置图例
		List<String> listLegend = new ArrayList<String>();
		for (Integer ii = 0; ii < this.seriesName.size(); ii++) {
			listLegend.add(this.seriesName.get(ii));
		}
		option.legend(listLegend.toArray());

		if ("pie".equalsIgnoreCase(type)) {
			// 设置数据系列
			List<Series> series = new ArrayList<Series>();

			List<Object> seriesElements; // = new ArrayList<Object>();
			Iterator<?> iterData = this.xAxisCategoryData.entrySet().iterator();
			Integer whileIndex = 0;
			while (iterData.hasNext()) {
				seriesElements = new ArrayList<Object>();
				Map.Entry entry = (Map.Entry) iterData.next();
				String kMonth = (String) entry.getKey();
				HashMap<String, String> v = (HashMap<String, String>) entry.getValue();
				Iterator iterV = v.entrySet().iterator();
				while (iterV.hasNext()) {
					Map.Entry entryV = (Map.Entry) iterV.next();
					seriesElements.add(new Data((String) entryV.getKey(),
							entryV.getValue()));
				}
				Pie pie = new Pie(kMonth);
				pie.radius(
						90 * whileIndex / this.xAxisCategoryData.size() + "%",
						90 * (whileIndex + 1) / this.xAxisCategoryData.size()
								+ "%").data(seriesElements.toArray());
				pie.label().normal().show(false);
				pie.label().emphasis().show(true);
				series.add(pie);
				whileIndex++;
			}
			option.series(series);
			// 设置提示框
			option.tooltip().show(true).formatter("{a} <br/>{b} : {c} ({d}%)");

			// 设置工具箱
			if (toolbox) {
				option.toolbox().show(true)
						.feature(Tool.dataView, Tool.restore, Tool.saveAsImage);
			}
		} else if ("bar".equalsIgnoreCase(type) || "line".equalsIgnoreCase(type)) {
			// 设置x轴
			CategoryAxis xAxis = new CategoryAxis();

			xAxis.name(colSet.get(catCol.get("xAxisField")));
			
			xAxis.axisLabel().rotate(-30).interval(0);
			if(null!=echart_axisLabel_rotate && !"".equalsIgnoreCase(echart_axisLabel_rotate)){
				xAxis.axisLabel().rotate(Integer.parseInt(echart_axisLabel_rotate));
			}
			if(null!=echart_axisLabel_interval && !"auto".equalsIgnoreCase(echart_axisLabel_interval)){
				xAxis.axisLabel().interval(Integer.parseInt(echart_axisLabel_interval));
			}
			xAxis.data(this.xAxisData);
			option.xAxis(xAxis);

			// 设置y轴
			ValueAxis yAxis = new ValueAxis();
			if (yAxisName != null) {
				yAxis.name(yAxisName);
			}
			if (yAxisUnit != null) {
				yAxis.axisLabel().formatter("{value} " + yAxisUnit);
			}
			if (yAxisMax != null) {
				yAxis.max(yAxisMax);
			}
			if (yAxisMin != null) {
				yAxis.min(yAxisMin);
			}
			option.yAxis(yAxis);

			// 设置数据系列
			List<Series> series = new ArrayList<Series>();
			for (Integer i = 0; i < data.size(); i++) {
				List<Object> dd = new ArrayList<Object>();
				for (Integer j = 0; j < data.get(this.seriesName.get(i)).size(); j++) {
					dd.add(data.get(this.seriesName.get(i)).get(j));
				}
				if ("line".equalsIgnoreCase(type)) {
					series.add(new Line(this.seriesName.get(i)).data(dd.toArray()));
				} else {
					series.add(new Bar(this.seriesName.get(i)).data(dd.toArray()));
				}
			}
			option.series(series);
			// 设置提示框
			option.tooltip().show(true).formatter("{a} <br/>{b} : {c}");
			// 设置工具箱
			if (toolbox) {
				option.toolbox()
						.show(true)
						.feature(
								Tool.dataView,
								new MagicType(Magic.line, Magic.bar).show(true),
								Tool.restore, Tool.saveAsImage);
			}
		}
		return option.toString();
	}

	public String render(Map params) {
		String result = new String();
		
		params.put("loadDataUrl", params.get("reportid")+"/"+params.get("componentid")+"/loadData.jhtml");
		params.put("echartsContainer","echarts-"+params.get("reportid")+"-"+params.get("componentid"));
		// 预设布局渲染，先按布局ID 从模板目录下的layout目录查
		try {
			result = UITemplateEngine.renderTemplate(UIContext.getConfig().getChartTemplate("echarts.html"), params);
		} catch (ServiceException e) {
			throw new RuntimeException("图表渲染异常，请联系管理员！");
		} catch (TemplateNotFoundException e) {
			// @TODO 去布局数据库中的内容渲染
			e.printStackTrace();
		}
		
		return result;

	}

	/*
	 * 初始化一个Option，仅设置数据系列部分
	 */
	public void setSeries(EnhancedOption option) {

	}

	public EnhancedOption getOption() {
		if (option == null) {
			this.option = new EnhancedOption();
		}
		return this.option;
	};

}
