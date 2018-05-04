/**
 * AwosDao.java
 * @Description: 机场自观数据查询Dao
 * @author: tangkai
 * @time: 2017年5月17日 下午3:42:29
 * Copyright (C) 2017 NRIET
 * 
 */
package com.nriet.datacenter.mapper.mhzl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nriet.datacenter.model.mhzl.Awos;



/**
 * AwosDao.java
 * 
 * @Description: 机场自观数据查询Dao
 * @author: tangkai
 * @time: 2017年5月17日 下午3:42:29
 */
@Mapper
public interface AwosMapper {

	/**
	 * 根据机场站号、观测时间获取机场自观数据
	 * 
	 * @param stationId
	 *            机场站号
	 * @param time
	 *            观测时间
	 * @return 机场自观数据
	 */
	List<Awos> getAWOSInfo(@Param("stationId") String stationId,@Param("time") String time);	

	/**
	 * 获取指定站点(最近2小时)的时序图数据
	 * 
	 * @param stationId
	 *            机场站号
	 * @param time
	 *            查询时间
	 * @return 机场自观数据
	 */
	List<Awos> getAWOSInfo2H(@Param("stationId") String stationId,@Param("time") String time);	
}
