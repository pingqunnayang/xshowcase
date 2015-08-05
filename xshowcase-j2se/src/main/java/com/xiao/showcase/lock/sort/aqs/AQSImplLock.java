package com.xiao.showcase.lock.sort.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AQSImplLock {
	
	private static class Sync extends AbstractQueuedSynchronizer{

		private static final long serialVersionUID = 1L;
		
		@Override
		public boolean tryAcquire(int arg) {
			if(compareAndSetState(0, 1)){
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}
		
		@Override
		protected boolean tryRelease(int releases) {
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}
	}
	
	private final Sync sync = new Sync();
	 
	public void lock(){
		sync.acquire(1);
	}
	
	public void unlock(){
		sync.release(1); 
	}

}
