package com.nriet.datacenter.service.fuse;

import java.util.Calendar;
import java.util.List;

import com.nriet.datacenter.model.fuse.AREA;
import com.nriet.datacenter.model.fuse.GridData;
import com.nriet.datacenter.model.fuse.HeightArea;
import com.nriet.datacenter.model.fuse.StormEcho;
import com.nriet.datacenter.model.fuse.TitanStorm;
import com.nriet.datacenter.model.fuse.WARN;

import com.nriet.framework.api.images.entity.Product;

public interface FuseService {

	/**
	 * 获取融合数据
	 * 
	 * @param time
	 *            观测时间
	 * @param element
	 *            要素
	 * @return
	 */
	public GridData readLapsData(Calendar time, String element, int height);

	/**
	 * 获取TITAN实况数据
	 * 
	 * @param time
	 *            查询时间
	 * @return
	 */
	public List<TitanStorm> getTitanLiveData(String time);

	/**
	 * 获取指定落区编号的TITAN过去半小时数据
	 * 
	 * @param time
	 *            查询时间
	 * @param num
	 *            落区编号
	 * @return
	 */
	public List<TitanStorm> getTitanPastDataByNum(String time, int num);

	/**
	 * 获取告警全时间数据
	 * 
	 * @param time
	 *            观测时间或预报时间
	 * @param type
	 *            类型.0：机场，1：导航点，2：航线
	 * @param height
	 * @return
	 */
	public List<WARN> getWarnInfo(Calendar time, int type, int height);

	/**
	 * 获取告警要素落区信息
	 * 
	 * @param time
	 *            起报时间或观测时间
	 * @param interval
	 *            间隔时间（单位：分钟）
	 * @param height
	 *            高度层
	 * @return
	 */
	public List<AREA> getAreaInfo(Calendar time, int interval, int height);

	/**
	 * 获取所有高度层有落区的数据信息
	 * 
	 * @param time
	 *            起报时间或观测时间
	 * @return
	 */
	public List<HeightArea> getHeightAreaInfo(Calendar time);

	/**
	 * 回波顶高回波底高时序图数据获取
	 * 
	 * @param time
	 *            查询时间
	 * @param lon
	 *            经度
	 * @param lat
	 *            纬度
	 * @return
	 */
	public List<StormEcho> getEchoData(String time, float lon, float lat);

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
