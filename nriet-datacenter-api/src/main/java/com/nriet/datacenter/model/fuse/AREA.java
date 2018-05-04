package com.nriet.datacenter.model.fuse;

import java.io.Serializable;

/**
 * 告警要素落区信息实体类
 */
public class AREA implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 观测时间
	 */
	private String time;
	
	/**
	 * 资料类型
	 */
	private String dataType;
	
	/**
	 * 要素
	 */
	private String element;
	
	/**
	 * 等级
	 */
	private String level;
	
	/**
	 * 移向
	 */
	private int moveDir;
	
	/**
	 *移速 
	 */
	private float moveSpeed;
	
	/**
	 * 落区ID
	 */
	private int id;
	
	
	/**
	 * 过去落区信息
	 */
	private float[][] past;
	
	/**
	 * 当前落区信息
	 */
	private float[][] current;
	
	/**
	 * 预报落区信息
	 */
	private float[][] forecast;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getMoveDir() {
		return moveDir;
	}

	public void setMoveDir(int moveDir) {
		this.moveDir = moveDir;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float[][] getPast() {
		return past;
	}

	public void setPast(float[][] past) {
		this.past = past;
	}

	public float[][] getCurrent() {
		return current;
	}

	public void setCurrent(float[][] current) {
		this.current = current;
	}

	public float[][] getForecast() {
		return forecast;
	}

	public void setForecast(float[][] forecast) {
		this.forecast = forecast;
	}	
	
}
