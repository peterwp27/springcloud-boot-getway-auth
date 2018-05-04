package com.nriet.framework.util;

import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件的工具类
 * 
 * @author b_wangpei
 * @date 2018-4-2
 */
public class PropertiesUtil {
    private Properties props;

    public PropertiesUtil(String fileName) {
        readProperties(fileName);
    }
    
    public static PropertiesUtil Instances(String fileName) {
    	return new PropertiesUtil(fileName);
    }
    /**
     * 加载配置文件
     * 
     * @param fileName
     */
    private void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(
                    this.getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8");
            props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key读取对应的value
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        return props.getProperty(key);
    }

    /**
     * 得到所有的配置信息
     * 
     * @return
     */
    public Map<?, ?> getAll() {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<?> enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }
}