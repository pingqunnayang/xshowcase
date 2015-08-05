package com.xiao.showcase.transation.spouts;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产数据流，Thread
 * @author zpxiao
 */
public class DataSource {
	
	public static AtomicInteger clientId= new AtomicInteger(0);
	
	public static int getIncreClientId(){
		return  clientId.incrementAndGet();
	}
	
}
