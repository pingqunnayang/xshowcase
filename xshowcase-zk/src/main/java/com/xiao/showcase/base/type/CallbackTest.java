package com.xiao.showcase.base.type;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author zpxiao
 * @date 2014-11-21 上午11:21:16
 */
public class CallbackTest implements Watcher,StatCallback{
	
	private CountDownLatch connectedSignal = new CountDownLatch(1);
	private ZooKeeper zk = null;
	
	public CallbackTest(){
		try {
			zk = new ZooKeeper("10.3.6.93:2181", 2000, this);
//			connectedSignal.await();
			System.out.println("zookeeper connect ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws KeeperException, InterruptedException {
		String path = "/cbtest";
		CallbackTest cb = new CallbackTest();
//		cb.zk.exists("/test", cb,cb,"123");
		if(cb.zk.exists(path, false)!=null){
			cb.zk.delete(path, -1);
		}
		System.out.println("create----");
		cb.zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	
	@Override
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		System.out.println("rc="+rc+"path="+path+"ctx="+ctx+"stat="+stat);
	}
	
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
		      System.out.println("watcher received event");  
	        connectedSignal.countDown();// 倒数-1
	    }
	}

}
