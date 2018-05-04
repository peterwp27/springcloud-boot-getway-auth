package com.nriet.datacenter.model.airport;

/**
 * 微波辐射计数据实体类
 */
public class MwRadioMeter {

	/**
	 * 要素
	 */
	private String element;

	/**
	 * 时间
	 */
	private String time;

	/**
	 * 高度层
	 */
	private String height;

	/**
	 * 数据
	 */
	private String data;

	/**
	 * 高度角(°)
	 */
	private String heightAngle;

	/**
	 * 方位角(°)
	 */
	private String localAngle;

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHeightAngle() {
		return heightAngle;
	}

	public void setHeightAngle(String heightAngle) {
		this.heightAngle = heightAngle;
	}

	public String getLocalAngle() {
		return localAngle;
	}

	public void setLocalAngle(String localAngle) {
		this.localAngle = localAngle;
	}

}
