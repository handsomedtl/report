package com.asiainfo.edata.dataset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.asiainfo.edata.ArrayEDataList;
import com.asiainfo.edata.EData;
import com.asiainfo.edata.EDataset;
import com.asiainfo.edata.EStaticConstant;
import com.asiainfo.edata.exception.ErrorDatasetDefineException;
import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.edata.meta.FieldMeta;
import com.asiainfo.eframe.dataset.DataFieldMeta;
import com.asiainfo.eframe.dataset.EDataList;
import com.asiainfo.eframe.entity.UserInfo;
import com.asiainfo.eframe.service.ContextHolderService;
import com.asiainfo.eframe.util.ApplicationContextUtil;

import jodd.bean.BeanUtil;

public abstract class AbstractDataset implements EDataset<EData<String, Object>, Map<String, Object>> {
	protected static String BASE_DATA_TYPE_STRING = "String";
	protected static String BASE_DATA_TYPE_NUMBER = "number";
	protected static String BASE_DATA_TYPE_DATE = "date";
	private EDatasetMeta meta;

	public AbstractDataset(EDatasetMeta meta) {
		this.meta = meta;
	}

	@Override
	public EDatasetMeta getMeta() {
		return meta;
	}

	@Override
	public EDataList<EData<String, Object>> load(Map<String, Object> param) {
		return listData(param);
	}

	protected abstract EDataList<EData<String, Object>> listData(Map<String, Object> param);

	@Override
	public EDataList<EData<String, Object>> loadPage(Map<String, Object> param, int offset, int limit) {
		return listPage(param, offset, limit);
	}

	protected abstract EDataList<EData<String, Object>> listPage(Map<String, Object> param, int offset, int limit);

	@Override
	public long count(Map<String, Object> param) {
		return getcount(param);
	}

	@Override
	public EData<String, Object> sum(Map<String, Object> param) {

		return this.getSummaryRow(param);
	}

	/**
	 * 获取总行数
	 * 
	 * @param param
	 * @return
	 */
	protected abstract Long getcount(Map<String, Object> param);

	/**
	 * 获取汇总行
	 * 
	 * @param param
	 * @return
	 */
	protected abstract EData<String, Object> getSummaryRow(Map<String, Object> param);

	@Override
	public abstract ParseResult parseAndPreeview() throws ErrorDatasetDefineException;

	protected EDataList<EData<String, Object>> convertRowDatasetToEDataList(SqlRowSet sqlRowSet) {
		int colcount = sqlRowSet.getMetaData().getColumnCount();
		List<EData<String, Object>> dataList = new ArrayList<EData<String, Object>>();
		Map<String, DataFieldMeta> fieldmetas = new HashMap<String, DataFieldMeta>();
		for (int i = 1; i <= colcount; i++) {
			int coltype = sqlRowSet.getMetaData().getColumnType(i);
			DataFieldMeta fieldMeta = new DataFieldMeta();
			if (coltype == Types.VARCHAR || coltype == Types.CHAR || coltype == Types.CLOB) {
				fieldMeta.setTypeName(BASE_DATA_TYPE_STRING);
			} else if (coltype == Types.DATE || coltype == Types.TIMESTAMP || coltype == Types.TIME) {
				fieldMeta.setTypeName(BASE_DATA_TYPE_DATE);
			} else if (coltype == Types.INTEGER || coltype == Types.DECIMAL || coltype == Types.DOUBLE
					|| coltype == Types.NUMERIC) {
				fieldMeta.setTypeName(BASE_DATA_TYPE_NUMBER);
			} else if (coltype == -8) {
				continue;
			} else {
				fieldMeta.setTypeName(BASE_DATA_TYPE_STRING);
			}
			String colname = sqlRowSet.getMetaData().getColumnName(i);
			fieldMeta.setName(colname);
			fieldmetas.put(colname, fieldMeta);
		}
		while (sqlRowSet.next()) {
			EData<String, Object> rowdata = new EData<String, Object>();
			for (int i = 1; i <= colcount; i++) {
				if (sqlRowSet.getMetaData().getColumnTypeName(i).equalsIgnoreCase("ROWID")) {
					continue;
				}
				String classname = sqlRowSet.getMetaData().getColumnClassName(i);
				if (classname.equalsIgnoreCase("java.math.BigDecimal")) {
					BigDecimal bigDecimal = (BigDecimal) sqlRowSet.getObject(i);
					if (bigDecimal != null && bigDecimal.scale() == 0) {
						rowdata.put(sqlRowSet.getMetaData().getColumnName(i), bigDecimal.longValue());
					} else if (bigDecimal != null && bigDecimal.scale() > 0) {
						rowdata.put(sqlRowSet.getMetaData().getColumnName(i), bigDecimal.doubleValue());
					} else {
						rowdata.put(sqlRowSet.getMetaData().getColumnName(i), sqlRowSet.getObject(i));
					}
				} else if (classname.equalsIgnoreCase("java.sql.Clob")) {
					Clob clob = (Clob) sqlRowSet.getObject(i);
					if (clob != null) {
						rowdata.put(sqlRowSet.getMetaData().getColumnName(i), readClob(clob));
					} else {
						rowdata.put(sqlRowSet.getMetaData().getColumnName(i), null);
					}
				} else {
					rowdata.put(sqlRowSet.getMetaData().getColumnName(i), sqlRowSet.getObject(i));
				}
			}
			dataList.add(rowdata);
		}
		ArrayEDataList<EData<String, Object>> returnList = new ArrayEDataList<EData<String, Object>>(dataList.size());
		returnList.setFieldmetas(fieldmetas);
		returnList.addAll(dataList);
		return returnList;
	}

