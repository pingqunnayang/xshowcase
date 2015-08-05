package com.xiao.showcase.curator.lock;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import com.xiao.showcase.curator.base.ClientUtils;

/**
 * mutex（互斥量）也是一种二元的锁机制，只有是（1）和否（0）的两个值，和二元信号量比较相似。
 * 但是它和二元信号量不同的是，占有和释放必须是同一个线程。比如互斥量M被线程A占有，
 * 那么释放的时候肯定也是A线程释放的。二元信号量则不必如此，一个二元信号量的占有和释放可以是不同线程。
 * @author zpxiao   
 * @date 2014-12-5 下午5:15:44
 */
public class MutexLock implements Runnable {
	
	private String name = null;
	public static InterProcessMutex lock = new InterProcessMutex(ClientUtils.getClient(), "/lockPath");
	
	public MutexLock(String name){
		this.name = name;
	}
	
	public void tryLock() throws Exception{
		lock.acquire(10000,TimeUnit.MILLISECONDS);
	    try 
	    {
	        System.out.println(name + " - get lock!");
	    }
	    finally
	    {
	    	System.out.println(name + " - release lock!");
	        lock.release();
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
