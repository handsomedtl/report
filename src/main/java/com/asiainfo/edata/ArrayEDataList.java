package com.asiainfo.edata;

import java.util.ArrayList;
import java.util.Map;

import com.asiainfo.eframe.dataset.DataFieldMeta;
import com.asiainfo.eframe.dataset.EDataList;

public class ArrayEDataList<E> extends ArrayList<E> implements EDataList<E> {
	private Map<String, DataFieldMeta> fieldmetas;
    private long total=0;
	public ArrayEDataList(long total) {
		super();
		this.total=total;
	}

	public ArrayEDataList(long total,Map<String, DataFieldMeta> fieldmetas) {
		super();
		this.fieldmetas = fieldmetas;
		this.total=total;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int offset() {

		return 0;
	}

	@Override
	public int limit() {
		return 0;
	}

	public Map<String, DataFieldMeta> getFieldmetas() {
		return fieldmetas;
	}

	public void setFieldmetas(Map<String, DataFieldMeta> fieldmetas) {
		this.fieldmetas = fieldmetas;
	}

	@Override
	public long total() {		
			return this.total;
	
	}

}
