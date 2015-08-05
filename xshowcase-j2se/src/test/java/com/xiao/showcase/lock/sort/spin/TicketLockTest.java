package com.xiao.showcase.lock.sort.spin;


import org.junit.Test;

public class TicketLockTest {

	@Test
	public void test() {
		try {
			TicketLock lock = new TicketLock();
			System.out.println("----ticket lock-----");
			for(int i=0;i<10;i++){
				TCounter t = new TCounter(i,lock);
				new Thread("Thread-" + i){
					@Override
					public void run(){
						t.output();
					}
				}.start();
				Thread.sleep(5);
			}
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class TCounter{
	
	private TicketLock lock;
	
	private int i;
	
	TCounter(int i,TicketLock lock){
		this.lock = lock;
		this.i = i;
	}
	
	public void output(){
		lock.lock();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("i=" + i);
		lock.unLock();
	}
}