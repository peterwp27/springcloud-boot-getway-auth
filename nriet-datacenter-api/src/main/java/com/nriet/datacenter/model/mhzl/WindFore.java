package com.nriet.datacenter.model.mhzl;

import java.io.Serializable;

/**
 * 大风预报数据实体类
 */
public class WindFore implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 预报时间
	 */
	private String foreTime;
	
	/**
	 * 跑道编号
	 */
	private String rno;

	/**
	 * 跑道第一方向风速
	 */
	private float rn_one_ws;

	/**
	 * 跑道第一方向风向
	 */
	private float rn_one_wd;

	/**
	 * 跑道第一方向顺逆风
	 */
	private float rn_one_counterw;

	/**
	 * 跑道第一方向侧风
	 */
	private float rn_one_crossw;

	/**
	 * 跑道中间方向风速
	 */
	private float rn_mid_ws;

	/**
	 * 跑道中间方向风向
	 */
	private float rn_mid_wd;

	/**
	 * 跑道中间方向顺逆风
	 */
	private float rn_mid_counterw;

	/**
	 * 跑道中间方向侧风
	 */
	private float rn_mid_crossw;

	/**
	 * 跑道第二方向风速
	 */
	private float rn_two_ws;

	/**
	 * 跑道第二方向风向
	 */
	private float rn_two_wd;

	/**
	 * 跑道第二方向顺逆风
	 */
	private float rn_two_counterw;

	/**
	 * 跑道第二方向侧风
	 */
	private float rn_two_crossw;

	public String getForeTime() {
		return foreTime;
	}

	public void setForeTime(String foreTime) {
		this.foreTime = foreTime;
	}
	
	public String getRno() {
		return rno;
	}

	public void setRno(String rno) {
		this.rno = rno;
	}

	public float getRn_one_ws() {
		return rn_one_ws;
	}

	public void setRn_one_ws(float rn_one_ws) {
		this.rn_one_ws = rn_one_ws;
	}

	public float getRn_one_wd() {
		return rn_one_wd;
	}

	public void setRn_one_wd(float rn_one_wd) {
		this.rn_one_wd = rn_one_wd;
	}

	public float getRn_one_counterw() {
		return rn_one_counterw;
	}

	public void setRn_one_counterw(float rn_one_counterw) {
		this.rn_one_counterw = rn_one_counterw;
	}

	public float getRn_one_crossw() {
		return rn_one_crossw;
	}

	public void setRn_one_crossw(float rn_one_crossw) {
		this.rn_one_crossw = rn_one_crossw;
	}

	public float getRn_mid_ws() {
		return rn_mid_ws;
	}

	public void setRn_mid_ws(float rn_mid_ws) {
		this.rn_mid_ws = rn_mid_ws;
	}

	public float getRn_mid_wd() {
		return rn_mid_wd;
	}

	public void setRn_mid_wd(float rn_mid_wd) {
		this.rn_mid_wd = rn_mid_wd;
	}

	public float getRn_mid_counterw() {
		return rn_mid_counterw;
	}

	public void setRn_mid_counterw(float rn_mid_counterw) {
		this.rn_mid_counterw = rn_mid_counterw;
	}

	public float getRn_mid_crossw() {
		return rn_mid_crossw;
	}

	public void setRn_mid_crossw(float rn_mid_crossw) {
		this.rn_mid_crossw = rn_mid_crossw;
	}

	public float getRn_two_ws() {
		return rn_two_ws;
	}

	public void setRn_two_ws(float rn_two_ws) {
		this.rn_two_ws = rn_two_ws;
	}

	public float getRn_two_wd() {
		return rn_two_wd;
	}

	public void setRn_two_wd(float rn_two_wd) {
		this.rn_two_wd = rn_two_wd;
	}

	public float getRn_two_counterw() {
		return rn_two_counterw;
	}

	public void setRn_two_counterw(float rn_two_counterw) {
		this.rn_two_counterw = rn_two_counterw;
	}

	public float getRn_two_crossw() {
		return rn_two_crossw;
	}

	public void setRn_two_crossw(float rn_two_crossw) {
		this.rn_two_crossw = rn_two_crossw;
	}

}
