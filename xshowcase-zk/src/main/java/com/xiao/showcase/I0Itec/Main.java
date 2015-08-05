package com.xiao.showcase.I0Itec;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import com.xiao.showcase.I0Itec.base.impl.ZkclientImpl;


public class Main {
	
	
	public static void main(String[] args) {
		ZkclientImpl client = new ZkclientImpl("10.3.6.93:2181");
		if(!client.exists("/showcase")){
			client.createEphemeral("/showcase", "showcase");
		}
		
		
	}

}
