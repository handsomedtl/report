package com.asiainfo.ereport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.asiainfo.eframe.conf.SysConfig;

@Component("reportConfig")
public class EReportConfig extends SysConfig {
	@Value("${domain}")
	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
