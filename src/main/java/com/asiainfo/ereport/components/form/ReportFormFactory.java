package com.asiainfo.ereport.components.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.eframe.service.exception.ServiceException;
import com.asiainfo.ereport.components.impl.AbstractComponentFactory;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ewebframe.ui.UIElementTypes;
import com.asiainfo.ewebframe.ui.form.DynamicForm;
import com.asiainfo.ewebframe.ui.form.DynamicFormEngine;
@Service("reportFormFactory")
public class ReportFormFactory extends AbstractComponentFactory<ReportForm> {

	@Autowired
	private DynamicFormEngine dynamicFormEngine;
	
	@Override
	public String getfactoryTypeName() {
		return UIElementTypes.UI_ELEMENT_TYPE_FORM;
	}

	@Override
	public ReportForm createComponent(ReportComponentMeta meta, DatasetFactory datafactory) {
		try {
			DynamicForm form = dynamicFormEngine.getDynamicForm(meta.getCompId());
			return new ReportForm(meta,form);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
