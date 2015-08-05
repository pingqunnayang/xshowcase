package com.xiao.showcase.zknode;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import com.xiao.showcase.base.BaseZookeeper;

import backtype.storm.daemon.common.Assignment;

/**
 * 任务分配信息
 */
public class Assignments {
	
	public static void main(String[] args) throws Exception {
		BaseZookeeper baseZookeeper = new BaseZookeeper();  
        String host = "10.3.6.93:2181";  
        //zookeeper  dd
        baseZookeeper.connectZookeeper(host);  
        List<String> childrens = baseZookeeper.getChildren("/infosec-storm-mine/assignments");
        for(String children : childrens){
	        byte[] data = baseZookeeper.getData("/infosec-storm-mine/assignments/" + children);
	        ObjectInputStream in=new ObjectInputStream(new ByteArrayInputStream(data));
	        Assignment s = (Assignment) in.readObject();
	        System.out.println("==========TopoLogyId=" + children + "================");
	        System.out.println("对应分配端口:" + s.executor__GT_node_PLUS_port);
	        System.out.println("分配的时间:" + s.executor__GT_start_time_secs);
	        System.out.println("存储目录:" + s.master_code_dir);
	        System.out.println("HostName: " + s.node__GT_host);
        }
	}
}
