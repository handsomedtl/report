package com.asiainfo.edata.meta;

/**
 * 报表统计维度
 * 
 * @author baowzh
 *
 */
public class EStatisticsDimension {
	/**
	 * 维度字段名称
	 */
	private String dimensionCol;
	/**
	 * 维度名称
	 */
	private String dimensionName;
	/**
	 * 渲染表单组件id
	 */
	private String renderElementCode;

	public String getDimensionCol() {
		return dimensionCol;
	}

	public void setDimensionCol(String dimensionCol) {
		this.dimensionCol = dimensionCol;
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}

	public String getRenderElementCode() {
		return renderElementCode;
	}

	public void setRenderElementCode(String renderElementCode) {
		this.renderElementCode = renderElementCode;
	}

}
