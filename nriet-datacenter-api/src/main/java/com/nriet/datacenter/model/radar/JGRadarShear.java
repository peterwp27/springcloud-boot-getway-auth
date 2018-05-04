package com.nriet.datacenter.model.radar;

import java.io.Serializable;

/**
 * 激光雷达风切变数据实体类
 */
public class JGRadarShear implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 观测时间
	 */
	private String obsTime;

	/**
	 * 机场跑道区域(A,B,C,D)
	 */
	private String rnoArea;

	/**
	 * 风切变类型(0:无,1:中度,2:重度)
	 */
	private String type;

	/**
	 * 风切变值
	 */
	private String value;

	public String getObsTime() {
		return obsTime;
	}

	public void setObsTime(String obsTime) {
		this.obsTime = obsTime;
	}

	public String getRnoArea() {
		return rnoArea;
	}

	public void setRnoArea(String rnoArea) {
		this.rnoArea = rnoArea;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
