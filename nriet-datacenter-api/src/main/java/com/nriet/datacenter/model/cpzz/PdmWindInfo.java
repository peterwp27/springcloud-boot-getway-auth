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
 * 产品制作大风天气告警报文发布单表实体类
 */
//@Entity
@Table(name = "pdm_wind_info")
public class PdmWindInfo {
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
     * 风速(单位:m/s)
     */
    @Column(name = "WS")
    private BigDecimal ws;

    /**
     * 风向(单位：°)
     */
    @Column(name = "WD")
    private BigDecimal wd;

    /**
     * 阵风风速(单位：m/s)
     */
    @Column(name = "GUST_WS")
    private BigDecimal gustWs;

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
     * 预报风速(单位:m/s)
     */
    @Column(name = "FORE_WS")
    private BigDecimal foreWs;

    /**
     * 预报风向(单位：°)
     */
    @Column(name = "FORE_WD")
    private String foreWd;

    /**
     * 预报阵风风速(单位:m/s)
     */
    @Column(name = "FORE_GUST_WS")
    private BigDecimal foreGustWs;

    /**
     * 预报颠簸或风切变开始时间
     */
    @Column(name = "FORE_OTHER_TIME_START")
    private Date foreOtherTimeStart;

    /**
     * 预报颠簸或风切变结束时间
     */
    @Column(name = "FORE_OTHER_TIME_END")
    private Date foreOtherTimeEnd;

    /**
     * 预报颠簸或风切变位置
     */
    @Column(name = "FORE_OTHER_LOCATION")
    private String foreOtherLocation;

    /**
     * 预报颠簸或风切变强度
     */
    @Column(name = "FORE_OTHER_INTENSITY")
    private String foreOtherIntensity;

    /**
     * 颠簸或风切变（1：颠簸，2：风切变）
     */
    @Column(name = "FORE_OTHER_WEATHER")
    private String foreOtherWeather;

    /**
     * 报文类型(1:警报，2:更正报，3:取消报)
     */
    @Column(name = "REPORT_TYPE")
    private Integer reportType;

    /**
     * 取消报文风强度
     */
    @Column(name = "CANCEL_WIND_INTENSITY")
    private String cancel_wind_intensity;
    
