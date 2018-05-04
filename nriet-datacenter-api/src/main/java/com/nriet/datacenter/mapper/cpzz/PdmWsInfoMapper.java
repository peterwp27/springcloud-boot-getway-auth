package com.nriet.datacenter.mapper.cpzz;

import org.apache.ibatis.annotations.Param;

import com.nriet.datacenter.model.cpzz.PdmWsInfo;
import com.nriet.framework.myBatis.Mapper;

public interface PdmWsInfoMapper extends Mapper<PdmWsInfo> {

	/**
	 * 查询指定报文发布序号、发布时间、报文类型的风切变已发布报文
	 * 
	 * @param msgId
	 *            报文发布序号
	 * @param time
	 *            发布时间
	 * @param reportType
	 *            报文类型
	 * @return 风切变已发布报文
	 */
	PdmWsInfo queryWsReport(@Param("msgId") String msgId, @Param("time") String time, @Param("reportType") String reportType);
	
	/**
	 * 报文保存
	 * 
	 * @param ws
	 *            报文内容
	 */
	void saveReport(@Param("ws") PdmWsInfo ws);
}