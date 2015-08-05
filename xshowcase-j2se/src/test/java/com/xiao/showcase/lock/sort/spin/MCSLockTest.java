package com.xiao.showcase.lock.sort.spin;


import org.junit.Test;

public class MCSLockTest {

	@Test
	public void test() {
		try {
			MCSLock lock = new MCSLock();
			MCounter counter = new MCounter(lock);
			for(int i = 0; i <20; i++){
				new Thread(i+""){
					@Override
					public void run(){
						counter.output();
					}
				}.start();
			}
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class MCounter{
	
	private MCSLock lock = null;
	
	MCounter(MCSLock lock){
		this.lock = lock;
	}
	
	public void output(){
		lock.lock();
		System.out.println("***run" + Thread.currentThread().getName());
		lock.unlock();
	}

}
