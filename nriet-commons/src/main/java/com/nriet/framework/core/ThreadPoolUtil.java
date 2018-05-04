package com.nriet.framework.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {

	public static ExecutorService es = null;
	
	private static  int PoolSize = 1;
	
	public static ExecutorService getExecutorService() {
		if(es != null) {
			return es;
		}else {
			return Executors.newFixedThreadPool(PoolSize);
		}
	}
	
}
