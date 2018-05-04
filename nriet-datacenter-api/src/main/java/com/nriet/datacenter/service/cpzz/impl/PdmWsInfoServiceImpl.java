package com.nriet.datacenter.service.cpzz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nriet.datacenter.mapper.cpzz.PdmWsInfoMapper;
import com.nriet.datacenter.model.cpzz.PdmWsInfo;
import com.nriet.datacenter.service.cpzz.PdmWsInfoService;
import com.nriet.framework.myBatis.AbstractService;

/**
 * Created by CodeGenerator on 2018/02/26.
 */
@Service
@Transactional
public class PdmWsInfoServiceImpl extends AbstractService<PdmWsInfo> implements PdmWsInfoService {

	@Resource
	private PdmWsInfoMapper pdmWsInfoMapper;

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
	@Override
	public PdmWsInfo queryWsReport(String msgId, String time, String reportType) {
		return pdmWsInfoMapper.queryWsReport(msgId, time, reportType);
	}

	/**
	 * 报文保存
	 * 
	 * @param ts
	 *            报文
	 * @return 0:保存成功, 1:保存失败
	 */
	@Override
	public int saveReport(PdmWsInfo ws) {
		try {
			pdmWsInfoMapper.saveReport(ws);
			return 0;
		}catch(Exception e) {
			return 1;
		}
	}
}
