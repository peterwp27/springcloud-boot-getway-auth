package com.nriet.datacenter.model.fuse;

import java.io.Serializable;
import java.util.List;

/**
 * 告警全时间数据实体类
 */
public class WARN implements Serializable{

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
	private List<String> level;
	

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

	public List<String> getLevel() {
		return level;
	}

	public void setLevel(List<String> level) {
		this.level = level;
	}
}
