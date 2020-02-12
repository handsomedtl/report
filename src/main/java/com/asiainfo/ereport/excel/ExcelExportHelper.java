package com.asiainfo.ereport.excel;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asiainfo.edata.EData;
import com.asiainfo.eframe.dataset.Dataset;
import com.asiainfo.eframe.excel.CustomrizeExp;
import com.asiainfo.eframe.excel.CustomrizeExpImpl;
import com.asiainfo.eframe.excel.GridInfo;
import com.asiainfo.eframe.excel.SinggleFieldData;
import com.asiainfo.ewebframe.dao.UIGridMetaDao;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridMeta;

@Service("excelExportHelper")
public class ExcelExportHelper {
	@Resource(name = "uiGridMetaDao")
	private UIGridMetaDao gridDao;
	public byte[] export(String title, String gridid, Dataset<EData<String, Object>, Map<String, Object>> dataset,
			Map<String, Object> params, List<SinggleFieldData> fieldDatds) {
		CustomrizeExp<EData<String, Object>, Map<String, Object>> exp = new CustomrizeExpImpl<EData<String, Object>, Map<String, Object>>();
		UIGridMeta uiGrid = gridDao.getUiGrid(gridid);
		GridInfo gridInfo = GridInfoHelper.createExcelGridInfo(uiGrid);
		return exp.exportToExcel(title, gridInfo, dataset, 65000, params, fieldDatds);
	}

	public byte[] export(String title, UIGridMeta uiGrid, Dataset<EData<String, Object>, Map<String, Object>> dataset,
			Map<String, Object> params, List<SinggleFieldData> fieldDatds) {
		CustomrizeExp<EData<String, Object>, Map<String, Object>> exp = new CustomrizeExpImpl<EData<String, Object>, Map<String, Object>>();
		GridInfo gridInfo = GridInfoHelper.createExcelGridInfo(uiGrid);
		return exp.exportToExcel(title, gridInfo, dataset, 65000, params, fieldDatds);
	}
}
