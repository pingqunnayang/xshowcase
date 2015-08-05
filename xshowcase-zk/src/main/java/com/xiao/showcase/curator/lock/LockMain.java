package com.xiao.showcase.curator.lock;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zpxiao   
 * @date 2014-12-8 下午3:05:54
 */
public class LockMain {
	
	public static void main(String[] args) {
		mutexLockTest();
		mutexSemaphoreLockTest();
	}
	
	private static void mutexSemaphoreLockTest(){
		List<SemaphoreLock> list=  new ArrayList<SemaphoreLock>();
		for(int i=0;i<20;i++){
			SemaphoreLock lock = new SemaphoreLock("Thread" + i);
			list.add(lock);
		}
		for(int i=0;i<20;i++){
			new Thread(list.get(i)).start();
		}
	}
	
	private static void mutexLockTest(){
		List<MutexLock> list=  new ArrayList<MutexLock>();
		for(int i=0;i<20;i++){
			MutexLock lock = new MutexLock("Thread" + i);
			list.add(lock);
		}
		for(int i=0;i<20;i++){
			new Thread(list.get(i)).start();
		}
	}
}
