package com.asiainfo.ereport.excel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.asiainfo.eframe.excel.GridCol;
import com.asiainfo.eframe.excel.GridHead;
import com.asiainfo.eframe.excel.GridInfo;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridColMeta;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridMeta;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 根据UiGrid配置生成gridInfo对象
 * 
 * @author baowzh
 *
 */

class GridInfoHelper {
	public static GridInfo createExcelGridInfo(UIGridMeta uiGrid) {
		GridInfo gridInfo = new GridInfo();
		List<UIGridColMeta> uicols = uiGrid.getCols();
		List<GridCol> gridCols = new ArrayList<GridCol>();
		for (UIGridColMeta col : uicols) {
			GridCol gridCol = new GridCol();
			try {
				BeanUtils.copyProperties(gridCol, col);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gridCols.add(gridCol);
		}
		gridInfo.setCols(gridCols);
		gridInfo.setRowSummery(uiGrid.getRowSummary());
		gridInfo.setRowSummeryConfig(uiGrid.getRowSummaryConfig());
		String headjson = uiGrid.getHeader();
		JSONObject a = JSONObject.fromObject(headjson);
		List<GridHead> allheas = new ArrayList<GridHead>();

		//
		String frozenheader = uiGrid.getFrozenheader();
		int startcolindex = 0;
		if (frozenheader != null && !frozenheader.equalsIgnoreCase("")) {
			JSONObject frozenheadera = JSONObject.fromObject(frozenheader);

			JSONArray fjsonArrays = JSONArray.fromObject(frozenheadera.get("head"));

			for (int i = 0; i < fjsonArrays.size(); i++) {
				JSONArray array = fjsonArrays.getJSONArray(i);
				GridHead heads[] = (GridHead[]) JSONArray.toArray(array, GridHead.class);
				for (int k = 0; k < fjsonArrays.getJSONArray(i).size(); k++) {
					heads[k].setRowindex(i + 1);
					heads[k].setColindex(k + 1);
				}
				allheas.addAll(Arrays.asList(heads));
			}
		}
		
		JSONArray jsonArrays1 = JSONArray.fromObject(a.get("head"));
		for (int i1 = 0; i1 < jsonArrays1.size(); i1++) {
			if(allheas!=null){
				startcolindex=getFrozenRowobjectnum(allheas,i1+1);
			}
			JSONArray array1 = jsonArrays1.getJSONArray(i1);
			GridHead heads1[] = (GridHead[]) JSONArray.toArray(array1, GridHead.class);
			for (int k1 = 0; k1 < jsonArrays1.getJSONArray(i1).size(); k1++) {
				heads1[k1].setRowindex(i1 + 1);
				heads1[k1].setColindex(startcolindex + k1 + 1);
			}
			allheas.addAll(Arrays.asList(heads1));
		}
		gridInfo.setHeads(allheas);
		return gridInfo;

	}
	public static int getFrozenRowobjectnum(List<GridHead> heads,int rownum){
		int i=0;
		for(GridHead gh:heads){
			if(gh.getRowindex()==rownum) i++;
		}
		return i;
	}
}
