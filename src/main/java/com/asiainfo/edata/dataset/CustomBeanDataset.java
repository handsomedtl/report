package com.asiainfo.edata.dataset;

import java.util.Map;

import com.asiainfo.edata.EData;
import com.asiainfo.edata.exception.ErrorDatasetDefineException;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.eframe.dataset.EDataList;

public class CustomBeanDataset extends AbstractDataset {

	public CustomBeanDataset(EDatasetMeta meta) {
		super(meta);
	}

	@Override
	protected EDataList<EData<String, Object>> listData(Map<String, Object> param) {
		
		return null;
	}

	@Override
	protected EDataList<EData<String, Object>> listPage(Map<String, Object> param, int offset, int limit) {
		
		return null;
	}

	@Override
	public ParseResult parseAndPreeview() throws ErrorDatasetDefineException {
		
		return null;
	}

	@Override
	protected Long getcount(Map<String, Object> param) {
		
		return null;
	}

	@Override
	protected EData<String, Object> getSummaryRow(Map<String, Object> param) {
	
		return null;
	}

}
