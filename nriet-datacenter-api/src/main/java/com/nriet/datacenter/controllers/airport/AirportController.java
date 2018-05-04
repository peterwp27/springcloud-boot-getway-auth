package com.nriet.datacenter.controllers.airport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nriet.datacenter.model.airport.MwRadioMeter;
import com.nriet.datacenter.service.airport.AirportService;
import com.nriet.framework.core.vo.Result;
import com.nriet.framework.core.vo.ResultGenerator;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/airportData")
public class AirportController {

	@Autowired
	private AirportService airportService;
	
	/**
	 * 微波辐射计数据获取
	 * 
	 * @param time
	 *            时间
	 * @param dataType
	 *            资料类型
	 * @param sum
	 *            时效(小时)
	 * @return
	 */
	@GetMapping("/getMwRmData")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间（20170707120000）", defaultValue = "20170707120000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "dataType", value = "资料类型", defaultValue = "MET", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "sum", value = "时效(小时)", defaultValue = "6", required = true, paramType = "query", dataType = "int") })
	@ResponseBody
	public Result getMwRmData(@RequestParam String time, @RequestParam String dataType, @RequestParam int sum) {
		List<MwRadioMeter> dataList = airportService.getMwRmData(time, dataType, sum);
		return ResultGenerator.genSuccessResult(dataList);
	}
}
