package com.nriet.datacenter.model.fuse;

/**
 * 回波顶高回波底高时序图数据实体类
 */
public class StormEcho {

	/**
	 * 时间
	 */
	private String time;

	/**
	 * 回波顶高
	 */
	private String echoTop;

	/**
	 * 回波底高
	 */
	private String echoBottom;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEchoTop() {
		return echoTop;
	}

	public void setEchoTop(String echoTop) {
		this.echoTop = echoTop;
	}

	public String getEchoBottom() {
		return echoBottom;
	}

	public void setEchoBottom(String echoBottom) {
		this.echoBottom = echoBottom;
	}

}
