package com.nriet.datacenter.model.fuse;

import java.io.Serializable;
import java.util.Date;

/**
 * 格点数据
 */
public class GridData implements Serializable {

	private static final long serialVersionUID = -2972329970915179568L;
	/**
	 * 数据时间
	 */
	private Date time;
	/**
	 * 经向格点数
	 */
	private int nx;
	/**
	 * 纬向格点数
	 */
	private int ny;
	/**
	 * 起始经度(由西向东)
	 */
	private float startLon;
	/**
	 * 截止经度(由西向东)
	 */
	private float endLon;
	/**
	 * 起始纬度(由南向北)
	 */
	private float startLat;
	/**
	 * 截止纬度(由南向北)
	 */
	private float endLat;
	/**
	 * 经向格距
	 */
	private float dx;
	/**
	 * 纬向格距
	 */
	private float dy;
	/**
	 * 无效值
	 */
	private float invalid;
	/**
	 * 等值线间隔
	 */
	private Float isolineDist;
	/**
	 * 等值线起始值
	 */
	private Float startIsoline;
	/**
	 * 等值线终止值
	 */
	private Float endIsoline;
	/**
	 * 平滑系数
	 */
	private float coef;
	/**
	 * 加粗线值
	 */
	private float thick;
	
	/**
	* 等值线数组值
	 */
	private double[] lineVal;
	
	/**
	 * 0：data, 1：data1, 2：data2, 3：data3, 4：data4
	 */
	private int index = -1;
	
	/**
	 * 等值线json数据
	 */
	private String eqValJsonStr;
	/**
	 * 面条图成员个数
	 */
	private int ensemble;
	/**
	 * 中间点纬度(单站雷达剖面用)
	 */
	private float midLat;
	/**
	 * 中间点经度(单站雷达剖面用)
	 */
	private float midLon;
	
	/**
	 * 格点数据(先行后列，当为风时，表示U分量)
	 */
	private float[][] data;
	/**
	 * 格点数据(先行后列，当为风时，表示V分量;当位综合分析多产品时，表示产品2)
	 */
	private float[][] data2;
	/**
	 * 格点数据(先行后列，综合分析多产品:产品3)
	 */
	private float[][] data3;
	/**
	 * 格点数据(先行后列，综合分析多产品:产品4)
	 */
	private float[][] data4;
	
	/**
	 * 格点数据(ECJIHE面条图三维数据)
	 */
	private float[][][] data1;
	
	
	public float getMidLat() {
		return midLat;
	}
	public void setMidLat(float midLat) {
		this.midLat = midLat;
	}
	public float getMidLon() {
		return midLon;
	}
	public void setMidLon(float midLon) {
		this.midLon = midLon;
	}
	public double[] getLineVal() {
		return lineVal;
	}
	public void setLineVal(double[] lineVal) {
		this.lineVal = lineVal;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getEqValJsonStr() {
		return eqValJsonStr;
	}
	public void setEqValJsonStr(String eqValJsonStr) {
		this.eqValJsonStr = eqValJsonStr;
	}
	public int getEnsemble() {
		return ensemble;
	}
	public void setEnsemble(int ensemble) {
		this.ensemble = ensemble;
	}
	public float[][][] getData1() {
		return data1;
	}
	public void setData1(float[][][] data1) {
		this.data1 = data1;
	}
	public float[][] getData4() {
		return data4;
	}
	public void setData4(float[][] data4) {
		this.data4 = data4;
	}
	public float[][] getData3() {
		return data3;
	}
	public void setData3(float[][] data3) {
		this.data3 = data3;
	}
	public float[][] getData2() {
		return data2;
	}
	public void setData2(float[][] data2) {
		this.data2 = data2;
	}
	public int getNx() {
		return nx;
	}
	public void setNx(int nx) {
		this.nx = nx;
	}
	public int getNy() {
		return ny;
	}
	public void setNy(int ny) {
		this.ny = ny;
	}
	public float getStartLon() {
		return startLon;
	}
	public void setStartLon(float startLon) {
		this.startLon = startLon;
	}
	public float getEndLon() {
		return endLon;
	}
	public void setEndLon(float endLon) {
		this.endLon = endLon;
	}
	public float getStartLat() {
		return startLat;
	}
	public void setStartLat(float startLat) {
		this.startLat = startLat;
	}
	public float getEndLat() {
		return endLat;
	}
	public void setEndLat(float endLat) {
		this.endLat = endLat;
	}
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public float getDy() {
		return dy;
	}
	public void setDy(float dy) {
		this.dy = dy;
	}
	public float getInvalid() {
		return invalid;
	}
	public void setInvalid(float invalid) {
		this.invalid = invalid;
	}
	public float[][] getData() {
		return data;
	}
	public void setData(float[][] data) {
		this.data = data;
	}
	public Float getIsolineDist() {
		return isolineDist;
	}
	public void setIsolineDist(Float isolineDist) {
		this.isolineDist = isolineDist;
	}
	public Float getStartIsoline() {
		return startIsoline;
	}
	public void setStartIsoline(Float startIsoline) {
		this.startIsoline = startIsoline;
	}
	public Float getEndIsoline() {
		return endIsoline;
	}
	public void setEndIsoline(Float endIsoline) {
		this.endIsoline = endIsoline;
	}
	public float getCoef() {
		return coef;
	}
	public void setCoef(float coef) {
		this.coef = coef;
	}
	public float getThick() {
		return thick;
	}
	public void setThick(float thick) {
		this.thick = thick;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
