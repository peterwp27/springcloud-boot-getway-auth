/**
 * 文件名：Awos.java
 * 描述：机场自观
 * 版权：Copyright (C) 2017 NRIET
 * 作者：Kai Tang
 * 日期：2017-5-8
 */
package com.nriet.datacenter.model.mhzl;

import java.io.Serializable;

/**
 * 机场自观bean
 */
public class Awos implements Serializable {

	private static final long serialVersionUID = 1L;

	private String station_id; // 机场站点

	private String datetime; // 观测时间

	private Float lon; // 经度

	private Float lat; // 纬度

	private Float xCoord; // 站点的x坐标

	private Float yCoord; // 站点的y坐标

	private String direction; // 跑道方向

	private String rno; // 跑道号

	private String tdz; // 跑道第一方向标识

	private String mid; // 跑道中间方向标识

	private String end; // 跑道第二方向标识

	private String tdz_rvr_1m; // 跑道第一方向1分钟RVR平均值

	private String tdz_rvr_10m; // 跑道第一方向10分钟RVR平均值

	private String tdz_mor_1m; // 跑道第一方向1分钟MOR平均值

	private String tdz_mor_10m; // 跑道第一方向10分钟MOR平均值

	private String tdz_bl_1m; // 跑道第一方向背景亮度1分钟平均值

	private String tdz_ms_2m; // 跑道第一方向2分钟平均风风速

	private String tdz_md_2m; // 跑道第一方向2分钟平均风风向

	private String tdz_ms_10m; // 跑道第一方向10分钟平均风风速

	private String tdz_md_10m; // 跑道第一方向10分钟平均风风向

	private String tdz_ms_max; // 跑道第一方向最大阵风风速

	private String tdz_md_max; // 跑道第一方向最大阵风风向

	private String tdz_slp; // 跑道第一方向修正海压

	private String tdz_sp; // 跑道第一方向场压

	private String tdz_t; // 跑道第一方向温度

	private String tdz_rh; // 跑道第一方向相对湿度

	private String tdz_dpt; // 跑道第一方向露点温度

	private String tdz_lst; // 跑道第一方向道面温度

	private String tdz_cld_hl; // 跑道第一方向低层云底高

	private String tdz_cld_hm; // 跑道第一方向中层云底高

	private String tdz_cld_hh; // 跑道第一方向高层云底高

	private String tdz_vis; // 跑道第一方向垂直能见度

	private String tdz_wp; // 跑道第一方向天气现象

	private String tdz_raini; // 跑道第一方向降水强度

	private String tdz_rain; // 跑道第一方向降水量

	private String tdz_reserve1; // 跑道第一方向备用1

	private String tdz_reserve2; // 跑道第一方向备用2

	private String tdz_reserve3; // 跑道第一方向备用3

	private String mid_rvr_1m; // 中间端1分钟RVR平均值

	private String mid_rvr_10m; // 中间端10分钟RVR平均值

	private String mid_mor_1m; // 中间端1分钟MOR平均值

	private String mid_mor_10m; // 中间端10分钟MOR平均值

	private String mid_bl_1m; // 中间端背景亮度1分钟平均值

	private String mid_ms_2m; // 中间端2分钟平均风风速

	private String mid_md_2m; // 中间端2分钟平均风风向

	private String mid_ms_10m; // 中间端10分钟平均风风速

	private String mid_md_10m; // 中间端10分钟平均风风向

	private String mid_ms_max; // 中间端最大阵风风速

	private String mid_md_max; // 中间端最大阵风风向

	private String mid_slp; // 中间端修正海压

	private String mid_sp; // 中间端场压

	private String mid_t; // 中间端温度

	private String mid_rh; // 中间端相对湿度

	private String mid_dpt; // 中间端露点温度

	private String mid_lst; // 中间端道面温度

	private String mid_cld_hl; // 中间端低层云底高

	private String mid_cld_hm; // 中间端中层云底高

	private String mid_cld_hh; // 中间端高层云底高

	private String mid_vis; // 中间端垂直能见度

	private String mid_wp; // 中间端天气现象

	private String mid_raini; // 中间端降水强度

	private String mid_rain; // 中间端降水量

	private String mid_reserve1; // 中间端备用1

	private String mid_reserve2; // 中间端备用2

	private String mid_reserve3; // 中间端备用3

	private String end_rvr_1m; // 跑道第二方向1分钟RVR平均值

