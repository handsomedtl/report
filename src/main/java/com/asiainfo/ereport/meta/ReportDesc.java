package com.asiainfo.ereport.meta;

public class ReportDesc {	
    private String category;
	private String type;
	private String name;
	private String sql;
	private String restype;
	private String dataroute;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDataroute() {
		return dataroute;
	}
	public void setDataroute(String dataroute) {
		this.dataroute = dataroute;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getRestype() {
		return restype;
	}
	public void setRestype(String restype) {
		this.restype = restype;
	}
}
