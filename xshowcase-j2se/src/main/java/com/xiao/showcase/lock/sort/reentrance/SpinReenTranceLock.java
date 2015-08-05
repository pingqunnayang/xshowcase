package com.xiao.showcase.lock.sort.reentrance;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 在spinLock中当我们一次调用两次lock会出现死锁的现象，这不是个可重入锁，需要做些修改.
 * 该自旋锁即为可重入锁。
 * @author zpxiao
 *
 */
public class SpinReenTranceLock {
	
	AtomicReference<Thread> lock = new AtomicReference<Thread>();
	private int count;
	
	public void lock(){
		Thread currThread = Thread.currentThread();
		if(currThread == lock.get()){
			count++;
			return; 
		}
		while(!lock.compareAndSet(null, currThread)){};
	}
	
	public void unlock(){
		Thread currThread = Thread.currentThread();
		if(count!=0){
			count--;
			return;
		}
		lock.compareAndSet(currThread, null);
	}
	
	
	

}
