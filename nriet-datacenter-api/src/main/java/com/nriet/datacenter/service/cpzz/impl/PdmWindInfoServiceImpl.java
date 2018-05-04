package com.nriet.datacenter.service.cpzz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nriet.datacenter.mapper.cpzz.PdmWindInfoMapper;
import com.nriet.datacenter.model.cpzz.PdmWindInfo;
import com.nriet.datacenter.service.cpzz.PdmWindInfoService;
import com.nriet.framework.myBatis.AbstractService;

/**
 * Created by CodeGenerator on 2018/02/26.
 */
@Service
@Transactional
public class PdmWindInfoServiceImpl extends AbstractService<PdmWindInfo> implements PdmWindInfoService {

	@Resource
	private PdmWindInfoMapper pdmWindInfoMapper;

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
	@Override
	public PdmWindInfo queryWindReport(String msgId, String time, String reportType) {
		return pdmWindInfoMapper.queryWindReport(msgId, time, reportType);
	}

	/**
	 * 报文保存
	 * 
	 * @param ts
	 *            报文
	 * @return 0:保存成功, 1:保存失败
	 */
	@Override
	public int saveReport(PdmWindInfo wind) {
		try {
			pdmWindInfoMapper.saveReport(wind);
			return 0;
		}catch(Exception e) {
			return 1;
		}
	}

}
