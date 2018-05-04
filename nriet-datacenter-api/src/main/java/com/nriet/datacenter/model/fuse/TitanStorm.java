package com.nriet.datacenter.model.fuse;

import java.io.Serializable;
import java.util.Date;

public class TitanStorm implements Serializable {
	private static final long serialVersionUID = 4445119531022759817L;
	/**
	 * 风暴时间
	 */
	private Date time;
	/**
	 * 风暴边界点经度
	 */
	private float[] lons;
	/**
	 * 风暴边界点纬度
	 */
	private float[] lats;
	/**
	 * 风暴投影中心经度
	 */
	private float centLon;
	/**
	 * 风暴投影中心纬度
	 */
	private float centLat;
	/**
	 * 风暴中心高度
	 */
	private float centHgt;
	/**
	 * 风暴反射率因子中心高度
	 */
	private float reflCentHgt;
	/**
	 * 风暴顶高度
	 */
	private float topHgt;
	
	/**
	 * 风暴底高
	 */
	private float bottomHgt;
	
	/**
	 * 最大反射率因子
	 */
	private float maxRefl;
	/**
	 * 风暴体积
	 */
	private float volume;
	/**
	 * 风暴质量
	 */
	private float mass;
	/**
	 * 风暴面积
	 */
	private float area;
	/**
	 * 风暴移动速度
	 */
	private float moveSpeed;
	/**
	 * 风暴移动方向，正北为0
	 */
	private float moveDir;
	/**
	 * 风暴vil值
	 */
	private float vil;
	
	/**
	 * 平均反射率
	 */
	private float avgDbz;
	
	/**
	 * 最大反射率
	 */
	private float maxDbz;
	
	/**
	 * 最大反射率所在高度
	 */
	private float maxDbzHgt;
	
	/**
	 * 水汽通量
	 */
	private float flux;
	
	/**
	 * vil
	 */
	private float vilFromMaxz;
	
	/**
	 * 雹暴级别 (0-4 5个级别)
	 */
	private int hailStormLvl;
	
	/**
	 * 冰雹概率
	 */
	private float hailProb;
	
	/**
	 *  冰雹质量 (单位：ktons)
	 */
	private float hailMass;
	
	/**
	 * VIHM
	 */
	private float hailVIHM;
	
	/**
	 * 预报风暴长度变化率
	 */
	private float foreLengthRatio;
	
	/**
	 * 风暴编号
	 */
	private int num;
	
	public float[] getLons() {
		return lons;
	}
	public void setLons(float[] lons) {
		this.lons = lons;
	}
	public float[] getLats() {
		return lats;
	}
	public void setLats(float[] lats) {
		this.lats = lats;
	}
	public float getCentLon() {
		return centLon;
	}
	public void setCentLon(float centLon) {
		this.centLon = centLon;
	}
	public float getCentLat() {
		return centLat;
	}
	public void setCentLat(float centLat) {
		this.centLat = centLat;
	}
	public float getCentHgt() {
		return centHgt;
	}
	public void setCentHgt(float centHgt) {
		this.centHgt = centHgt;
	}
	public float getReflCentHgt() {
		return reflCentHgt;
	}
	public void setReflCentHgt(float reflCentHgt) {
		this.reflCentHgt = reflCentHgt;
	}
	public float getTopHgt() {
		return topHgt;
	}
	public void setTopHgt(float topHgt) {
		this.topHgt = topHgt;
	}
	public float getMaxRefl() {
		return maxRefl;
	}
	public void setMaxRefl(float maxRefl) {
		this.maxRefl = maxRefl;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public float getMass() {
		return mass;
	}
	public void setMass(float mass) {
		this.mass = mass;
	}
	public float getArea() {
		return area;
	}
	public void setArea(float area) {
		this.area = area;
	}
	public float getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	public float getMoveDir() {
		return moveDir;
	}
	public void setMoveDir(float moveDir) {
		this.moveDir = moveDir;
	}
	public float getVil() {
		return vil;
	}
	public void setVil(float vil) {
		this.vil = vil;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public float getBottomHgt() {
		return bottomHgt;
	}
	public void setBottomHgt(float bottomHgt) {
		this.bottomHgt = bottomHgt;
	}
	public float getAvgDbz() {
		return avgDbz;
	}
	public void setAvgDbz(float avgDbz) {
		this.avgDbz = avgDbz;
	}
	public float getMaxDbz() {
		return maxDbz;
	}
	public void setMaxDbz(float maxDbz) {
		this.maxDbz = maxDbz;
	}
	public float getMaxDbzHgt() {
		return maxDbzHgt;
	}
	public void setMaxDbzHgt(float maxDbzHgt) {
		this.maxDbzHgt = maxDbzHgt;
	}
	public float getFlux() {
		return flux;
	}
	public void setFlux(float flux) {
		this.flux = flux;
	}
	public float getVilFromMaxz() {
		return vilFromMaxz;
	}
	public void setVilFromMaxz(float vilFromMaxz) {
		this.vilFromMaxz = vilFromMaxz;
	}
	public int getHailStormLvl() {
		return hailStormLvl;
	}
	public void setHailStormLvl(int hailStormLvl) {
		this.hailStormLvl = hailStormLvl;
	}
	public float getHailProb() {
		return hailProb;
	}
	public void setHailProb(float hailProb) {
		this.hailProb = hailProb;
	}
	public float getHailMass() {
		return hailMass;
	}
	public void setHailMass(float hailMass) {
		this.hailMass = hailMass;
	}
	public float getHailVIHM() {
		return hailVIHM;
	}
	public void setHailVIHM(float hailVIHM) {
		this.hailVIHM = hailVIHM;
	}
	public float getForeLengthRatio() {
		return foreLengthRatio;
	}
	public void setForeLengthRatio(float foreLengthRatio) {
		this.foreLengthRatio = foreLengthRatio;
	}
	
}
