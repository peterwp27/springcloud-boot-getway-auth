package com.nriet.datacenter.service.cpzz;

import java.util.Date;
import java.util.List;

import com.nriet.datacenter.model.cpzz.PublishedMsgList;

public interface WarningProMakerService {

	/**
	 * 已发布报文一览取得
	 * 
	 * @param time
	 *            查询时间
	 * @param msgType
	 *            报文类型
	 * @return 已发布报文一览
	 */
	List<PublishedMsgList> queryPublishedMsgList(Date time, String msgType);

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
	PublishedMsgList queryOneReport(Date time, String msgId, String msgType, String warnType);

	/**
	 * 获取报文发布序号
	 * 
	 * @param time
	 *            发布时间
	 * @return
	 */
	String getLatestPublishId(Date time);

}
