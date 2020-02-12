package com.asiainfo.ereport.service;

import java.util.List;

import com.asiainfo.ereport.meta.HeadsTransMeta;

/**
 * 报表服务
 * 
 * @author baowzh
 *
 */
public interface HeadsService {
	
	public List<HeadsTransMeta> dataHeads();
	public void updateHeads(String rptid,String comheads,String froheads);
   

}
