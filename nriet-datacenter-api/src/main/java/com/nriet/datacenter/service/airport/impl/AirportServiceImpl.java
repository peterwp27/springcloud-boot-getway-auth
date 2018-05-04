package com.nriet.datacenter.service.airport.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nriet.datacenter.model.airport.MwRadioMeter;
import com.nriet.datacenter.service.airport.AirportService;
import com.nriet.datacenter.util.DataCenterAPIConfig;
import com.nriet.framework.util.DateUtil;

@Service
@Transactional
public class AirportServiceImpl implements AirportService {

	@Autowired
	private DataCenterAPIConfig config;

	/**
	 * 获取指定时间、资料、时间范围的微波辐射计数据
	 * 
	 * @param time
	 *            查询时间
	 * @param dataType
	 *            资料类型
	 * @param sum
	 *            时效(小时)
	 * @return
	 */
	@Override
	public List<MwRadioMeter> getMwRmData(String time, String dataType, int sum) {
		if (time == null || dataType == null) {
			return null;
		}

		// 获取该当资料类型的文件名格式以及所包含要素
		String elementInfo = config.getMwrmMap().get(dataType);
		if (elementInfo == null) {
			return null;
		}

		List<MwRadioMeter> resultList = new ArrayList<>();
		String[] elementPieces = elementInfo.split("-");
		// 文件名格式
		String fileNameFmt = elementPieces[1];
		// 该当资料所包含要素
		String[] elements = elementPieces[0].split(",");

		Calendar cal = Calendar.getInstance();
		Date date = DateUtil.strToDate(time, DateUtil.YMDHMS_NUM);
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -sum);
		String startTime = DateUtil.dateToStr(cal.getTime(), DateUtil.YMDHMS_NUM);
		cal.add(Calendar.HOUR_OF_DAY, sum);
		for (int i = 0; i < sum + 1; i++) {
			// 文件时间
			String fileTime = DateUtil.dateToStr(cal.getTime(), DateUtil.YMD_NUM);
			// 文件路径
			String filePath = config.getRootPath() + String.format(config.getMwrmPath(), fileTime);
			// 文件名
			String fileName = String.format(fileNameFmt, DateUtil.dateToStr(cal.getTime(), DateUtil.YMDH_SIMPLE));
			File file = new File(filePath + fileName);
			// 文件是否存在，不存在则添加空数据
			if (!file.exists()) {
				List<MwRadioMeter> emptyList = addEmptyData(cal, time, startTime, elements);
				resultList.addAll(emptyList);
			} else {
				List<MwRadioMeter> dataList = new ArrayList<>();
				if ("MET".equals(dataType)) {
					// 读取气象传感器资料文件
					dataList = readMETFile(filePath + fileName, startTime, time, elements);
				} else if ("HPC".equals(dataType)) {
					// 读取湿度廓线资料文件
					dataList = readHPCFile(filePath + fileName, startTime, time, elements);
				} else if ("LPR".equals(dataType) || "TPC".equals(dataType)) {
					// 读取液态水廓线或温度廓线（对流层）资料文件
					dataList = readLPRTPCFile(filePath + fileName, startTime, time, elements);
				} else if ("IWV".equals(dataType) || "LWP".equals(dataType)) {
					// 读取综合水汽含量或液态水路径资料文件
					dataList = readIWVLWPFile(filePath + fileName, startTime, time, elements);
				}
				resultList.addAll(dataList);
			}
			// 资料文件为小时文件，依次读取指定时间范围内所有文件
			cal.add(Calendar.HOUR_OF_DAY, -1);
		}
		if (!resultList.isEmpty()) {
			// 数据按要素>时间>高度升序排序
			Collections.sort(resultList, new Comparator<MwRadioMeter>() {
				@Override
				public int compare(MwRadioMeter o1, MwRadioMeter o2) {
					String element1 = o1.getElement();
					String element2 = o2.getElement();
					String time1 = o1.getTime();
					String time2 = o2.getTime();
					String height1 = o1.getHeight();
					String height2 = o2.getHeight();
					if (!element1.equals(element2)) {
						return element1.compareTo(element2);
					} else if (!time1.equals(time2)) {
						return time1.compareTo(time2);
					} else if (height1 != null && height2 != null && Integer.valueOf(height1).compareTo(Integer.valueOf(height2)) != 0) {
						return Integer.valueOf(height1).compareTo(Integer.valueOf(height2));
					}
					return 0;
				}
			});
		}
		return resultList;
	}

	/**
	 * 气象传感器资料文件读取
	 * 
	 * @param path
	 *            文件路径
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param elements
	 *            要素
	 * @return
	 */
	private List<MwRadioMeter> readMETFile(String path, String startTime, String endTime, String[] elements) {
		BufferedReader reader = null;
		File file = new File(path);
		List<MwRadioMeter> resultList = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			int i = 0;
			boolean isData = false;
			String tempMinute = null;
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (line.isEmpty()) {
					continue;
				}
				if (i == 0) {
					if (!line.contains("MET")) {
						break;
					}
				}

				if (isData) {
					String[] linePieces = line.split(",");
					if (linePieces == null || linePieces.length != 13) {
						continue;
					}
					// 数据时间
					String dataTime = "20" + linePieces[0].trim() + linePieces[1].trim() + linePieces[2].trim()
							+ linePieces[3].trim() + linePieces[4].trim() + linePieces[5].trim();
					String minute = linePieces[4].trim();
					
					// 过滤时间范围外的数据
					if (dataTime.compareTo(startTime) < 0 || dataTime.compareTo(endTime) > 0 || (tempMinute != null && tempMinute.equals(minute))) {
						continue;
					}
					// 压力
					String p = linePieces[7].trim();
					// 温度
					String t = linePieces[8].trim();
					// 相对湿度
					String rh = linePieces[9].trim();
					for (String element : elements) {
						MwRadioMeter rm = new MwRadioMeter();
						rm.setTime(dataTime);
						rm.setElement(element);
						if ("P".equals(element)) {
							rm.setData(p);
						} else if ("T".equals(element)) {
							rm.setData(t);
						} else if ("RH".equals(element)) {
							rm.setData(rh);
						}
						resultList.add(rm);
					}
					tempMinute = minute;
				}
				if (line.contains("#")) {
					String[] linePieces = line.split("#");
					String lineStr = linePieces[1].trim();
					if (lineStr.startsWith("Ye")) {
						isData = true;
					}
				}
				i++;
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
	 * 湿度廓线资料文件读取
	 * 
	 * @param path
	 *            文件路径
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param elements
	 *            要素
	 * @return
	 */
	private List<MwRadioMeter> readHPCFile(String path, String startTime, String endTime, String[] elements) {
		BufferedReader reader = null;
		File file = new File(path);
		List<MwRadioMeter> resultList = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			int count = 0;
			List<String> heightList = new ArrayList<>();
			boolean isRH = false;
			boolean isData = false;
			String tempMinute = null;
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (line.isEmpty()) {
					continue;
				}
				if (count == 0) {
					if (!line.contains("HPC")) {
						break;
					}
				}

				if (isRH && isData) {
					String[] linePieces = line.split(",");
					if (linePieces == null || linePieces.length != (7 + heightList.size())) {
						continue;
					}
					// 数据时间
					String dataTime = "20" + linePieces[0].trim() + linePieces[1].trim() + linePieces[2].trim()
							+ linePieces[3].trim() + linePieces[4].trim() + linePieces[5].trim();
					String minute = linePieces[4].trim();
					// 过滤时间范围外数据
					if (dataTime.compareTo(startTime) < 0 || dataTime.compareTo(endTime) > 0 || (tempMinute != null && tempMinute.equals(minute))) {
						continue;
					}
					for (int i = 0; i < heightList.size(); i++) {
						String height = heightList.get(i).trim();
						MwRadioMeter rm = new MwRadioMeter();
						rm.setTime(dataTime);
						rm.setElement(elements[0]);
						rm.setHeight(height);
						String data = linePieces[i + 7].trim();
						rm.setData(data);
						resultList.add(rm);
					}
					tempMinute = minute;
				}

				if (line.contains("#")) {
					String[] linePieces = line.split("#");
					String sign = linePieces[1].trim();
					// 获取高度层数据
					if (sign.startsWith("Altitude")) {
						String[] heights = linePieces[0].trim().split(",");
						heightList = Arrays.asList(heights);
					}
					// 判断是相对湿度还是绝对湿度，取相对湿度
					if (sign.contains("relative") && sign.contains("Humidity")) {
						isRH = true;
					}
					// 是否数据开始行
					if (sign.startsWith("Ye") && isRH) {
						isData = true;
					}
				}

				count++;
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
	 * 液态水廓线或温度廓线（对流层）资料文件读取
	 * 
	 * @param path
	 *            文件路径
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param elements
	 *            要素
	 * @return
	 */
	private List<MwRadioMeter> readLPRTPCFile(String path, String startTime, String endTime, String[] elements) {
		BufferedReader reader = null;
		File file = new File(path);
		List<MwRadioMeter> resultList = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			int count = 0;
			List<String> heightList = new ArrayList<>();
			boolean isData = false;
			String tempMinute = null;
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (line.isEmpty()) {
					continue;
				}
				if (count == 0) {
					if (!line.contains("LPR") && !line.contains("TPC")) {
						break;
					}
				}

				if (isData) {
					String[] linePieces = line.split(",");
					if (linePieces == null || linePieces.length != (7 + heightList.size())) {
						continue;
					}
					// 数据时间
					String dataTime = "20" + linePieces[0].trim() + linePieces[1].trim() + linePieces[2].trim()
							+ linePieces[3].trim() + linePieces[4].trim() + linePieces[5].trim();
					String minute = linePieces[4].trim();
					// 过滤时间范围外数据
					if (dataTime.compareTo(startTime) < 0 || dataTime.compareTo(endTime) > 0 || (tempMinute != null && tempMinute.equals(minute))) {
						continue;
					}
					for (int i = 0; i < heightList.size(); i++) {
						String height = heightList.get(i).trim();
						MwRadioMeter rm = new MwRadioMeter();
						rm.setTime(dataTime);
						rm.setElement(elements[0]);
						rm.setHeight(height);
						String data = linePieces[i + 7].trim();
						rm.setData(data);
						resultList.add(rm);
					}
					tempMinute = minute;
				}

				if (line.contains("#")) {
					String[] linePieces = line.split("#");
					String sign = linePieces[1].trim();
					// 高度层数据
					if (sign.startsWith("Altitude")) {
						String[] heights = linePieces[0].trim().split(",");
						heightList = Arrays.asList(heights);
					}
					// 是否数据开始行
					if (sign.startsWith("Ye")) {
						isData = true;
					}
				}
				count++;
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
	 * 综合水汽含量或液态水路径资料文件
	 * 
	 * @param path
	 *            文件路径
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param elements
	 *            要素
	 * @return
	 */
	private List<MwRadioMeter> readIWVLWPFile(String path, String startTime, String endTime, String[] elements) {
		BufferedReader reader = null;
		File file = new File(path);
		List<MwRadioMeter> resultList = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			int count = 0;
			boolean isData = false;
			String tempMinute = null;
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (line.isEmpty()) {
					continue;
				}
				if (count == 0) {
					if (!line.contains("IWV") && !line.contains("LWP")) {
						break;
					}
				}

				if (isData) {
					String[] linePieces = line.split(",");
					if (linePieces == null || linePieces.length != 10) {
						continue;
					}
					// 数据时间
					String dataTime = "20" + linePieces[0].trim() + linePieces[1].trim() + linePieces[2].trim()
							+ linePieces[3].trim() + linePieces[4].trim() + linePieces[5].trim();
					
					String minute = linePieces[4].trim();
					// 过滤时间范围外数据
					if (dataTime.compareTo(startTime) < 0 || dataTime.compareTo(endTime) > 0 || (tempMinute != null && tempMinute.equals(minute))) {
						continue;
					}
					String data = linePieces[7].trim();
					// 高度角
					String heightAngle = linePieces[8].trim();
					// 方位角
					String localAngle = linePieces[9].trim();
					MwRadioMeter rm = new MwRadioMeter();
					rm.setTime(dataTime);
					rm.setElement(elements[0]);
					rm.setData(data);
					rm.setHeightAngle(heightAngle);
					rm.setLocalAngle(localAngle);
					resultList.add(rm);
					tempMinute = minute;
				}

				if (line.contains("#")) {
					String[] linePieces = line.split("#");
					String sign = linePieces[1].trim();
					if (sign.startsWith("Ye")) {
						isData = true;
					}
				}
				
				count++;
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
	 * 空数据添加
	 * 
	 * @param cal
	 *            Calendar
	 * @param time
	 *            结束时间
	 * @param startTime
	 *            开始时间
	 * @param elements
	 *            要素
	 * @return
	 */
	private List<MwRadioMeter> addEmptyData(Calendar cal, String time, String startTime, String[] elements) {
		Calendar tempCal = (Calendar) cal.clone();
		String endTime = DateUtil.dateToStr(tempCal.getTime(), DateUtil.YMDHMS_NUM);
		List<MwRadioMeter> resultList = new ArrayList<>();
		if (endTime.equals(time)) {
			// tempCal.add(Calendar.MINUTE, -(tempCal.get(Calendar.MINUTE) % 10));
			tempCal.set(Calendar.SECOND, 0);
			while (true) {
				for (int i = 0; i < elements.length; i++) {
					MwRadioMeter rm = new MwRadioMeter();
					rm.setTime(DateUtil.dateToStr(tempCal.getTime(), DateUtil.YMDHMS_NUM));
					rm.setElement(elements[i]);
					resultList.add(rm);
				}
				int minute = tempCal.get(Calendar.MINUTE);
				if (minute == 0) {
					break;
				}
				tempCal.add(Calendar.MINUTE, -1);
			}
		} else if (endTime.equals(startTime)) {
			tempCal.set(Calendar.SECOND, 0);
			while (true) {
				for (int i = 0; i < elements.length; i++) {
					MwRadioMeter rm = new MwRadioMeter();
					rm.setTime(DateUtil.dateToStr(tempCal.getTime(), DateUtil.YMDHMS_NUM));
					rm.setElement(elements[i]);
					resultList.add(rm);
				}
				int minute = tempCal.get(Calendar.MINUTE);
				if (minute == 0) {
					break;
				}
				tempCal.add(Calendar.MINUTE, 1);
			}
		} else {
			tempCal.set(Calendar.MINUTE, 0);
			tempCal.set(Calendar.SECOND, 0);
			while (true) {
				for (int i = 0; i < elements.length; i++) {
					MwRadioMeter rm = new MwRadioMeter();
					rm.setTime(DateUtil.dateToStr(tempCal.getTime(), DateUtil.YMDHMS_NUM));
					rm.setElement(elements[i]);
					resultList.add(rm);
				}
				tempCal.add(Calendar.MINUTE, 1);
				int minute = tempCal.get(Calendar.MINUTE);
				if (minute == 0) {
					break;
				}
			}
		}
		return resultList;
	}
}
