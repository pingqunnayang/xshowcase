package com.xiao.showcase.watcher.barrier;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 双屏障（Double barriers）使得所有客户端在进入和结束某一计算任务时都会得到同步。
 * 当足够的进程processes（注：此处指节点）加入到屏障时，才启动任务，然后当任务完成时，离开屏障区，
 * @author zpxiao
 * @date 2014-11-20 下午7:24:37
 */
public class Barrier implements Watcher {  
	  
    private static final String addr = "10.3.6.93:2181";  
    private ZooKeeper           zk   = null;  
    private Integer             mutex;  
    private int                 size = 0;  
    private String              root;  
  
    public Barrier(String root, int size){  
        this.root = root;  
        this.size = size;  
        try {  
            zk = new ZooKeeper(addr, 10 * 1000, this);  
            mutex = new Integer(-1);  
            Stat s = zk.exists(root, false);  
            if (s == null) {  
                zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  
            }  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
  
    public  void process(WatchedEvent event) {  
        synchronized (mutex) {
            mutex.notify();  
        }  
    }  
  
    public boolean enter(String name) throws Exception {  
        zk.create(root + "/" + name, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);  
        while (true) {  
            synchronized (mutex) {  
                List<String> list = zk.getChildren(root, true);  
                if (list.size() < size) {  
                    mutex.wait();  
                } else {  
                    return true;  
                }  
            }  
        }  
    }  
  
    public boolean leave(String name) throws KeeperException, InterruptedException {  
        zk.delete(root + "/" + name, 0);  
        while (true) {  
            synchronized (mutex) {  
                List<String> list = zk.getChildren(root, true);  
                if (list.size() > 0) {  
                    mutex.wait();  
                } else {
                    return true;  
                }  
            }  
        }  
    }  
  
}  
