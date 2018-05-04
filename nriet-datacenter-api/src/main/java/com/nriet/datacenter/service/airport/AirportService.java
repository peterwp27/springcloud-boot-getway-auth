package com.nriet.datacenter.service.airport;

import java.util.List;

import com.nriet.datacenter.model.airport.MwRadioMeter;

public interface AirportService {

	/**
	 * 获取指定时间、资料、时间范围的微波辐射计数据
	 * 
	 * @param time
	 *            查询时间
	 * @param dataType
	 *            资料类型
	 * @param sum
	 *            时效(小时)
	 * @return
	 */
	List<MwRadioMeter> getMwRmData(String time, String dataType, int sum);
}
