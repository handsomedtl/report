package com.asiainfo.edata.dataset;

import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.asiainfo.edata.ArrayEDataList;
import com.asiainfo.edata.EData;
import com.asiainfo.edata.EStaticConstant;
import com.asiainfo.edata.exception.ErrorDatasetDefineException;
import com.asiainfo.edata.exception.ParamNotFoundException;
import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.edata.meta.FieldMeta;
import com.asiainfo.edata.sqlparse.SqlParseForOracle;
import com.asiainfo.eframe.dao.QueryDataset;
import com.asiainfo.eframe.dataset.EDataList;
import com.asiainfo.eframe.util.StaticConstant;

import jodd.util.StringUtil;

/**
 * 自定义sql数据集
 * 
 * @author baowzh
 *
 */
public class SqlSourceDataset extends AbstractDataset {

	private QueryDataset queryDataset;
	private static Logger logger = LoggerFactory.getLogger(SqlSourceDataset.class);

	public SqlSourceDataset(EDatasetMeta meta, QueryDataset queryDataset) {
		super(meta);
		if (meta.getContent() != null) {
			this.getMeta().setContent(meta.getContent().toUpperCase());
		}
		this.queryDataset = queryDataset;
	}

	@Override
	protected EDataList<EData<String, Object>> listData(Map<String, Object> param) {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.putAll(param);
			uppercaseMapKey(queryParams);
			ParseResult parseResult = parseDynamicSql(queryParams, SqlParseForOracle.SQL_ACTION_EXECUTE);
			SqlRowSet sqlRowSet = this.queryData(parseResult.getFinalsql(), queryParams);
			return convertRowDatasetToEDataList(sqlRowSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
			// return new ArrayEDataList<EData<String, Object>>(0);
		}
	}

