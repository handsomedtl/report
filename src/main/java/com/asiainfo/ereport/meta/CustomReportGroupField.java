package com.asiainfo.ereport.meta;

/**
 * 行尾绑定配置
 * 
 * @author baowzh
 *
 */
public class CustomReportGroupField {

	private String expression;
	private String colIndex;
	private String format;
	private String align;
	private boolean isformula = false;
	private String background;
	private String[] style;
	private String alias;
	private String dataTypeString;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getColIndex() {
		return colIndex;
	}

	public void setColIndex(String colIndex) {
		this.colIndex = colIndex;
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

	public boolean isIsformula() {
		return isformula;
	}

	public void setIsformula(boolean isformula) {
		this.isformula = isformula;
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDataTypeString() {
		return dataTypeString;
	}

	public void setDataTypeString(String dataTypeString) {
		this.dataTypeString = dataTypeString;
	}

}
