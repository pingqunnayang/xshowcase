package com.xiao.showcase.lock.sort.spin;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ticket锁主要解决的是访问顺序的问题，主要的问题是在多核cpu上
 * MCSlock和CLHLock则是采用链表形式实现
 * @author zpxiao
 *
 */
public class TicketLock {
	private AtomicInteger serviceNum = new AtomicInteger();
	private AtomicInteger ticketNum = new AtomicInteger();
	private ThreadLocal<Integer> local = new ThreadLocal<Integer>();
	
	public void lock(){
		int myTicket = ticketNum.getAndIncrement();
		System.out.println(Thread.currentThread().getName());		
		local.set(myTicket);
		while(myTicket != serviceNum.get()){
		}
	}
	
	public void unLock(){
		int myTicket = local.get();
		serviceNum.compareAndSet(myTicket, myTicket+1);
	}
	
	/**
	 * 每次都要查询一个serviceNum 服务号，影响性能（必须要到主内存读取，并阻止其他cpu修改）。
	 */
}
