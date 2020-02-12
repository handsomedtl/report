package com.asiainfo.edata.sqlparse;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.edata.dataset.ParseResult;
import com.asiainfo.edata.exception.ErrorDatasetDefineException;
import com.asiainfo.edata.exception.ParamNotFoundException;
import com.asiainfo.edata.meta.EDatasetMeta;

public class SqlParseForOracle {
	/**
	 * sql解析
	 */
	public static final int SQL_ACTION_PARESE = 1;
	/**
	 * sql执行
	 */
	public static final int SQL_ACTION_EXECUTE = 2;
	/**
	 * 用户查询条件
	 */
	private Map<String, Object> userParams = new HashMap<String, Object>();
	private EDatasetMeta datasetMeta;
	private int actionType;
	

	public SqlParseForOracle(EDatasetMeta datasetMeta, Map<String, Object> userParams,
			 Integer actionType) {
		this.datasetMeta = datasetMeta;
		this.userParams = userParams;
		this.actionType = actionType;
		
	}

	public ParseResult parserSql() throws ErrorDatasetDefineException, ParamNotFoundException {
		Sqlparser sqlparser = new Sqlparser(this.datasetMeta, this.userParams,
				this.actionType);
		return sqlparser.parserSql();
	}
}
