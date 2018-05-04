package com.nriet.datacenter.service.radar.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nriet.datacenter.model.radar.JGRadar;
import com.nriet.datacenter.model.radar.JGRadarShear;
import com.nriet.datacenter.service.radar.RadarService;
import com.nriet.datacenter.util.DataCenterAPIConfig;

import com.nriet.framework.api.images.ImgProduceUtil;
import com.nriet.framework.api.images.entity.ProductParam;
import com.nriet.framework.api.images.entity.Product;
import com.nriet.framework.util.DateUtil;

@Service
@Transactional
public class RadarServiceImpl implements RadarService {

	@Autowired
	private DataCenterAPIConfig config;

	/**
	 * 获取激光雷达数据
	 * 
	 * @param time
	 *            查询时间
	 * @param stationId
	 *            机场站号
	 * @return
	 */
	@Override
	public Map<String, List<JGRadar>> readRadarData(Calendar time, String stationId) {
		String root = config.getRootPath()
				+ String.format(config.getJgRadarPath(), "ALL", DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
		File rootFile = new File(root);
		if (!rootFile.exists()) {
			time.add(Calendar.DAY_OF_MONTH, -1);
			root = config.getRootPath() + String.format(config.getJgRadarPath(), "ALL",
					DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
			rootFile = new File(root);
			if (!rootFile.exists()) {
				return null;
			}
		}
		Map<String, List<JGRadar>> map = new HashMap<String, List<JGRadar>>();
		File[] fileList = rootFile.listFiles();
		long start = Long.valueOf(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));
		time.add(NumberUtils.toInt(config.getJgRadarDelayType()), NumberUtils.toInt(config.getJgRadarDelayTime()));
		long end = Long.valueOf(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));

		String[] rno = stationId.split("\\-");
		int length = rno[1].length();
		for (int i = 0; i < 2; i++) {
			String no = rno[1].substring(i * (length / 2), (i + 1) * (length / 2));
			String fileNameRex = rno[0] + "-" + no;
			long t = 0;
			File file = null;
			for (File name : fileList) {
				if (!name.getName().contains(fileNameRex)) {
					continue;
				}
				String[] names = name.getName().split("_");
				long fileTime = Long.valueOf(names[4]);
				if (fileTime > t && fileTime <= start && fileTime >= end) {
					file = name;
					t = fileTime;
				}
			}
			if (null == file) {
				continue;
			}
			List<JGRadar> list = parseJGRadar(file);
			map.put(no, list);
		}
		return map;
	}

