package com.nriet.datacenter.service.mhzl;

import java.util.List;

import com.nriet.datacenter.model.airport.MwRadioMeter;
import com.nriet.datacenter.model.mhzl.Awos;
import com.nriet.datacenter.model.mhzl.WindFore;


public interface MhzlService {

	
	/**
	 * 根据机场站号、观测时间获取机场自观数据
	 * 
	 * @param stationId
	 *            机场站号
	 * @param time
	 *            观测时间
	 * @return 机场自观数据
	 */
	List<Awos> getAWOSInfo(String stationId, String time);

	/**
	 * 获取指定站点(最近2小时)的时序图数据
	 * 
	 * @param stationId
	 *            机场站号
	 * @param time
	 *            查询时间
	 * @return 机场自观数据
	 */
	List<Awos> getAWOSInfo2H(String stationId, String time);
	
	/**
	 * 获取大风预报1小时数据
	 * 
	 * @param time
	 *            查询时间(yyyyMMddHHmmss)
	 * @param station
	 *            站号+跑道号
	 * @return
	 */
	List<WindFore> getWindForeInfo(String time, String station);

}
