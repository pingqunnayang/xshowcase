package com.xiao.showcase.lock.sort.spin;


import org.junit.Test;

public class SpinLockTest {

	@Test
	public void test() {
		SpinLock lock = new SpinLock();
		final Counter counter = new Counter(lock);
		System.out.println("****SpinLockTest****");
		for(int i = 0; i <2; i++){
			new Thread("thread" + i){
				@Override
				public void run(){
					counter.output();
				}
			}.start();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("****UnLockTest****");
		for(int i = 0; i <2; i++){
			new Thread("thread" + i){
				@Override
				public void run(){
					counter.unLockoutput();
				}
			}.start();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Counter{
	
	private SpinLock lock = null;
	
	private  String  name;
	
	 Counter(SpinLock lock){
		this.lock = lock;
	}
	
	public void output(){
		lock.lock();
		name = Thread.currentThread().getName();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "--" + name);
		lock.unlock();
	}
	
	
	public void unLockoutput(){
		name = Thread.currentThread().getName();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "--" + name);
	}
}
