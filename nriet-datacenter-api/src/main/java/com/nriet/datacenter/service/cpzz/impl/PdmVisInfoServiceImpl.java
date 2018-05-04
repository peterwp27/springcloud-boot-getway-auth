package com.nriet.datacenter.service.cpzz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nriet.datacenter.mapper.cpzz.PdmVisInfoMapper;
import com.nriet.datacenter.model.cpzz.PdmVisInfo;
import com.nriet.datacenter.service.cpzz.PdmVisInfoService;
import com.nriet.framework.myBatis.AbstractService;

/**
 * Created by CodeGenerator on 2018/02/26.
 */
@Service
@Transactional
public class PdmVisInfoServiceImpl extends AbstractService<PdmVisInfo> implements PdmVisInfoService {

	@Resource
	private PdmVisInfoMapper pdmVisInfoMapper;

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
	@Override
	public PdmVisInfo queryVisReport(String msgId, String time, String reportType) {
		return pdmVisInfoMapper.queryVisReport(msgId, time, reportType);
	}

	/**
	 * 报文保存
	 * 
	 * @param ts
	 *            报文
	 * @return 0:保存成功, 1:保存失败
	 */
	@Override
	public int saveReport(PdmVisInfo vis) {
		try {
			pdmVisInfoMapper.saveReport(vis);
			return 0;
		}catch(Exception e) {
			return 1;
		}
	}

}
