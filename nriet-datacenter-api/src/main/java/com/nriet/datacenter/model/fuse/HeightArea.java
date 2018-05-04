package com.nriet.datacenter.model.fuse;

import java.io.Serializable;
import java.util.Map;

/**
 * 所有高度层有落区的数据信息
 */
public class HeightArea implements Serializable{

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
	 * 当前落区信息
	 */
	private Map<String, Integer> current;	
	

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

	public Map<String, Integer> getCurrent() {
		return current;
	}

	public void setCurrent(Map<String, Integer> current) {
		this.current = current;
	}	
	
}
