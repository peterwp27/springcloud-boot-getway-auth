package com.nriet.datacenter.service.cpzz;

import com.nriet.datacenter.model.cpzz.PdmTsInfo;
import com.nriet.framework.myBatis.*;

/**
 * Created by CodeGenerator on 2018/02/26.
 */
public interface PdmTsInfoService extends Service<PdmTsInfo> {

	/**
	 * 查询指定发布序号、发布时间的雷暴已发布报文
	 * 
	 * @param msgId
	 *            报文发布序号
	 * @param date
	 *            发布时间
	 * @param reportType
	 *            报文类型
	 * @return 雷暴已发布报文
	 */
	PdmTsInfo queryReport(String msgId, String date, String reportType);

	/**
	 * 报文保存
	 * 
	 * @param ts
	 *            报文
	 * @return 0:保存成功, 1:保存失败
	 */
	int saveReport(PdmTsInfo ts);

}
