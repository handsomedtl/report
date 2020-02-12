package com.asiainfo.edata.dao;

import java.util.List;

import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.eframe.dao.BaseDao;

public interface DatasetConditionDao extends BaseDao {
public  List<ConditionMeta> getConditionMeta(String datasetId);
public  int insertConditionMeta(ConditionMeta conditionMeta);

}
