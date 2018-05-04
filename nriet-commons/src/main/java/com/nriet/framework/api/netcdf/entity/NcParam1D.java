package com.nriet.framework.api.netcdf.entity;

public class NcParam1D<T>{

	/**
	 * 1维变量名
	 */
	private String variable1DName;
	
	/**
	 * 指定开始值
	 */
	private T startValue;
	
	/**
	 * 指定结束值(单值不赋值)
	 */
	private T endValue;
	
	/**
	 * 指定开始位置
	 */
	private Integer startInt;
	
	/**
	 * 指定结束位置(单值不赋值)
	 */
	private Integer endInt;
	
	/**
	 * 抽析系数
	 */
	private int cutNum = 1;
	
	/**
	 * 平均系数
	 */
	private int avgNum = 1;

	public String getVariable1DName() {
		return variable1DName;
	}

	public void setVariable1DName(String variable1dName) {
		variable1DName = variable1dName;
	}

	public T getStartValue() {
		return startValue;
	}

	public void setStartValue(T startValue) {
		this.startValue = startValue;
	}

	public T getEndValue() {
		return endValue;
	}

	public void setEndValue(T endValue) {
		this.endValue = endValue;
	}

	public Integer getStartInt() {
		return startInt;
	}

	public void setStartInt(Integer startInt) {
		this.startInt = startInt;
	}

	public Integer getEndInt() {
		return endInt;
	}

	public void setEndInt(Integer endInt) {
		this.endInt = endInt;
	}

	public int getCutNum() {
		return cutNum;
	}

	public void setCutNum(int cutNum) {
		this.cutNum = cutNum;
	}

	public int getAvgNum() {
		return avgNum;
	}

	public void setAvgNum(int avgNum) {
		this.avgNum = avgNum;
	}

}
