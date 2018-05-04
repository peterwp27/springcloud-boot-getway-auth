package com.nriet.datacenter.service.cpzz;

import com.nriet.datacenter.model.cpzz.PdmVisInfo;
import com.nriet.framework.myBatis.Service;

/**
 * Created by CodeGenerator on 2018/02/26.
 */
public interface PdmVisInfoService extends Service<PdmVisInfo> {

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
	PdmVisInfo queryVisReport(String msgId, String time, String reportType);
	
	/**
	 * 报文保存
	 * 
	 * @param ts
	 *            报文
	 * @return 0:保存成功, 1:保存失败
	 */
	int saveReport(PdmVisInfo vis);
	
}
