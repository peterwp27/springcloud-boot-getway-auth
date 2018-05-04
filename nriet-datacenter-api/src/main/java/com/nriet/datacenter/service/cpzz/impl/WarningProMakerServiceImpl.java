package com.nriet.datacenter.service.cpzz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nriet.datacenter.mapper.cpzz.WarningProMakerMapper;
import com.nriet.datacenter.model.cpzz.PublishedMsgList;
import com.nriet.datacenter.service.cpzz.WarningProMakerService;
import com.nriet.datacenter.util.DataCenterAPIConfig;

@Service
@Transactional
public class WarningProMakerServiceImpl implements WarningProMakerService{

	@Autowired
	private DataCenterAPIConfig config;
	
	@Resource
	private WarningProMakerMapper warningProMakerMapper;
	
	/**
	 * 已发布报文一览取得
	 * 
	 * @param time
	 *            查询时间
	 * @param msgType
	 *            报文类型
	 * @return 已发布报文一览
	 */
	@Override
	public List<PublishedMsgList> queryPublishedMsgList(Date time, String msgType) {
		return warningProMakerMapper.queryPublishedMsgList(time, msgType);
	}

	/**
	 * 警报预览用数据取得
	 * 
	 * @param time
	 *            报文发布时间
	 * @param msgId
	 *            报文发布序号
	 * @param msgType
	 *            报文类型
	 * @param warnType
	 *            告警类型
	 * @return 已发布报文内容
	 */
	@Override
	public PublishedMsgList queryOneReport(Date time, String msgId, String msgType, String warnType) {
		String tableName = "";
		List<String> codes = new ArrayList<>(config.getWeatherTypeMap().keySet());
		Collections.sort(codes);
		if(codes.get(0).equals(warnType)) {
			tableName = "pdm_ts_info";
		}else if (codes.get(1).equals(warnType)) {
			tableName =  "pdm_vis_info";
		}else if (codes.get(2).equals(warnType)) {
			tableName = "pdm_wind_info";
		}else if (codes.get(3).equals(warnType)) {
			tableName = "pdm_ws_info";
		}
		if(tableName.isEmpty()) {
			return null;
		}
		return warningProMakerMapper.queryOneReport(time, msgId, msgType, tableName);
	}

	/**
	 * 获取报文发布序号
	 * 
	 * @param time
	 *            发布时间
	 * @return
	 */
	@Override
	public String getLatestPublishId(Date time) {
		List<String> publishIdList = warningProMakerMapper.queryMaxPublishId(time);
		int maxId = 0;
		if(publishIdList != null && !publishIdList.isEmpty()) {
			for(String publishId : publishIdList) {
				if(publishId == null || publishId.isEmpty()) {
					continue;
				}
				if(maxId < Integer.valueOf(publishId)) {
					maxId = Integer.valueOf(publishId);
				}
			}
		}
		return String.format("%02d", (maxId + 1));
	}
	
}
