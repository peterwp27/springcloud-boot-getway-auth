package com.nriet.datacenter.service.fuse.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.api.data.ReadFileUtil;
import com.nriet.datacenter.model.fuse.AREA;
import com.nriet.datacenter.model.fuse.GridData;
import com.nriet.datacenter.model.fuse.HeightArea;
import com.nriet.datacenter.model.fuse.StormEcho;
import com.nriet.datacenter.model.fuse.TitanStorm;
import com.nriet.datacenter.model.fuse.WARN;
import com.nriet.datacenter.service.fuse.FuseService;
import com.nriet.datacenter.util.DataCenterAPIConfig;
import com.nriet.framework.api.images.ImgProduceUtil;
import com.nriet.framework.api.images.entity.Product;
import com.nriet.framework.api.images.entity.ProductParam;
import com.nriet.framework.api.netcdf.NetcdfFileReader;
import com.nriet.framework.api.netcdf.entity.NcData;
import com.nriet.framework.api.netcdf.entity.NcParam;
import com.nriet.framework.util.DateUtil;

import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

@Service
@Transactional
public class FuseServiceImpl implements FuseService {

	@Autowired
	private DataCenterAPIConfig config;

	private static final DecimalFormat DF = new DecimalFormat("###0.0");

	/**
	 * 获取融合数据
	 * 
	 * @param time
	 *            观测时间
	 * @param element
	 *            要素
	 * @return
	 */
	@Override
	public GridData readLapsData(Calendar time, String element, int height) {
		return ReadFileUtil.readLapsData(time, element, height, config.getRootPath(), config.getLapsPath(),
				config.getLapsFileName(), config.getStartLon(), config.getEndLon(), config.getStartLat(),
				config.getEndLat());
	}

