package com.asiainfo.edata.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.edata.dao.DatasetConditionDao;
import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
import com.asiainfo.eframe.util.StaticConstant;

@Repository("datasetConditionDao")
public class DatasetConditionDaoImpl extends MybatisBaseDao implements DatasetConditionDao {

	@Override
	public List<ConditionMeta> getConditionMeta(String datasetId) {
		return this.getData(ConditionMeta.class, datasetId,StaticConstant.DEFAULTEPARCHYROUTE,null);
	}
	@Override
	public  int insertConditionMeta(ConditionMeta conditionMeta){
		return this.insert(ConditionMeta.class, conditionMeta);
	}

}
