package com.nriet.datacenter.mapper.cpzz;

import org.apache.ibatis.annotations.Param;

import com.nriet.datacenter.model.cpzz.PdmVisInfo;
import com.nriet.framework.myBatis.Mapper;

public interface PdmVisInfoMapper extends Mapper<PdmVisInfo> {

	/**
	 * 查询指定发布序号、发布时间、报文类型的低能见度已发布报文
	 * 
	 * @param msgId
	 *            报文发布序号
	 * @param time
	 *            发布时间
	 * @param reportType
	 *            报文类型
	 * @return 低能见度已发布报文
	 */
	PdmVisInfo queryVisReport(@Param("msgId") String msgId, @Param("time") String time,
			@Param("reportType") String reportType);

	/**
	 * 报文保存
	 * 
	 * @param vis
	 *            报文内容
	 */
	void saveReport(@Param("vis") PdmVisInfo vis);
}