package com.asiainfo.edata.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.edata.dao.FieldMetaDao;
import com.asiainfo.edata.meta.FieldMeta;
import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
import com.asiainfo.eframe.util.StaticConstant;
@Repository("fieldMetaDao")
public class FieldMetaDaoImpl extends MybatisBaseDao implements FieldMetaDao{
	@Override
	public List<FieldMeta> getFieldMeta(String datasetId) {
		return this.getData(FieldMeta.class, datasetId,StaticConstant.DEFAULTEPARCHYROUTE,null);
	}
	@Override
	public int insertFieldMeta(FieldMeta fieldMeta){
		return this.insert(FieldMeta.class,fieldMeta);
	}

}
