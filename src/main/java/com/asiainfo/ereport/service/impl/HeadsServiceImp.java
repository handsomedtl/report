package com.asiainfo.ereport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.ereport.dao.HeadsServiceDao;
import com.asiainfo.ereport.meta.HeadsTransMeta;
import com.asiainfo.ereport.service.HeadsService;
@Service("headsService")
public class HeadsServiceImp implements HeadsService{
	@Autowired
	private HeadsServiceDao headsServiceDao;
	@Override
	public List<HeadsTransMeta> dataHeads(){
		return headsServiceDao.getHeadsTrans();
	}
	@Override
	public void updateHeads(String rptid,String comheads,String froheads){
		headsServiceDao.updateHeadsTrans(rptid,comheads,froheads);
	}

}