    /**
     * 取消报文风速
     */
    @Column(name = "CANCEL_WS")
    private BigDecimal cancel_ws; 
    
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
     * @param id 报文ID
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
     * @param insertTime 数据插入时间
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
     * @param updateTime 数据更新时间
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
     * @param isdeleted 物理删除flag(0:不删除,1:删除)
     */
    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }

    /**
     * 获取风速(单位:m/s)
     *
     * @return WS - 风速(单位:m/s)
     */
    public BigDecimal getWs() {
        return ws;
    }

    /**
     * 设置风速(单位:m/s)
     *
     * @param ws 风速(单位:m/s)
     */
    public void setWs(BigDecimal ws) {
        this.ws = ws;
    }

    /**
     * 获取风向(单位：°)
     *
     * @return WD - 风向(单位：°)
     */
    public BigDecimal getWd() {
        return wd;
    }

    /**
     * 设置风向(单位：°)
     *
     * @param wd 风向(单位：°)
     */
    public void setWd(BigDecimal wd) {
        this.wd = wd;
    }

    /**
     * 获取阵风风速(单位：m/s)
     *
     * @return GUST_WS - 阵风风速(单位：m/s)
     */
    public BigDecimal getGustWs() {
        return gustWs;
    }

    /**
     * 设置阵风风速(单位：m/s)
     *
     * @param gustWs 阵风风速(单位：m/s)
     */
    public void setGustWs(BigDecimal gustWs) {
        this.gustWs = gustWs;
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
     * @param foreTimeStart 预报开始时间
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
     * @param foreTimeEnd 预报结束时间
     */
    public void setForeTimeEnd(Date foreTimeEnd) {
        this.foreTimeEnd = foreTimeEnd;
    }

    /**
     * 获取预报风速(单位:m/s)
     *
     * @return FORE_WS - 预报风速(单位:m/s)
     */
    public BigDecimal getForeWs() {
        return foreWs;
    }

    /**
     * 设置预报风速(单位:m/s)
     *
     * @param foreWs 预报风速(单位:m/s)
     */
    public void setForeWs(BigDecimal foreWs) {
        this.foreWs = foreWs;
    }

    /**
     * 获取预报风向(单位：°)
     *
     * @return FORE_WD - 预报风向(单位：°)
     */
    public String getForeWd() {
        return foreWd;
    }

    /**
     * 设置预报风向(单位：°)
     *
     * @param foreWd 预报风向(单位：°)
     */
    public void setForeWd(String foreWd) {
        this.foreWd = foreWd;
    }

    /**
     * 获取预报阵风风速(单位:m/s)
     *
     * @return FORE_GUST_WS - 预报阵风风速(单位:m/s)
     */
    public BigDecimal getForeGustWs() {
        return foreGustWs;
    }

    /**
     * 设置预报阵风风速(单位:m/s)
     *
     * @param foreGustWs 预报阵风风速(单位:m/s)
     */
    public void setForeGustWs(BigDecimal foreGustWs) {
        this.foreGustWs = foreGustWs;
    }

    /**
     * 获取预报颠簸或风切变开始时间
     *
     * @return FORE_OTHER_TIME_START - 预报颠簸或风切变开始时间
     */
    public Date getForeOtherTimeStart() {
        return foreOtherTimeStart;
    }

    /**
     * 设置预报颠簸或风切变开始时间
     *
     * @param foreOtherTimeStart 预报颠簸或风切变开始时间
     */
    public void setForeOtherTimeStart(Date foreOtherTimeStart) {
        this.foreOtherTimeStart = foreOtherTimeStart;
    }

    /**
     * 获取预报颠簸或风切变结束时间
     *
     * @return FORE_OTHER_TIME_END - 预报颠簸或风切变结束时间
     */
    public Date getForeOtherTimeEnd() {
        return foreOtherTimeEnd;
    }

    /**
     * 设置预报颠簸或风切变结束时间
     *
     * @param foreOtherTimeEnd 预报颠簸或风切变结束时间
     */
    public void setForeOtherTimeEnd(Date foreOtherTimeEnd) {
        this.foreOtherTimeEnd = foreOtherTimeEnd;
    }

    /**
     * 获取预报颠簸或风切变位置
     *
     * @return FORE_OTHER_LOCATION - 预报颠簸或风切变位置
     */
    public String getForeOtherLocation() {
        return foreOtherLocation;
    }

    /**
     * 设置预报颠簸或风切变位置
     *
     * @param foreOtherLocation 预报颠簸或风切变位置
     */
    public void setForeOtherLocation(String foreOtherLocation) {
        this.foreOtherLocation = foreOtherLocation;
    }

    /**
     * 获取预报颠簸或风切变强度
     *
     * @return FORE_OTHER_INTENSITY - 预报颠簸或风切变强度
     */
    public String getForeOtherIntensity() {
        return foreOtherIntensity;
    }

    /**
     * 设置预报颠簸或风切变强度
     *
     * @param foreOtherIntensity 预报颠簸或风切变强度
     */
    public void setForeOtherIntensity(String foreOtherIntensity) {
        this.foreOtherIntensity = foreOtherIntensity;
    }

    /**
     * 获取颠簸或风切变（1：颠簸，2：风切变）
     *
     * @return FORE_OTHER_WEATHER - 颠簸或风切变（1：颠簸，2：风切变）
     */
    public String getForeOtherWeather() {
        return foreOtherWeather;
    }

    /**
     * 设置颠簸或风切变（1：颠簸，2：风切变）
     *
     * @param foreOtherWeather 颠簸或风切变（1：颠簸，2：风切变）
     */
    public void setForeOtherWeather(String foreOtherWeather) {
        this.foreOtherWeather = foreOtherWeather;
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
     * @param reportType 报文类型(1:警报，2:更正报，3:取消报)
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
     * @param publishId 报文发布序号
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
     * @param publishTime 报文发布时间
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
     * @param publisher 报文发布人
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
     * @param publishContent 报文发布内容
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

	public String getCancel_wind_intensity() {
		return cancel_wind_intensity;
	}

	public void setCancel_wind_intensity(String cancel_wind_intensity) {
		this.cancel_wind_intensity = cancel_wind_intensity;
	}

	public BigDecimal getCancel_ws() {
		return cancel_ws;
	}

	public void setCancel_ws(BigDecimal cancel_ws) {
		this.cancel_ws = cancel_ws;
	}
	
	/**
	 * 报文生成
	 * 
	 * @return 报文
	 */
	public String formReport() {
		if(this.reportType == 3) {
			return formCancelReport();
		}else {
			return formWarnReport();
		}
	}
	
	private String formWarnReport() {
		StringBuilder builder = new StringBuilder();
		builder.append("长水机场目前已出现风天气，风速").append(this.ws).append("米/秒，风向")
				.append(this.wd).append("，阵风风速").append(this.gustWs).append("米/秒。预计北京时")
				.append(DateUtil.dateToStr(this.foreTimeStart, DateUtil.YMDHM_STD)).append("－")
				.append(DateUtil.dateToStr(this.foreTimeEnd, DateUtil.YMDHM_STD))
				.append("将出现平均风速").append(this.foreWs).append("米/秒，风向")
				.append(this.foreWd).append("的风，阵风风速").append(this.foreGustWs).append("米/秒");
		if(this.foreOtherWeather != null && !this.foreOtherWeather.isEmpty()) {
			if(this.foreTimeStart.equals(this.foreOtherTimeStart) 
					&& this.foreTimeEnd.equals(this.foreOtherTimeEnd)) {
				builder.append("其间");
			}else {
				builder.append("起止（北京时）").append(DateUtil.dateToStr(this.foreOtherTimeStart, DateUtil.YMDHM_STD))
						.append("－").append(DateUtil.dateToStr(this.foreOtherTimeEnd, DateUtil.YMDHM_STD));
			}
			builder.append(this.foreOtherLocation).append("将出现").append(this.foreOtherIntensity).append("的");
			String[] weathers = this.foreOtherWeather.split(",");
			for(String weather : weathers) {
				if("1".equals(weather)) {
					builder.append("颠簸、");
				}else if("2".equals(weather)) {
					builder.append("风切变、");
				}
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append("等伴随天气。");
		}
		return builder.toString();
	}
	
	private String formCancelReport() {
		StringBuilder builder = new StringBuilder();
		builder.append("机场已转至").append(this.cancel_wind_intensity).append("风，")
			.append("风速减小至").append(this.cancel_ws).append("米/秒。").append("取消")
			.append(DateUtil.dateToStr(this.cancelTime, DateUtil.YMDHM_STD)).append("发布的")
			.append(this.cancelNo).append("号机场警报（大风）");
		return builder.toString();
	}
}