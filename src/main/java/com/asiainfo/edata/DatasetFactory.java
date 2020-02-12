package com.asiainfo.edata;

import com.asiainfo.edata.meta.EDatasetMeta;

public interface DatasetFactory {
	/**
	 * 根据数据集id生成数据集对象
	 * @param datasetid
	 * @return
	 */
	public <E,P> EDataset<E, P> createDatasetFromConfig(String datasetid);
	/**
	 * 根据数据集描述对象生成数据集
	 * @param datasetMeta
	 * @return
	 */
	public <E,P> EDataset<E, P> createDataset(EDatasetMeta datasetMeta);
}
