//package com.nriet.datacenter.dao.cpzz;
//
//import java.util.Date;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import com.nriet.datacenter.model.cpzz.PdmWindInfo;
//
//@Repository
//public interface IPdmWindInfoDao extends JpaRepository<PdmWindInfo, String> {
//
//	/**
//	 * 通过指定报文发布序号、发布时间、报文类型查找大风天气报文
//	 * 
//	 * @param publishId
//	 *            报文发布序号
//	 * @param publishTime
//	 *            发布时间
//	 * @param reportType
//	 *            报文类型
//	 * @return
//	 */
//	PdmWindInfo findByPublishIdAndPublishTimeAndReportType(String publishId, Date publishTime, Integer reportType);
//}
