package com.xiao.showcase.lock.sort.spin;


import org.junit.Test;

public class CLHLockTest {

	@Test
	public void test() {
		try {
			CLHLock lock = new CLHLock();
			CCounter counter = new CCounter(lock);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deadLock(){
		CLHLock lock = new CLHLock();
		lock.lock();
		lock.lock();
		lock.unlock();
	}

}

class CCounter{
	
	private CLHLock lock = null;
	
	CCounter(CLHLock lock){
		this.lock = lock;
	}
	
	public void output(){
		lock.lock();
		System.out.println("***run" + Thread.currentThread().getName());
		lock.unlock();
	}

}
