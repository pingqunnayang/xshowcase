package com.xiao.showcase.base;

import java.io.IOException;

/**
 * @author zpxiao
 * @date 2014-11-20 下午3:20:55
 */
public class ZkUtils {
	
	static BaseZookeeper zk = new BaseZookeeper();
	static{
		try {
			zk.connectZookeeper("10.3.6.93:2181");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static BaseZookeeper getConn(){
		return zk;
	}

}
