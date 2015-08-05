package com.xiao.showcase.watcher.leaderelection;
import org.apache.zookeeper.KeeperException;

import com.xiao.showcase.base.type.Persistent;
/**
 * @author zpxiao
 * @date 2014-12-4 下午5:35:41
 */
public class LeadersTest {
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		Persistent.creatPersitentZnode("/leader", "1");
		new Leaders("leader1");
		new Leaders("leader2");
		new Leaders("leader3");
		new Leaders("leader4");
		new Leaders("leader5");
		Thread.sleep(10000);
		Thread.sleep(1000000);
	}
}