	private String end_rvr_10m; // 跑道第二方向10分钟RVR平均值

	private String end_mor_1m; // 跑道第二方向1分钟MOR平均值

	private String end_mor_10m; // 跑道第二方向10分钟MOR平均值

	private String end_bl_1m; // 跑道第二方向背景亮度1分钟平均值

	private String end_ms_2m; // 跑道第二方向2分钟平均风风速

	private String end_md_2m; // 跑道第二方向2分钟平均风风向

	private String end_ms_10m; // 跑道第二方向10分钟平均风风速

	private String end_md_10m; // 跑道第二方向10分钟平均风风向

	private String end_ms_max; // 跑道第二方向最大阵风风速

	private String end_md_max; // 跑道第二方向最大阵风风向

	private String end_slp; // 跑道第二方向修正海压

	private String end_sp; // 跑道第二方向场压

	private String end_t; // 跑道第二方向温度

	private String end_rh; // 跑道第二方向相对湿度

	private String end_dpt; // 跑道第二方向露点温度

	private String end_lst; // 跑道第二方向道面温度

	private String end_cld_hl; // 跑道第二方向低层云底高

	private String end_cld_hm; // 跑道第二方向中层云底高

	private String end_cld_hh; // 跑道第二方向高层云底高

	private String end_vis; // 跑道第二方向垂直能见度

	private String end_wp; // 跑道第二方向天气现象

	private String end_raini; // 跑道第二方向降水强度

	private String end_rain; // 跑道第二方向降水量

	private String end_reserve1; // 跑道第二方向备用1

	private String end_reserve2; // 跑道第二方向备用2

	private String end_reserve3; // 跑道第二方向备用3

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getRno() {
		return rno;
	}

	public void setRno(String rno) {
		this.rno = rno;
	}

	public String getTdz() {
		return tdz;
	}

