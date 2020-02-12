package com.asiainfo.edata.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.edata.dao.EDatasetMetaDao;
import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.edata.meta.FieldMeta;
import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
import com.asiainfo.eframe.util.StaticConstant;

@Repository("eDatasetMetaDao")
public class EDatasetMetaDaoImpl extends MybatisBaseDao implements EDatasetMetaDao {

	@Override
	public EDatasetMeta getEDatasetMeta(String datasetId) {
		return this.getDataSingle(EDatasetMeta.class, datasetId, StaticConstant.DEFAULTEPARCHYROUTE, null);
	}

	@Override
	public List<EDatasetMeta> getEDatasetMetas(String categoryid,String category,String name) {
        Map map=new HashMap();
        map.put("categoryid", categoryid);
        map.put("category", category);
        map.put("name", name);
		return this.getData("datasetcategoryid", map);
	}
	@Override
	public List<EDatasetMeta> getEDatasetMetas(String id,String staffId) {
        Map map=new HashMap();
        map.put("staffId", staffId);
        map.put("categoryid", id);
		return this.getData("datasetcategoryid", map);
	}
    @Override
    public int insertEDatasetMeta(EDatasetMeta edatasetMeta){
    	return this.insert(EDatasetMeta.class, edatasetMeta);
    }
    @Override
    public void delDatasetMeta(String datasetId){
    	this.del(ConditionMeta.class, datasetId);
    	this.del(FieldMeta.class, datasetId);
    	this.del(EDatasetMeta.class, datasetId);
    }
}
