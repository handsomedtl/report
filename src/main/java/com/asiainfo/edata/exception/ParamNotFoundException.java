package com.asiainfo.edata.exception;

public class ParamNotFoundException extends Exception {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private String paramsname;

	public ParamNotFoundException(String mess,String paramsname) {
		super(mess);
		this.paramsname=paramsname;
	}

	public String getParamsname() {
		return paramsname;
	}

	public void setParamsname(String paramsname) {
		this.paramsname = paramsname;
	}
	
}
