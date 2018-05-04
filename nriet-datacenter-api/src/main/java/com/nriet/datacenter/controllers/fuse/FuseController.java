package com.nriet.datacenter.controllers.fuse;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nriet.datacenter.model.fuse.AREA;
import com.nriet.datacenter.model.fuse.GridData;
import com.nriet.datacenter.model.fuse.HeightArea;
import com.nriet.datacenter.model.fuse.StormEcho;
import com.nriet.datacenter.model.fuse.TitanStorm;
import com.nriet.datacenter.model.fuse.WARN;
import com.nriet.datacenter.service.fuse.FuseService;
import com.nriet.datacenter.util.JsonDateValueProcessor;
import com.nriet.framework.api.images.entity.Product;
import com.nriet.framework.core.vo.Result;
import com.nriet.framework.core.vo.ResultGenerator;
import com.nriet.framework.util.DateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@RestController
@RequestMapping("/fuseData")
public class FuseController {

	@Autowired
	private FuseService fuseDataService;

	/**
	 * 融合数据获取
	 * 
	 * @param time
	 *            时间
	 * @param element
	 *            要素
	 * @param height
	 *            高度
	 * @return
	 */
	@GetMapping("/readLapsData")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20170807002000）", defaultValue = "20170807002000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "element", value = "要素", defaultValue = "EDA", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "height", value = "3000", defaultValue = "3000", required = true, paramType = "query", dataType = "int") })
	public Result readLapsData(@RequestParam String time, @RequestParam String element, @RequestParam int height) {
		Calendar cal = DateUtil.getStringToCalendar(time);
		GridData lapsData = fuseDataService.readLapsData(cal, element, height);
		return ResultGenerator.genSuccessResult(lapsData);
	}

	/**
	 * 指定落区编号TITAN过去半小时数据获取
	 * 
	 * @param time
	 *            查询时间
	 * @param num
	 *            落区编号
	 * @return
	 */
	@GetMapping("/getTitanPastNum")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20180309110000）", defaultValue = "20180309110000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "num", value = "落区编号", defaultValue = "0", required = true, paramType = "query", dataType = "int") })
	@ResponseBody
	public Result getTitanPastDataByNum(@RequestParam String time, @RequestParam int num) {
		List<TitanStorm> dataList = fuseDataService.getTitanPastDataByNum(time, num);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyyMMddHHmmss"));
		JSONArray jsonObj = JSONArray.fromObject(dataList, jsonConfig);
		return ResultGenerator.genSuccessResult(jsonObj.toString());
	}

	/**
	 * TITAN实况数据获取
	 * 
	 * @param time
	 *            查询时间
	 * @return
	 */
	@GetMapping("/getTitanLive")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20180309110000）", defaultValue = "20180309110000", required = true, paramType = "query", dataType = "String"), })
	@ResponseBody
	public Result getTitanLiveData(@RequestParam String time) {
		List<TitanStorm> dataList = fuseDataService.getTitanLiveData(time);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyyMMddHHmmss"));
		JSONArray jsonObj = JSONArray.fromObject(dataList, jsonConfig);
		return ResultGenerator.genSuccessResult(jsonObj.toString());
	}

	/**
	 * 获取告警全时间数据
	 * 
	 * @param time
	 *            观测时间或预报时间
	 * @param type
	 *            类型.0：机场，1：导航点，2：航线
	 * @param height
	 *            高度层
	 * @return
	 */
	@GetMapping("/getWarnInfo")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20170707120000）", defaultValue = "20170707120000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型(0：机场，1：导航点，2：航线)", defaultValue = "0", required = true, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "height", value = "高度", defaultValue = "500", required = true, paramType = "query", dataType = "int") })
	public Result getWarnInfo(@RequestParam String time, @RequestParam int type, @RequestParam int height) {
		Calendar cal = DateUtil.getStringToCalendar(time);
		List<WARN> warnData = fuseDataService.getWarnInfo(cal, type, height);
		return ResultGenerator.genSuccessResult(warnData);
	}

	/**
	 * 获取告警要素落区信息
	 * 
	 * @param time
	 *            起报时间或观测时间
	 * @param interval
	 *            间隔时间（单位：分钟）
	 * @param height
	 *            高度层
	 * @return
	 */
	@GetMapping("/getAreaInfo")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20170707120000）", defaultValue = "20170807002000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "interval", value = "时效", defaultValue = "0", required = true, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "height", value = "高度", defaultValue = "500", required = true, paramType = "query", dataType = "int") })
	@ResponseBody
	public Result getAreaInfo(@RequestParam String time, @RequestParam int interval, @RequestParam int height) {
		Calendar cal = DateUtil.getStringToCalendar(time);
		List<AREA> areaData = fuseDataService.getAreaInfo(cal, interval, height);
		return ResultGenerator.genSuccessResult(areaData);
	}

	/**
	 * 获取所有高度层有落区的数据信息
	 * 
	 * @param time
	 *            起报时间或观测时间
	 * @return
	 */
	@GetMapping("/getHeightAreaInfo")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20170707120000）", defaultValue = "20170707120000", required = true, paramType = "query", dataType = "String") })
	@ResponseBody
	public Result getHeightAreaInfo(@RequestParam String time) {
		Calendar cal = DateUtil.getStringToCalendar(time);
		List<HeightArea> heightData = fuseDataService.getHeightAreaInfo(cal);
		return ResultGenerator.genSuccessResult(heightData);
	}

	/**
	 * 融合数据回波顶高回波底高时序图数据获取
	 * 
	 * @param time
	 *            查询时间
	 * @param lon
	 *            经度
	 * @param lat
	 *            纬度
	 * @return
	 */
	@GetMapping("/getEchoData")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20170807010000）", defaultValue = "20170807010000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "lon", value = "经度", defaultValue = "102.5", required = true, paramType = "query", dataType = "float"),
			@ApiImplicitParam(name = "lat", value = "纬度", defaultValue = "24.5", required = true, paramType = "query", dataType = "float") })
	public Result getEchoData(@RequestParam String time, @RequestParam float lon, @RequestParam float lat) {
		List<StormEcho> dataList = fuseDataService.getEchoData(time, lon, lat);
		return ResultGenerator.genSuccessResult(dataList);
	}

	/**
	 * 获取指定时间指定时间范围内的最新的图片
	 * 
	 * @param time
	 *            查询时间
	 * @param stationId
	 *            站号
	 * @param dataType
	 *            资料类型
	 * @param element
	 *            要素
	 * @param height
	 *            高度(3位)或仰角
	 * @param number
	 *            获取的图片数
	 * @param sum
	 *            指定范围内的分钟数(负数往前推，整数往后推)
	 * @param interval
	 *            时效(3位)
	 * @return
	 */
	@GetMapping("/getLatestProduct")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20180108032000）", defaultValue = "20180108032000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "stationId", value = "站点（ZSNJ）", defaultValue = "ZPPP", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "dataType", value = "资料类型（WRFOUT等）", defaultValue = "JGRadar", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "element", value = "要素（综合图：WTRV）", defaultValue = "DBZ", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "height", value = "高度层", defaultValue = "2", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "number", value = "图片张数", defaultValue = "1", required = true, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "sum", value = "指定范围内的分钟数(负数往前推，整数往后推)", defaultValue = "-10", required = true, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "interval", value = "时效(3位)", defaultValue = "000", required = true, paramType = "query", dataType = "String") })
	public Result getLatestProduct(@RequestParam String time, @RequestParam String stationId,
			@RequestParam String dataType, @RequestParam String element, @RequestParam String height,
			@RequestParam int number, @RequestParam int sum, @RequestParam String interval) {
		Calendar cal = DateUtil.getStringToCalendar(time);
		List<Product> dataList = fuseDataService.getLatestProduct(cal, stationId, dataType, element, height, number,
				sum, interval);
		return ResultGenerator.genSuccessResult(dataList);
	}
	
}
