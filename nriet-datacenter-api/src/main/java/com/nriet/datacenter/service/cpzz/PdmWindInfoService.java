package com.nriet.datacenter.service.cpzz;

import com.nriet.datacenter.model.cpzz.PdmWindInfo;
import com.nriet.framework.myBatis.Service;

/**
 * Created by CodeGenerator on 2018/02/26.
 */
public interface PdmWindInfoService extends Service<PdmWindInfo> {

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
	PdmWindInfo queryWindReport(String msgId, String time, String reportType);
	
	/**
	 * 报文保存
	 * 
	 * @param ts
	 *            报文
	 * @return 0:保存成功, 1:保存失败
	 */
	int saveReport(PdmWindInfo wind);
}
