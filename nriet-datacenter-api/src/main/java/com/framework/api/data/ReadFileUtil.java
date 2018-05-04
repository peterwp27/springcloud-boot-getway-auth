package com.framework.api.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.nriet.datacenter.model.fuse.AREA;
import com.nriet.datacenter.model.fuse.GridData;
import com.nriet.datacenter.model.fuse.HeightArea;
import com.nriet.datacenter.model.fuse.TitanStorm;
import com.nriet.datacenter.model.fuse.WARN;
import com.nriet.datacenter.model.radar.JGRadar;

import com.nriet.framework.util.DateUtil;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * 
 * @author b_wangpei
 *
 */
public class ReadFileUtil {
	/**
	 * 
	 * @param time
	 * @param stationId
	 * @param delay
	 * @param delayTime
	 * @param rootPath
	 * @param radarPath
	 * @return
	 */
	public static Map<String, List<JGRadar>> readRadarData(Calendar time, String stationId, String delay,
			String delayTime, String rootPath, String radarPath) {
		String root = rootPath + String.format(radarPath, "ALL", DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
		File rootFile = new File(root);
		if (!rootFile.exists()) {
			time.add(Calendar.DAY_OF_MONTH, -1);
			root = rootPath + String.format(radarPath, "ALL", DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
			rootFile = new File(root);
			if (!rootFile.exists()) {
				return null;
			}
		}
		Map<String, List<JGRadar>> map = new HashMap<String, List<JGRadar>>();
		File[] fileList = rootFile.listFiles();
		long start = Long.valueOf(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));
		time.add(NumberUtils.toInt(delay), NumberUtils.toInt(delayTime));
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

	/**
	 * 
	 * @param time
	 * @param element
	 * @param height
	 * @param rootPath
	 * @param lapsPath
	 * @param lapsfileName
	 * @param startlon
	 * @param endlon
	 * @param startlat
	 * @param endlat
	 * @return
	 */
	public static GridData readLapsData(Calendar time, String element, int height, String rootPath, String lapsPath,
			String lapsfileName, String startlon, String endlon, String startlat, String endlat) {
		GridData gridData = new GridData();
		String root = rootPath + String.format(lapsPath, DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
		File rootFile = new File(root);
		if (!rootFile.exists()) {
			return null;
		}
		String filePath = "";
		GridData gd = new GridData();
		if ("EDA".equals(element)) {
			String[] elements = { "U3", "V3" };
			int i = 0;
			for (String ele : elements) {
				filePath = root
						+ String.format(lapsfileName, ele, DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));
				File file = new File(filePath);
				if (!file.exists()) {
					return null;
				}
				if (i == 0) {
					gridData = parseNCFile(filePath, ele, 1, height, startlon, endlon, startlat, endlat);
					if (null == gridData) {
						return null;
					}
					i++;
					continue;
				}
				gd = parseNCFile(filePath, ele, 1, height, startlon, endlon, startlat, endlat);

			}
			// gridData.setData2(gd.getData());
			float[][] u = gridData.getData();
			float[][] v = gd.getData();
			int nx = gridData.getNx();
			int ny = gridData.getNy();
			float[][] ws = new float[ny][nx];
			float[][] wd = new float[ny][nx];
			for (int j = 0; j < ny; j++) {
				for (int k = 0; k < nx; k++) {
					float[] windArr = uv2wind(u[j][k], v[j][k], -1);
					ws[j][k] = windArr[0];
					wd[j][k] = windArr[1];
				}
			}
			gridData.setData(ws);
			gridData.setData2(wd);
			return gridData;
		}
		filePath = root + String.format(lapsfileName, element, DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		gridData = parseNCFile(filePath, element, 1, height, startlon, endlon, startlat, endlat);
		return gridData;
	}

	/**
	 * 
	 * @param time
	 * @param stationTypeid
	 * @param height
	 * @param dataType
	 * @param elements
	 * @param rootPath
	 * @param warnPath
	 * @return
	 */
	public static List<WARN> getWarnInfo(Calendar time, int stationTypeid, int height, String dataType,
			String rootPath, String warnPath) {
		List<WARN> list = null;
		String stationType = "AIRPORT";
		String root = rootPath + String.format(warnPath, DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
		File rootFile = new File(root);
		if (!rootFile.exists()) {
			time.add(Calendar.DAY_OF_MONTH, -1);
			root = rootPath + String.format(warnPath, DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
			rootFile = new File(root);
			if (!rootFile.exists()) {
				return list;
			}
			time.add(Calendar.DAY_OF_MONTH, 1);
		}

		if (1 == stationTypeid) {
			stationType = "POINT";
		} else if (2 == stationTypeid) {
			stationType = "LINE";
		}

		long start = Long.valueOf(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));
		time.add(Calendar.MINUTE, -30);
		long end = Long.valueOf(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));
		long t = 0l;

		File[] files = rootFile.listFiles();
		File resultFile = null;
		for (File file : files) {
			String name = file.getName();
			if (!name.contains(stationType) || !name.contains(height + "")) {
				continue;
			}
			String fileStrTime = file.getName().split("_")[4];
			long fileTime = Long.valueOf(fileStrTime);
			if (fileTime > t && fileTime >= end && fileTime <= start) {
				t = fileTime;
				resultFile = file;
			}

		}
		if (null == resultFile) {
			return list;
		}
		list = parserAirport(resultFile);
		return list;
	}

	/**
	 * 
	 * @param time
	 * @param interval
	 * @param height
	 * @param dataType
	 * @param elements
	 * @param levels
	 * @param rootPath
	 * @param warnPath
	 * @param warnncfile
	 * @return
	 */
	public static List<AREA> getAreaInfo(Calendar time, int interval, int height, String dataType, String elements,
			String levels, String rootPath, String warnPath, String warnncfile) {
		List<AREA> list = new ArrayList<AREA>();
		String root = rootPath + String.format(warnPath, DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
		File rootFile = new File(root);
		if (!rootFile.exists()) {
			time.add(Calendar.DAY_OF_MONTH, -1);
			root = rootPath + String.format(warnPath, DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
			rootFile = new File(root);
			if (!rootFile.exists()) {
				return list;
			}
			time.add(Calendar.DAY_OF_MONTH, 1);
		}

		long start = Long.valueOf(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));
		time.add(Calendar.MINUTE, -30);
		long end = Long.valueOf(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));

		File[] files = rootFile.listFiles();
		File resultFile = null;
		String[] levelArr = levels.split(",");
		for (String level : levelArr) {
			long t = 0l;
			for (File file : files) {
				String name = file.getName();
				String[] values = name.split("_");
				if (!level.equals(values[2])) {
					continue;
				}
				String fileStrTime = name.split("_")[3];
				long fileTime = Long.valueOf(fileStrTime);
				if (fileTime > t && fileTime >= end && fileTime <= start) {
					t = fileTime;
					resultFile = file;
				}
			}
			if (null == resultFile) {
				continue;
			}

			String[] name = resultFile.getName().split("_");
			Map<String, float[][]> map = parserWarnNC(root + resultFile.getName(), height);
			for (String value : map.keySet()) {
				String[] id = value.split("\\_");
				AREA area = new AREA();
				area.setDataType(name[0]);
				area.setElement(name[1]);
				area.setLevel(level);
				area.setTime(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));
				area.setId(NumberUtils.toInt(id[0]));
				area.setMoveSpeed(NumberUtils.toFloat(id[1]));
				area.setMoveDir(NumberUtils.toInt(id[2]));
				area.setCurrent(map.get(value));
				list.add(area);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param time
	 * @param dataType
	 * @param elements
	 * @param levels
	 * @param rootPath
	 * @param warnPath
	 * @param warnncfile
	 * @return
	 */
	public static List<HeightArea> getHeightAreaInfo(Calendar time, String dataType, String levels,
			String rootPath, String warnPath) {
		List<HeightArea> list = new ArrayList<HeightArea>();
		String root = rootPath + String.format(warnPath, DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
		File rootFile = new File(root);
		if (!rootFile.exists()) {
			time.add(Calendar.DAY_OF_MONTH, -1);
			root = rootPath + String.format(warnPath, DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
			rootFile = new File(root);
			if (!rootFile.exists()) {
				return list;
			}
			time.add(Calendar.DAY_OF_MONTH, 1);
		}

		long start = Long.valueOf(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));
		time.add(Calendar.MINUTE, -30);
		long end = Long.valueOf(DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM));

		File[] files = rootFile.listFiles();
		String[] levelArr = levels.split(",");
		File resultFile = null;
		for (String level : levelArr) {
			long t = 0l;
			for (File file : files) {
				String name = file.getName();
				String[] values = name.split("_");
				if (!level.equals(values[2])) {
					continue;
				}
				String fileStrTime = name.split("_")[3];
				long fileTime = Long.valueOf(fileStrTime);
				if (fileTime > t && fileTime >= end && fileTime <= start) {
					t = fileTime;
					resultFile = file;
				}
			}
			if (null == resultFile) {
				continue;
			}

			String[] name = resultFile.getName().split("_");
			Map<String, Integer> map = new HashMap<String, Integer>();
			HeightArea area = new HeightArea();
			area.setDataType(name[0]);
			area.setElement(name[1]);
			area.setTime(name[3]);
			area.setCurrent(map);
			int le = NumberUtils.toInt(level);
			parserWarnNC(root + resultFile.getName(), le, map);
			list.add(area);
		}
		return list;
	}

	public static List<TitanStorm> getTitanLiveData(String time, String rootPath, String titanPath, String titanName,
			String[] ncElements) {
		if (time == null) {
			return null;
		}
		String fileTime = DateUtil.dateToStr(getFileTime(time).getTime(), DateUtil.YMDHMS_NUM);
		String filePath = rootPath + String.format(titanPath, fileTime.substring(0, 8));
		String fileName = String.format(titanName, fileTime);
		File file = new File(filePath + fileName);
		if (!file.exists()) {
			return null;
		}
		List<TitanStorm> resultList = readTitanNcFile(filePath + fileName, fileTime, ncElements);
		return resultList;
	}

	public static List<TitanStorm> getTitanPastDataByNum(String time, int num, String rootPath, String titanPath,
			String titanName, String[] ncElements) {
		if (time == null) {
			return null;
		}
		Calendar cal = getFileTime(time);
		List<TitanStorm> resultList = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			String fileTime = DateUtil.dateToStr(cal.getTime(), DateUtil.YMDHMS_NUM);
			String filePath = rootPath + String.format(titanPath, fileTime.substring(0, 8));
			String fileName = String.format(titanName, fileTime);
			File file = new File(filePath + fileName);
			if (!file.exists()) {
				TitanStorm emptyStorm = addEmptyTitanPastData(fileTime, num);
				resultList.add(emptyStorm);
			} else {
				TitanStorm storm = readTitanNcFile(filePath + fileName, fileTime, num, ncElements);
				if (storm == null) {
					TitanStorm emptyStorm = addEmptyTitanPastData(fileTime, num);
					resultList.add(emptyStorm);
				} else {
					resultList.add(storm);
				}
			}
			cal.add(Calendar.MINUTE, -10);
		}

		if (!resultList.isEmpty()) {
			Collections.sort(resultList, new Comparator<TitanStorm>() {
				@Override
				public int compare(TitanStorm o1, TitanStorm o2) {
					Date time1 = o1.getTime();
					Date time2 = o2.getTime();
					return time1.compareTo(time2);
				}
			});
		}
		return resultList;
	}

	// -----------------------------------------------------------------------------------------//
	public static void parserWarnNC(String filePath, int level, Map<String, Integer> map) {
		NetcdfFile netcdFile = null;
		try {
			netcdFile = NetcdfFile.open(filePath);

			Variable levels = netcdFile.findVariable("levels");
			if (null == levels) {
				return;
			}

			int[] levelArray = (int[]) levels.read().copyTo1DJavaArray();
			for (int h : levelArray) {
				map.put(h + "", level);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (netcdFile != null) {
				try {
					netcdFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * UV转风速风向
	 */
	public static float[] uv2wind(float u, float v, float invalid) {
		float[] arr = new float[2];
		if (u >= 999.0 || v >= 999.0) {
			arr[0] = invalid;
			arr[1] = invalid;
			return arr;
		}
		BigDecimal wsDec = new BigDecimal(Math.hypot(u, v));
		wsDec = wsDec.setScale(1, RoundingMode.HALF_UP);
		float ws = wsDec.floatValue();
		BigDecimal wdDec = null;
		if (u == 0 && v == 0) {
			wdDec = new BigDecimal(Math.atan2(u, v) / Math.PI * 180);
		} else {
			wdDec = new BigDecimal(Math.atan2(u, v) / Math.PI * 180 + 180);
		}
		wdDec = wdDec.setScale(1, RoundingMode.HALF_UP);
		float wd = wdDec.floatValue();
		arr[0] = ws;
		arr[1] = wd % 360;
		return arr;
	}

	/**
	 * 
	 * @param filePath
	 * @param height
	 * @return
	 */
	public static Map<String, float[][]> parserWarnNC(String filePath, int height) {
		NetcdfFile netcdFile = null;
		Map<String, float[][]> map = new HashMap<String, float[][]>();
		try {
			netcdFile = NetcdfFile.open(filePath);

			Variable plevels = netcdFile.findVariable("levels");
			if (null == plevels) {
				return map;
			}

			int[] levelArray = (int[]) plevels.read().copyTo1DJavaArray();
			boolean result = ArrayUtils.contains(levelArray, height);
			if (!result) {
				return map;
			}

			for (int i = 0; i < 1000; i++) {
				Variable lon = netcdFile.findVariable("LON_" + height + "_" + String.format("%03d", i));
				if (null == lon) {
					return map;
				}

				float[] lonValue = (float[]) lon.read().copyTo1DJavaArray();

				Variable lat = netcdFile.findVariable("LAT_" + height + "_" + String.format("%03d", i));
				if (null == lat) {
					return map;
				}
				float[] latValue = (float[]) lat.read().copyTo1DJavaArray();
				float[][] value = new float[lonValue.length][2];
				for (int j = 0; j < lonValue.length; j++) {
					for (int k = 0; k < 2; k++) {
						if (k == 0) {
							value[j][k] = lonValue[j];
						} else {
							value[j][k] = latValue[j];
						}
					}
				}

				Variable maxWind = netcdFile.findVariable("MAXVALUE_" + height + "_" + String.format("%03d", i));
				if (null == maxWind) {
					return map;
				}

				float[] maxWindValue = (float[]) maxWind.read().copyTo1DJavaArray();

				Variable direction = netcdFile.findVariable("DIRECTION_" + height + "_" + String.format("%03d", i));
				if (null == direction) {
					return map;
				}

				int[] directionValue = (int[]) direction.read().copyTo1DJavaArray();
				map.put(i + "_" + maxWindValue[0] + "_" + directionValue[0], value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (netcdFile != null) {
				try {
					netcdFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;

	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	private static List<WARN> parserAirport(File file) {
		List<WARN> list = new ArrayList<WARN>();
		BufferedReader br = null;
		String line = "";
		String[] values = file.getName().split("_");
		try {
			br = new BufferedReader(new FileReader(file));
			while (null != (line = br.readLine())) {
				String lineString = line.trim();
				WARN warn = new WARN();
				String[] v = lineString.trim().split("\\s+");
				String[] value = v[1].split(",");
				int length = value.length;
				List<String> lev = new ArrayList<String>();
				for (int i = 0; i < length; i++) {
					lev.add(value[i]);
				}
				warn.setTime(v[0]);
				warn.setDataType(values[0]);
				warn.setElement(values[2]);
				warn.setLevel(lev);
				list.add(warn);
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

	private static List<JGRadar> parseJGRadar(File file) {
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
	 * 
	 * @param filePath
	 * @param element
	 * @param value
	 * @param height
	 * @param startlon
	 * @param endlon
	 * @param startlat
	 * @param endlat
	 * @return
	 */
	private static GridData parseNCFile(String filePath, String element, int value, int height, String startlon,
			String endlon, String startlat, String endlat) {
		GridData gridData = new GridData();
		NetcdfFile netcdFile = null;
		try {
			netcdFile = NetcdfFile.open(filePath);

			// 指定元素变量
			Variable ele = netcdFile.findVariable(element);
			if (null == ele) {
				return null;
			}

			// 经度
			Variable lon = netcdFile.findVariable("lon");
			if (null == lon) {
				return null;
			}
			Array lonArray = lon.read();
			// Array lonArray = lon.read("0:"+lon.getSize()+":" + value);
			float westLon = Float.valueOf(getValue(lon.getDataType(), 0, lonArray).toString());
			float westLon2 = Float.valueOf(getValue(lon.getDataType(), 1, lonArray).toString());
			// 经度格距
			float dx = (float) ((Math.round((westLon2 - westLon) * 100)) / 100.0);

			int lonstart = 0;
			if ((NumberUtils.toFloat(startlon) - westLon) * dx > 0) {
				lonstart = (int) ((NumberUtils.toFloat(startlon) - westLon) / dx);
			}

			int lonend = (int) lonArray.getSize();
			if ((NumberUtils.toFloat(endlon)
					- Float.valueOf(getValue(lon.getDataType(), lonend - 1, lonArray).toString())) * dx < 0) {
				lonend = (int) ((NumberUtils.toFloat(endlon) - westLon) / dx) + 1;
			}

			// 纬度
			Variable lat = netcdFile.findVariable("lat");
			if (null == lat) {
				return null;
			}
			Array latArray = lat.read();
			// 起始纬度
			float southLat = Float.valueOf(getValue(lat.getDataType(), 0, latArray).toString());
			float southLat2 = Float.valueOf(getValue(lat.getDataType(), 1, latArray).toString());
			// 纬度格距
			float dy = (float) ((Math.round((southLat2 - southLat) * 100)) / 100.0);

			int latstart = 0;
			int latend = (int) latArray.getSize();

			if (dy > 0) {
				if ((NumberUtils.toFloat(startlat) - southLat) > 0) {
					latstart = (int) (Math.abs((NumberUtils.toFloat(startlat) - southLat) / dy));
				}

				if ((NumberUtils.toFloat(endlat)
						- Float.valueOf(getValue(lat.getDataType(), latend - 1, latArray).toString())) < 0) {
					latend = (int) (Math.abs((NumberUtils.toFloat(endlat) - southLat) / dy)) + 1;
				}
			} else {
				if ((NumberUtils.toFloat(endlat) - southLat) < 0) {
					latstart = (int) (Math.abs((NumberUtils.toFloat(endlat) - southLat) / dy));
				}

				if ((NumberUtils.toFloat(startlat)
						- Float.valueOf(getValue(lat.getDataType(), latend - 1, latArray).toString())) > 0) {
					latend = (int) (Math.abs((NumberUtils.toFloat(startlat) - southLat) / dy)) + 1;
				}
			}
			int[] size = ele.getShape();
			String sectionSpec = "";
			Float countx = Float.valueOf(lonend - lonstart) / value;
			Float minusx = countx - countx.intValue();
			int lonx = countx.intValue();
			if (minusx != Float.valueOf(0)) {
				lonx = lonx + 1;
			}
			Float county = Float.valueOf(latend - latstart) / value;
			float minusy = county - county.intValue();
			int laty = county.intValue();
			if (minusy != Float.valueOf(0)) {
				laty = laty + 1;
			}
			float[][] avgData = new float[laty][lonx];
			if (3 == size.length) {
				sectionSpec = "0," + latstart + ":" + (latend - 1) + "," + lonstart + ":" + (lonend - 1);
				float[][] data = (float[][]) ele.read(sectionSpec).reduce().copyToNDJavaArray();
				for (int i = 0; i < laty; i++) {
					for (int j = 0; j < lonx; j++) {
						float pieceTotal = 0;
						int count = 0;
						for (int m = i * value; m < ((i + 1) * value > data.length ? data.length
								: (i + 1) * value); m++) {
							for (int n = j * value; n < ((j + 1) * value > data[m].length ? data[m].length
									: (j + 1) * value); n++) {
								if (data[m][n] == 0 || data[m][n] > 999999) {
									continue;
								}
								pieceTotal = pieceTotal + data[m][n];
								count++;
							}
						}
						if (0 == count) {
							avgData[i][j] = 0;
							continue;
						}
						avgData[i][j] = pieceTotal / count;
					}
				}
				gridData.setData(avgData);
			} else if (4 == size.length) {
				Variable high = netcdFile.findVariable("level");
				if (null == high) {
					return null;
				}
				float[] highValue = (float[]) high.read().copyTo1DJavaArray();
				int highLocal = -1;
				for (int i = 0; i < highValue.length; i++) {
					if (highValue[i] == height) {
						highLocal = i;
						break;
					}

				}
				if (-1 == highLocal) {
					return null;
				}
				sectionSpec = "0," + highLocal + "," + latstart + ":" + (latend - 1) + "," + lonstart + ":"
						+ (lonend - 1);
				float[][] data = (float[][]) ele.read(sectionSpec).reduce().copyToNDJavaArray();
				if (value == 1) {
					gridData.setData(data);
				} else {
					for (int i = 0; i < laty; i++) {
						for (int j = 0; j < lonx; j++) {
							float pieceTotal = 0;
							int count = 0;
							for (int m = i * value; m < ((i + 1) * value > data.length ? data.length
									: (i + 1) * value); m++) {
								for (int n = j * value; n < ((j + 1) * value > data[m].length ? data[m].length
										: (j + 1) * value); n++) {
									if (data[m][n] == 0 || data[m][n] > 999999) {
										continue;
									}
									pieceTotal = pieceTotal + data[m][n];
									count++;
								}
							}
							if (0 == count) {
								avgData[i][j] = 0;
								continue;
							}
							avgData[i][j] = pieceTotal / count;
						}
					}
					gridData.setData(avgData);
				}
			}

			// 设置无效值
			for (Attribute attri : ele.getAttributes()) {
				if ("_FillValue".equals(attri.getFullName())) {
					gridData.setInvalid(attri.getNumericValue().floatValue());
					break;
				}
			}

			gridData.setStartLon(westLon + lonstart * dx);
			gridData.setEndLon(westLon + (lonend - 1) * dx);
			gridData.setDx(dx * value);
			gridData.setNx((int) Math.ceil(Math.abs(lonend - lonstart) / (value * 1f)));

			if (dy < 0) {
				gridData.setStartLat(southLat + (latend - 1) * dy);
				gridData.setEndLat(southLat + latstart * dy);
				gridData.setDy(-dy * value);
				gridData.setNy((int) Math.ceil(Math.abs(latend - latstart) / (value * 1f)));
			} else {
				gridData.setEndLat(southLat + (latend - 1) * dy);
				gridData.setStartLat(southLat + latstart * dy);
				gridData.setDy(dy * value);
				gridData.setNy((int) Math.ceil(Math.abs(latend - latstart) / (value * 1f)));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidRangeException e) {
			e.printStackTrace();
		} finally {
			if (netcdFile != null) {
				try {
					netcdFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return gridData;

	}

	/**
	 * 读取属性变量存储的数据
	 * 
	 * @param dataType
	 *            数据类型
	 * @param i
	 *            数据位置
	 * @param array
	 *            数据集合
	 * @return 指定位置数据值
	 */
	private static Object getValue(DataType dataType, final int i, final Array array) {
		if (dataType == DataType.BOOLEAN) {
			return array.getBoolean(i);
		}
		if (dataType == DataType.BYTE) {
			return array.getByte(i);
		}
		if (dataType == DataType.CHAR) {
			return array.getChar(i);
		}
		if (dataType == DataType.DOUBLE) {
			return array.getDouble(i);
		}
		if (dataType == DataType.FLOAT) {
			return array.getFloat(i);
		}
		if (dataType == DataType.INT) {
			return array.getInt(i);
		}
		if (dataType == DataType.LONG) {
			return array.getLong(i);
		}
		if (dataType == DataType.SHORT) {
			return array.getShort(i);
		}
		return array.getObject(i);
	}

	private static Calendar getFileTime(String time) {
		Date date = DateUtil.strToDate(time, DateUtil.YMDHMS_NUM);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		int minute = cal.get(Calendar.MINUTE);
		cal.add(Calendar.MINUTE, -minute % 10);
		return cal;
	}

	private static List<TitanStorm> readTitanNcFile(String filePath, String fileTime, String[] ncElements) {
		if (filePath == null || filePath.isEmpty()) {
			return null;
		}
		NetcdfFile ncFile = null;
		List<TitanStorm> stormList = new ArrayList<>();
		try {
			ncFile = NetcdfFile.open(filePath);
			List<Variable> variables = ncFile.getVariables();
			if (variables == null || variables.isEmpty()) {
				return null;
			}
			int count = 0;
			while (true) {
				TitanStorm storm = putStormValue(count, ncFile, ncElements);
				if (storm == null) {
					break;
				}
				storm.setTime(DateUtil.strToDate(fileTime, DateUtil.YMDHMS_NUM));
				stormList.add(storm);
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ncFile != null) {
				try {
					ncFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stormList;
	}

	private static TitanStorm putStormValue(int num, NetcdfFile ncFile, String[] ncElements) {
		TitanStorm storm = new TitanStorm();
		try {
			// 经度
			Variable lon = ncFile.findVariable(String.format(ncElements[4], String.format("%03d", num)));
			if (lon != null) {
				float[] lonData = (float[]) lon.read().reduce().copyTo1DJavaArray();
				if (lonData != null && lonData.length != 0) {
					storm.setLons(lonData);
				}
			} else {
				return null;
			}
			// 纬度
			Variable lat = ncFile.findVariable(String.format(ncElements[3], String.format("%03d", num)));
			if (lat != null) {
				float[] latData = (float[]) lat.read().reduce().copyTo1DJavaArray();
				if (latData != null && latData.length != 0) {
					storm.setLats(latData);
				}
			}
			// 速度
			Variable speed = ncFile.findVariable(String.format(ncElements[5], num));
			if (speed != null) {
				float[] speedData = (float[]) speed.read().reduce().copyTo1DJavaArray();
				if (speedData != null && speedData.length != 0) {
					storm.setMoveSpeed(speedData[0]);
				}
			}
			// 顶高
			Variable topHgt = ncFile.findVariable(String.format(ncElements[6], num));
			if (topHgt != null) {
				float[] topHgtData = (float[]) topHgt.read().reduce().copyTo1DJavaArray();
				if (topHgtData != null && topHgtData.length != 0) {
					storm.setTopHgt(topHgtData[0]);
				}
			}
			// 速度
			Variable dir = ncFile.findVariable(String.format(ncElements[1], num));
			if (dir != null) {
				float[] dirData = (float[]) dir.read().reduce().copyTo1DJavaArray();
				if (dirData != null && dirData.length != 0) {
					storm.setMoveDir(dirData[0]);
				}
			}
			// 中心经度
			Variable centLon = ncFile.findVariable(String.format(ncElements[7], num));
			if (centLon != null) {
				float[] centLonData = (float[]) centLon.read().reduce().copyTo1DJavaArray();
				if (centLonData != null && centLonData.length != 0) {
					storm.setCentLon(centLonData[0]);
				}
			}
			// 中心纬度
			Variable centLat = ncFile.findVariable(String.format(ncElements[8], num));
			if (centLat != null) {
				float[] centLatData = (float[]) centLat.read().reduce().copyTo1DJavaArray();
				if (centLatData != null && centLatData.length != 0) {
					storm.setCentLat(centLatData[0]);
				}
			}
			// 中心高度
			Variable centHgt = ncFile.findVariable(String.format(ncElements[9], num));
			if (centHgt != null) {
				float[] centHgtData = (float[]) centHgt.read().reduce().copyTo1DJavaArray();
				if (centHgtData != null && centHgtData.length != 0) {
					storm.setCentHgt(centHgtData[0]);
				}
			}
			// 最大反射率因子
			Variable maxRefl = ncFile.findVariable(String.format(ncElements[10], num));
			if (maxRefl != null) {
				float[] maxReflData = (float[]) maxRefl.read().reduce().copyTo1DJavaArray();
				if (maxReflData != null && maxReflData.length != 0) {
					storm.setMaxRefl(maxReflData[0]);
				}
			}
			// vil值
			Variable vil = ncFile.findVariable(String.format(ncElements[11], num));
			if (vil != null) {
				float[] vilData = (float[]) vil.read().reduce().copyTo1DJavaArray();
				if (vilData != null && vilData.length != 0) {
					storm.setVil(vilData[0]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storm;
	}

	private static TitanStorm readTitanNcFile(String filePath, String fileTime, int num, String[] ncElements) {
		if (filePath == null || filePath.isEmpty()) {
			return null;
		}
		NetcdfFile ncFile = null;
		TitanStorm storm = null;
		try {
			ncFile = NetcdfFile.open(filePath);
			List<Variable> variables = ncFile.getVariables();
			if (variables == null || variables.isEmpty()) {
				return null;
			}
			storm = putStormValue(num, ncFile, ncElements);
			if (storm != null) {
				storm.setTime(DateUtil.strToDate(fileTime, DateUtil.YMDHMS_NUM));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ncFile != null) {
				try {
					ncFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return storm;
	}

	private static TitanStorm addEmptyTitanPastData(String time, int num) {
		TitanStorm storm = new TitanStorm();
		storm.setTime(DateUtil.strToDate(time, DateUtil.YMDHMS_NUM));
		storm.setNum(num);
		return storm;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
