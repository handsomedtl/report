package com.asiainfo.ereport.components.grid;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.ereport.components.impl.AbstractComponentFactory;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ewebframe.dao.UIGridMetaDao;
import com.asiainfo.ewebframe.ui.UIElementTypes;
import com.asiainfo.ewebframe.ui.grid.meta.UIGridMeta;

@Service("reportGridFactory")
public class ReportGridFactory extends AbstractComponentFactory<ReportGrid> {
	@Resource(name = "uiGridMetaDao")
	private UIGridMetaDao gridMetaDao;
	
	@Override
	public String getfactoryTypeName() {
		return UIElementTypes.UI_ELEMENT_TYPE_GRID;
	}


	@Override
	public ReportGrid createComponent(ReportComponentMeta meta, DatasetFactory datafactory) {
		UIGridMeta gridMeta = gridMetaDao.getUiGrid(meta.getCompId());
		return new ReportGrid(meta, gridMeta,datafactory);
	}

}
