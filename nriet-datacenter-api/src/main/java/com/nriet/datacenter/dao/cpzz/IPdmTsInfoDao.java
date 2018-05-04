//package com.nriet.datacenter.dao.cpzz;
//
//import java.util.Date;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.nriet.datacenter.model.cpzz.PdmTsInfo;
//
//@Repository
//public interface IPdmTsInfoDao extends JpaRepository<PdmTsInfo, String> {
//	// @Query("SELECT" +
//	// " t" +
//	// " FROM" +
//	// " PdmTsInfo t" +
//	// " WHERE" +
//	// " t.reportType = :reportType AND" +
//	// " t.publishId = :msgId AND" +
//	// " t.publishTime = :time")
//	// PdmTsInfo queryReport(@Param("msgId") String publishId, @Param("time") Date
//	// date, @Param("reportType") Integer reportType);
//
//	/**
//	 * 通过指定报文发布序号、发布时间、报文类型查找雷暴天气报文
//	 * 
//	 * @param publishId
//	 *            报文发布序号
//	 * @param publishTime
//	 *            发布时间
//	 * @param reportType
//	 *            报文类型
//	 * @return
//	 */
//	PdmTsInfo findByPublishIdAndPublishTimeAndReportType(String publishId, Date publishTime, Integer reportType);
//}
