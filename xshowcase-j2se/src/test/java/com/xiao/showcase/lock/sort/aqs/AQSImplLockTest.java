package com.xiao.showcase.lock.sort.aqs;

import org.junit.Test;

public class AQSImplLockTest {

	@Test
	public void test() {
		try {
			AQSImplLock lock = new AQSImplLock();
			AQCounter counter = new AQCounter(lock);
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
	
	class AQCounter{
		
		private AQSImplLock lock = null;
		public int count = 0;
		
		AQCounter(AQSImplLock lock){
			this.lock = lock;
		}
		
		public void output(){
			lock.lock();
			count++;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
			System.out.println(Thread.currentThread().getName() + "=" + count);
			lock.unlock();
		}
	}
}



