package com.xiao.showcase.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
	
//    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
    
	public static void main(String[] args) {
		
		final AtomicInteger i = new AtomicInteger(0);
		
		ExecutorService service = Executors.newFixedThreadPool(5);
		for(int x=0;x<10;x++){
			service.execute(new Runnable(){
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() +" "+ i.getAndAdd(1));
				}
				
			});
		}
		System.out.println(RUNNING & CAPACITY);
		System.out.println(RUNNING & ~CAPACITY);
	     System.out.println( RUNNING | 0);
		
		System.out.println(RUNNING + "-" +TIDYING);
	}
}
