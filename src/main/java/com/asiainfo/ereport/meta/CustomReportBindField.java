package com.asiainfo.ereport.meta;

/**
 * 网格和字段绑定关系
 * 
 * @author baowzh
 *
 */
public class CustomReportBindField {
	/**
	 * 数据集id
	 */
	private String datasetId;
	/**
	 * 绑定字段名
	 */
	private String fieldName;
	/**
	 * 
	 */
	private String finalFieldName;
	/**
	 * 格式
	 */
	private String format;
	/**
	 * 对齐方式
	 */
	private String align;
	/**
	 * 显示顺序
	 */
	private String showOrder;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 宽度
	 */
	private Integer width;
	/**
	 * 列索引
	 */
	private Integer colIndex;
	/**
	 * 列索引名称
	 */
	private String colIndexName;
	/**
	 * 背景颜色
	 */
	private String background;
	/**
	 * 样式
	 */
	private String[] style;
	/**
	 * 字段标签
	 */
	private String name;
	/**
	 * 分组操作
	 */
	private String groupoer;
	/**
	 * 字段数据类型
	 */
	private Integer dataType;
	/**
	 * 数据类型-string、number、date
	 */
	private String dataTypeString;
	/**
	 * 是否公式
	 */
	private boolean formula = false;

	private String alias;

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getColIndex() {
		return colIndex;
	}

	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	public String getColIndexName() {
		return colIndexName;
	}

	public void setColIndexName(String colIndexName) {
		this.colIndexName = colIndexName;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String[] getStyle() {
		return style;
	}

	public void setStyle(String[] style) {
		this.style = style;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupoer() {
		return groupoer;
	}

	public void setGroupoer(String groupoer) {
		this.groupoer = groupoer;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getDataTypeString() {
		return dataTypeString;
	}

	public void setDataTypeString(String dataTypeString) {
		this.dataTypeString = dataTypeString;
	}

	public boolean isFormula() {
		return formula;
	}

	public void setFormula(boolean formula) {
		this.formula = formula;
	}

	public String getFinalFieldName() {
		return finalFieldName;
	}

	public void setFinalFieldName(String finalFieldName) {
		this.finalFieldName = finalFieldName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}