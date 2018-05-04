package com.nriet.datacenter.model.fuse;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TitanData implements Serializable {
	private static final long serialVersionUID = -7033601741285049817L;
	/**
	 * 当前风暴时间
	 */
	private Date time;
	/**
	 * 当前风暴
	 */
	private List<TitanStorm> curStormList;
	/**
	 * 过去风暴时间序列（与过去1小时风暴对应）
	 */
	private Date[] pastTimes;
	/**
	 * 过去1小时风暴（当前风暴时间前，每6分钟一次，共10时次，每个时次有多个风暴）
	 */
	private List<TitanStorm>[] pastStormLists;
	/**
	 * 预报风暴时间序列（与预报风暴对应）
	 */
	private Date[] fcstTimes;
	/**
	 * 预报风暴（预报时效：10、20、30、40、50、60分钟，共6时次）
	 */
	private List<TitanStorm>[] fcstStormLists;
	/**
	 * 历史轨迹（风暴轨迹点使用其中心经纬度）
	 */
	private TitanStormTrack[] hisTracks;
	/** 
	 * 预报轨迹（风暴轨迹点使用其中心经纬度）
	 */
	private TitanStormTrack[] fcstTracks;
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public List<TitanStorm> getCurStormList() {
		return curStormList;
	}
	public void setCurStormList(List<TitanStorm> curStormList) {
		this.curStormList = curStormList;
	}
	public Date[] getPastTimes() {
		return pastTimes;
	}
	public void setPastTimes(Date[] pastTimes) {
		this.pastTimes = pastTimes;
	}
	public List<TitanStorm>[] getPastStormLists() {
		return pastStormLists;
	}
	public void setPastStormLists(List<TitanStorm>[] pastStormLists) {
		this.pastStormLists = pastStormLists;
	}
	public Date[] getFcstTimes() {
		return fcstTimes;
	}
	public void setFcstTimes(Date[] fcstTimes) {
		this.fcstTimes = fcstTimes;
	}
	public List<TitanStorm>[] getFcstStormLists() {
		return fcstStormLists;
	}
	public void setFcstStormLists(List<TitanStorm>[] fcstStormLists) {
		this.fcstStormLists = fcstStormLists;
	}
	public TitanStormTrack[] getHisTracks() {
		return hisTracks;
	}
	public void setHisTracks(TitanStormTrack[] hisTracks) {
		this.hisTracks = hisTracks;
	}
	public TitanStormTrack[] getFcstTracks() {
		return fcstTracks;
	}
	public void setFcstTracks(TitanStormTrack[] fcstTracks) {
		this.fcstTracks = fcstTracks;
	}
}
