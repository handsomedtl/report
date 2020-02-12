package com.asiainfo.edata;

import com.asiainfo.edata.dataset.CustomBeanDataset;
import com.asiainfo.edata.dataset.ProcudureDataset;
import com.asiainfo.edata.dataset.SqlSourceDataset;
import com.asiainfo.edata.meta.DatasetMetaService;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.eframe.dao.QueryDataset;

public class DefaultDatasetFactory implements DatasetFactory {

	private DatasetMetaService datasetMetaService;
	private QueryDataset queryDataset;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EDataset createDatasetFromConfig(String datasetid) {
		EDatasetMeta datasetMeta = datasetMetaService.getDatadetConfig(datasetid);
		if (EStaticConstant.DATASET_TYPE_SQL == datasetMeta.getType()) {
			return new SqlSourceDataset(datasetMeta, queryDataset);
		} else if (EStaticConstant.DATASET_TYPE_PROCUDURE == datasetMeta.getType()) {
			return new ProcudureDataset(datasetMeta, queryDataset);
		} else if (EStaticConstant.DATASET_TYPE_CUSTOMBEAN == datasetMeta.getType()) {
			return new CustomBeanDataset(datasetMeta);
		} else {
			return new SqlSourceDataset(datasetMeta, queryDataset);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public EDataset createDataset(EDatasetMeta datasetMeta) {

		if (EStaticConstant.DATASET_TYPE_SQL == datasetMeta.getType()) {
			return new SqlSourceDataset(datasetMeta, queryDataset);
		} else if (EStaticConstant.DATASET_TYPE_PROCUDURE == datasetMeta.getType()) {
			return new ProcudureDataset(datasetMeta, queryDataset);
		} else if (EStaticConstant.DATASET_TYPE_CUSTOMBEAN == datasetMeta.getType()) {
			return new CustomBeanDataset(datasetMeta);
		} else {
			return new SqlSourceDataset(datasetMeta, queryDataset);
		}
	}

	public DatasetMetaService getDatasetMetaService() {
		return datasetMetaService;
	}

	public void setDatasetMetaService(DatasetMetaService datasetMetaService) {
		this.datasetMetaService = datasetMetaService;
	}

	public QueryDataset getQueryDataset() {
		return queryDataset;
	}

	public void setQueryDataset(QueryDataset queryDataset) {
		this.queryDataset = queryDataset;
	}

}
