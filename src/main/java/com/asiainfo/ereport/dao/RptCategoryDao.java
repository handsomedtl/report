package com.asiainfo.ereport.dao;

import java.util.List;

import com.asiainfo.ereport.meta.RptCategory;

public interface RptCategoryDao {
      public List<RptCategory> getReportCategory(String staffId);
      public void saveNewRptCategory(RptCategory t);
}
