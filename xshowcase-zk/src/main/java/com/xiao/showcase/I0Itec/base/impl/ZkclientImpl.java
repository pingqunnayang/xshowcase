/**
 * Ctrip.com Inc. Copyright (c) 2000-2018 All Rights Reserved.
 */
package com.xiao.showcase.I0Itec.base.impl;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.xiao.showcase.I0Itec.base.ZookeeperClient;


/**
 * @author zpxiao
 * @date 2015-1-13 上午11:08:12
 */
public class ZkclientImpl implements ZookeeperClient{
	
	private final ZkClient client;

	private volatile KeeperState state = KeeperState.SyncConnected;
	
	public ZkclientImpl(String zkServers) {
		client = new ZkClient(zkServers);
	}
	
	@Override
	public void createPersistent(String path,Object data) {
		try {
			client.createPersistent(path, data);
		} catch (ZkNodeExistsException e) {
		}
	}

	@Override
	public void createEphemeral(String path, Object data) {
		try {
			client.createEphemeral(path, data);
		} catch (ZkNodeExistsException e) {
		}
	}

	@Override
	public void delete(String path) {
		try {
			client.delete(path);
		} catch (ZkNoNodeException e) {
		}
	}

	@Override
	public List<String> getChildren(String path) {
		try {
			return client.getChildren(path);
        } catch (ZkNoNodeException e) {
            return null;
        }
	}
	
	public boolean exists(String path){
		return client.exists(path);
	}
	
	public <T>T readData(String path){
		return client.readData(path);
	}
	
	public <T>T readData(String path,boolean returnNullIfPathNotExists){
		return client.readData(path, returnNullIfPathNotExists);
	}

	@Override
	public List<String> addChildListener(String path, IZkChildListener listener) {
		return client.subscribeChildChanges(path, listener);
	}

	@Override
	public void removeChildListener(String path, IZkChildListener listener) {
		client.unsubscribeChildChanges(path, listener);
	}

	@Override
	public void addStateListener(IZkStateListener listener) {
		client.subscribeStateChanges(listener);
	}

	@Override
	public void removeStateListener(IZkStateListener listener) {
		client.unsubscribeStateChanges(listener);
	}

	@Override
	public void addDataChange(String path,IZkDataListener listener) {
		client.subscribeDataChanges(path, listener);
	}

	@Override
	public void removeDataChange(String path,IZkDataListener listener) {
		client.unsubscribeDataChanges(path, listener);
	}

	@Override
	public boolean isConnected() {
		return state == KeeperState.SyncConnected;
	}

	@Override
	public void close() {
		client.close();
	}

	public ZkClient getClient() {
		return client;
	}
	
}
