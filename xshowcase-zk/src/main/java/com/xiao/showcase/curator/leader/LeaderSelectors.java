package com.xiao.showcase.curator.leader;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

/**
 * @author zpxiao
 * @date 2014-12-9 下午3:42:26
 */
public class LeaderSelectors implements Runnable{
	
	private CuratorFramework  client = null;
	
	private String path = null;
	
	private String name = null;
	
	private LeaderLatch latch = null;
	
	public LeaderSelectors(String name,CuratorFramework  client,String path) throws Exception{
			this.name = name;
			this.client = client;
			this.path = path;
	}

	@Override
	public void run() {
		latch = new LeaderLatch(client,path);  
		//让listener在单独的线程池中运行  
		Executor executor = Executors.newCachedThreadPool();  
		//每个listener都用来执行角色变换的事件处理.  
		LeaderLatchListener latchListener = new LeaderLatchListener() {  
		    @Override  
		    public void isLeader() {  
		        System.out.println("I am leader...my name is " + name);  
		    }  
		  
		    @Override  
		    public void notLeader() {  
		        System.out.println("I am not leader...my name is " + name);  
		    }  
		};  
		latch.addListener(latchListener,executor);  
		try {
			latch.start();
			latch.await();//等待leader角色.  
		} catch (Exception e) {
			e.printStackTrace();
		}  
//		//在await退出之后,你需要通过其他手段继续关注leader状态变更.  
//		Thread.sleep(5000);  
//		System.out.println(latch.hasLeadership());  
//		latch.close();  
//		Thread.sleep(5000);  
//		System.out.println(latch.hasLeadership());  
//		client.close();
	}

	public LeaderLatch getLatch() {
		return latch;
	}

	public CuratorFramework getClient() {
		return client;
	}

	public String getName() {
		return name;
	}
	
}
