package com.xiao.showcase.lock.sort.spin;


import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 * 自旋锁是采用让当前线程不停地的在循环体内执行实现的，当循环的条件被其他线程改变时 才能进入临界区。
 * 连续两次调用会产生死锁：
 * SpinLock.lock();SpinLock.lock();
 * @author zpxiao
 *
 */
public class SpinLock {
	
	private AtomicReference<Thread> owner = new AtomicReference<>();
	
	/**
	 * 使用了CAS原子操作，lock函数将owner设置为当前线程，并且预测原来的值为空.
	 */
	public void lock(){
		Thread current = Thread.currentThread();
		while(!owner.compareAndSet(null, current)){
			
		}
	}
	
	/**
	 * unlock函数将owner设置为null，并且预测值为当前线程
	 */
	public void unlock(){
		Thread current = Thread.currentThread();
		owner.compareAndSet(current, null);
	}
	
	/**由于自旋锁只是将当前线程不停地执行循环体，不进行线程状态的改变，
	 * 所以响应速度更快。但当线程数不停增加时，性能下降明显，
	 * 因为每个线程都需要执行，占用CPU时间。如果线程竞争不激烈，
	 * 并且保持锁的时间段。适合使用自旋锁。*/

}
