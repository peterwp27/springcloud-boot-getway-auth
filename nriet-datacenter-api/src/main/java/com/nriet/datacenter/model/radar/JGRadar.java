package com.nriet.datacenter.model.radar;

import java.io.Serializable;

/**
 * 激光雷达数据实体类
 */
public class JGRadar implements Serializable{

	/**
	 * 默认值
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 观测时间
	 */
	private String time;
	
	/**
	 * 风速
	 */
	private float ws;
	
	/**
	 * 风切变
	 */
	private float wc;
	
	/**
	 * 高度
	 */
	private float height;
	
	/**
	 * 距离
	 */
	private float range;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public float getWs() {
		return ws;
	}

	public void setWs(float ws) {
		this.ws = ws;
	}

	public float getWc() {
		return wc;
	}

	public void setWc(float wc) {
		this.wc = wc;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}	

}
