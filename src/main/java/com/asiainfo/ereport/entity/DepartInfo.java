package com.asiainfo.ereport.entity;

import java.util.Date;

public class DepartInfo {
	private String departid;
	private String departcode;
	private String departname;
	private String departkindcode;
	private String departframe;
	private boolean validflag;
	private Integer departlevel;
	private String arecode;
	private String parentdepartid;
	private Integer orderno;
	private String startdate;
	private Date enddate;
	private Integer cost;
	private String income;
	private Date starttime;
	private Date endtime;
	private String eparchycode;

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public String getDepartcode() {
		return departcode;
	}

	public void setDepartcode(String departcode) {
		this.departcode = departcode;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public String getDepartkindcode() {
		return departkindcode;
	}

	public void setDepartkindcode(String departkindcode) {
		this.departkindcode = departkindcode;
	}

	public String getDepartframe() {
		return departframe;
	}

	public void setDepartframe(String departframe) {
		this.departframe = departframe;
	}

	public boolean isValidflag() {
		return validflag;
	}

	public void setValidflag(boolean validflag) {
		this.validflag = validflag;
	}

	public String getArecode() {
		return arecode;
	}

	public void setArecode(String arecode) {
		this.arecode = arecode;
	}

	public String getParentdepartid() {
		return parentdepartid;
	}

	public void setParentdepartid(String parentdepartid) {
		this.parentdepartid = parentdepartid;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Integer getDepartlevel() {
		return departlevel;
	}

	public void setDepartlevel(Integer departlevel) {
		this.departlevel = departlevel;
	}

	public String getEparchycode() {
		return eparchycode;
	}

	public void setEparchycode(String eparchycode) {
		this.eparchycode = eparchycode;
	}

}
