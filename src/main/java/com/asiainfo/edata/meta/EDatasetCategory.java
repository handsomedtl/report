package com.asiainfo.edata.meta;

import java.util.Date;
/**
 * 数据集分类
 * @author baowzh
 *
 */
public class EDatasetCategory    {
	/**
	 * 分类id
	 */
	private String id;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 分类类型
	 */
	private String type;
	/**
	 * 父级分类
	 */
	private String parentid;
	/**
	 * 显示顺序
	 */
	private Integer showorder;
	/**
	 * 更新时间
	 */
	private Date upddate;
	/**
	 * 更新人
	 */
	private String updstaff;
	/**
	 * 更新部门
	 */
	private String upddepart;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public Integer getShoworder() {
		return showorder;
	}
	public void setShoworder(Integer showorder) {
		this.showorder = showorder;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	public String getUpdstaff() {
		return updstaff;
	}
	public void setUpdstaff(String updstaff) {
		this.updstaff = updstaff;
	}
	public String getUpddepart() {
		return upddepart;
	}
	public void setUpddepart(String upddepart) {
		this.upddepart = upddepart;
	}

}