	private List<JGRadar> parseJGRadar(File file) {
		List<JGRadar> list = new ArrayList<JGRadar>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file));
			while (null != (line = br.readLine())) {
				String[] arrayValue = line.trim().split("\\s+");
				JGRadar jr = new JGRadar();
				jr.setWs(NumberUtils.toFloat(arrayValue[0]));
				jr.setWc(NumberUtils.toFloat(arrayValue[1]));
				jr.setHeight(NumberUtils.toFloat(arrayValue[2]));
				jr.setRange(NumberUtils.toFloat(arrayValue[3]));
				long time = NumberUtils.toLong(arrayValue[4]) * 1000;
				Date date = new Date(time);
				jr.setTime(DateUtil.dateToStr(date, DateUtil.YMDHMS_NUM));
				list.add(jr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * 获取激光雷达风切变
	 * 
	 * @param time
	 *            查询时间(yyyyMMddHHmmss)
	 * @return
	 */
	@Override
	public List<JGRadarShear> getJGRadarShear(String time) {
		List<JGRadarShear> resultList = new ArrayList<>();
		String startTime = getShearStartTime(time);
		boolean isTwoDay = false;
		// 是否跨天
		if (!startTime.substring(0, 8).equals(time.substring(0, 8))) {
			isTwoDay = true;
		}
		for (String area : config.getJgRadarShearAreas()) {
			String idAndArea = config.getStation() + "-" + area;
			String fileNameRegex = String.format(config.getJgRadarShearNameRegex(), idAndArea);
			// 是否跨天
			if (!isTwoDay) {
				String filePath = config.getRootPath()
						+ String.format(config.getJgRadarShearPath(), time.substring(0, 8));
				File file = new File(filePath);
				if (!file.exists()) {
					continue;
				}
				File[] files = file.listFiles();
				if (files == null || files.length == 0) {
					continue;
				}

				String lastestPath = getLastestFilePath(time, startTime, fileNameRegex, files);
				JGRadarShear data = readShearFile(lastestPath);
				if (data != null) {
					resultList.add(data);
				}
			} else {
				String filePath1 = config.getRootPath()
						+ String.format(config.getJgRadarShearPath(), startTime.substring(0, 8));
				String filePath2 = config.getRootPath()
						+ String.format(config.getJgRadarShearPath(), time.substring(0, 8));
				File file1 = new File(filePath1);
				File file2 = new File(filePath2);
				if (!file1.exists() && !file2.exists()) {
					continue;
				}
				File[] files1 = file1.listFiles();
				File[] files2 = file2.listFiles();
				if ((files1 == null || files1.length == 0) && (files2 == null || files2.length == 0)) {
					continue;
				}
				boolean hasFile2 = false;
				if (files2 != null && files2.length != 0) {
					String lastestPath = getLastestFilePath(time, startTime, fileNameRegex, files2);
					JGRadarShear data = readShearFile(lastestPath);
					if (data != null) {
						hasFile2 = true;
						resultList.add(data);
					}
				} else if (!hasFile2 && files1 != null && files1.length != 0) {
					String lastestPath = getLastestFilePath(time, startTime, fileNameRegex, files1);
					JGRadarShear data = readShearFile(lastestPath);
					if (data != null) {
						resultList.add(data);
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * 获取查询时间5分钟内最新的文件路径
	 * 
	 * @param time
	 *            结束时间
	 * @param startTime
	 *            开始时间
	 * @param fileNameRegex
	 *            文件名定则
	 * @param files
	 * @return
	 */
	private String getLastestFilePath(String time, String startTime, String fileNameRegex, File[] files) {
		String tempTime = "";
		String tempFilePath = "";
		for (File f : files) {
			String fileName = f.getName();
			if (!fileName.matches(fileNameRegex)) {
				continue;
			}
			String fileTime = fileName.split("_")[4];
			// 获取查询时间5分钟内最新的文件路径
			if (fileTime.compareTo(time) <= 0 && fileTime.compareTo(startTime) >= 0) {
				if (tempTime.compareTo(fileTime) < 0) {
					tempFilePath = f.getPath();
					tempTime = fileTime;
				}
			}
		}
		return tempFilePath;
	}

	private String getShearStartTime(String time) {
		Date date = DateUtil.strToDate(time, DateUtil.YMDHMS_NUM);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -5);
		return DateUtil.dateToStr(cal.getTime(), DateUtil.YMDHMS_NUM);
	}

	/**
	 * 激光雷达风切变文件读取
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 激光雷达风切变数据
	 */
	private JGRadarShear readShearFile(String filePath) {
		if (filePath == null || filePath.isEmpty()) {
			return null;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}

		String[] fileNamePieces = file.getName().split("_");
		String fileTime = fileNamePieces[4];
		String area = fileNamePieces[3].split("-")[1];

		BufferedReader reader = null;
		JGRadarShear shear = null;
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
				String[] lineStrs = lineStr.trim().split("\\s+");
				if (lineStrs == null || lineStrs.length != 2) {
					continue;
				}
				shear = new JGRadarShear();
				// 观测时间
				shear.setObsTime(fileTime);
				// 机场跑道区域
				shear.setRnoArea(area);
				// 风切变类型
				shear.setType(lineStrs[0].trim());
				// 风切变值
				shear.setValue(lineStrs[1].trim());
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
		return shear;
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
	 *            指定范围内的分钟数
	 * @param interval
	 *            时效(3位)
	 * @return
	 */
	@Override
	public List<Product> getLatestProduct(Calendar time, String stationId, String dataType, String element,
			String height, int number, int sum, String interval) {
		ProductParam pro = new ProductParam();
		pro.setTime(time);
		pro.setDataType(dataType);
		pro.setElement(element);
		pro.setHeight(height);
		pro.setInterval(interval);
		pro.setNumber(number);
		pro.setSum(sum);
		pro.setStationId(stationId);
		return ImgProduceUtil.getLatestProduct(pro, config.getRootPath(), config.getImagePath(),
				config.getLegendRootPath(), config.getProLegendName());
	}
}
