package com.asiainfo.edata.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.edata.dao.DatasetConditionDao;
import com.asiainfo.edata.dao.EDatasetMetaDao;
import com.asiainfo.edata.dao.FieldMetaDao;

/**
 * 读取 dataset 配置表，实例化 EDatasetMeta
 * 
 * @author tsing
 *
 */
public class DefaultDatasetMetaService implements DatasetMetaService {
	private DatasetConditionDao datasetConditionDao;
	private FieldMetaDao fieldMetaDao;
	private EDatasetMetaDao datasetMetaDao;

	// @Cacheable(value = "asiainfo", key = "#datasetid")
	public EDatasetMeta getDatadetConfig(String datasetid) {
		List<ConditionMeta> conditions = this.datasetConditionDao.getConditionMeta(datasetid);
		List<FieldMeta> fields = this.fieldMetaDao.getFieldMeta(datasetid);
		EDatasetMeta eDatasetMeta = this.datasetMetaDao.getEDatasetMeta(datasetid);
		for (int i = 0; i < conditions.size(); i++) {
			eDatasetMeta.addConditionMeta(conditions.get(i));
		}
		for (int i = 0; i < fields.size(); i++) {
			eDatasetMeta.addFieldMeta(fields.get(i));
		}
		return eDatasetMeta;
	}

	public DatasetConditionDao getDatasetConditionDao() {
		return datasetConditionDao;
	}

	public void setDatasetConditionDao(DatasetConditionDao datasetConditionDao) {
		this.datasetConditionDao = datasetConditionDao;
	}

	public FieldMetaDao getFieldMetaDao() {
		return fieldMetaDao;
	}

	public void setFieldMetaDao(FieldMetaDao fieldMetaDao) {
		this.fieldMetaDao = fieldMetaDao;
	}

	public EDatasetMetaDao getDatasetMetaDao() {
		return datasetMetaDao;
	}

	public void setDatasetMetaDao(EDatasetMetaDao datasetMetaDao) {
		this.datasetMetaDao = datasetMetaDao;
	}

	@Override
	public List<EDatasetMeta> getDatadetConfigs(String categoryid, String category, String name) {
		List<EDatasetMeta> mets = this.datasetMetaDao.getEDatasetMetas(categoryid, category, name);
		List<EDatasetMeta> returnMeatas = new ArrayList<EDatasetMeta>();
		for (int j = 0; j < mets.size(); j++) {
			EDatasetMeta datasetMeta = mets.get(j);
			String datasetid = datasetMeta.getId();
			List<ConditionMeta> conditions = this.datasetConditionDao.getConditionMeta(datasetid);
			List<FieldMeta> fields = this.fieldMetaDao.getFieldMeta(datasetid);
			for (int i = 0; i < conditions.size(); i++) {
				datasetMeta.addConditionMeta(conditions.get(i));
			}
			for (int i = 0; i < fields.size(); i++) {
				datasetMeta.addFieldMeta(fields.get(i));
			}
			returnMeatas.add(datasetMeta);
		}
		return returnMeatas;
	}

	@Override
	public List<EDatasetMeta> getDatadetConfigs(String id, String staffId) {
		List<EDatasetMeta> mets = this.datasetMetaDao.getEDatasetMetas(id, staffId);
		List<EDatasetMeta> returnMeatas = new ArrayList<EDatasetMeta>();
		for (int j = 0; j < mets.size(); j++) {
			EDatasetMeta datasetMeta = mets.get(j);
			String datasetid = datasetMeta.getId();
			List<ConditionMeta> conditions = this.datasetConditionDao.getConditionMeta(datasetid);
			List<FieldMeta> fields = this.fieldMetaDao.getFieldMeta(datasetid);
			for (int i = 0; i < conditions.size(); i++) {
				datasetMeta.addConditionMeta(conditions.get(i));
			}
			for (int i = 0; i < fields.size(); i++) {
				datasetMeta.addFieldMeta(fields.get(i));
			}
			returnMeatas.add(datasetMeta);
		}
		return returnMeatas;
	}

	@Override
	public void save(EDatasetMeta dtasetMeta) {
		// 如果存在则修改
		if (this.datasetMetaDao.exists(EDatasetMeta.class, dtasetMeta)) {
			this.datasetMetaDao.update(EDatasetMeta.class, dtasetMeta);
		} else {
			this.datasetMetaDao.insert(EDatasetMeta.class, dtasetMeta);
		}
		this.datasetConditionDao.del(ConditionMeta.class, dtasetMeta.getId());
		this.fieldMetaDao.del(FieldMeta.class, dtasetMeta.getId());
		for (FieldMeta fieldMeta : dtasetMeta.getFieldMetaList()) {
			this.fieldMetaDao.insert(FieldMeta.class, fieldMeta);
		}
		for (ConditionMeta conditionMeta : dtasetMeta.getConditionMetaList()) {
			this.datasetConditionDao.insert(ConditionMeta.class, conditionMeta);
		}
	}
}
