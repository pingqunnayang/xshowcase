package com.xiao.showcase.lock.sort.spin;


import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 采用链表的形式进行排序类似于TicketLock和MCSlock
 * @author zpxiao
 *
 */
@SuppressWarnings("unused")
public class CLHLock {
	public static class CLHNode{
		private volatile boolean isLocked = true;
	}
	
	private volatile CLHNode tail;
	private static final ThreadLocal<CLHNode> local = new ThreadLocal<CLHNode>();
	private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> update = 
			AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");
	
	public void lock(){
		CLHNode node = new CLHNode();
		local.set(node); 
		System.out.println(Thread.currentThread().getName());
		CLHNode preNode = update.getAndSet(this, node);
		if(preNode!=null){
			while(preNode.isLocked){
			}
			preNode=null;
			local.set(node);
		}
	}
	
	public void unlock(){
		CLHNode node = local.get();
		if(!update.compareAndSet(this, node, null)){
			node.isLocked=false;
		}		
		node = null;
	}
	
	/**
	 * CLHlock是不停的查询前驱变量， 导致不适合在NUMA架构下使用（在这种结构下，每个线程分布在不同的物理内存区域 ）
	 * 非一致存储访问结构(NUMA：Non-Uniform Memory Access)
	 */
}
