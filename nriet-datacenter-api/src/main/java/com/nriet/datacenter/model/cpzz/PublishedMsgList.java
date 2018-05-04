package com.nriet.datacenter.model.cpzz;

/**
 * 产品制作已发布报文信息（预览用）实体类
 */
public class PublishedMsgList {

	/**
	 * 报文发布序号
	 */
	private String msgId;

	/**
	 * 发布日期
	 */
	private String publishDate;

	/**
	 * 发布人
	 */
	private String publisher;

	/**
	 * 报文类型(警报,更正报,取消报)
	 */
	private String warnType;

	/**
	 * 报文内容
	 */
	private String reportContent;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getWarnType() {
		return warnType;
	}

	public void setWarnType(String warnType) {
		this.warnType = warnType;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

}