	/**
	 * 获取告警全时间数据
	 * 
	 * @param time
	 *            观测时间或预报时间
	 * @param type
	 *            类型.0：机场，1：导航点，2：航线
	 * @param height
	 * @return
	 */
	@Override
	public List<WARN> getWarnInfo(Calendar time, int type1, int height) {
		return ReadFileUtil.getWarnInfo(time, type1, height, config.getWarnDataType(),
				config.getRootPath(), config.getWarnPath());
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
	@Override
	public List<AREA> getAreaInfo(Calendar time, int interval, int height) {
		List<AREA> list = new ArrayList<AREA>();
		String root = config.getRootPath()
				+ String.format(config.getWarnPath(), DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
		File rootFile = new File(root);
		if (!rootFile.exists()) {
			time.add(Calendar.DAY_OF_MONTH, -1);
			root = config.getRootPath()
					+ String.format(config.getWarnPath(), DateUtil.dateToStr(time.getTime(), DateUtil.YMD_NUM));
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
		String[] levelArr = config.getWarnLevel().split(",");
		String[] elements = config.getWarnElement().split(",");
		for (String level : levelArr) {
			for(String element : elements) {
				long t = 0l;
				for (File file : files) {
					String name = file.getName();
					String[] values = name.split("_");
					if (!level.equals(values[2]) || !element.equals(values[1])) {
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
				List<AREA> dataList = readFogNcFile(root + resultFile.getName(), element, name, height, level);
				if(dataList != null && !dataList.isEmpty()) {
					list.addAll(dataList);
				}
			}
		}
		return list;
	}
	
	private List<AREA> readFogNcFile(String filePath, String element, String[] name, int height, String level){
		List<NcParam> params = new ArrayList<>();
		String[] variableNames = config.getNcVariableMap().get(element);
		for(String variableName : variableNames) {
			NcParam ncParam = new NcParam();
			ncParam.setVariableName(String.format(variableName, String.valueOf(height)));
			params.add(ncParam);
		}
		List<NcData> dataList = NetcdfFileReader.readNcFile(filePath, params); 
		if(dataList == null || dataList.isEmpty()) {
			return null;
		}
		List<AREA> resultList = new ArrayList<>();
		Collections.sort(dataList, new Comparator<NcData>() {
			@Override
			public int compare(NcData o1, NcData o2) {
				String name1 = o1.getVariableName();
				String name2 = o2.getVariableName();
				int areaId1 = Integer.valueOf(name1.split("_")[2]);
				int areaId2 = Integer.valueOf(name2.split("_")[2]);
				return areaId1 - areaId2;
			}
		});
		for(int i = 0; i < dataList.size() / variableNames.length; i++) {
			AREA area = new AREA();
			area.setDataType(name[0]);
			area.setElement(element);
			area.setTime(name[3]);
			area.setLevel(level);
			area.setId(i);
			float[][] lonlat = null;
			float[] lonData = null;
			float[] latData = null;
			for(int j = 0; j < variableNames.length; j++) {
				NcData ncData = dataList.get(variableNames.length * i + j);
				if(ncData.getVariableName().contains("LON")) {
					lonData = (float[]) ncData.getData();
				}else if(ncData.getVariableName().contains("LAT")) {
					latData = (float[]) ncData.getData();
				}else if(ncData.getVariableName().contains("DIRECTION")) {
					int[] dirData = (int[]) ncData.getData();
					area.setMoveDir(dirData[0]);
				}else if(ncData.getVariableName().contains("MAXVALUE")) {
					float[] maxWindData = (float[]) ncData.getData();
					area.setMoveSpeed(maxWindData[0]);
				}
			}
			if(lonData == null || latData == null || lonData.length != latData.length) {
				continue;
			}
			lonlat = new float[lonData.length][2];
			for(int j = 0; j < lonData.length; j++) {
				lonlat[j][0] = lonData[j];
				lonlat[j][1] = latData[j];
			}
			area.setCurrent(lonlat);
			resultList.add(area);
		}
		return resultList;
	}

//	public Map<String, float[][]> parserWarnNC(String filePath, int height, String element) {
//		NetcdfFile netcdFile = null;
//		Map<String, float[][]> map = new HashMap<String, float[][]>();
//		try {
//			netcdFile = NetcdfFile.open(filePath);
//
//			Variable plevels = netcdFile.findVariable("levels");
//			if (null == plevels) {
//				return map;
//			}
//
//			int[] levelArray = (int[]) plevels.read().copyTo1DJavaArray();
//			boolean result = ArrayUtils.contains(levelArray, height);
//			if (!result) {
//				return map;
//			}
//
//			for (int i = 0; i < 1000; i++) {
//				Variable lon = netcdFile.findVariable("LON_" + height + "_" + String.format("%03d", i));
//				if (null == lon) {
//					return map;
//				}
//
//				float[] lonValue = (float[]) lon.read().copyTo1DJavaArray();
//
//				Variable lat = netcdFile.findVariable("LAT_" + height + "_" + String.format("%03d", i));
//				if (null == lat) {
//					return map;
//				}
//				float[] latValue = (float[]) lat.read().copyTo1DJavaArray();
//				float[][] value = new float[lonValue.length][2];
//				for (int j = 0; j < lonValue.length; j++) {
//					for (int k = 0; k < 2; k++) {
//						if (k == 0) {
//							value[j][k] = lonValue[j];
//						} else {
//							value[j][k] = latValue[j];
//						}
//					}
//				}
//
//				Variable maxWind = netcdFile.findVariable("MAXVALUE_" + height + "_" + String.format("%03d", i));
//				if (null == maxWind) {
//					return map;
//				}
//
//				float[] maxWindValue = (float[]) maxWind.read().copyTo1DJavaArray();
//
//				Variable direction = netcdFile.findVariable("DIRECTION_" + height + "_" + String.format("%03d", i));
//				if (null == direction) {
//					return map;
//				}
//
//				int[] directionValue = (int[]) direction.read().copyTo1DJavaArray();
//				map.put(i + "_" + maxWindValue[0] + "_" + directionValue[0], value);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (netcdFile != null) {
//				try {
//					netcdFile.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return map;
//
//	}

	/**
	 * 获取所有高度层有落区的数据信息
	 * 
	 * @param time
	 *            起报时间或观测时间
	 * @return
	 */
	@Override
	public List<HeightArea> getHeightAreaInfo(Calendar time) {
		return ReadFileUtil.getHeightAreaInfo(time, config.getWarnDataType(), 
				config.getWarnLevel(), config.getRootPath(), config.getWarnPath());
	}

	/**
	 * 获取TITAN实况数据
	 * 
	 * @param time
	 *            查询时间
	 * @return
	 */
	@Override
	public List<TitanStorm> getTitanLiveData(String time) {
		return ReadFileUtil.getTitanLiveData(time, config.getRootPath(), config.getTitanPath(), config.getTitanName(),
				config.getTitanVariables());
	}

	/**
	 * 获取指定落区编号的TITAN过去半小时数据
	 * 
	 * @param time
	 *            查询时间
	 * @param num
	 *            落区编号
	 * @return
	 */
	@Override
	public List<TitanStorm> getTitanPastDataByNum(String time, int num) {
		return ReadFileUtil.getTitanPastDataByNum(time, num, config.getRootPath(), config.getTitanPath(),
				config.getTitanName(), config.getTitanVariables());
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

	/**
	 * 回波顶高回波底高时序图数据获取
	 * 
	 * @param time
	 *            查询时间
	 * @param lon
	 *            经度
	 * @param lat
	 *            纬度
	 * @return
	 */
	@Override
	public List<StormEcho> getEchoData(String time, float lon, float lat) {
		if (time == null) {
			return null;
		}
		Calendar cal = getFileTime(time);
		List<StormEcho> resultList = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			String fileTime = DateUtil.dateToStr(cal.getTime(), DateUtil.YMDHMS_NUM);
			String filePath = config.getRootPath() + String.format(config.getLapsPath(), fileTime.substring(0, 8));
			String fileName = String.format(config.getLapsFileName(), config.getEchotop(), fileTime);
			File file = new File(filePath + fileName);
			if (!file.exists()) {
				// 源数据文件不存在则添加空数据
				addEmptyEchoTop(resultList, fileTime);
			} else {
				// 获取回波顶高
				String echoTop = readEchoTopFile(filePath + fileName, lon, lat);
				System.out.println(echoTop);
				StormEcho echo = new StormEcho();
				echo.setTime(fileTime);
				// echo.setEchoTop(echoTop);
				// TODO 回波底高
				// test
				echo.setEchoBottom(DF.format(Math.random() * 100));
				echo.setEchoTop(DF.format(Math.random() * 100));
				resultList.add(echo);
			}
			cal.add(Calendar.MINUTE, -10);
		}
		return resultList;
	}

	private String readEchoTopFile(String filePath, float lon, float lat) {
		NetcdfFile ncFile = null;
		try {
			ncFile = NetcdfFile.open(filePath);
			Variable lonV = ncFile.findVariable("lon");
			Variable latV = ncFile.findVariable("lat");
			Variable echoTopV = ncFile.findVariable("ECHOTOP");
			if (lonV == null || latV == null || echoTopV == null) {
				return null;
			}
			float[] lonData = (float[]) lonV.read().reduce().copyTo1DJavaArray();
			float[] latData = (float[]) latV.read().reduce().copyTo1DJavaArray();

			int lonInt = 0;
			int latInt = 0;
			float tempLon = 9999.0f;
			float tempLat = 9999.0f;
			// 获取指定经纬度在网格中与其距离最近的格点
			for (int i = 0; i < lonData.length; i++) {
				float minusLon = Math.abs(lon - lonData[i]);
				if (tempLon > minusLon) {
					tempLon = minusLon;
					lonInt = i;
				}
			}
			for (int j = 0; j < latData.length; j++) {
				float minusLat = Math.abs(lat - latData[j]);
				if (tempLat > minusLat) {
					tempLat = minusLat;
					latInt = j;
				}
			}

			String sectionSpec = "0," + latInt + "," + lonInt;
			// 读取该格点的处的回波顶高数据
			float[] data = (float[]) echoTopV.read(sectionSpec).reduce().copyTo1DJavaArray();
			return DF.format(data[0]);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidRangeException e) {
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
		return null;
	}

	private void addEmptyEchoTop(List<StormEcho> resultList, String fileTime) {
		StormEcho echo = new StormEcho();
		echo.setTime(fileTime);
		resultList.add(echo);
	}

	private Calendar getFileTime(String time) {
		Date date = DateUtil.strToDate(time, DateUtil.YMDHMS_NUM);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		int minute = cal.get(Calendar.MINUTE);
		cal.add(Calendar.MINUTE, -minute % 10);
		return cal;
	}

}
