package com.asiainfo.ereport.components;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.ereport.components.impl.AbstractReportElement;
import com.asiainfo.ereport.meta.ReportComponentMeta;

public interface ComponentFactory<E extends AbstractReportElement> {

	public  E getComponent(ReportComponentMeta meta);
	
	public E getComponent(ReportComponentMeta meta, DatasetFactory datafactory);
	
	public String getfactoryTypeName();
}
