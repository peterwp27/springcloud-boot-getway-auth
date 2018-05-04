package com.nriet.datacenter.mapper.cpzz;

import org.apache.ibatis.annotations.Param;

import com.nriet.datacenter.model.cpzz.PdmTsInfo;
import com.nriet.framework.myBatis.Mapper;

public interface PdmTsInfoMapper extends Mapper<PdmTsInfo> {

	/**
	 * 查询指定发布序号、发布时间的雷暴已发布报文
	 * 
	 * @param msgId
	 *            报文发布序号
	 * @param date
	 *            发布时间
	 * @return 雷暴已发布报文
	 */
	PdmTsInfo queryReport(@Param("msgId") String msgId, @Param("time") String date,
			@Param("reportType") String reportType);

	/**
	 * 报文保存
	 * 
	 * @param ts
	 *            报文
	 */
	void saveReport(@Param("report") PdmTsInfo ts);
}