package cn.liandi.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String YMDHMS_STD = "yyyy-MM-dd HH:mm:ss";
	
	public static final String YMD_STD = "yyyy-MM-dd";
	
	public static final String YMDHM_STD = "yyyy-MM-dd HH:mm";
	
	public static final String YMDH_STD = "yyyy-MM-dd HH";
	
	public static final String YMDHMS_NUM = "yyyyMMddHHmmss";
	
	public static final String YMDHM_NUM = "yyyyMMddHHmm";
	
	public static final String YMDH_NUM = "yyyyMMddHH";
	
	public static String dateToStr(Date date, String format) {
		if(format == null || format.isEmpty()) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static Date strToDate(String time, String format) {
		if(time == null || time.isEmpty()) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 把时间字符串转为Calendar类型
	 */
	public static Calendar getStringToCalendar(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		cal.setTime(date);
		return cal;
	}
}