	@Override
	protected EDataList<EData<String, Object>> listPage(Map<String, Object> param, int offset, int limit) {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.putAll(param);
			uppercaseMapKey(queryParams);
			ParseResult parseResult = parseDynamicSql(queryParams, SqlParseForOracle.SQL_ACTION_EXECUTE);
			Integer startindex = offset + 1;
			Integer endindex = offset + limit;
			String sql = " select * from (select rownum as num,a.* from (" + parseResult.getFinalsql()
					+ ") a order by num ) where  num between :start and :end  ";
			queryParams.put("start", startindex);
			queryParams.put("end", endindex);
			SqlRowSet sqlRowSet = this.queryData(sql, queryParams);
			EDataList<EData<String, Object>> datalist = convertRowDatasetToEDataList(sqlRowSet);
			long rowcount = this.count(queryParams);
			datalist.getFieldmetas();
			EDataList<EData<String, Object>> result = new ArrayEDataList<EData<String, Object>>(rowcount,
					datalist.getFieldmetas());
			result.addAll(datalist);

			return result;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
			// throw new Exception(ex.getMessage());
			// return new ArrayEDataList<EData<String, Object>>(0);

		}
	}

	@Override
	public ParseResult parseAndPreeview() throws ErrorDatasetDefineException {
		try {
			ParseResult parseResult = parseDynamicSql(new HashMap<String, Object>(),
					SqlParseForOracle.SQL_ACTION_PARESE);
			Map<String, Object> emptyParams = new HashMap<String, Object>();
			List<ConditionMeta> conditionMetas = parseResult.getConditions();
			// 把所有查询条件设置为''在执行，主要目的是为了获取sql查询列数据类型
			for (ConditionMeta conditionMeta : conditionMetas) {
				emptyParams.put(conditionMeta.getId().toUpperCase(), null);
			}
			parseResult.setDatasetid(this.getMeta().getId());
			SqlRowSet resultset = queryData("select * from (" + parseResult.getFinalsql() + ") where 1<>1 ",
					emptyParams);
			setDatasetColsConfig(parseResult, resultset.getMetaData());
			return parseResult;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ErrorDatasetDefineException(ex.getMessage());
		}
	}

	/**
	 * 查询数据并返回结果
	 * 
	 * @param sql
	 * @param queryparams
	 * @return
	 */
	private SqlRowSet queryData(String sql, Map<String, Object> queryparams) {
		if (EStaticConstant.TARGET_DATABASE_TYPE_CEN == this.getMeta().getDataRoute()) {
			return queryDataset.listData(sql, queryparams, EStaticConstant.DEFAULT_CEN_DATABASE_FLAG,
					StaticConstant.DEFAULTEPARCHYROUTE);
		} else if (EStaticConstant.TARGET_DATABASE_TYPE_CRM == this.getMeta().getDataRoute()) {
			return queryDataset.listData(sql, queryparams, null, null);
		} else if (EStaticConstant.TARGET_DATABASE_TYPE_RPT == this.getMeta().getDataRoute()) {
			return queryDataset.listData(sql, queryparams, EStaticConstant.RPT_DATABASE_ROUTE,
					EStaticConstant.DEFAULT_RPT_DATABASE_FLAG);
		} else {
			return queryDataset.listData(sql, queryparams, EStaticConstant.DEFAULT_CEN_DATABASE_FLAG,
					StaticConstant.DEFAULTEPARCHYROUTE);
		}
	}

	/**
	 * 解析sql语句
	 * 
	 * @param queryParam
	 * @param actionType
	 * @return
	 * @throws ErrorDatasetDefineException
	 */
	private ParseResult parseDynamicSql(Map<String, Object> queryParam, int actionType)
			throws ErrorDatasetDefineException, ParamNotFoundException {
		SqlParseForOracle sqlParseForOracle = new SqlParseForOracle(this.getMeta(), queryParam, actionType);
		ParseResult parseResult = sqlParseForOracle.parserSql();
		this.wiredSystemParams(queryParam, parseResult);
		return parseResult;

	}

	@Override
	protected Long getcount(Map<String, Object> param) {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.putAll(param);
			uppercaseMapKey(queryParams);
			ParseResult parseResult = parseDynamicSql(queryParams, SqlParseForOracle.SQL_ACTION_EXECUTE);
			String querySql = "select count(1) count from (" + parseResult.getFinalsql() + ")";
			SqlRowSet sqlRowSet = this.queryData(querySql, queryParams);
			Object obj = this.convertRowDatasetToEDataList(sqlRowSet).get(0).get("COUNT");
			return new Long(obj.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("{} getcount params:{} error:{}", this.getMeta().getId(), param, ex);
			throw new RuntimeException("行统计异常！" + ex.getMessage());
		}

	}

	@Override
	protected EData<String, Object> getSummaryRow(Map<String, Object> param) {
		String oldsql = "";
		Map<String, ConditionMeta> oldConditionMetas = new HashMap<String, ConditionMeta>();
		oldConditionMetas.putAll(this.getMeta().getConditionMetas());
		try {
			oldsql = this.getMeta().getContent();
			Map<String, Object> queryParams = new HashMap<String, Object>();
			ParseResult parseResult = this.parseAndPreeview();
			String sumsql = "select ";
			boolean existsSumCol = false;
			String sumcols = getSumCols(parseResult);
			existsSumCol = StringUtil.isNotBlank(sumcols);
			sumsql = sumsql + sumcols + " from (" + parseResult.getFinalsql() + ") a";
			this.getMeta().setContent(sumsql.toUpperCase());
			this.getMeta().setConditionMetas(oldConditionMetas);
			queryParams.clear();
			queryParams.putAll(param);
			uppercaseMapKey(queryParams);
			ParseResult parseResultExe = parseDynamicSql(queryParams, SqlParseForOracle.SQL_ACTION_EXECUTE);

			if (existsSumCol) {
				SqlRowSet sqlRowSet = this.queryData(parseResultExe.getFinalsql().toUpperCase(), queryParams);
				EData<String, Object> sumdata = this.convertRowDatasetToEDataList(sqlRowSet).get(0);
				for (FieldMeta fieldMeta : parseResult.getFields()) {
					if (!sumdata.containsKey(fieldMeta.getName())) {
						sumdata.put(fieldMeta.getName(), null);
					}
				}
				this.getMeta().setContent(oldsql);
				return sumdata;
			} else {
				return new EData<String, Object>();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("{} getSummaryRow params:{} error:{}", this.getMeta().getId(), param, ex);
			throw new RuntimeException("获取合计异常！" + ex.getMessage());
		}

	}

	/**
	 * 获取合计的列
	 * 
	 * @param parseResult
	 * @return
	 */
	protected String getSumCols(ParseResult parseResult) {

		// 如果有个性配置合计项则返回
		if (StringUtil.isNotBlank(this.getMeta().getSummaryConfig())) {
			return this.getMeta().getSummaryConfig();
		}

		// 没有配置合计所有的数字列
		String sumsql = "";
		for (FieldMeta fieldMeta : parseResult.getFields()) {
			if (Types.NUMERIC == fieldMeta.getDataType() || Types.DECIMAL == fieldMeta.getDataType()
					|| Types.BIGINT == fieldMeta.getDataType() || Types.DOUBLE == fieldMeta.getDataType()
					|| Types.FLOAT == fieldMeta.getDataType() || Types.INTEGER == fieldMeta.getDataType()) {
				sumsql = sumsql + "sum(" + fieldMeta.getName() + ") as " + fieldMeta.getName() + ",";
				// existsSumCol = true;
			} else {
				// notsummaryFields.add(fieldMeta.getName());
			}
		}
		if (sumsql.length() > 0)
			sumsql = sumsql.substring(0, sumsql.length() - 1);
		return sumsql;
	}

	private void uppercaseMapKey(Map<String, Object> param) {
		Map<String, Object> newParams = new HashMap<String, Object>();
		Set<String> keyset = param.keySet();
		Iterator<String> iterator = keyset.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = param.get(key);
			newParams.put(key.toUpperCase(), value);
		}
		param.clear();
		param.putAll(newParams);
	}

}
