package com.xiao.showcase.curator.base;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.xiao.showcase.base.Constants;

/**
 * @author zpxiao  
 * @date 2014-12-8 下午2:27:13
 */
public class ClientUtils {
	
	private static CuratorFramework  client = null;
	static{
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(Constants.conn, retryPolicy);
		client.start();
	}
	
	public static CuratorFramework getClient(){
		return client;
	}
}
