package com.nriet.datacenter.service.mhzl.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nriet.datacenter.mapper.mhzl.AwosMapper;
import com.nriet.datacenter.model.mhzl.Awos;
import com.nriet.datacenter.model.mhzl.WindFore;
import com.nriet.datacenter.service.mhzl.MhzlService;
import com.nriet.datacenter.util.DataCenterAPIConfig;
import com.nriet.framework.util.DateUtil;

@Service
@Transactional
public class MhzlServiceImpl implements MhzlService {

	@Autowired
	private DataCenterAPIConfig config;

	/** 机场自观数据查询Dao */
//  LIANDI侧一时TODO
//	@Resource
	private AwosMapper awosMapper;

	/**
	 * 获取大风预报1小时数据
	 * 
	 * @param time
	 *            查询时间
	 * @param station
	 *            站号+跑道号
	 * @return
	 */
	@Override
	public List<WindFore> getWindForeInfo(String time, String station) {
		if (time == null || station == null) {
			return null;
		}

		List<WindFore> resultList = new ArrayList<>();
		Date date = DateUtil.strToDate(time, DateUtil.YMDHMS_NUM);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int second = cal.get(Calendar.SECOND);
		cal.add(Calendar.SECOND, -second);
		// 查询时间秒取0
		String startTime = DateUtil.dateToStr(cal.getTime(), DateUtil.YMDHMS_NUM);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		// 查询时间向后推一小时并取整分
		String endTime = DateUtil.dateToStr(cal.getTime(), DateUtil.YMDHMS_NUM);
		cal.add(Calendar.HOUR_OF_DAY, -1);
		String filePath_0321 = config.getRootPath()
				+ String.format(config.getWindForePath(), startTime.substring(0, 8));
		String fileName_0321 = String.format(config.getWindForeName(), station + "-" + config.getRunway0321(),
				startTime);
		String filePath_0422 = config.getRootPath()
				+ String.format(config.getWindForePath(), startTime.substring(0, 8));
		String fileName_0422 = String.format(config.getWindForeName(), station + "-" + config.getRunway0422(),
				startTime);
		File file = new File(filePath_0321 + fileName_0321);
		File file2 = new File(filePath_0422 + fileName_0422);
		if (!file.exists() && !file2.exists()) {
			return null;
		}
		resultList.addAll(readFileData(filePath_0321 + fileName_0321, startTime, endTime, config.getRunway0321()));
		resultList.addAll(readFileData(filePath_0422 + fileName_0422, startTime, endTime, config.getRunway0422()));
		return resultList;
	}
	
	/**
	 * 读取自观预报1小时数据
	 * 
	 * @param path
	 *            文件路径
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param rno
	 *            跑道编号
	 * @return
	 */
	private List<WindFore> readFileData(String path, String startTime, String endTime, String rno) {
		if (path == null || path.isEmpty()) {
			return null;
		}
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		BufferedReader reader = null;
		List<WindFore> resultList = new ArrayList<>();

		try {
			reader = new BufferedReader(new FileReader(file));
			String lineStr = null;
			while (true) {
				lineStr = reader.readLine();
				if (lineStr == null) {
					break;
				}
				if (lineStr.isEmpty()) {
					continue;
				}
				String[] lineStrs = lineStr.split("\\s+");
				if (lineStrs == null || lineStrs.length != 13) {
					continue;
				}
				// 预报时间
				String foreTime = lineStrs[0];
				// 获取预报时间在时间段范围内的数据
				if (foreTime.compareTo(startTime) < 0 || foreTime.compareTo(endTime) > 0) {
					continue;
				}
				// 跑道第一方向风速
				float rnOneWs = Float.valueOf(lineStrs[1]);
				// 跑道第一方向风向
				float rnOneWd = Float.valueOf(lineStrs[2]);
				// 跑道第一方向顺逆风
				float rnOneCounterW = Float.valueOf(lineStrs[3]);
				// 跑道第一方向侧风
				float rnOneCrossW = Float.valueOf(lineStrs[4]);
				// 跑道中间方向风速
				float rnMidWs = Float.valueOf(lineStrs[5]);
				// 跑道中间方向风向
				float rnMidWd = Float.valueOf(lineStrs[6]);
				// 跑道中间方向顺逆风
				float rnMidCounterW = Float.valueOf(lineStrs[7]);
				// 跑道中间方向侧风
				float rnMidCrossW = Float.valueOf(lineStrs[8]);
				// 跑道第二方向风速
				float rnTwoWs = Float.valueOf(lineStrs[9]);
				// 跑道第二方向风向
				float rnTwoWd = Float.valueOf(lineStrs[10]);
				// 跑道第二方向顺逆风
				float rnTwoCounterW = Float.valueOf(lineStrs[11]);
				// 跑道第二方向侧风
				float rnTwoCrossW = Float.valueOf(lineStrs[12]);

				WindFore windFore = new WindFore();
				windFore.setForeTime(foreTime);
				windFore.setRno(rno);
				windFore.setRn_one_ws(rnOneWs);
				windFore.setRn_one_wd(rnOneWd);
				windFore.setRn_one_counterw(rnOneCounterW);
				windFore.setRn_one_crossw(rnOneCrossW);
				windFore.setRn_mid_ws(rnMidWs);
				windFore.setRn_mid_wd(rnMidWd);
				windFore.setRn_mid_counterw(rnMidCounterW);
				windFore.setRn_mid_crossw(rnMidCrossW);
				windFore.setRn_two_ws(rnTwoWs);
				windFore.setRn_two_wd(rnTwoWd);
				windFore.setRn_two_counterw(rnTwoCounterW);
				windFore.setRn_two_crossw(rnTwoCrossW);
				resultList.add(windFore);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 根据机场站号、观测时间获取机场自观数据
	 * 
	 * @param stationId
	 *            机场站号
	 * @param time
	 *            观测时间
	 * @return 机场自观数据
	 */
	@Override
	public List<Awos> getAWOSInfo(String stationId, String time) {
		return awosMapper.getAWOSInfo(stationId, time);
	}


	/**
	 * 获取指定站点(最近2小时)的时序图数据
	 * 
	 * @param stationId
	 *            机场站号
	 * @param time
	 *            查询时间
	 * @return 机场自观数据
	 */
	@Override
	public List<Awos> getAWOSInfo2H(String stationId, String time) {
		List<Awos> list = awosMapper.getAWOSInfo2H(stationId, time);
		for (Awos awos : list) {
			if (null != awos && null != awos.getTdz_rvr_10m() && awos.getTdz_rvr_10m().startsWith("P")) {
				awos.setTdz_rvr_10m(awos.getTdz_rvr_10m().replaceAll("P", ""));
			}
		}
		return list;
	}

}
