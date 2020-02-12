package com.asiainfo.edata.dao;

import java.util.List;

import com.asiainfo.edata.meta.EDatasetCategory;
import com.asiainfo.eframe.dao.BaseDao;

public interface DatasetCategoryDao extends BaseDao {
	/**
	 * 获取所有数据分类
	 * @return
	 */
	public List<EDatasetCategory> getAllCategorys();
	/**
	 * 获取所有有数据集的分类和他的上级分类
	 * @return
	 */
	public List<EDatasetCategory> getAllCategorysHaveDataset(String category,String name);
	public List<EDatasetCategory> getAllCategorysHaveDataset(String staffId);
}
