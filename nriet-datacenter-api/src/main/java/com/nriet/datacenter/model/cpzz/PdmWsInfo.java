package com.nriet.datacenter.model.cpzz;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nriet.framework.util.DateUtil;

/**
 * 产品制作风切变天气告警报文发布单表实体类
 */
//@Entity
@Table(name = "pdm_ws_info")
public class PdmWsInfo {
	/**
	 * 报文ID
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	/**
	 * 数据插入时间
	 */
	@Column(name = "INSERT_TIME")
	private Date insertTime;

	/**
	 * 数据更新时间
	 */
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	/**
	 * 物理删除flag(0:不删除,1:删除)
	 */
	@Column(name = "ISDELETED")
	private Integer isdeleted;

	/**
	 * 影响天气现象
	 */
	@Column(name = "IMPACTED_WW")
	private String impactedWw;

	/**
	 * 位置
	 */
	@Column(name = "LOCATION")
	private String location;

	/**
	 * 强度
	 */
	@Column(name = "INTENSITY")
	private String intensity;

	/**
	 * 预报开始时间
	 */
	@Column(name = "FORE_TIME_START")
	private Date foreTimeStart;

	/**
	 * 预报结束时间
	 */
	@Column(name = "FORE_TIME_END")
	private Date foreTimeEnd;

	/**
	 * 预报位置
	 */
	@Column(name = "FORE_LOCATION")
	private String foreLocation;

	/**
	 * 预报强度
	 */
	@Column(name = "FORE_INTENSITY")
	private String foreIntensity;

	/**
	 * 报文类型(1:警报，2:更正报，3:取消报)
	 */
	@Column(name = "REPORT_TYPE")
	private Integer reportType;

	/**
	 * 取消报时间
	 */
	@Column(name = "CANCEL_TIME")
	private Date cancelTime;

	/**
	 * 取消报报文序号
	 */
	@Column(name = "CANCEL_NO")
	private String cancelNo;

	/**
	 * 报文发布序号
	 */
	@Column(name = "PUBLISH_ID")
	private String publishId;

	/**
	 * 报文发布时间
	 */
	@Column(name = "PUBLISH_TIME")
	private Date publishTime;

	/**
	 * 报文发布人
	 */
	@Column(name = "PUBLISHER")
	private String publisher;

	/**
	 * 报文发布内容
	 */
	@Column(name = "PUBLISH_CONTENT")
	private String publishContent;

	/**
	 * 获取报文ID
	 *
	 * @return ID - 报文ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置报文ID
	 *
	 * @param id
	 *            报文ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取数据插入时间
	 *
	 * @return INSERT_TIME - 数据插入时间
	 */
	public Date getInsertTime() {
		return insertTime;
	}

	/**
	 * 设置数据插入时间
	 *
	 * @param insertTime
	 *            数据插入时间
	 */
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	/**
	 * 获取数据更新时间
	 *
	 * @return UPDATE_TIME - 数据更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置数据更新时间
	 *
	 * @param updateTime
	 *            数据更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取物理删除flag(0:不删除,1:删除)
	 *
	 * @return ISDELETED - 物理删除flag(0:不删除,1:删除)
	 */
	public Integer getIsdeleted() {
		return isdeleted;
	}

	/**
	 * 设置物理删除flag(0:不删除,1:删除)
	 *
	 * @param isdeleted
	 *            物理删除flag(0:不删除,1:删除)
	 */
	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	/**
	 * 获取影响天气现象
	 *
	 * @return IMPACTED_WW - 影响天气现象
	 */
	public String getImpactedWw() {
		return impactedWw;
	}

	/**
	 * 设置影响天气现象
	 *
	 * @param impactedWw
	 *            影响天气现象
	 */
	public void setImpactedWw(String impactedWw) {
		this.impactedWw = impactedWw;
	}

	/**
	 * 获取位置
	 *
	 * @return LOCATION - 位置
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * 设置位置
	 *
	 * @param location
	 *            位置
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 获取强度
	 *
	 * @return INTENSITY - 强度
	 */
	public String getIntensity() {
		return intensity;
	}

