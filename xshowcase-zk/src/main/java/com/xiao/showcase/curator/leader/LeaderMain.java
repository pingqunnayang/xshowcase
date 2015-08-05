package com.xiao.showcase.curator.leader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.curator.framework.CuratorFramework;

import com.xiao.showcase.curator.base.ClientUtils;

/**
 *  通过LeaderSelectorListener可以对领导权进行控制， 在适当的时候释放领导权，这样每个节点都有可能获得领导权。 
 *  而LeaderLatch一根筋到死， 除非调用close方法，否则它不会释放领导权
 * @author zpxiao
 * @date 2014-12-9 下午4:28:39
 */
public class LeaderMain {
	public static void main(String[] args) throws Exception {
		CuratorFramework  client = ClientUtils.getClient();
		List<LeaderSelectors> list = new ArrayList<LeaderSelectors>();
		for(int i=0;i<10;i++){
			LeaderSelectors lea = new LeaderSelectors("Thread"+i,client,"/curator/leader");
			list.add(lea);
		}
		for(LeaderSelectors leader : list){
			new Thread(leader).start();
		}
		while(list.size()>0){
			Thread.sleep(2000);
			LeaderSelectors leader = null;
			Iterator<LeaderSelectors> it = list.iterator();
			while(it.hasNext()){
				leader = it.next();
				if(leader.getLatch().hasLeadership()){
					System.out.println("---------I am leader....my name is " + leader.getName() + "I will by die---------");
					leader.getLatch().close();
					it.remove();
					break;
				}
			}
		}
		System.out.println("everything is die!");
	}
}
