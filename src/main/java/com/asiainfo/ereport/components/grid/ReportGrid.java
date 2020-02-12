package com.asiainfo.ereport.components.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.edata.EData;
import com.asiainfo.edata.EDataset;
import com.asiainfo.eframe.dataset.EDataList;
import com.asiainfo.ereport.components.ReportSubViewUIElement;
import com.asiainfo.ereport.components.impl.AbstractReportElement;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ewebframe.ui.UIElementTypes;
import com.asiainfo.ewebframe.ui.exception.InvalidConfigException;
import com.asiainfo.ewebframe.ui.grid.UIGrid;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridColMeta;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridMeta;

import jodd.bean.BeanUtil;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 报表grid
 * 
 * @author baowzh
 * @param <E>
 *
 */
public class ReportGrid extends AbstractReportElement implements ReportSubViewUIElement {
	/**
	 * grid 配置
	 */
	private UIGridMeta gridMeta;
	/**
	 * 数据集id
	 */
	private String datasetid;
	/**
	 * 内置gird 对象
	 */
	private UIGrid grid;

	private String subDatasetId;
	private ResourceBundle bundler = ResourceBundle.getBundle("reportConfig");

	protected ReportGrid(ReportComponentMeta componentMeta, UIGridMeta gridMeta) {
		super(componentMeta);
		this.gridMeta = gridMeta;

		init();
	}

	public ReportGrid(ReportComponentMeta componentMeta, UIGridMeta gridMeta, DatasetFactory datafactory) {
		super(componentMeta, datafactory);
		this.gridMeta = gridMeta;
		// this.datasetid = componentMeta.getDatasetId();
		init();
	}

	private void init() {
		this.datasetid = this.getComponentMeta().getDatasetId();
		this.subDatasetId = gridMeta.getSubDataSrc();
		if (StringUtil.isBlank(subDatasetId))
			this.subDatasetId = datasetid;

		String load_data_addr = this.bundler.getString("report_load_data_addr");
		String page_data_addr = this.bundler.getString("report_page_data_addr");
		String load_child_addr = this.bundler.getString("report_load_child_addr");
		String report_export_addr = this.bundler.getString("report_export_addr");
		if (this.gridMeta.isPaging()) {
			gridMeta.setDataSrc(this.getComponentMeta().getReportId() + "/" + this.getComponentMeta().getCompId() + "/"
					+ page_data_addr);
		} else {
			gridMeta.setDataSrc(this.getComponentMeta().getReportId() + "/" + this.getComponentMeta().getCompId() + "/"
					+ load_data_addr);
		}
		gridMeta.setExpurl(this.getComponentMeta().getReportId() + "/" + this.getComponentMeta().getCompId() + "/"
				+ report_export_addr);
		gridMeta.setSubDataSrc(this.getComponentMeta().getReportId() + "/" + this.getComponentMeta().getCompId() + "/"
				+ load_child_addr);
		grid = new UIGrid(gridMeta);
	}

	public UIGridMeta getGridMeta() {
		return gridMeta;
	}

	public String getDatasetid() {
		return datasetid;
	}

	@Override
	public Object render(Map params) throws InvalidConfigException {
		return grid.render(params);
	}

	@Override
	public Object renderview(Map params) throws InvalidConfigException {

		return grid.renderview(params);
	}

	@Override
	public String getComponentid() {

		return gridMeta.getId();
	}

	@Override
	public String getComponentType() {

		return UIElementTypes.UI_ELEMENT_TYPE_GRID;
	}

	@Override
	public String getTypeString() {
		return UIElementTypes.UI_ELEMENT_TYPE_GRID;
	}

	@Override
	public Object handleData(EDataList data, Map param) {
		Map<String, Object> returnData = new HashMap<String, Object>();

		if (this.getGridMeta().getType() == UIElementTypes.GRID_TYPE_TREEGRID) {
			handleTreeData(data);
		}

		returnData.put("rows", data.toArray());
		returnData.put("total", data.total());
		returnData.put("colmeta", data.getFieldmetas());
		switch (this.getGridMeta().getRowSummary()) {
		case UIGridMeta.ROW_SUMMARY_EACH_PAGE:
			
			returnData.put("footer", getSum(param));
			break;
		default:

		}

		return returnData;
	}

	@Override
	public String getSubDataSrc() {
		return this.subDatasetId;
	}

	@Override
	public Object handleSubData(EDataList data, Map params) {
		return handleData(data, params);
	}

	protected void handleTreeData(EDataList data) {

		if(data==null){
			return ;
		}
		for (Object d : data) {
			EData dd = (EData) d;
			if (this.gridMeta.getParentField() != null) {
				String parentField = this.gridMeta.getParentField().toUpperCase();
				dd.put("parentId", dd.containsKey(parentField) ? dd.get(parentField) : "0");
			}

			if (this.gridMeta.getHasSubField() != null) {
				String stateField = this.gridMeta.getHasSubField().toUpperCase();
				dd.put("state",
						(dd.containsKey(stateField) && "0".equals(dd.get(stateField).toString()) ? "open" : "closed"));
			} else if (this.gridMeta.getHasSubField() == null && !dd.containsKey("state")) {
				dd.put("state", "closed");
			}

		}

	}

	@Override
	public Object dataforInnerReport(String parentId, Map params) {
		String ds = this.getSubDataSrc();
		params.put("_parentId", parentId);
		if (StringUtil.isNotBlank(ds) && this.dataFactory != null) {
			EDataset mainds = this.dataset;
			try {
				this.dataset = dataFactory.createDatasetFromConfig(ds);
				return handleSubData(dataset.load(params), params);

			} finally {
				this.dataset = mainds;
			}
		}
		return null;
	}

	@Override
	protected Object handlePageData(EDataList data, Map param, Integer pageindex, Integer pagesize) {
		Object returnObj = this.handleData(data, param);

		if (UIGridMeta.ROW_SUMMARY_LAST_PAGE == this.gridMeta.getRowSummary()
				&& (data.total()<=pageindex*pagesize)) {
			((Map) returnObj).put("footer", getSum(param));
		}
		else{
			((Map) returnObj).put("footer", new HashMap());
		}
		return returnObj;
	}

	public List getSum(Map param){
		List ls= new ArrayList();
		Object sum = this.getDataset().sum(param);
		EData<String, Object> sumdata = (EData<String, Object>)sum;
		
		//有个性话配置则根据配置扩展
		if(StringUtil.isNotBlank(this.gridMeta.getRowSummaryConfig())){
			JSONArray config = JSONArray.fromObject(this.gridMeta.getRowSummaryConfig().toUpperCase());
			for(int i=0; i<config.size();i++){
				JSONObject o= config.getJSONObject(i);
				EData<String,Object> data = new EData<String,Object>();
				for(UIGridColMeta meta :this.gridMeta.getCols()){
					if(o.containsKey(meta.getField())){
						if(sumdata.containsKey(o.getString(meta.getField()).trim())){
							data.put(meta.getField(), sumdata.get(o.getString(meta.getField()).trim()));
						}else{
							data.put(meta.getField(), o.get(meta.getField()));
						}
					}else{
						data.put(meta.getField(), null);
					}
				}
				
				ls.add(data);
			}
			
			
		}else{
		
			//默认处理
			String field = this.gridMeta.getCols().get(0).getField();
			BeanUtil.setProperty(sum, field, "合计");
			ls.add(sum);
		}
		for(Object o:ls){
			BeanUtil.setProperty(o, "_footer", true);
		}
		return ls;
	}
}
