package com.xiao.showcase.zknode;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import com.xiao.showcase.base.BaseZookeeper;

import clojure.lang.PersistentArrayMap;

/**
 * worker的心跳信息
 */
public class Workerbeats {
	
	public static void main(String[] args) throws Exception {
		BaseZookeeper baseZookeeper = new BaseZookeeper();  
        String host = "10.3.6.93:2181";  
        // zookeeper  dd
        baseZookeeper.connectZookeeper(host);  
        List<String> topoLogys = baseZookeeper.getChildren("/infosec-storm-mine/workerbeats");
        for(String topology : topoLogys){
        	List<String> childrens = baseZookeeper.getChildren("/infosec-storm-mine/workerbeats/" + topology);
        	System.out.println("==========TopoLogyId=" + topology + "================");
	        for(String children : childrens){
		        byte[] data = baseZookeeper.getData("/infosec-storm-mine/workerbeats/" +topology +"/"+ children);
		        ObjectInputStream in=new ObjectInputStream(new ByteArrayInputStream(data));
		        PersistentArrayMap s = (PersistentArrayMap) in.readObject();
		        System.out.println(s);
	        }
        }
	}
}
