package com.nriet.framework.api.images;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nriet.framework.api.images.entity.ProductParam;
import com.nriet.framework.api.images.entity.Product;
import com.nriet.framework.util.DateUtil;
import com.nriet.framework.util.ImageUtil;

public class ImgProduceUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHH0000");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 
	 * @param imgProduct
	 * @param rootPath
	 * @param nafpPath
	 * @param nafpfileName
	 * @return
	 */
	public static List<Product> getProduct(ProductParam imgProduct,String rootPath,String nafpPath,String nafpfileName) {
		String filePath2 = "";
		Calendar time = imgProduct.getTime();
		List<Product> list = new ArrayList<Product>();
		String filePath = rootPath + String.format(nafpPath, imgProduct.getDataType(), imgProduct.getElement(), sdf.format(time.getTime()));
		File filePath1 = new File(filePath);
		if (!filePath1.exists()) {
			return null;
		}
		if ("000" == imgProduct.getInterval() || imgProduct.getSum() == Integer.valueOf(imgProduct.getInterval())) {
			filePath2 = filePath + String.format(nafpfileName, imgProduct.getDataType(), imgProduct.getElement(), imgProduct.getHeight(), imgProduct.getStationId(), sdf1.format(imgProduct.getTime().getTime()), String.format("%03d", imgProduct.getInterval()));
			File file = new File(filePath2);
			if (!file.exists()) {
				return null;
			}
			Product p = new Product();			
			p.setUrl(ImageUtil.getImageStr(filePath2));		
			list.add(p);
		} else {
			for (int i = 0; i <= imgProduct.getSum()/Integer.valueOf(imgProduct.getInterval()); i++) {
				filePath2 = filePath + String.format(nafpfileName, imgProduct.getDataType(), imgProduct.getElement(), imgProduct.getHeight(), imgProduct.getStationId(), sdf1.format(imgProduct.getTime().getTime()), String.format("%03d", i * Integer.valueOf(imgProduct.getInterval())));
				File filen = new File(filePath2);
				if (!filen.exists()) {
					continue;
				}
				Product p = new Product();
				time.add(Calendar.MINUTE, i * Integer.valueOf(imgProduct.getInterval()));
				p.setTime(sdf2.format(time.getTime()));
				p.setUrl(ImageUtil.getImageStr(filePath2));		
				list.add(p);
				time.add(Calendar.MINUTE, -i * Integer.valueOf(imgProduct.getInterval()));
			}
		}
		return list;
	}
	/**
	 * 
	 * @param imgProduct
	 * @param rootPath
	 * @param hisPath
	 * @return
	 */
	public static List<Product> getHisProduct(ProductParam imgProduct,String rootPath,String hisPath) {
		List<Product> list = new ArrayList<Product>();	
		Calendar time = imgProduct.getTime();
		String filePath = rootPath + String.format(hisPath, imgProduct.getDataType(), imgProduct.getElement(), sdf.format(imgProduct.getTime().getTime()));
		File filePath1 = new File(filePath);
		if (!filePath1.exists()) {
			time.add(Calendar.HOUR_OF_DAY, -(imgProduct.getSum() / 60));
			filePath = rootPath + String.format(hisPath, imgProduct.getDataType(), imgProduct.getElement(), sdf.format(time.getTime()));			
			filePath1 = new File(filePath);
			if (!filePath1.exists()) {
				return null;
			}
			time.add(Calendar.HOUR_OF_DAY, imgProduct.getSum() / 60);
		}	
		boolean result = false;
		long start = Long.valueOf(sdf2.format(time.getTime()));
		time.add(Calendar.MINUTE, -imgProduct.getSum());
		long end = Long.valueOf(sdf2.format(time.getTime()));
		time.add(Calendar.MINUTE, imgProduct.getSum());	
		while(true) {					
			File[] files = filePath1.listFiles();
			for (File f : files) {
				if (!f.getName().contains(imgProduct.getStationId())) {
					continue;
				}
				Product p = new Product();			
				String[] fileName = f.getName().split("\\_");
				long fileTime = Long.valueOf(fileName[4]);
				if (imgProduct.getHeight().equals(fileName[2]) && fileTime >= end && fileTime <= start) {
					p.setTime(fileName[4]);
					String path = filePath + f.getName();
					p.setUrl(ImageUtil.getImageStr(path));		
					list.add(p);
				}
			}
			if (result) {
				return list;
			}
			if (time.get(Calendar.HOUR_OF_DAY) < imgProduct.getSum() / 60) {
				time.add(Calendar.MINUTE, -imgProduct.getSum());
				filePath = rootPath + String.format(hisPath, imgProduct.getDataType(), imgProduct.getElement(), sdf.format(time.getTime()));
				filePath1 = new File(filePath);
				if (!filePath1.exists()) {
					return list;
				}
				result = true;				
			} else {
				return list;
			}
			
		}		
	}
	/**
	 * 
	 * @param imgProduct
	 * @param rootPath
	 * @param hisPath
	 * @return
	 */
	public static List<Product> getHisNumProduct(ProductParam imgProduct,String rootPath, String hisPath) {
		List<Product> list = new ArrayList<Product>();
		Calendar time = imgProduct.getTime();
		long start = Long.valueOf(sdf2.format(time.getTime()));
		Map<Long, Product> map = new HashMap<Long, Product>();
		List<Long> timesList = new ArrayList<Long>();
		for (int k = 0; k < imgProduct.getSum() / 24; k++) {
			int hour = time.get(Calendar.HOUR_OF_DAY);
			String filePath = rootPath + String.format(hisPath, imgProduct.getDataType(), imgProduct.getElement(), sdf.format(time.getTime()));
			File filePath1 = new File(filePath);
			if (!filePath1.exists()) {
				continue;
			}			
			time.add(Calendar.HOUR_OF_DAY, -hour);
			long end = Long.valueOf(sdf2.format(time.getTime()));
			time.add(Calendar.HOUR_OF_DAY, hour);			
			
			File[] files = filePath1.listFiles();
			for (File f : files) {
				if (!f.getName().contains(imgProduct.getStationId())) {
					continue;
				}
				Product p = new Product();			
				String[] fileName = f.getName().split("\\_");
				long fileTime = Long.valueOf(fileName[4]);
				
				if (imgProduct.getHeight().equals(fileName[2]) && fileTime >= end && fileTime <= start) {
					if (timesList.contains(fileTime)) {
						continue;
					}
					p.setTime(fileName[4]);				
					String path = filePath + f.getName();
					p.setUrl(ImageUtil.getImageStr(path));		
					timesList.add(fileTime);
					map.put(fileTime, p);
				}
			}
			if (timesList.size() >= imgProduct.getNumber()) {
				break;
			}			
			time.add(Calendar.HOUR_OF_DAY, -k * 24);
		}
		
		
		if (0 == timesList.size()) {
			return list;
		}
		
		Collections.sort(timesList);
		for (int i = timesList.size() - 1; i >= 0; i--) {
			list.add(map.get(timesList.get(i)));
			if (list.size() == imgProduct.getNumber()) {
				break;
			}
		}	
		return list;
	}
	/**
	 * 获取指定时间指定时间范围内的最新的图片
	 * @param imgProduct
	 * 						ImgProduct对象
	 * @param rootPath
	 * 					根目录
	 * @param hisPath
	 * 					hisPath
	 * @param legendRootPath
	 * 							legendRootPath
	 * @param legendName
	 * 						legendName
	 * @return
	 */
	public static List<Product> getLatestProduct(ProductParam imgProduct,String rootPath, String hisPath,String legendRootPath,String legendName) {
		List<Product> productList = new ArrayList<>();
		Calendar time = imgProduct.getTime();
		String time1 = DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM);
		int day1 = time.get(Calendar.DAY_OF_MONTH);
		time.add(Calendar.MINUTE, imgProduct.getSum());
		String time2 = DateUtil.dateToStr(time.getTime(), DateUtil.YMDHMS_NUM);
		int day2 = time.get(Calendar.DAY_OF_MONTH);
		
		String startTime = "";
		String endTime = "";
		if(time1.compareTo(time2) > 0) {
			startTime = time2;
			endTime = time1;
		}else if (time1.compareTo(time2) < 0) {
			startTime = time1;
			endTime = time2;
		}else {
			startTime = time1;
			endTime = time1;
		}
		
		List<File> fileList = new ArrayList<>();
		// 是否跨天
		if(day1 != day2) {
			String filePath1 = rootPath + String.format(hisPath, imgProduct.getDataType(), imgProduct.getElement(), time1.substring(0, 8));
			String filePath2 = rootPath + String.format(hisPath, imgProduct.getDataType(), imgProduct.getElement(), time2.substring(0, 8));
			File file1 = new File(filePath1);
			File file2 = new File(filePath2);
			if(!file1.exists() && !file2.exists()) {
				return null;
			}
			File[] files1 = file1.listFiles();
			File[] files2 = file2.listFiles();
			if((files1 == null || files1.length == 0) && (files2 == null || files2.length == 0)) {
				return null;
			}
			if(files1 != null && files1.length != 0) {
				for(File file : files1) {
					String fileName = file.getName();
					String[] fileNamePieces = fileName.split("_");
					if(fileNamePieces.length != 6) {
						continue;
					}
					String fileDataType = fileNamePieces[0];
					String fileElement = fileNamePieces[1];
					String fileHeight = fileNamePieces[2];
					String fileStation = fileNamePieces[3];
					String fileTime = fileNamePieces[4];
					String fileInterval = fileNamePieces[5].split("\\.")[0];
					if(!fileDataType.equals(imgProduct.getDataType()) || !fileElement.equals(imgProduct.getElement()) 
							|| !fileHeight.equals(imgProduct.getHeight()) || !fileStation.equals(imgProduct.getStationId()) 
							|| !fileInterval.equals(imgProduct.getInterval())) {
						continue;
					}
					if(fileTime.compareTo(startTime) >= 0 && fileTime.compareTo(endTime) <= 0) {
						fileList.add(file);
					}
				}
			}
			if(files2 != null && files2.length != 0) {
				for(File file : files2) {
					String fileName = file.getName();
					String[] fileNamePieces = fileName.split("_");
					if(fileNamePieces.length != 6) {
						continue;
					}
					String fileDataType = fileNamePieces[0];
					String fileElement = fileNamePieces[1];
					String fileHeight = fileNamePieces[2];
					String fileStation = fileNamePieces[3];
					String fileTime = fileNamePieces[4];
					String fileInterval = fileNamePieces[5].split("\\.")[0];
					if(!fileDataType.equals(imgProduct.getDataType()) || !fileElement.equals(imgProduct.getElement()) 
							|| !fileHeight.equals(imgProduct.getHeight()) || !fileStation.equals(imgProduct.getStationId()) 
							|| !fileInterval.equals(imgProduct.getInterval())) {
						continue;
					}
					if(fileTime.compareTo(startTime) >= 0 && fileTime.compareTo(endTime) <= 0) {
						fileList.add(file);
					}
				}
			}
		}else {
			String filePath = rootPath + String.format(hisPath, imgProduct.getDataType(), imgProduct.getElement(), time1.substring(0, 8));
			File file = new File(filePath);
			if(!file.exists()) {
				return null;
			}
			File[] files = file.listFiles();
			if(files == null || files.length == 0) {
				return null;
			}
			for(File f : files) {
				String fileName = f.getName();
				String[] fileNamePieces = fileName.split("_");
				if(fileNamePieces.length != 6) {
					continue;
				}
				String fileDataType = fileNamePieces[0];
				String fileElement = fileNamePieces[1];
				String fileHeight = fileNamePieces[2];
				String fileStation = fileNamePieces[3];
				String fileTime = fileNamePieces[4];
				String fileInterval = fileNamePieces[5].split("\\.")[0];
				if(!fileDataType.equals(imgProduct.getDataType()) || !fileElement.equals(imgProduct.getElement()) 
						|| !fileHeight.equals(imgProduct.getHeight()) || !fileStation.equals(imgProduct.getStationId()) 
						|| !fileInterval.equals(imgProduct.getInterval())) {
					continue;
				}
				if(fileTime.compareTo(startTime) > 0 && fileTime.compareTo(endTime) <= 0) {
					fileList.add(f);
				}
			}
		}
		if(fileList.isEmpty()) {
			return null;
		}
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				String fileName1 = o1.getName();
				String fileName2 = o2.getName();
				String fileTime1 = fileName1.split("_")[4];
				String fileTime2 = fileName2.split("_")[4];
				return fileTime2.compareTo(fileTime1);
			}
		});
		
		int size = 0;
		if(imgProduct.getNumber() >= fileList.size()) {
			size = fileList.size();
		}else {
			size = imgProduct.getNumber();
		}
		for(int i = 0; i < size; i++) {
			File f = fileList.get(i);
			String[] fileNamePieces = f.getName().split("_");
			String fileTime = fileNamePieces[4];
			Product p = new Product();
			p.setUrl(ImageUtil.getImageStr(f.getPath()));
			p.setLegendUrl(ImageUtil.getImageStr(legendRootPath + legendName));
			p.setTime(fileTime);
			p.setType(imgProduct.getElement());
			productList.add(p);
		}
		return productList;
	}
	
	public static void main(String[] args) {
		ProductParam pro = new ProductParam();
		pro.setTime(DateUtil.getStringToCalendar("20170806130000"));
		pro.setDataType("LAPS");
		pro.setElement("WIND-SURF");
		pro.setHeight("000");
		pro.setInterval("000");
		pro.setNumber(1);
		pro.setSum(-10);
		pro.setStationId("ZPPP");
		List<Product> list = null;
//		list = ImgProduceUtil.getProduct(pro, "D:/DownLoadFile/product_fz/", "image/MOP/%s/%s/%s/","%s_%s_%s_%s_%s_%s.png");
		list = ImgProduceUtil.getLatestProduct(pro, "D:/DownLoadFile/product_fz/", "image/MOP/%s/%s/%s/","D:/DownLoadFile/COLORMAPS/","COLOR_EDA_000.png");
		System.out.println(list.get(0).getLegendUrl());
	}
}
