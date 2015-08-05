package com.xiao.showcase.lock.sort.spin;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * CLH 的队列是隐式的队列，没有真实的后继结点属性。
 * MCS 的队列是显式的队列，有真实的后继结点属性。
 * JUC ReentrantLock 默认内部使用的锁 即是 CLH锁（有很多改进的地方，将自旋锁换成了阻塞锁等等）
 * @author zpxiao
 *
 */
public class MCSLock {
	
	public static class MCSNode{
		volatile MCSNode next;
		volatile boolean isLocked=true;
	}
	
	private static final ThreadLocal<MCSNode> local = new ThreadLocal<MCSNode>();
	private volatile MCSNode queue;
	private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> update = 
			AtomicReferenceFieldUpdater.newUpdater(MCSLock.class, MCSNode.class, "queue");
	
	public void lock(){
		MCSNode currNode = new MCSNode();
		local.set(currNode);
		System.out.println(Thread.currentThread().getName());
		MCSNode preNode = update.getAndSet(this, currNode);
		if(preNode !=null){
			preNode.next = currNode;
			while(currNode.isLocked){
			}
		}
	}
	
	public void unlock(){
		MCSNode currNode = local.get();
		if (currNode.next == null) {
			 if (update.compareAndSet(this, currNode, null)) {
				 
             } else {
                 while (currNode.next == null) {
                 }
             }
		}else{
			currNode.next.isLocked = false;
			currNode.next = null;
		}	 
	}
	
}
