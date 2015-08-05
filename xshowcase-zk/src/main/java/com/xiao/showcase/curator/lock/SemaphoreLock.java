package com.xiao.showcase.curator.lock;

import java.util.concurrent.TimeUnit;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

import com.xiao.showcase.curator.base.ClientUtils;

/**
 * semaphore（信号量）范围比较广，semaphore可能会有多个属性值。
 * 比如常见的生产者和消费者问题，就是多元信号量的一种。生产者可以生产多个元素，
 * 消费者可以消费的元素必须小于生产者的生产元素个数。从此也可以看出，
 * semaphore是允许多个线程进入，访问互斥资源。除了多元信号量之外，
 * 还存在一种二元信号量。即只存在是与否，0与1两种状态
 * @author zpxiao  
 * @date 2014-12-9 上午10:46:47
 */
public class SemaphoreLock implements Runnable{
	
	private static InterProcessSemaphoreMutex semaphore = new InterProcessSemaphoreMutex(ClientUtils.getClient(), "/SemaphoreLockPath");

	private String name = null;
	
	public SemaphoreLock(String name){
		this.name = name;
	}
	
	public void tryLock() throws Exception{
		semaphore.acquire(10000,TimeUnit.MILLISECONDS);
	    try 
	    {
	        System.out.println(name + " - get lock!");
	    }
	    finally
	    {
	    	System.out.println(name + " - release lock!");
	    	semaphore.release();
	    }
	}

	@Override
	public void run() {
		try {
			 tryLock();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
