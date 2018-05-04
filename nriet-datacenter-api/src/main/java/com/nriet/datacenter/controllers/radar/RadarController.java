package com.nriet.datacenter.controllers.radar;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nriet.datacenter.model.radar.JGRadar;
import com.nriet.datacenter.model.radar.JGRadarShear;
import com.nriet.datacenter.service.radar.RadarService;

import com.nriet.framework.api.images.entity.Product;
import com.nriet.framework.core.vo.Result;
import com.nriet.framework.core.vo.ResultGenerator;
import com.nriet.framework.util.DateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/radarData")
public class RadarController {

	@Autowired
	private RadarService radarDataService;

	/**
	 * 获取激光雷达数据
	 * 
	 * @param time
	 *            查询时间
	 * @param stationId
	 *            机场站号
	 * @return
	 */
	@GetMapping("/readRadarData")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20180108031436）", defaultValue = "20180108031436", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "stationId", value = "站点（ZPPP-22）", defaultValue = "ZPPP-22", required = true, paramType = "query", dataType = "String") })
	public Result readRadarData(@RequestParam String time, @RequestParam String stationId) {
		Calendar cal = DateUtil.getStringToCalendar(time);
		Map<String, List<JGRadar>> radarData = radarDataService.readRadarData(cal, stationId);
		return ResultGenerator.genSuccessResult(radarData);
	}

	/**
	 * 激光雷达风切变数据获取
	 * 
	 * @param time
	 *            查询时间
	 * @return
	 */
	@GetMapping("/getJGRadarShear")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20170707120000）", defaultValue = "20170707120000", required = true, paramType = "query", dataType = "String"), })
	@ResponseBody
	public Result getJGRadarShearInfo(@RequestParam String time) {
		List<JGRadarShear> dataList = radarDataService.getJGRadarShear(time);
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
		List<Product> dataList = radarDataService.getLatestProduct(cal, stationId, dataType, element, height, number,
				sum, interval);
		return ResultGenerator.genSuccessResult(dataList);
	}
}
