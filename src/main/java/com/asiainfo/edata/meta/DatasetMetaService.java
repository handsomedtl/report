package com.asiainfo.edata.meta;

import java.util.List;

public interface DatasetMetaService {
	
	public EDatasetMeta getDatadetConfig(String datasetid);

	public List<EDatasetMeta> getDatadetConfigs(String categoryid, String category, String name);

	public List<EDatasetMeta> getDatadetConfigs(String id, String stafId);

	public void save(EDatasetMeta dtasetMeta);

}
