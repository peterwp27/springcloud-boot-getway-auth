/**
 * DataForNc.java
 * @Description: 
 * @author: tangkai
 * @time: 2017年9月6日 上午9:11:00
 * Copyright (C) 2017 NRIET
 */
package com.nriet.framework.api.netcdf.entity;

import java.io.Serializable;
import java.util.Map;

import ucar.ma2.DataType;


/**
 * DataForNc.java
 * 
 * @Description:
 * @author: tangkai
 * @time: 2017年9月6日 上午9:11:00
 */
public class DataForNc implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object vals;

	private Map<String, String> attributes;

	private Float fillValue;

	private DataType dataType;

	/**
	 * @return the vals
	 */
	public Object getVals() {
		return vals;
	}

	/**
	 * @param vals
	 *            the vals to set
	 */
	public void setVals(Object vals) {
		this.vals = vals;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the fillValue
	 */
	public Float getFillValue() {
		return fillValue;
	}

	/**
	 * @param fillValue
	 *            the fillValue to set
	 */
	public void setFillValue(Float fillValue) {
		this.fillValue = fillValue;
	}

	/**
	 * @return the dataType
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

}