	private String readClob(Clob clob) {
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = clob.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);
			String line;
			while (null != (line = br.readLine())) {
				sb.append(line);
			}
			br.close();
			return sb.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据查询结果生成sql查询列配置信息
	 * 
	 * @param parseResult
	 * @param metaData
	 */
	protected void setDatasetColsConfig(ParseResult parseResult, SqlRowSetMetaData metaData) {
		int colcount = metaData.getColumnCount();
		List<FieldMeta> fields = new ArrayList<FieldMeta>();
		for (int i = 1; i <= colcount; i++) {
			FieldMeta fieldMeta = new FieldMeta();
			fieldMeta.setId(metaData.getColumnName(i));
			fieldMeta.setName(metaData.getColumnName(i));
			fieldMeta.setDataType(metaData.getColumnType(i));
			fieldMeta.setDatasetId(parseResult.getDatasetid());
			fields.add(fieldMeta);
		}
		parseResult.setFields(fields);

	}

	protected void wiredSystemParams(Map<String, Object> params, ParseResult parseResult) {
		ContextHolderService contextHolderService = ApplicationContextUtil.getBean("sessionContextHolder",
				ContextHolderService.class);
		UserInfo userinfo = null;
		try {
			userinfo = contextHolderService.getSessionUserInfo();
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		if (userinfo != null) {
			List<ConditionMeta> conditions = this.getMeta().getConditionMetaList();
			for (ConditionMeta conditionMeta : conditions) {
				if (EStaticConstant.PARAM_SOURCE_TYPE_SYSTEM == conditionMeta.getSrcType()) {
					if (BeanUtil.hasDeclaredProperty(userinfo, conditionMeta.getId())) {
						params.put(conditionMeta.getId().toUpperCase(), BeanUtil.getProperty(userinfo, conditionMeta.getId()));
					}
				}
			}
		}
		for (String multiparam : parseResult.getMultiselectFields()) {
			String paramvalue = params.get(multiparam.toUpperCase()).toString();
			String paramvalues[] = paramvalue.split(EStaticConstant.MULTISELECTCONDSPLITSTR);
			params.put(multiparam.toUpperCase(), paramvalues[0]);
			for (int i = 1; i < paramvalues.length; i++) {
				params.put(multiparam.toUpperCase() + i, paramvalues[i]);
			}
		}
		List<ConditionMeta> conditions = this.getMeta().getConditionMetaList();
		for (ConditionMeta conditionMeta : conditions) {
			if (!params.containsKey(conditionMeta.getId().toUpperCase())) {
				params.put(conditionMeta.getId().toUpperCase(), null);
			}
		}
	}

}
