//package com.nriet.datacenter.dao.cpzz;
//
//import java.util.Date;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import com.nriet.datacenter.model.cpzz.PdmVisInfo;
//
//@Repository
//public interface IPdmVisInfoDao extends JpaRepository<PdmVisInfo, String> {
//
//	/**
//	 * 通过指定报文发布序号、发布时间、报文类型查找低能见度天气报文
//	 * 
//	 * @param publishId
//	 *            报文发布序号
//	 * @param publishTime
//	 *            发布时间
//	 * @param reportType
//	 *            报文类型
//	 * @return
//	 */
//	PdmVisInfo findByPublishIdAndPublishTimeAndReportType(String publishId, Date publishTime, Integer reportType);
//}
