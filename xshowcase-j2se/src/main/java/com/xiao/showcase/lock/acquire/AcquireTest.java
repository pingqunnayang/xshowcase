package com.xiao.showcase.lock.acquire;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * ReentrantLock可以实现公平性。 如果在绝对时间上，先对锁进行获取的请求一定被先满足，那么这个锁是公平的，反之，是不公平的。
 * 
 * @author zpxiao
 *
 */

public class AcquireTest {

	@Test
	public void fair() {
		System.out.println("fair version");
		final Lock fairLock = new ReentrantLock(true); // 公平锁
		final Counter counter = new Counter();
		// 公平锁机制
		for (int i = 0; i < 10; i++) {
			new Thread(i + "") {
				public void run() {
					counter.output(this.getName(), fairLock);
				}
			}.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void unfair() {
		System.out.println("unfair version");
		final Lock unfairLock = new ReentrantLock(); // 非公平锁
		final Counter counter = new Counter();
		// 公平锁机制
		for (int i = 0; i < 10; i++) {
			new Thread(i + "") {
				public void run() {
					counter.output(this.getName(), unfairLock);
				}
			}.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Counter {
	
	private int i = 0;
	
	public void output(String threadName, Lock lock) {
		// TODO 线程输出方法
		lock.lock();// 得到锁
		try {
			i++;
			if(threadName.equals((i-1) +"")){
				System.out.println("name=" + threadName + "--" + i);
			}else{
				System.out.println("name=" + threadName + "--" + i + "--unfair");
			}
		} finally {
			lock.unlock();// 释放锁
		}
	}

}
