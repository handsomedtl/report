package com.asiainfo.edata.dao;
import java.util.List;

import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.eframe.dao.BaseDao;

public interface EDatasetMetaDao extends BaseDao{
	public EDatasetMeta getEDatasetMeta(String datasetId);
	public List<EDatasetMeta> getEDatasetMetas(String categoryid,String category,String name);
	public int insertEDatasetMeta(EDatasetMeta edatasetMeta);
	public List<EDatasetMeta> getEDatasetMetas(String id,String staffId);
    public void delDatasetMeta(String datasetId);
}