	public void setTdz(String tdz) {
		this.tdz = tdz;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTdz_rvr_1m() {
		return tdz_rvr_1m;
	}

	public void setTdz_rvr_1m(String tdz_rvr_1m) {
		this.tdz_rvr_1m = tdz_rvr_1m;
	}

	public String getTdz_rvr_10m() {
		return tdz_rvr_10m;
	}

	public void setTdz_rvr_10m(String tdz_rvr_10m) {
		this.tdz_rvr_10m = tdz_rvr_10m;
	}

	public String getTdz_mor_1m() {
		return tdz_mor_1m;
	}

	public void setTdz_mor_1m(String tdz_mor_1m) {
		this.tdz_mor_1m = tdz_mor_1m;
	}

	public String getTdz_mor_10m() {
		return tdz_mor_10m;
	}

	public void setTdz_mor_10m(String tdz_mor_10m) {
		this.tdz_mor_10m = tdz_mor_10m;
	}

	public String getTdz_bl_1m() {
		return tdz_bl_1m;
	}

	public void setTdz_bl_1m(String tdz_bl_1m) {
		this.tdz_bl_1m = tdz_bl_1m;
	}

	public String getTdz_ms_2m() {
		return tdz_ms_2m;
	}

	public void setTdz_ms_2m(String tdz_ms_2m) {
		this.tdz_ms_2m = tdz_ms_2m;
	}

	public String getTdz_md_2m() {
		return tdz_md_2m;
	}

	public void setTdz_md_2m(String tdz_md_2m) {
		this.tdz_md_2m = tdz_md_2m;
	}

	public String getTdz_ms_10m() {
		return tdz_ms_10m;
	}

	public void setTdz_ms_10m(String tdz_ms_10m) {
		this.tdz_ms_10m = tdz_ms_10m;
	}

	public String getTdz_md_10m() {
		return tdz_md_10m;
	}

	public void setTdz_md_10m(String tdz_md_10m) {
		this.tdz_md_10m = tdz_md_10m;
	}

	public String getTdz_ms_max() {
		return tdz_ms_max;
	}

	public void setTdz_ms_max(String tdz_ms_max) {
		this.tdz_ms_max = tdz_ms_max;
	}

	public String getTdz_md_max() {
		return tdz_md_max;
	}

	public void setTdz_md_max(String tdz_md_max) {
		this.tdz_md_max = tdz_md_max;
	}

	public String getTdz_slp() {
		return tdz_slp;
	}

	public void setTdz_slp(String tdz_slp) {
		this.tdz_slp = tdz_slp;
	}

	public String getTdz_sp() {
		return tdz_sp;
	}

	public void setTdz_sp(String tdz_sp) {
		this.tdz_sp = tdz_sp;
	}

	public String getTdz_t() {
		return tdz_t;
	}

	public void setTdz_t(String tdz_t) {
		this.tdz_t = tdz_t;
	}

	public String getTdz_rh() {
		return tdz_rh;
	}

	public void setTdz_rh(String tdz_rh) {
		this.tdz_rh = tdz_rh;
	}

	public String getTdz_dpt() {
		return tdz_dpt;
	}

	public void setTdz_dpt(String tdz_dpt) {
		this.tdz_dpt = tdz_dpt;
	}

	public String getTdz_lst() {
		return tdz_lst;
	}

	public void setTdz_lst(String tdz_lst) {
		this.tdz_lst = tdz_lst;
	}

	public String getTdz_cld_hl() {
		return tdz_cld_hl;
	}

	public void setTdz_cld_hl(String tdz_cld_hl) {
		this.tdz_cld_hl = tdz_cld_hl;
	}

	public String getTdz_cld_hm() {
		return tdz_cld_hm;
	}

	public void setTdz_cld_hm(String tdz_cld_hm) {
		this.tdz_cld_hm = tdz_cld_hm;
	}

	public String getTdz_cld_hh() {
		return tdz_cld_hh;
	}

	public void setTdz_cld_hh(String tdz_cld_hh) {
		this.tdz_cld_hh = tdz_cld_hh;
	}

	public String getTdz_vis() {
		return tdz_vis;
	}

	public void setTdz_vis(String tdz_vis) {
		this.tdz_vis = tdz_vis;
	}

	public String getTdz_wp() {
		return tdz_wp;
	}

	public void setTdz_wp(String tdz_wp) {
		this.tdz_wp = tdz_wp;
	}

	public String getTdz_raini() {
		return tdz_raini;
	}

	public void setTdz_raini(String tdz_raini) {
		this.tdz_raini = tdz_raini;
	}

	public String getTdz_rain() {
		return tdz_rain;
	}

	public void setTdz_rain(String tdz_rain) {
		this.tdz_rain = tdz_rain;
	}

	public String getTdz_reserve1() {
		return tdz_reserve1;
	}

	public void setTdz_reserve1(String tdz_reserve1) {
		this.tdz_reserve1 = tdz_reserve1;
	}

	public String getTdz_reserve2() {
		return tdz_reserve2;
	}

	public void setTdz_reserve2(String tdz_reserve2) {
		this.tdz_reserve2 = tdz_reserve2;
	}

	public String getTdz_reserve3() {
		return tdz_reserve3;
	}

	public void setTdz_reserve3(String tdz_reserve3) {
		this.tdz_reserve3 = tdz_reserve3;
	}

	public String getMid_rvr_1m() {
		return mid_rvr_1m;
	}

	public void setMid_rvr_1m(String mid_rvr_1m) {
		this.mid_rvr_1m = mid_rvr_1m;
	}

	public String getMid_rvr_10m() {
		return mid_rvr_10m;
	}

	public void setMid_rvr_10m(String mid_rvr_10m) {
		this.mid_rvr_10m = mid_rvr_10m;
	}

	public String getMid_mor_1m() {
		return mid_mor_1m;
	}

	public void setMid_mor_1m(String mid_mor_1m) {
		this.mid_mor_1m = mid_mor_1m;
	}

	public String getMid_mor_10m() {
		return mid_mor_10m;
	}

	public void setMid_mor_10m(String mid_mor_10m) {
		this.mid_mor_10m = mid_mor_10m;
	}

	public String getMid_bl_1m() {
		return mid_bl_1m;
	}

	public void setMid_bl_1m(String mid_bl_1m) {
		this.mid_bl_1m = mid_bl_1m;
	}

	public String getMid_ms_2m() {
		return mid_ms_2m;
	}

	public void setMid_ms_2m(String mid_ms_2m) {
		this.mid_ms_2m = mid_ms_2m;
	}

	public String getMid_md_2m() {
		return mid_md_2m;
	}

	public void setMid_md_2m(String mid_md_2m) {
		this.mid_md_2m = mid_md_2m;
	}

	public String getMid_ms_10m() {
		return mid_ms_10m;
	}

	public void setMid_ms_10m(String mid_ms_10m) {
		this.mid_ms_10m = mid_ms_10m;
	}

	public String getMid_md_10m() {
		return mid_md_10m;
	}

	public void setMid_md_10m(String mid_md_10m) {
		this.mid_md_10m = mid_md_10m;
	}

	public String getMid_ms_max() {
		return mid_ms_max;
	}

	public void setMid_ms_max(String mid_ms_max) {
		this.mid_ms_max = mid_ms_max;
	}

	public String getMid_md_max() {
		return mid_md_max;
	}

	public void setMid_md_max(String mid_md_max) {
		this.mid_md_max = mid_md_max;
	}

	public String getMid_slp() {
		return mid_slp;
	}

	public void setMid_slp(String mid_slp) {
		this.mid_slp = mid_slp;
	}

	public String getMid_sp() {
		return mid_sp;
	}

	public void setMid_sp(String mid_sp) {
		this.mid_sp = mid_sp;
	}

	public String getMid_t() {
		return mid_t;
	}

	public void setMid_t(String mid_t) {
		this.mid_t = mid_t;
	}

	public String getMid_rh() {
		return mid_rh;
	}

	public void setMid_rh(String mid_rh) {
		this.mid_rh = mid_rh;
	}

	public String getMid_dpt() {
		return mid_dpt;
	}

	public void setMid_dpt(String mid_dpt) {
		this.mid_dpt = mid_dpt;
	}

	public String getMid_lst() {
		return mid_lst;
	}

	public void setMid_lst(String mid_lst) {
		this.mid_lst = mid_lst;
	}

	public String getMid_cld_hl() {
		return mid_cld_hl;
	}

	public void setMid_cld_hl(String mid_cld_hl) {
		this.mid_cld_hl = mid_cld_hl;
	}

	public String getMid_cld_hm() {
		return mid_cld_hm;
	}

	public void setMid_cld_hm(String mid_cld_hm) {
		this.mid_cld_hm = mid_cld_hm;
	}

	public String getMid_cld_hh() {
		return mid_cld_hh;
	}

	public void setMid_cld_hh(String mid_cld_hh) {
		this.mid_cld_hh = mid_cld_hh;
	}

	public String getMid_vis() {
		return mid_vis;
	}

	public void setMid_vis(String mid_vis) {
		this.mid_vis = mid_vis;
	}

	public String getMid_wp() {
		return mid_wp;
	}

	public void setMid_wp(String mid_wp) {
		this.mid_wp = mid_wp;
	}

	public String getMid_raini() {
		return mid_raini;
	}

	public void setMid_raini(String mid_raini) {
		this.mid_raini = mid_raini;
	}

	public String getMid_rain() {
		return mid_rain;
	}

	public void setMid_rain(String mid_rain) {
		this.mid_rain = mid_rain;
	}

	public String getMid_reserve1() {
		return mid_reserve1;
	}

	public void setMid_reserve1(String mid_reserve1) {
		this.mid_reserve1 = mid_reserve1;
	}

	public String getMid_reserve2() {
		return mid_reserve2;
	}

	public void setMid_reserve2(String mid_reserve2) {
		this.mid_reserve2 = mid_reserve2;
	}

	public String getMid_reserve3() {
		return mid_reserve3;
	}

	public void setMid_reserve3(String mid_reserve3) {
		this.mid_reserve3 = mid_reserve3;
	}

	public String getEnd_rvr_1m() {
		return end_rvr_1m;
	}

	public void setEnd_rvr_1m(String end_rvr_1m) {
		this.end_rvr_1m = end_rvr_1m;
	}

	public String getEnd_rvr_10m() {
		return end_rvr_10m;
	}

	public void setEnd_rvr_10m(String end_rvr_10m) {
		this.end_rvr_10m = end_rvr_10m;
	}

	public String getEnd_mor_1m() {
		return end_mor_1m;
	}

	public void setEnd_mor_1m(String end_mor_1m) {
		this.end_mor_1m = end_mor_1m;
	}

	public String getEnd_mor_10m() {
		return end_mor_10m;
	}

	public void setEnd_mor_10m(String end_mor_10m) {
		this.end_mor_10m = end_mor_10m;
	}

	public String getEnd_bl_1m() {
		return end_bl_1m;
	}

	public void setEnd_bl_1m(String end_bl_1m) {
		this.end_bl_1m = end_bl_1m;
	}

	public String getEnd_ms_2m() {
		return end_ms_2m;
	}

	public void setEnd_ms_2m(String end_ms_2m) {
		this.end_ms_2m = end_ms_2m;
	}

	public String getEnd_md_2m() {
		return end_md_2m;
	}

	public void setEnd_md_2m(String end_md_2m) {
		this.end_md_2m = end_md_2m;
	}

	public String getEnd_ms_10m() {
		return end_ms_10m;
	}

	public void setEnd_ms_10m(String end_ms_10m) {
		this.end_ms_10m = end_ms_10m;
	}

	public String getEnd_md_10m() {
		return end_md_10m;
	}

	public void setEnd_md_10m(String end_md_10m) {
		this.end_md_10m = end_md_10m;
	}

	public String getEnd_ms_max() {
		return end_ms_max;
	}

	public void setEnd_ms_max(String end_ms_max) {
		this.end_ms_max = end_ms_max;
	}

	public String getEnd_md_max() {
		return end_md_max;
	}

	public void setEnd_md_max(String end_md_max) {
		this.end_md_max = end_md_max;
	}

	public String getEnd_slp() {
		return end_slp;
	}

	public void setEnd_slp(String end_slp) {
		this.end_slp = end_slp;
	}

	public String getEnd_sp() {
		return end_sp;
	}

	public void setEnd_sp(String end_sp) {
		this.end_sp = end_sp;
	}

	public String getEnd_t() {
		return end_t;
	}

	public void setEnd_t(String end_t) {
		this.end_t = end_t;
	}

	public String getEnd_rh() {
		return end_rh;
	}

	public void setEnd_rh(String end_rh) {
		this.end_rh = end_rh;
	}

	public String getEnd_dpt() {
		return end_dpt;
	}

	public void setEnd_dpt(String end_dpt) {
		this.end_dpt = end_dpt;
	}

	public String getEnd_lst() {
		return end_lst;
	}

	public void setEnd_lst(String end_lst) {
		this.end_lst = end_lst;
	}

	public String getEnd_cld_hl() {
		return end_cld_hl;
	}

	public void setEnd_cld_hl(String end_cld_hl) {
		this.end_cld_hl = end_cld_hl;
	}

	public String getEnd_cld_hm() {
		return end_cld_hm;
	}

	public void setEnd_cld_hm(String end_cld_hm) {
		this.end_cld_hm = end_cld_hm;
	}

	public String getEnd_cld_hh() {
		return end_cld_hh;
	}

	public void setEnd_cld_hh(String end_cld_hh) {
		this.end_cld_hh = end_cld_hh;
	}

	public String getEnd_vis() {
		return end_vis;
	}

	public void setEnd_vis(String end_vis) {
		this.end_vis = end_vis;
	}

	public String getEnd_wp() {
		return end_wp;
	}

	public void setEnd_wp(String end_wp) {
		this.end_wp = end_wp;
	}

	public String getEnd_raini() {
		return end_raini;
	}

	public void setEnd_raini(String end_raini) {
		this.end_raini = end_raini;
	}

	public String getEnd_rain() {
		return end_rain;
	}

	public void setEnd_rain(String end_rain) {
		this.end_rain = end_rain;
	}

	public String getEnd_reserve1() {
		return end_reserve1;
	}

	public void setEnd_reserve1(String end_reserve1) {
		this.end_reserve1 = end_reserve1;
	}

	public String getEnd_reserve2() {
		return end_reserve2;
	}

	public void setEnd_reserve2(String end_reserve2) {
		this.end_reserve2 = end_reserve2;
	}

	public String getEnd_reserve3() {
		return end_reserve3;
	}

	public void setEnd_reserve3(String end_reserve3) {
		this.end_reserve3 = end_reserve3;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the lon
	 */
	public Float getLon() {
		return lon;
	}

	/**
	 * @param lon
	 *            the lon to set
	 */
	public void setLon(Float lon) {
		this.lon = lon;
	}

	/**
	 * @return the lat
	 */
	public Float getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(Float lat) {
		this.lat = lat;
	}

	/**
	 * @return the xCoord
	 */
	public Float getxCoord() {
		return xCoord;
	}

	/**
	 * @param xCoord
	 *            the xCoord to set
	 */
	public void setxCoord(Float xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * @return the yCoord
	 */
	public Float getyCoord() {
		return yCoord;
	}

	/**
	 * @param yCoord
	 *            the yCoord to set
	 */
	public void setyCoord(Float yCoord) {
		this.yCoord = yCoord;
	}
}
