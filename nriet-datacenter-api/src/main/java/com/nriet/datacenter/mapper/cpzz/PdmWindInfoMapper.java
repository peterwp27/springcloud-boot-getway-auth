package com.nriet.datacenter.mapper.cpzz;

import org.apache.ibatis.annotations.Param;

import com.nriet.datacenter.model.cpzz.PdmWindInfo;
import com.nriet.framework.myBatis.Mapper;

public interface PdmWindInfoMapper extends Mapper<PdmWindInfo> {

	/**
	 * 查询指定发布序号、发布时间、报文类型的大风已发布报文
	 * 
	 * @param msgId
	 *            报文发布序号
	 * @param time
	 *            发布时间
	 * @param reportType
	 *            报文类型
	 * @return 大风已发布报文
	 */
	PdmWindInfo queryWindReport(@Param("msgId") String msgId, @Param("time") String time, @Param("reportType") String reportType);

	/**
	 * 报文保存
	 * 
	 * @param wind
	 *            报文内容
	 */
	void saveReport(@Param("wind") PdmWindInfo wind);
}