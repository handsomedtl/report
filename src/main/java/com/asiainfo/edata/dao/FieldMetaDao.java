package com.asiainfo.edata.dao;

import com.asiainfo.edata.meta.FieldMeta;
import com.asiainfo.eframe.dao.BaseDao;

import java.util.List;

public interface FieldMetaDao extends BaseDao {
	public List<FieldMeta> getFieldMeta(String datasetId);
	public int insertFieldMeta(FieldMeta fieldMeta);

}
