package com.xiao.showcase.lock.sort.reentrance;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁，也叫做递归锁，指的是同一线程 外层函数获得锁之后 ，内层递归函数仍然有获取该锁的代码，但不受影响。
 * 在JAVA环境下 ReentrantLock 和synchronized 都是 可重入锁
 * @author zpxiao
 *
 */
public class ReentrantLockTest implements Runnable{
	
	ReentrantLock lock = new ReentrantLock();
	
	private void get(){
		lock.lock();
		System.out.println(Thread.currentThread().getId());
		set();
		lock.unlock();
	}
	
	private void set(){
		lock.lock();
		System.out.println(Thread.currentThread().getId());
		lock.unlock();
	}
	
	@Override
	public void run() {
		get();
	}
	
	public static void main(String[] args) {
		ReentrantLockTest rlt = new ReentrantLockTest();
		new Thread(rlt).start();
		new Thread(rlt).start();
		new Thread(rlt).start();
	}

}
