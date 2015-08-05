package com.xiao.showcase.lock.sort.reentrance;

import org.junit.Test;

public class SpinUpdateLockTest {

	@Test
	public void test() {
		SpinReenTranceLock lock = new SpinReenTranceLock();
		Counter count = new Counter(lock);
		new Thread(){
			@Override
			public void run(){
				count.output();
			}
		}.start();
	}

}

class Counter{
	
	private SpinReenTranceLock lock = null;
	
	private  String  name;
	
	 Counter(SpinReenTranceLock lock){
		this.lock = lock;
	}
	
	public void output(){
		lock.lock();
		name = Thread.currentThread().getName();
		System.out.println("ThreadName==" + name);
		reOutput();
		lock.unlock();
	}
	
	public void reOutput(){
		lock.lock();
		name = Thread.currentThread().getName();
		System.out.println("RThreadName==" + name);
		lock.unlock();
	}
}