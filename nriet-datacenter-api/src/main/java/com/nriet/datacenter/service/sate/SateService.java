package com.nriet.datacenter.service.sate;

import java.util.Calendar;
import java.util.List;

import com.nriet.framework.api.images.entity.Product;

public interface SateService {

	/**
	 * 获取指定时间指定时间范围内的最新的图片
	 * 
	 * @param time
	 *            查询时间
	 * @param stationId
	 *            站号
	 * @param dataType
	 *            资料类型
	 * @param element
	 *            要素
	 * @param height
	 *            高度(3位)或仰角
	 * @param number
	 *            获取的图片数
	 * @param sum
	 *            指定范围内的分钟数(负数往前推，整数往后推)
	 * @param interval
	 *            时效(3位)
	 * @return
	 */
	public List<Product> getLatestProduct(Calendar time, String stationId, String dataType, String element,
			String height, int number, int sum, String interval);
	
}
