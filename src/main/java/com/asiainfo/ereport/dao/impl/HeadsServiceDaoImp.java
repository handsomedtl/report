package com.asiainfo.ereport.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.eframe.dao.impl.MybatisBaseDao;
import com.asiainfo.ereport.dao.HeadsServiceDao;
import com.asiainfo.ereport.meta.HeadsTransMeta;
@Repository("headsServiceDao")
public class HeadsServiceDaoImp extends MybatisBaseDao implements HeadsServiceDao {

	@Override
	public List<HeadsTransMeta> getHeadsTrans() {
		return this.getData(HeadsTransMeta.class,"1");
	}
	@Override
	public void updateHeadsTrans(String rptid,String comheads,String froheads) {
		Map map=new HashMap();
		map.put("rptid", rptid);
		map.put("comheads", comheads);
		map.put("froheads", froheads);
		this.update(HeadsTransMeta.class, map);
	}
}