	/**
	 * 设置强度
	 *
	 * @param intensity
	 *            强度
	 */
	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}

	/**
	 * 获取预报开始时间
	 *
	 * @return FORE_TIME_START - 预报开始时间
	 */
	public Date getForeTimeStart() {
		return foreTimeStart;
	}

	/**
	 * 设置预报开始时间
	 *
	 * @param foreTimeStart
	 *            预报开始时间
	 */
	public void setForeTimeStart(Date foreTimeStart) {
		this.foreTimeStart = foreTimeStart;
	}

	/**
	 * 获取预报结束时间
	 *
	 * @return FORE_TIME_END - 预报结束时间
	 */
	public Date getForeTimeEnd() {
		return foreTimeEnd;
	}

	/**
	 * 设置预报结束时间
	 *
	 * @param foreTimeEnd
	 *            预报结束时间
	 */
	public void setForeTimeEnd(Date foreTimeEnd) {
		this.foreTimeEnd = foreTimeEnd;
	}

	/**
	 * 获取预报位置
	 *
	 * @return FORE_LOCATION - 预报位置
	 */
	public String getForeLocation() {
		return foreLocation;
	}

	/**
	 * 设置预报位置
	 *
	 * @param foreLocation
	 *            预报位置
	 */
	public void setForeLocation(String foreLocation) {
		this.foreLocation = foreLocation;
	}

	/**
	 * 获取预报强度
	 *
	 * @return FORE_INTENSITY - 预报强度
	 */
	public String getForeIntensity() {
		return foreIntensity;
	}

	/**
	 * 设置预报强度
	 *
	 * @param foreIntensity
	 *            预报强度
	 */
	public void setForeIntensity(String foreIntensity) {
		this.foreIntensity = foreIntensity;
	}

	/**
	 * 获取报文类型(1:警报，2:更正报，3:取消报)
	 *
	 * @return REPORT_TYPE - 报文类型(1:警报，2:更正报，3:取消报)
	 */
	public Integer getReportType() {
		return reportType;
	}

	/**
	 * 设置报文类型(1:警报，2:更正报，3:取消报)
	 *
	 * @param reportType
	 *            报文类型(1:警报，2:更正报，3:取消报)
	 */
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	/**
	 * 获取报文发布序号
	 *
	 * @return PUBLISH_ID - 报文发布序号
	 */
	public String getPublishId() {
		return publishId;
	}

	/**
	 * 设置报文发布序号
	 *
	 * @param publishId
	 *            报文发布序号
	 */
	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	/**
	 * 获取报文发布时间
	 *
	 * @return PUBLISH_TIME - 报文发布时间
	 */
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * 设置报文发布时间
	 *
	 * @param publishTime
	 *            报文发布时间
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * 获取报文发布人
	 *
	 * @return PUBLISHER - 报文发布人
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * 设置报文发布人
	 *
	 * @param publisher
	 *            报文发布人
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * 获取报文发布内容
	 *
	 * @return PUBLISH_CONTENT - 报文发布内容
	 */
	public String getPublishContent() {
		return publishContent;
	}

	/**
	 * 设置报文发布内容
	 *
	 * @param publishContent
	 *            报文发布内容
	 */
	public void setPublishContent(String publishContent) {
		this.publishContent = publishContent;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelNo() {
		return cancelNo;
	}

	public void setCancelNo(String cancelNo) {
		this.cancelNo = cancelNo;
	}

	/**
	 * 报文生成
	 * 
	 * @return 报文
	 */
	public String formReport() {
		if (this.reportType == 3) {
			return formCancelReport();
		} else {
			return formWarnReport();
		}
	}

	private String formWarnReport() {
		StringBuilder builder = new StringBuilder();
		builder.append("受天气现象").append(this.impactedWw).append("的影响，长水机场在").append(this.location).append("已经出现了")
				.append(this.intensity).append("的风切变。预计北京时间")
				.append(DateUtil.dateToStr(this.foreTimeStart, DateUtil.YMDHM_STD)).append("－")
				.append(DateUtil.dateToStr(this.foreTimeEnd, DateUtil.YMDHM_STD)).append("在").append(this.foreLocation)
				.append("将有强度").append(this.foreIntensity).append("的风切变。");
		return builder.toString();
	}

	private String formCancelReport() {
		StringBuilder builder = new StringBuilder();
		builder.append("影响机场的").append(this.impactedWw).append("已经减弱。").append("取消")
				.append(DateUtil.dateToStr(this.cancelTime, DateUtil.YMDHM_STD)).append("发布的").append(this.cancelNo)
				.append("号机场警报（风切变）");
		return builder.toString();
	}
}