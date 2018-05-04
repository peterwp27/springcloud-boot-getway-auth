package com.nriet.datacenter.controllers.mhzl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nriet.datacenter.model.mhzl.Awos;
import com.nriet.datacenter.model.mhzl.WindFore;
import com.nriet.datacenter.service.mhzl.MhzlService;
import com.nriet.framework.core.vo.Result;
import com.nriet.framework.core.vo.ResultGenerator;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/mhData")
public class MhzlController {

	// LIANDI侧一时TODO
	// @Autowired
	private MhzlService mhzlService;

	/**
	 * 获取机场自观数据
	 * 
	 * @param stationId
	 *            机场站号
	 * @param time
	 *            观测时间
	 * @return
	 */
	@GetMapping("/getAwosInfo")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "stationId", value = "站号（ZPPP）", defaultValue = "ZPPP", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "time", value = "时间（20170707120000）", defaultValue = "201612280316", required = true, paramType = "query", dataType = "String") })
	@ResponseBody
	public Result getAwosInfo(@RequestParam String stationId, @RequestParam String time) {
		// Calendar cal = DateUtil.getStringToCalendar(time);
		List<Awos> awosData = mhzlService.getAWOSInfo(stationId, time);
		return ResultGenerator.genSuccessResult(awosData);
	}

	/**
	 * 获取指定站点(最近2小时)的时序图数据
	 * 
	 * @param stationId
	 *            机场站号
	 * @param time
	 *            观测时间
	 * @return
	 */
	@GetMapping("/getAWOSInfo2H")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "stationId", value = "站号（ZPPP）", defaultValue = "ZPPP", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "time", value = "时间（20170707120000）", defaultValue = "201612280316", required = true, paramType = "query", dataType = "String") })
	@ResponseBody
	public Result getAWOSInfo2H(@RequestParam String stationId, @RequestParam String time) {
		// Calendar cal = DateUtil.getStringToCalendar(time);
		List<Awos> awosData = mhzlService.getAWOSInfo2H(stationId, time);
		return ResultGenerator.genSuccessResult(awosData);
	}

	/**
	 * 大风预报数据获取
	 * 
	 * @param time
	 *            查询时间
	 * @param station
	 *            站号+跑道号
	 * @return
	 */
	@GetMapping("/getWindForeInfo")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20170707120000）", defaultValue = "20170707120000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "station", value = "站点（ZPPP-22）", defaultValue = "ZPPP-22", required = true, paramType = "query", dataType = "String") })
	@ResponseBody
	public Result getWindForeInfo(@RequestParam String time, @RequestParam String station) {
		List<WindFore> dataList = mhzlService.getWindForeInfo(time, station);
		return ResultGenerator.genSuccessResult(dataList);
	}

}
