package com.asiainfo.test;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.eframe.service.exception.ServiceException;
import com.asiainfo.ewebframe.ui.form.UITemplateEngine;
import com.asiainfo.ewebframe.ui.form.def.FormElement;

import freemarker.template.TemplateNotFoundException;
import junit.framework.Assert;
import junit.framework.TestCase;
public class TemplateTest extends TestCase {
	

	public void getTemplate() throws ServiceException{
		
//		String template = "<div class=\"outer\"> \n <#include $element> </div>";
//		Map param = new HashMap();
//		param.put("element", "formelement/tree.html");
//		System.out.println(UITemplateEngine.renderTemplate("test1", template,param ));
//		try {
//			try {
//				String template =UITemplateEngine.renderTemplate("easyuigrid.html", null);
//			} catch (TemplateNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Assert.assertTrue(false);
//		}
	}


	public void testTemplateLookup() throws TemplateNotFoundException, ServiceException{
//		String tree = UITemplateEngine.renderTemplate("web/*/bootstrap/*/formelement/tree", null);
//		Assert.assertEquals(tree, "web/bootstrap/formelement/tree");
//		tree = UITemplateEngine.renderTemplate("web/*/dd/*/formelement/tree", null);
//		Assert.assertEquals(tree, "web/formelement/tree");
//		
//		tree = UITemplateEngine.renderTemplate("web/*/dd/*/formelement/tree", null);
//		Assert.assertEquals(tree, "web/formelement/tree");
//		
//		tree = UITemplateEngine.renderTemplate("weixin/*/dd/*/formelement/tree", null);
//		Assert.assertEquals(tree, "formelement/tree");
//		System.out.println("=============================" +tree);
	}
	
	public void testRenderLabel() throws ServiceException{
		String content ="<label for=\"${def.elementcode}\"	class=\"control-label <#if def.labelClass??> ${def.labelClass!'col-md-1 col-sm-12 col-xs-12'}  <#elseif def.labelspan??> col-md-${def.labelspan} col-sm-6 col-xs-12 <#else> col-md-1 col-sm-6 col-xs-12</#if>\">${def.lable}<#if def.required ==\"1\"><span class='text-danger'>*</span></#if></label>";
		Map param = new HashMap();
		FormElement def = new FormElement();
		param.put("def", def);
		def.setLabelspan(3);
		def.setLable("My label");
		def.setElementcode("element");
		def.setRequired("0");
		System.out.println(UITemplateEngine.renderTemplate("test2", content,param));
		
	}
	
	public void testSplit(){
		String s="aa";
		String[] a = s.split(",");
		
		System.out.println(a.length);
	}
}
