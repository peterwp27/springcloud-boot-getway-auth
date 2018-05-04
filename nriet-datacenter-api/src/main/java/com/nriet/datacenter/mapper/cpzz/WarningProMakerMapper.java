package com.nriet.datacenter.mapper.cpzz;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nriet.datacenter.model.cpzz.PublishedMsgList;

@Mapper
public interface WarningProMakerMapper {

	/**
	 * 查询指定时间（当天内）、报文类型的所有已发布报文
	 * 
	 * @param time
	 *            查询时间
	 * @param msgType
	 *            报文类型(1:警报，2:更正报，3:取消报)
	 * @return
	 */
	List<PublishedMsgList> queryPublishedMsgList(@Param("time") Date time, @Param("msgType") String msgType);

	/**
	 * 查询指定发布时间、报文发布序号、报文类型、告警天气的已发布报文
	 * 
	 * @param time
	 *            发布时间
	 * @param msgId
	 *            报文发布序号
	 * @param msgType
	 *            报文类型
	 * @param tableName
	 *            告警天气类型
	 * @return
	 */
	PublishedMsgList queryOneReport(@Param("time") Date time, @Param("msgId") String msgId,
			@Param("msgType") String msgType, @Param("table") String tableName);

	/**
	 * 查询指定查询时间当天内最大的报文发布序号
	 * 
	 * @param time
	 *            查询时间
	 * @return
	 */
	List<String> queryMaxPublishId(@Param("time") Date time);

}
