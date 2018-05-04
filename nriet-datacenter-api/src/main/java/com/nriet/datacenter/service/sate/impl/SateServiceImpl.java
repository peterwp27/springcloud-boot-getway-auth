package com.nriet.datacenter.service.sate.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nriet.datacenter.service.sate.SateService;
import com.nriet.datacenter.util.DataCenterAPIConfig;

import com.nriet.framework.api.images.ImgProduceUtil;
import com.nriet.framework.api.images.entity.ProductParam;
import com.nriet.framework.api.images.entity.Product;

@Service
@Transactional
public class SateServiceImpl implements SateService {

	@Autowired
	private DataCenterAPIConfig config;
	
	/**
	 * 获取指定时间指定时间范围内的最新的图片
	 * 
	 * @param time 查询时间
	 * @param stationId 站号
	 * @param dataType 资料类型
	 * @param element 要素
	 * @param height 高度(3位)或仰角
	 * @param number 获取的图片数
	 * @param sum 指定范围内的分钟数
	 * @param interval 时效(3位)
	 * @return
	 */
	@Override
	public List<Product> getLatestProduct(Calendar time, String stationId, String dataType, String element,
			String height, int number, int sum, String interval) {
		ProductParam pro = new ProductParam();
		pro.setTime(time);
		pro.setDataType(dataType);
		pro.setElement(element);
		pro.setHeight(height);
		pro.setInterval(interval);
		pro.setNumber(number);
		pro.setSum(sum);
		pro.setStationId(stationId);
		return ImgProduceUtil.getLatestProduct(pro, config.getRootPath(), config.getImagePath(), config.getLegendRootPath(), config.getProLegendName());
	}

}
