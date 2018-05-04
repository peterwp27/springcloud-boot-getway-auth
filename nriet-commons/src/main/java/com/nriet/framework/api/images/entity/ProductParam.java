package com.nriet.framework.api.images.entity;

import java.util.Calendar;
/**
 * 
 * @author b_wangpei
 *
 */
public class ProductParam {
	
	private Calendar time;
	private String stationId;
	private String dataType;
	private String element;
	private String height;
	private int sum;
	private String interval;
	private int number;
	
	
	public ProductParam() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param time
	 * @param stationId
	 * @param dataType
	 * @param element
	 * @param height
	 * @param sum
	 * @param interval
	 * @param number
	 */
	public ProductParam(Calendar time, String stationId, String dataType, String element, String height, int sum,
			String interval, int number) {
		super();
		this.time = time;
		this.stationId = stationId;
		this.dataType = dataType;
		this.element = element;
		this.height = height;
		this.sum = sum;
		this.interval = interval;
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Calendar getTime() {
		return time;
	}
	public void setTime(Calendar time) {
		this.time = time;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
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
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	
}
