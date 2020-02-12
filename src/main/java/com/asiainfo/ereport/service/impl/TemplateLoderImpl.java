package com.asiainfo.ereport.service.impl;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.asiainfo.eframe.util.ApplicationContextUtil;
import com.asiainfo.ereport.service.TemplateLoder;

@Service("templateLoder")
public class TemplateLoderImpl implements TemplateLoder {

	@Override
	public String getTemplateContent(String basepath,String tempname) {
		try {
			org.springframework.core.io.Resource resource = ApplicationContextUtil.getApplicationContext()
					.getResource("classpath:freemarker/" + tempname+".html");
			InputStream stream = resource.getInputStream();
			byte[] reader = IOUtils.toByteArray(stream);
			String content = new String(reader, "utf-8");
			stream.close();
			return content;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
