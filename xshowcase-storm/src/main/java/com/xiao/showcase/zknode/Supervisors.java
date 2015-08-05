package com.xiao.showcase.zknode;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.xiao.showcase.base.BaseZookeeper;
import backtype.storm.daemon.common.SupervisorInfo;

/**
 * supervisor记录心跳信息
 */
public class Supervisors {
	public static void main(String[] args) throws Exception {
		BaseZookeeper baseZookeeper = new BaseZookeeper();  
        String host = "10.3.6.93:2181";  
        // zookeeper  
        baseZookeeper.connectZookeeper(host);  
        List<String> childrens = baseZookeeper.getChildren("/infosec-storm-mine/supervisors");
        for(String children : childrens){
	        byte[] data = baseZookeeper.getData("/infosec-storm-mine/supervisors/" + children);
	        ObjectInputStream in=new ObjectInputStream(new ByteArrayInputStream(data));
	        SupervisorInfo s = (SupervisorInfo) in.readObject();
	        System.out.println("==========assignment=" + s.assignment_id + "================");
	        System.out.println("hostname=" + s.hostname);
	        System.out.println("port=" + s.used_ports);
	        System.out.println(s.uptime_secs + "updatetime=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date((Long)s.uptime_secs)));
	        System.out.println("meta=" + s.scheduler_meta);
	        for(Object key : s.keySet()){
	        	System.out.println(key.toString() + ":" + s.get(key));
	        }
//	        System.out.println("values=" + s.values());
	        
        }
	}
}
