package com.asiainfo.ereport.components.form;

import java.util.Map;

import com.asiainfo.eframe.service.exception.ServiceException;
import com.asiainfo.ereport.components.impl.AbstractReportElement;
import com.asiainfo.ereport.meta.ReportComponentMeta;
import com.asiainfo.ewebframe.ui.UIElementTypes;
import com.asiainfo.ewebframe.ui.exception.InvalidConfigException;
import com.asiainfo.ewebframe.ui.form.DynamicForm;

public class ReportForm   extends AbstractReportElement {
	
	
	DynamicForm form;

	public ReportForm(ReportComponentMeta componentMeta,DynamicForm form) {
		super(componentMeta);
		this.form= form;
	}

	@Override
	public Object render(Map params) throws InvalidConfigException {
		
		try {
			return form.render(params);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object renderview(Map params) throws InvalidConfigException {
		try {
			return form.renderview(params);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@Override
	public String getComponentType() {
		return getTypeString();
	}

	@Override
	public String getTypeString() {
		return UIElementTypes.UI_ELEMENT_TYPE_FORM;
	}

	

	

}
