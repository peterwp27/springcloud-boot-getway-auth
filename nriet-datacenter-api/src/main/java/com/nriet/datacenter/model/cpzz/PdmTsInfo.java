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
 * 产品制作雷暴天气告警报文发布单表实体类
 */
//@Entity
@Table(name = "pdm_ts_info")
public class PdmTsInfo {
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
	 * 方位
	 */
	@Column(name = "DIR")
	private String dir;

	/**
	 * 距离(单位：m)
	 */
	@Column(name = "DIST")
	private BigDecimal dist;

	/**
	 * 云顶高(单位：m)
	 */
	@Column(name = "HCT")
	private BigDecimal hct;

	/**
	 * 移速(单位：m/s)
	 */
	@Column(name = "SPEED")
	private BigDecimal speed;

	/**
	 * 移向
	 */
	@Column(name = "M_DIR")
	private String mDir;

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
	 * 伴随天气种类(1:强降水,2:大风,3:冰雹,4:低能见度,5:风切变)
	 */
	@Column(name = "FOLLOW_WEATHER")
	private String followWeather;

	/**
	 * 伴随天气开始时间
	 */
	@Column(name = "FOLLOW_TIME_START")
	private Date followTimeStart;

	/**
	 * 伴随天气结束时间
	 */
	@Column(name = "FOLLOW_TIME_END")
	private Date followTimeEnd;

	/**
	 * 伴随天气大风风向
	 */
	@Column(name = "FOLLOW_WD")
	private BigDecimal followWd;

	/**
	 * 伴随天气大风风速
	 */
	@Column(name = "FOLLOW_WS")
	private BigDecimal followWs;

	/**
	 * 伴随天气大风最大阵风
	 */
	@Column(name = "FOLLOW_QUST_WS")
	private BigDecimal followQustWs;

	/**
	 * 伴随天气低能见度
	 */
	@Column(name = "FOLLOW_VIS")
	private BigDecimal followVis;

	/**
	 * 伴随天气风切变
	 */
	@Column(name = "FOLLOW_WINDSHARE")
	private String followWindshare;

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
	 * 获取方位
	 *
	 * @return DIR - 方位
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * 设置方位
	 *
	 * @param dir
	 *            方位
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * 获取距离(单位：m)
	 *
	 * @return DIST - 距离(单位：m)
	 */
	public BigDecimal getDist() {
		return dist;
	}

	/**
	 * 设置距离(单位：m)
	 *
	 * @param dist
	 *            距离(单位：m)
	 */
	public void setDist(BigDecimal dist) {
		this.dist = dist;
	}

	/**
	 * 获取云顶高(单位：m)
	 *
	 * @return HCT - 云顶高(单位：m)
	 */
	public BigDecimal getHct() {
		return hct;
	}

	/**
	 * 设置云顶高(单位：m)
	 *
	 * @param hct
	 *            云顶高(单位：m)
	 */
	public void setHct(BigDecimal hct) {
		this.hct = hct;
	}

	/**
	 * 获取移速(单位：m/s)
	 *
	 * @return SPEED - 移速(单位：m/s)
	 */
	public BigDecimal getSpeed() {
		return speed;
	}

