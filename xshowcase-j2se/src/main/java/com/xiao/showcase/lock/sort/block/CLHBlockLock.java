package com.xiao.showcase.lock.sort.block;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/**
 * 阻塞锁
 * 阻塞锁，与自旋锁不同，改变了线程的运行状态，java中线程的状态有新建、就绪、运行、阻塞、死亡。
 * 阻塞锁可以让线程进入阻塞状态进行等待，当获得相应的信号时，才可以进入线程的就绪状态，就绪的线程通过竞争进入运行状态。
 * java中能够进入/退出阻塞状态的方法有：synochronized，ReentrantLock，object.wait()\notify(),LockSupport.park/unpark()
 * @author zpxiao
 *
 */
@SuppressWarnings("unused")
public class CLHBlockLock {
	
	private static class CLHNode{
		private volatile Thread isLock; 
	}
	
	private volatile CLHNode tail;
	private static final ThreadLocal<CLHNode> local = new ThreadLocal<CLHNode>();
	private static final AtomicReferenceFieldUpdater<CLHBlockLock, CLHNode> update = 
			AtomicReferenceFieldUpdater.newUpdater(CLHBlockLock.class, CLHNode.class, "tail");
	
	
	public void lock(){
		CLHNode node = new CLHNode();
		local.set(node);
		System.out.println(Thread.currentThread().getName());
		CLHNode preNode = update.getAndSet(this, node);
		if(preNode!=null){
			preNode.isLock = Thread.currentThread();
			LockSupport.park(this);
			preNode = null;
			local.set(node);
		}
		
	}
	
	public void unlock(){
		CLHNode node = local.get();
		if(!update.compareAndSet(this,node, null)){
			System.out.println("unpack=== " +Thread.currentThread().getName());
			LockSupport.unpark(node.isLock);
		}
		node = null;
	}
	/**
	 * 在这里我们使用了LockSupport.unpark()的阻塞锁。 该例子是将CLH锁修改而成。
	 * 阻塞锁的优势在于，阻塞的线程不会占用cpu时间， 不会导致 CPu占用率过高，但进入时间以及恢复时间都要比自旋锁略慢。
	 * 在竞争激烈的情况下 阻塞锁的性能要明显高于 自旋锁。
	 * 理想的情况则是; 在线程竞争不激烈的情况下使用自旋锁，竞争激烈的情况下使用阻塞锁。
	 */
}
