package com.nriet.datacenter.controllers.cpzz;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nriet.datacenter.model.cpzz.PdmTsInfo;
import com.nriet.datacenter.model.cpzz.PdmVisInfo;
import com.nriet.datacenter.model.cpzz.PdmWindInfo;
import com.nriet.datacenter.model.cpzz.PdmWsInfo;
import com.nriet.datacenter.model.cpzz.PublishedMsgList;
import com.nriet.datacenter.service.cpzz.PdmTsInfoService;
import com.nriet.datacenter.service.cpzz.PdmVisInfoService;
import com.nriet.datacenter.service.cpzz.PdmWindInfoService;
import com.nriet.datacenter.service.cpzz.PdmWsInfoService;
import com.nriet.datacenter.service.cpzz.WarningProMakerService;
import com.nriet.datacenter.util.DataCenterAPIConfig;
import com.nriet.datacenter.util.JsonDateValueProcessor;
import com.nriet.framework.core.vo.Result;
import com.nriet.framework.core.vo.ResultGenerator;
import com.nriet.framework.util.DateUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@RestController
@RequestMapping("/warningpromaker")
public class CpzzController {

	@Autowired
	private PdmTsInfoService pdmTsInfoService;
	@Autowired
	private PdmWindInfoService pdmWindInfoService;
	@Autowired
	private PdmWsInfoService pdmWsInfoService;
	@Autowired
	private PdmVisInfoService pdmVisInfoService;
	@Autowired
	private WarningProMakerService warningProMakerService;

	@Autowired
	private DataCenterAPIConfig config;
	@Value("${product.confpath}")
	private String confpath;