	/**
	 * 设置移速(单位：m/s)
	 *
	 * @param speed
	 *            移速(单位：m/s)
	 */
	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}

	/**
	 * 获取移向
	 *
	 * @return M_DIR - 移向
	 */
	public String getmDir() {
		return mDir;
	}

	/**
	 * 设置移向
	 *
	 * @param mDir
	 *            移向
	 */
	public void setmDir(String mDir) {
		this.mDir = mDir;
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
	 * 获取伴随天气种类(1:强降水,2:大风,3:冰雹,4:低能见度,5:风切变)
	 *
	 * @return FOLLOW_WEATHER - 伴随天气种类(1:强降水,2:大风,3:冰雹,4:低能见度,5:风切变)
	 */
	public String getFollowWeather() {
		return followWeather;
	}

	/**
	 * 设置伴随天气种类(1:强降水,2:大风,3:冰雹,4:低能见度,5:风切变)
	 *
	 * @param followWeather
	 *            伴随天气种类(1:强降水,2:大风,3:冰雹,4:低能见度,5:风切变)
	 */
	public void setFollowWeather(String followWeather) {
		this.followWeather = followWeather;
	}

	/**
	 * 获取伴随天气开始时间
	 *
	 * @return FOLLOW_TIME_START - 伴随天气开始时间
	 */
	public Date getFollowTimeStart() {
		return followTimeStart;
	}

	/**
	 * 设置伴随天气开始时间
	 *
	 * @param followTimeStart
	 *            伴随天气开始时间
	 */
	public void setFollowTimeStart(Date followTimeStart) {
		this.followTimeStart = followTimeStart;
	}

	/**
	 * 获取伴随天气结束时间
	 *
	 * @return FOLLOW_TIME_END - 伴随天气结束时间
	 */
	public Date getFollowTimeEnd() {
		return followTimeEnd;
	}

	/**
	 * 设置伴随天气结束时间
	 *
	 * @param followTimeEnd
	 *            伴随天气结束时间
	 */
	public void setFollowTimeEnd(Date followTimeEnd) {
		this.followTimeEnd = followTimeEnd;
	}

	/**
	 * 获取伴随天气大风风向
	 *
	 * @return FOLLOW_WD - 伴随天气大风风向
	 */
	public BigDecimal getFollowWd() {
		return followWd;
	}

	/**
	 * 设置伴随天气大风风向
	 *
	 * @param followWd
	 *            伴随天气大风风向
	 */
	public void setFollowWd(BigDecimal followWd) {
		this.followWd = followWd;
	}

	/**
	 * 获取伴随天气大风风速
	 *
	 * @return FOLLOW_WS - 伴随天气大风风速
	 */
	public BigDecimal getFollowWs() {
		return followWs;
	}

	/**
	 * 设置伴随天气大风风速
	 *
	 * @param followWs
	 *            伴随天气大风风速
	 */
	public void setFollowWs(BigDecimal followWs) {
		this.followWs = followWs;
	}

	/**
	 * 获取伴随天气大风最大阵风
	 *
	 * @return FOLLOW_QUST_WS - 伴随天气大风最大阵风
	 */
	public BigDecimal getFollowQustWs() {
		return followQustWs;
	}

	/**
	 * 设置伴随天气大风最大阵风
	 *
	 * @param followQustWs
	 *            伴随天气大风最大阵风
	 */
	public void setFollowQustWs(BigDecimal followQustWs) {
		this.followQustWs = followQustWs;
	}

	/**
	 * 获取伴随天气低能见度
	 *
	 * @return FOLLOW_VIS - 伴随天气低能见度
	 */
	public BigDecimal getFollowVis() {
		return followVis;
	}

	/**
	 * 设置伴随天气低能见度
	 *
	 * @param followVis
	 *            伴随天气低能见度
	 */
	public void setFollowVis(BigDecimal followVis) {
		this.followVis = followVis;
	}

	/**
	 * 获取伴随天气风切变
	 *
	 * @return FOLLOW_WINDSHARE - 伴随天气风切变
	 */
	public String getFollowWindshare() {
		return followWindshare;
	}

	/**
	 * 设置伴随天气风切变
	 *
	 * @param followWindshare
	 *            伴随天气风切变
	 */
	public void setFollowWindshare(String followWindshare) {
		this.followWindshare = followWindshare;
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
	 * 生成报文
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

	/**
	 * 生成警报报文
	 * 
	 * @return 报文
	 */
	private String formWarnReport() {
		StringBuilder builder = new StringBuilder();
		builder.append("长水机场目前已出现雷暴天气，在长水机场").append(this.dir).append("方向").append(this.dist).append("公里处有对流云团，")
				.append("云团顶高").append(this.hct).append("公里，以").append(this.speed).append("公里/小时的速度向").append(this.mDir)
				.append("移动，强度").append(this.intensity).append("。预计北京时间")
				.append(DateUtil.dateToStr(this.foreTimeStart, DateUtil.YMDHM_STD)).append("－")
				.append(DateUtil.dateToStr(this.foreTimeEnd, DateUtil.YMDHM_STD)).append("影响长水机场。");
		if (this.followWeather != null && !this.followWeather.isEmpty()) {
			String[] weathers = this.followWeather.split(",");
			if (this.foreTimeStart.equals(this.followTimeStart) && this.foreTimeEnd.equals(this.followTimeEnd)) {
				builder.append("其间长水机场还将出现");
			} else {
				builder.append("起止（北京时）").append(DateUtil.dateToStr(this.followTimeStart, DateUtil.YMDHM_STD))
						.append("－").append(DateUtil.dateToStr(this.followTimeEnd, DateUtil.YMDHM_STD))
						.append("长水机场还将出现");
			}
			for (String weather : weathers) {
				if ("1".equals(weather)) {
					builder.append("强降水、");
				} else if ("2".equals(weather)) {
					builder.append("大风、");
				} else if ("3".equals(weather)) {
					builder.append("冰雹、");
				} else if ("4".equals(weather)) {
					builder.append("低能见度、");
				} else if ("5".equals(weather)) {
					builder.append("风切变、");
				}
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append("等天气。");
		}
		return builder.toString();
	}

	/**
	 * 生成取消报文
	 * 
	 * @return 报文
	 */
	private String formCancelReport() {
		StringBuilder builder = new StringBuilder();
		builder.append("目前长水机场对流云团位于机场").append(this.dir).append("方向").append(this.dist).append("公里处，强度")
				.append(this.intensity).append("，预计").append(DateUtil.dateToStr(this.foreTimeStart, DateUtil.YMDHM_STD))
				.append("雷暴将结束。取消").append(DateUtil.dateToStr(this.cancelTime, DateUtil.YMDHM_STD)).append("发布的")
				.append(this.cancelNo).append("号机场警报（雷暴）。");
		return builder.toString();
	}

}