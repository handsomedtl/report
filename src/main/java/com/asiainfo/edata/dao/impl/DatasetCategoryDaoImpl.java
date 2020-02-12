package com.asiainfo.edata.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.edata.dao.DatasetCategoryDao;
import com.asiainfo.edata.meta.EDatasetCategory;
import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
@Repository("datasetCategoryDao")
public class DatasetCategoryDaoImpl extends MybatisBaseDao implements DatasetCategoryDao {

	@Override
	public List<EDatasetCategory> getAllCategorys() {

		return this.getData(EDatasetCategory.class, new HashMap<String, Object>());
	}

	@Override
	public List<EDatasetCategory> getAllCategorysHaveDataset(String category,String name) {
		Map map=new HashMap();
		map.put("category",category);
		map.put("name",name);
		return this.getData("getHaveDatasetCategorys", map);
	}
	@Override
	public List<EDatasetCategory> getAllCategorysHaveDataset(String staffId) {
		Map map=new HashMap();
		map.put("staffId",staffId);
		return this.getData("getHaveDatasetCategorys", map);
	}
}
