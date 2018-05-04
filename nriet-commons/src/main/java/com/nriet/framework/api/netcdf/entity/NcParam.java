package com.nriet.framework.api.netcdf.entity;

import java.util.Map;

public class NcParam {

	/**
	 * 变量名
	 */
	private String variableName;
	
	/**
	 * 变量名正则
	 */
	private String variableNameRegex;
	
	/**
	 * 指定一维变量相关参数
	 */
	private Map<Integer, NcParam1D> v1DParams;

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	public Map<Integer, NcParam1D> getV1DParams() {
		return v1DParams;
	}

	public void setV1DParams(Map<Integer, NcParam1D> v1dParams) {
		v1DParams = v1dParams;
	}

	public String getVariableNameRegex() {
		return variableNameRegex;
	}

	public void setVariableNameRegex(String variableNameRegex) {
		this.variableNameRegex = variableNameRegex;
	}

}
