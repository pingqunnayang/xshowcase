package com.xiao.showcase.base.type;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.xiao.showcase.base.ZkUtils;

/**
 * @author zpxiao
 * @date 2014-11-20 下午3:11:27
 */
public class Persistent {
	
	private static ZooKeeper zk = ZkUtils.getConn().getZooKeeper();
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		creatPersitentZnode("/test","123");
//		updatePersistentZnode(PATH,"4562",2);
//		System.out.println("res = " + new String(ZkUtils.getConn().getZooKeeper().getData(PATH, false, null)));
	}
	
	public static void creatPersitentZnode(String path,String value) throws KeeperException, InterruptedException{
		Stat stat = zk.exists(path, false);
		if(stat!=null){
			System.out.println("Znode is exists path=" + path);
			return;
		}
		String res= ZkUtils.getConn().getZooKeeper().create(path, value.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("CREAT Znode  [Path=" + res + " VALUE=" + value +"]");
	}
	
	public static void deletePersistentZnode(String path, int version) throws InterruptedException, KeeperException{
		zk.delete(path, version);
		System.out.println("DELETE Znode [Path=" + path + " VERSION="+ version+"]");
	}
	
	public static void updatePersistentZnode(String path,String value,int version) throws KeeperException, InterruptedException{
		Stat stat = zk.setData(path, value.getBytes(), version);
		System.out.println("UPDATE Znode [Path=" + path + " VERSION="+ version +"] [NEW VALUE= "+ value +" VERSION=" + stat.getVersion() +"]");
	}

}
