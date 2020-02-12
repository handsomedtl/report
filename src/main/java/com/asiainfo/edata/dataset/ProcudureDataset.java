package com.asiainfo.edata.dataset;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.LinkedCaseInsensitiveMap;

import com.asiainfo.edata.ArrayEDataList;
import com.asiainfo.edata.EData;
import com.asiainfo.edata.EStaticConstant;
import com.asiainfo.edata.exception.ErrorDatasetDefineException;
import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.eframe.dao.QueryDataset;
import com.asiainfo.eframe.dao.metadata.ProcedureParamMeta;
import com.asiainfo.eframe.dataset.DataFieldMeta;
import com.asiainfo.eframe.dataset.EDataList;
import com.asiainfo.eframe.util.StaticConstant;

public class ProcudureDataset extends AbstractDataset {
	private static final String RETURN_DATA_CURSOR = "listdata";
	private static final String RETURN_DATA_COUNT = "count_dta";
	private static final String RETURN_DATA_SUM = "sum_data";
	private QueryDataset queryDataset;

	public ProcudureDataset(EDatasetMeta meta, QueryDataset queryDataset) {
		super(meta);
		this.queryDataset = queryDataset;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected EDataList<EData<String, Object>> listData(Map<String, Object> param) {
		List<ProcedureParamMeta> parammetas = orgProcudureParams();
		String procedurecontent = this.getMeta().getContent();
		String packageandprocname[] = procedurecontent.split("\\.");
		String packagename = "";
		String procudurename = "";
		if (packageandprocname.length > 1) {
			packagename = packageandprocname[0];
			procudurename = packageandprocname[1];
		} else {
			procudurename = packagename = packageandprocname[0];
		}
		Map<String, Object> resultMap = queryData(packagename, procudurename, parammetas, param);
		Map<String, DataFieldMeta> fieldmetas = new HashMap<String, DataFieldMeta>();
		List<String> fieldnames = new ArrayList<String>();
		List<EData<String, Object>> dataset = new ArrayList<EData<String, Object>>(0);
		if (resultMap.containsKey(ProcudureDataset.RETURN_DATA_CURSOR)) {

			List<LinkedCaseInsensitiveMap> listdata = (List<LinkedCaseInsensitiveMap>) resultMap
					.get(ProcudureDataset.RETURN_DATA_CURSOR);

			for (LinkedCaseInsensitiveMap rowmap : listdata) {
				Set keyset = rowmap.keySet();
				Iterator iterator = keyset.iterator();
				if (fieldnames.isEmpty()) {
					while (iterator.hasNext()) {
						String colname = iterator.next().toString();
						fieldnames.add(colname);
					}
				}
				for (String fielname : fieldnames) {
					if (fieldmetas.containsKey(fielname)) {
						continue;
					}
					Object obj = rowmap.get(fielname);
					if (obj != null) {
						DataFieldMeta fieldMeta = new DataFieldMeta();
						fieldMeta.setName(fielname);
						if (obj.getClass().getSimpleName().equalsIgnoreCase("BigDecimal")) {
							fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_NUMBER);

						} else if (obj.getClass().getSimpleName().equalsIgnoreCase("String")) {
							fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_STRING);
						} else if (obj.getClass().getSimpleName().equalsIgnoreCase("Date")) {
							fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_DATE);
						} else if (obj.getClass().getSimpleName().equalsIgnoreCase("Timestamp")) {
							fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_DATE);
						} else {
							fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_STRING);
						}
						fieldmetas.put(fielname, fieldMeta);
					}
				}
				EData<String, Object> rowdata = new EData<String, Object>();
				rowdata.putAll(rowmap);
				dataset.add(rowdata);
			}
			ArrayEDataList<EData<String, Object>> returnDataset = new ArrayEDataList<EData<String, Object>>(
					dataset.size());
			returnDataset.addAll(dataset);
			returnDataset.setFieldmetas(fieldmetas);
			return returnDataset;
		} else {
			return  new ArrayEDataList<EData<String, Object>>(0);
		}

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected EDataList<EData<String, Object>> listPage(Map<String, Object> param, int offset, int limit) {
		List<ProcedureParamMeta> parammetas = orgProcudureParams();
		String procedurecontent = this.getMeta().getContent();
		String packageandprocname[] = procedurecontent.split("\\.");
		String packagename = "";
		String procudurename = "";
		if (packageandprocname.length > 1) {
			packagename = packageandprocname[0];
			procudurename = packageandprocname[1];
		} else {
			procudurename = packagename = packageandprocname[0];
		}
		Map<String, Object> resultMap = queryData(packagename, procudurename, parammetas, param);
		EDataList<EData<String, Object>> returnDataset = new ArrayEDataList<EData<String, Object>>(0);

		if (resultMap.containsKey(ProcudureDataset.RETURN_DATA_CURSOR)) {
			List<LinkedCaseInsensitiveMap> listdata = (List<LinkedCaseInsensitiveMap>) resultMap
					.get(ProcudureDataset.RETURN_DATA_CURSOR);
			/*
			 * for (LinkedCaseInsensitiveMap rowmap : listdata) { EData<String,
			 * Object> rowdata = new EData<String, Object>();
			 * rowdata.putAll(rowmap); returnDataset.add(rowdata); }
			 * returnDataset.setFieldmetas(fieldmetas);
			 */
			BigDecimal rowcount = (BigDecimal) resultMap.get(ProcudureDataset.RETURN_DATA_COUNT);
			return convertCusorDataToEDataList(listdata,rowcount.longValue());
			// return returnDataset;
		} else {
			return new ArrayEDataList<EData<String, Object>>(0);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private EDataList<EData<String, Object>> convertCusorDataToEDataList(List<LinkedCaseInsensitiveMap> listdata,Long rowCount) {
		Map<String, DataFieldMeta> fieldmetas = new HashMap<String, DataFieldMeta>();
		List<String> fieldnames = new ArrayList<String>();
		List<EData<String, Object>> dataset = new ArrayList<EData<String, Object>>(0);
		for (LinkedCaseInsensitiveMap rowmap : listdata) {
			Set keyset = rowmap.keySet();
			Iterator iterator = keyset.iterator();
			if (fieldnames.isEmpty()) {
				while (iterator.hasNext()) {
					String colname = iterator.next().toString();
					fieldnames.add(colname);
				}
			}
			for (String fielname : fieldnames) {
				if (fieldmetas.containsKey(fielname)) {
					continue;
				}
				Object obj = rowmap.get(fielname);
				if (obj != null) {
					DataFieldMeta fieldMeta = new DataFieldMeta();
					fieldMeta.setName(fielname);
					if (obj.getClass().getSimpleName().equalsIgnoreCase("BigDecimal")) {
						fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_NUMBER);

					} else if (obj.getClass().getSimpleName().equalsIgnoreCase("String")) {
						fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_STRING);
					} else if (obj.getClass().getSimpleName().equalsIgnoreCase("Date")) {
						fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_DATE);
					} else if (obj.getClass().getSimpleName().equalsIgnoreCase("Timestamp")) {
						fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_DATE);
					} else {
						fieldMeta.setTypeName(AbstractDataset.BASE_DATA_TYPE_STRING);
					}
					fieldmetas.put(fielname, fieldMeta);
				}
			}
			EData<String, Object> rowdata = new EData<String, Object>();
			rowdata.putAll(rowmap);
			dataset.add(rowdata);
		}
		ArrayEDataList<EData<String, Object>> returnDataset = new ArrayEDataList<EData<String, Object>>(rowCount);
		returnDataset.addAll(dataset);
		returnDataset.setFieldmetas(fieldmetas);
		return returnDataset;
	}

	@Override
	public ParseResult parseAndPreeview() throws ErrorDatasetDefineException {
		ParseResult parseResult = new ParseResult();
		parseResult.setFinalsql(this.getMeta().getContent());
		parseResult.setConditions(this.getMeta().getConditionMetaList());
		parseResult.setFields(this.getMeta().getFieldMetaList());
		return parseResult;
	}

	@Override
	protected Long getcount(Map<String, Object> param) {

		List<ProcedureParamMeta> parammetas = orgProcudureParams();
		String procedurecontent = this.getMeta().getContent();
		String packageandprocname[] = procedurecontent.split("\\.");
		String packagename = "";
		String procudurename = "";
		if (packageandprocname.length > 1) {
			packagename = packageandprocname[0];
			procudurename = packageandprocname[1];
		} else {
			procudurename = packagename = packageandprocname[0];
		}
		Map<String, Object> resultMap = queryData(packagename, procudurename, parammetas, param);
		BigDecimal rowcount = (BigDecimal) resultMap.get(ProcudureDataset.RETURN_DATA_COUNT);
		return new Long(rowcount.longValue());
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	protected EData<String, Object> getSummaryRow(Map<String, Object> param) {

		List<ProcedureParamMeta> parammetas = orgProcudureParams();
		String procedurecontent = this.getMeta().getContent();
		String packageandprocname[] = procedurecontent.split("\\.");
		String packagename = "";
		String procudurename = "";
		if (packageandprocname.length > 1) {
			packagename = packageandprocname[0];
			procudurename = packageandprocname[1];
		} else {
			procudurename = packagename = packageandprocname[0];
		}
		Map<String, Object> resultMap = queryData(packagename, procudurename, parammetas, param);
		/*
		 * LinkedCaseInsensitiveMap LinkedCaseInsensitiveMap =
		 * (LinkedCaseInsensitiveMap) resultMap
		 * .get(ProcudureDataset.RETURN_DATA_SUM);
		 */
		List<LinkedCaseInsensitiveMap> listdata = (List<LinkedCaseInsensitiveMap>) resultMap
				.get(ProcudureDataset.RETURN_DATA_SUM);
		EDataList<EData<String, Object>> dataset = this.convertCusorDataToEDataList(listdata,new Long(1));
		if (!dataset.isEmpty()) {
			return dataset.get(0);
		} else {
			return new EData<String, Object>();
		}
		// EData<String, Object> rowdata = new EData<String, Object>();
		// rowdata.putAll(rowdata);

	}

	public QueryDataset getQueryDataset() {
		return queryDataset;
	}

	public void setQueryDataset(QueryDataset queryDataset) {
		this.queryDataset = queryDataset;
	}

	private List<ProcedureParamMeta> orgProcudureParams() {
		List<ConditionMeta> conditions = this.getMeta().getConditionMetaList();
		List<ProcedureParamMeta> params = new ArrayList<ProcedureParamMeta>();
		for (ConditionMeta conditionMeta : conditions) {
			ProcedureParamMeta paramMeta = new ProcedureParamMeta();
			paramMeta.setParametername(conditionMeta.getId());
			paramMeta.setParametertype(conditionMeta.getDatatype());
			paramMeta.setParaminout(conditionMeta.getInout());
			params.add(paramMeta);
		}
		return params;
	}

	/**
	 * 查询数据并返回结果
	 * 
	 * @param sql
	 * @param queryparams
	 * @return
	 */
	private Map<String, Object> queryData(String packagename, String procudurename, List<ProcedureParamMeta> parammetas,
			Map<String, Object> queryparams) {
		if (EStaticConstant.TARGET_DATABASE_TYPE_CEN == this.getMeta().getDataRoute()) {
			return this.queryDataset.executeProcedure(packagename, procudurename, parammetas, queryparams,
					EStaticConstant.DEFAULT_CEN_DATABASE_FLAG, StaticConstant.DEFAULTEPARCHYROUTE);

		} else if (EStaticConstant.TARGET_DATABASE_TYPE_CRM == this.getMeta().getDataRoute()) {
			return this.queryDataset.executeProcedure(packagename, procudurename, parammetas, queryparams, null, null);

		} else if (EStaticConstant.TARGET_DATABASE_TYPE_RPT == this.getMeta().getDataRoute()) {
			return queryDataset.executeProcedure(packagename, procudurename, parammetas, queryparams,
					EStaticConstant.RPT_DATABASE_ROUTE, EStaticConstant.DEFAULT_RPT_DATABASE_FLAG);
		} else {
			return this.queryDataset.executeProcedure(packagename, procudurename, parammetas, queryparams,
					EStaticConstant.DEFAULT_CEN_DATABASE_FLAG, StaticConstant.DEFAULTEPARCHYROUTE);

		}
	}

}
