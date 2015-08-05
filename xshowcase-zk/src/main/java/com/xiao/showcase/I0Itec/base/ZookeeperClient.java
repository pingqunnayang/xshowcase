/**
 * Ctrip.com Inc. Copyright (c) 2000-2018 All Rights Reserved.
 */
package com.xiao.showcase.I0Itec.base;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;

/**
 * 使用
 * @author zpxiao
 * @date 2015-1-13 上午10:47:54
 */
public interface ZookeeperClient {
	
	void createPersistent(String path,Object data);
	
	void createEphemeral(String path,Object data);
	
	void delete(String path);

	List<String> getChildren(String path);

	List<String> addChildListener(String path, IZkChildListener listener);

	void removeChildListener(String path, IZkChildListener listener);

	void addStateListener(IZkStateListener listener);
	
	void removeStateListener(IZkStateListener listener);
	
	void addDataChange(String path,IZkDataListener listener);
	
	void removeDataChange(String path,IZkDataListener listener);
	
	boolean isConnected();

	void close();

}
