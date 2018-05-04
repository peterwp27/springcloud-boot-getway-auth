package com.nriet.datacenter.model.cpzz;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nriet.framework.util.DateUtil;

/**
 * 产品制作低能见度天气告警报文发布单表实体类
 */
//@Entity
@Table(name = "pdm_vis_info")
public class PdmVisInfo {
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
	 * 能见度(单位：m）
	 */
	@Column(name = "VIS")
	private BigDecimal vis;

	/**
	 * 跑道视程(单位：m)
	 */
	@Column(name = "RVR")
	private BigDecimal rvr;

	/**
	 * 云底高(单位：m）
	 */
	@Column(name = "HCB")
	private BigDecimal hcb;

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
	 * 预报能见度(单位：m）
	 */
	@Column(name = "FORE_VIS")
	private BigDecimal foreVis;

	/**
	 * 预报能见度趋势(1:下降,2:不变,3：上升)
	 */
	@Column(name = "FORE_VIS_TREND")
	private Integer foreVisTrend;

	/**
	 * 预报跑道视程(单位：m）
	 */
	@Column(name = "FORE_RVR")
	private BigDecimal foreRvr;

	/**
	 * 预报跑道视程趋势(1:下降,2:不变,3：上升)
	 */
	@Column(name = "FORE_RVR_TREND")
	private Integer foreRvrTrend;

	/**
	 * 预报云底高(单位：m）
	 */
	@Column(name = "FORE_HCB")
	private BigDecimal foreHcb;

	/**
	 * 预报云底高趋势(1:下降,2:不变,3：上升)
	 */
	@Column(name = "FORE_HCB_TREND")
	private Integer foreHcbTrend;

	/**
	 * 预报开始时间
	 */
	@Column(name = "FORE_TIME_START2")
	private Date foreTimeStart2;

	/**
	 * 预报结束时间
	 */
	@Column(name = "FORE_TIME_END2")
	private Date foreTimeEnd2;

	/**
	 * 预报能见度(单位：m）
	 */
	@Column(name = "FORE_VIS2")
	private BigDecimal foreVis2;

	/**
	 * 预报能见度趋势(1:下降,2:不变,3：上升)
	 */
	@Column(name = "FORE_VIS_TREND2")
	private Integer foreVisTrend2;

	/**
	 * 预报跑道视程(单位：m）
	 */
	@Column(name = "FORE_RVR2")
	private BigDecimal foreRvr2;

	/**
	 * 预报跑道视程趋势(1:下降,2:不变,3：上升)
	 */
	@Column(name = "FORE_RVR_TREND2")
	private Integer foreRvrTrend2;

	/**
	 * 预报云底高(单位：m）
	 */
	@Column(name = "FORE_HCB2")
	private BigDecimal foreHcb2;

	/**
	 * 预报云底高趋势(1:下降,2:不变,3：上升)
	 */
	@Column(name = "FORE_HCB_TREND2")
	private Integer foreHcbTrend2;

	/**
	 * 报文类型(1:警报，2:更正报，3:取消报)
	 */
	@Column(name = "REPORT_TYPE")
	private Integer reportType;

	/**
	 * 取消报当前能见度
	 */
	@Column(name = "CANCEL_NOW_VIS")
	private BigDecimal cancel_now_vis;

	/**
	 * 取消报能见度>6000米预计时间
	 */
	@Column(name = "CANCEL_FORETIME")
	private Date cancel_foreTime;

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
	 * 获取能见度(单位：m）
	 *
	 * @return VIS - 能见度(单位：m）
	 */
	public BigDecimal getVis() {
		return vis;
	}

	/**
	 * 设置能见度(单位：m）
	 *
	 * @param vis
	 *            能见度(单位：m）
	 */
	public void setVis(BigDecimal vis) {
		this.vis = vis;
	}

	/**
	 * 获取跑道视程(单位：m)
	 *
	 * @return RVR - 跑道视程(单位：m)
	 */
	public BigDecimal getRvr() {
		return rvr;
	}

	/**
	 * 设置跑道视程(单位：m)
	 *
	 * @param rvr
	 *            跑道视程(单位：m)
	 */
	public void setRvr(BigDecimal rvr) {
		this.rvr = rvr;
	}

	/**
	 * 获取云底高(单位：m）
	 *
	 * @return HCB - 云底高(单位：m）
	 */
	public BigDecimal getHcb() {
		return hcb;
	}

	/**
	 * 设置云底高(单位：m）
	 *
	 * @param hcb
	 *            云底高(单位：m）
	 */
	public void setHcb(BigDecimal hcb) {
		this.hcb = hcb;
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
	 * 获取预报能见度(单位：m）
	 *
	 * @return FORE_VIS - 预报能见度(单位：m）
	 */
	public BigDecimal getForeVis() {
		return foreVis;
	}

	/**
	 * 设置预报能见度(单位：m）
	 *
	 * @param foreVis
	 *            预报能见度(单位：m）
	 */
	public void setForeVis(BigDecimal foreVis) {
		this.foreVis = foreVis;
	}

	/**
	 * 获取预报能见度趋势(1:下降,2:不变,3：上升)
	 *
	 * @return FORE_VIS_TREND - 预报能见度趋势(1:下降,2:不变,3：上升)
	 */
	public Integer getForeVisTrend() {
		return foreVisTrend;
	}

	/**
	 * 设置预报能见度趋势(1:下降,2:不变,3：上升)
	 *
	 * @param foreVisTrend
	 *            预报能见度趋势(1:下降,2:不变,3：上升)
	 */
	public void setForeVisTrend(Integer foreVisTrend) {
		this.foreVisTrend = foreVisTrend;
	}

	/**
	 * 获取预报跑道视程(单位：m）
	 *
	 * @return FORE_RVR - 预报跑道视程(单位：m）
	 */
	public BigDecimal getForeRvr() {
		return foreRvr;
	}

	/**
	 * 设置预报跑道视程(单位：m）
	 *
	 * @param foreRvr
	 *            预报跑道视程(单位：m）
	 */
	public void setForeRvr(BigDecimal foreRvr) {
		this.foreRvr = foreRvr;
	}

	/**
	 * 获取预报跑道视程趋势(1:下降,2:不变,3：上升)
	 *
	 * @return FORE_RVR_TREND - 预报跑道视程趋势(1:下降,2:不变,3：上升)
	 */
	public Integer getForeRvrTrend() {
		return foreRvrTrend;
	}

	/**
	 * 设置预报跑道视程趋势(1:下降,2:不变,3：上升)
	 *
	 * @param foreRvrTrend
	 *            预报跑道视程趋势(1:下降,2:不变,3：上升)
	 */
	public void setForeRvrTrend(Integer foreRvrTrend) {
		this.foreRvrTrend = foreRvrTrend;
	}

	/**
	 * 获取预报云底高(单位：m）
	 *
	 * @return FORE_HCB - 预报云底高(单位：m）
	 */
	public BigDecimal getForeHcb() {
		return foreHcb;
	}

	/**
	 * 设置预报云底高(单位：m）
	 *
	 * @param foreHcb
	 *            预报云底高(单位：m）
	 */
	public void setForeHcb(BigDecimal foreHcb) {
		this.foreHcb = foreHcb;
	}

	/**
	 * 获取预报云底高趋势(1:下降,2:不变,3：上升)
	 *
	 * @return FORE_HCB_TREND - 预报云底高趋势(1:下降,2:不变,3：上升)
	 */
	public Integer getForeHcbTrend() {
		return foreHcbTrend;
	}

	/**
	 * 设置预报云底高趋势(1:下降,2:不变,3：上升)
	 *
	 * @param foreHcbTrend
	 *            预报云底高趋势(1:下降,2:不变,3：上升)
	 */
	public void setForeHcbTrend(Integer foreHcbTrend) {
		this.foreHcbTrend = foreHcbTrend;
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

	public Date getForeTimeStart2() {
		return foreTimeStart2;
	}

	public void setForeTimeStart2(Date foreTimeStart2) {
		this.foreTimeStart2 = foreTimeStart2;
	}

	public Date getForeTimeEnd2() {
		return foreTimeEnd2;
	}

	public void setForeTimeEnd2(Date foreTimeEnd2) {
		this.foreTimeEnd2 = foreTimeEnd2;
	}

	public BigDecimal getForeVis2() {
		return foreVis2;
	}

	public void setForeVis2(BigDecimal foreVis2) {
		this.foreVis2 = foreVis2;
	}

	public Integer getForeVisTrend2() {
		return foreVisTrend2;
	}

	public void setForeVisTrend2(Integer foreVisTrend2) {
		this.foreVisTrend2 = foreVisTrend2;
	}

	public BigDecimal getForeRvr2() {
		return foreRvr2;
	}

	public void setForeRvr2(BigDecimal foreRvr2) {
		this.foreRvr2 = foreRvr2;
	}

	public Integer getForeRvrTrend2() {
		return foreRvrTrend2;
	}

	public void setForeRvrTrend2(Integer foreRvrTrend2) {
		this.foreRvrTrend2 = foreRvrTrend2;
	}

	public BigDecimal getForeHcb2() {
		return foreHcb2;
	}

	public void setForeHcb2(BigDecimal foreHcb2) {
		this.foreHcb2 = foreHcb2;
	}

	public Integer getForeHcbTrend2() {
		return foreHcbTrend2;
	}

	public void setForeHcbTrend2(Integer foreHcbTrend2) {
		this.foreHcbTrend2 = foreHcbTrend2;
	}

	public BigDecimal getCancel_now_vis() {
		return cancel_now_vis;
	}

	public void setCancel_now_vis(BigDecimal cancel_now_vis) {
		this.cancel_now_vis = cancel_now_vis;
	}

	public Date getCancel_foreTime() {
		return cancel_foreTime;
	}

	public void setCancel_foreTime(Date cancel_foreTime) {
		this.cancel_foreTime = cancel_foreTime;
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
		builder.append("长水机场目前能见度").append(this.vis).append("米，RVR").append(this.rvr).append("米，云底高").append(this.hcb)
				.append("米。预计北京时间").append(DateUtil.dateToStr(this.foreTimeStart, DateUtil.YMDHM_STD)).append("－")
				.append(DateUtil.dateToStr(this.foreTimeEnd, DateUtil.YMDHM_STD)).append("能见度")
				.append(this.foreVisTrend).append(this.foreVis).append("米，").append("RVR").append(this.foreRvrTrend)
				.append(this.foreRvr).append("米，").append("云底高").append(this.foreHcbTrend).append(this.foreHcb)
				.append("米，").append("北京时间").append(DateUtil.dateToStr(this.foreTimeStart2, DateUtil.YMDHM_STD))
				.append("－").append(DateUtil.dateToStr(this.foreTimeEnd2, DateUtil.YMDHM_STD)).append("能见度")
				.append(this.foreVisTrend2).append(this.foreVis2).append("米，").append("RVR").append(this.foreRvrTrend2)
				.append(this.foreRvr2).append("米，").append("云底高").append(this.foreHcbTrend2).append(this.foreHcb2)
				.append("米，");
		return builder.toString();
	}

	private String formCancelReport() {
		StringBuilder builder = new StringBuilder();
		builder.append("目前长水机场能见度已上升至").append(this.cancel_now_vis).append("米").append("预计")
				.append(DateUtil.dateToStr(this.cancel_foreTime, DateUtil.YMDHM_STD)).append("能见度将>6000米。取消")
				.append(DateUtil.dateToStr(this.cancelTime, DateUtil.YMDHM_STD)).append("发布的").append(this.cancelNo)
				.append("号机场警报（低能见度）");
		return builder.toString();
	}

}