	/**
	 * 已发布报文一览取得
	 * 
	 * @param time
	 *            查询时间
	 * @param msgType
	 *            报文类型
	 * @return
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间", defaultValue = "20180316095000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "msgType", value = "报文类型(1:警报，2:更正报，3:取消报)", defaultValue = "1", required = true, paramType = "query", dataType = "String"), })
	public Result queryPublishedMsgList(@RequestParam String time, @RequestParam String msgType) {
		List<PublishedMsgList> resultList = new ArrayList<>();
		try {
			Date date = DateUtil.strToDate(time, DateUtil.YMDHMS_NUM);
			resultList = warningProMakerService.queryPublishedMsgList(date, msgType);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult("出现异常");
		}
		return ResultGenerator.genSuccessResult(resultList);
	}

	/**
	 * 已发布报文取得
	 * 
	 * @param time
	 *            查询时间
	 * @param msgId
	 *            报文发布序号
	 * @param weatherType
	 *            告警天气类型
	 * @param warnType
	 *            报文类型
	 * @return
	 */
	@GetMapping("/msg")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间", defaultValue = "20180316095000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "msgId", value = "报文发布id", defaultValue = "01", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "weatherType", value = "告警天气类型", defaultValue = "1", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "warnType", value = "报文类型", defaultValue = "1", required = true, paramType = "query", dataType = "String") })
	public Result queryPublishedMsg(@RequestParam String time, @RequestParam String msgId,
			@RequestParam String weatherType, @RequestParam String warnType) {
		Object result = null;
		try {
			List<String> codes = new ArrayList<>(config.getWeatherTypeMap().keySet());
			Collections.sort(codes);
			if (codes.get(0).equals(weatherType)) {
				result = pdmTsInfoService.queryReport(msgId, time, warnType);
			} else if (codes.get(1).equals(weatherType)) {
				result = pdmWindInfoService.queryWindReport(msgId, time, warnType);
			} else if (codes.get(2).equals(weatherType)) {
				result = pdmWsInfoService.queryWsReport(msgId, time, warnType);
			} else if (codes.get(3).equals(weatherType)) {
				result = pdmVisInfoService.queryVisReport(msgId, time, warnType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult("出现异常");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyyMMddHHmmss"));
		JSONObject jsonObj = JSONObject.fromObject(result, jsonConfig);
		return ResultGenerator.genSuccessResult(jsonObj.toString());
	}

	/**
	 * 报文发布
	 * 
	 * @param weatherType
	 *            告警天气类型
	 * @param data
	 *            报文信息
	 * @return
	 */
	@PostMapping("/add")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "weatherType", value = "告警天气类型", defaultValue = "1", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "data", value = "发布数据", defaultValue = "{}", required = true, paramType = "query", dataType = "String") })
	public Result addReport(@RequestParam String weatherType, @RequestParam String data) {
		JSONObject jsonObj = JSONObject.fromObject(data);
		try {
			List<String> codes = new ArrayList<>(config.getWeatherTypeMap().keySet());
			Collections.sort(codes);
			if (codes.get(0).equals(weatherType)) {
				PdmTsInfo ts = (PdmTsInfo) JSONObject.toBean(jsonObj, PdmTsInfo.class);
				ts.setPublishContent(ts.formReport());
				pdmTsInfoService.saveReport(ts);
			} else if (codes.get(1).equals(weatherType)) {
				PdmWindInfo wind = (PdmWindInfo) JSONObject.toBean(jsonObj, PdmWindInfo.class);
				wind.setPublishContent(wind.formReport());
				pdmWindInfoService.saveReport(wind);
			} else if (codes.get(2).equals(weatherType)) {
				PdmWsInfo ws = (PdmWsInfo) JSONObject.toBean(jsonObj, PdmWsInfo.class);
				ws.setPublishContent(ws.formReport());
				pdmWsInfoService.saveReport(ws);
			} else if (codes.get(3).equals(weatherType)) {
				PdmVisInfo vis = (PdmVisInfo) JSONObject.toBean(jsonObj, PdmVisInfo.class);
				vis.setPublishContent(vis.formReport());
				pdmVisInfoService.saveReport(vis);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult("出现异常");
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 预览数据取得
	 * 
	 * @param publishId
	 *            报文发布序号
	 * @param time
	 *            报文发布时间
	 * @param msgType
	 *            报文类型
	 * @param warnType
	 *            告警天气类型
	 * @return
	 */
	@GetMapping("/preview")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间", defaultValue = "20180316095000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "publishId", value = "报文发布id", defaultValue = "01", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "msgType", value = "报文类型", defaultValue = "1", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "warnType", value = "告警天气类型", defaultValue = "1", required = true, paramType = "query", dataType = "String") })
	public Result getPreviewData(@RequestParam String publishId, @RequestParam String time,
			@RequestParam String msgType, @RequestParam String warnType) {
		PublishedMsgList report = null;
		try {
			Date date = DateUtil.strToDate(time, DateUtil.YMDHMS_NUM);
			report = warningProMakerService.queryOneReport(date, publishId, msgType, warnType);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult("出现异常");
		}
		return ResultGenerator.genSuccessResult(report);
	}

	/**
	 * 产品制作发布报文下载
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param publishId
	 *            报文发布id
	 * @param time
	 *            发布时间
	 * @param msgType
	 *            报文类型
	 * @param warnType
	 *            告警天气类型
	 * @return
	 */
	@GetMapping("/download")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间", defaultValue = "20180316095000", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "publishId", value = "报文发布id", defaultValue = "01", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "msgType", value = "报文类型", defaultValue = "1", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "warnType", value = "告警天气类型", defaultValue = "1", required = true, paramType = "query", dataType = "String") })
	public Result previewDownload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String publishId, @RequestParam String time, @RequestParam String msgType,
			@RequestParam String warnType) {
		Map<String, String> dataMap = new HashMap<>();
		try {
			Date date = DateUtil.strToDate(time, DateUtil.YMDHMS_NUM);
			PublishedMsgList report = warningProMakerService.queryOneReport(date, publishId, msgType, warnType);
			if (report != null) {
				dataMap.put("publishNo", report.getMsgId());
				dataMap.put("publishTime", report.getPublishDate());
				dataMap.put("reportContent", report.getReportContent());
				dataMap.put("publisher", report.getPublisher());
			}
			Configuration configuration = new Configuration(new Version("2.3.23"));
			configuration.setDefaultEncoding("utf-8");

			String ftlPath = confpath;
			configuration.setDirectoryForTemplateLoading(new File(URLDecoder.decode(ftlPath, "UTF-8")));

			response.setContentType("application/msword");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(config.getPromakeDownloadName().getBytes("GBK"), "iso8859-1") + "\"");
			response.setCharacterEncoding("utf-8");
			OutputStream out = response.getOutputStream();
			Template t = configuration.getTemplate(config.getPromakeFtlName(), "utf-8");
			t.process(dataMap, new PrintWriter(out));
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult("出现异常");
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 报文发布序号获取
	 * 
	 * @param time
	 *            发布时间
	 * @return
	 */
	@GetMapping("/getPublishId")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "time", value = "时间", defaultValue = "20180316095000", required = true, paramType = "query", dataType = "String") })
	public Result getMaxPublishId(@RequestParam String time) {
		Date date = DateUtil.strToDate(time, DateUtil.YMDHMS_NUM);
		String publishId = warningProMakerService.getLatestPublishId(date);
		return ResultGenerator.genSuccessResult(publishId);
	}

}
