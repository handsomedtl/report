package com.asiainfo.edata;

public class EStaticConstant {
	/**
	 * 数据集类型-sql
	 */
	public static final Integer DATASET_TYPE_SQL = 1;
	/**
	 * 数据集类型-存储过程
	 */
	public static final Integer DATASET_TYPE_PROCUDURE = 2;
	/**
	 * 自定义bean
	 */
	public static final Integer DATASET_TYPE_CUSTOMBEAN = 3;

	/**
	 * 从表单获取
	 */
	public static final Integer PARAM_SOURCE_TYPE_FORM = 1;
	/**
	 * 从系统内部获取
	 */
	public static final Integer PARAM_SOURCE_TYPE_SYSTEM = 2;

	public static final Integer TARGET_DATABASE_TYPE_CEN = 1;
	public static final Integer TARGET_DATABASE_TYPE_CRM = 2;
	public static final Integer TARGET_DATABASE_TYPE_RPT = 3;
	public static final String DEFAULT_CEN_DATABASE_FLAG = "cen1";
	public static final String RPT_DATABASE_ROUTE = "report";
	public static final String DEFAULT_RPT_DATABASE_FLAG = "default";
	public static final String MULTISELECTCONDSPLITSTR = ",";
	public static final String DEFAULT_DATASET_CATEGOTY = "1";
	public static final String DEFAULT_LYAOUT_GRID_VAR = "grid1";
	public static final String DEFAULT_LYAOUT_FORM_VAR = "form1";
	public static final String DEFAULT_PAGE_TEMPLATE = "sequnce|colmd3lg4";
	public static final Integer DEFAULT_ELEMENT_SPAN = 2;
	public static final Integer DEFAULT_COL_WIDTH = 140;
	public static final String ALIGN_LEFT = "left";
	public static final String ALIGN_RIGHT = "right";
	public static final String ALIGN_CENTER = "center";
	public static final String DEFAULT_REPORT_CATEGORY_ID = "1";
	/**
	 * 
	 */
	public static final String QUERY_BUTTON_ELEMENT_ID = "10000060";
	public static final Integer SHOW_SUMMARY_ROLE_EVERYPAGE = 1;
	public static final Integer SHOW_SUMMARY_ROLE_NONE = 0;
	public static final Integer SHOW_SUMMARY_ROLE_LASTPAGE = 2;
}
