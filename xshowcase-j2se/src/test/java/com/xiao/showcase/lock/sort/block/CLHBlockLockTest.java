package com.xiao.showcase.lock.sort.block;

import org.junit.Test;

public class CLHBlockLockTest {

	@Test
	public void test() {
		try {
			CLHBlockLock lock = new CLHBlockLock();
			CBCounter counter = new CBCounter(lock);
			for(int i = 0; i <3; i++){
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

}

class CBCounter{
	
	private CLHBlockLock lock = null;
	
	CBCounter(CLHBlockLock lock){
		this.lock = lock;
	}
	
	public void output(){
		lock.lock();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("***run" + Thread.currentThread().getName());
		lock.unlock();
	}
}