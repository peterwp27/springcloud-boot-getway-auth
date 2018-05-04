package com.nriet.framework.api.netcdf.entity;

import java.util.Map;

public class NcData<T> {
	
	/**
	 * 变量名
	 */
	private String variableName;
	
	/**
	 * 变量相关属性
	 */
	private Map<String, Object> attributeMap;
	
	/**
	 * 数据
	 */
	private T data;

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public Map<String, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
