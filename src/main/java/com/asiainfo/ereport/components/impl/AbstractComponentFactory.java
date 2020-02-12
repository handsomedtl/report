package com.asiainfo.ereport.components.impl;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.ereport.components.ComponentFactory;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ewebframe.ui.UIElementTypes;

public abstract class AbstractComponentFactory<E extends AbstractReportElement> implements ComponentFactory<E> {

	@Override
	public E getComponent(ReportComponentMeta meta,DatasetFactory datafactory) {
		return UIElementTypes.isType(getfactoryTypeName(), meta.getType())?createComponent(meta,datafactory):null;
	}
	

	public E getComponent(ReportComponentMeta meta) {
		return getComponent(meta,null);
	}
	
	public abstract E createComponent(ReportComponentMeta meta,DatasetFactory datafactory) ;
	
	protected  E createComponent(ReportComponentMeta meta){
		return createComponent(meta,null);
	}

}
