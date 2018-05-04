package com.nriet.datacenter.model.fuse;

import java.io.Serializable;
import java.util.Date;

/**
 * 同一风暴演变轨迹
 */
public class TitanStormTrack implements Serializable {
	private static final long serialVersionUID = 5220999567353761711L;
	/**
	 * 时间序列（与风暴序列对应）
	 */
	private Date[] times;
	/**
	 * 风暴序列（按时间演变顺序排列）
	 */
	private TitanStorm[] storms;
	public Date[] getTimes() {
		return times;
	}
	public void setTimes(Date[] times) {
		this.times = times;
	}
	public TitanStorm[] getStorms() {
		return storms;
	}
	public void setStorms(TitanStorm[] storms) {
		this.storms = storms;
	}
}
