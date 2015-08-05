package com.xiao.showcase.transation.spouts;


import java.math.BigInteger;

import backtype.storm.transactional.ITransactionalSpout.Coordinator;

/**
 * @author zpxiao
 * @date 2014-10-22 下午2:52:14
 */
public class MineCoorDinator implements Coordinator<Integer>{
	
	/**
	 * 第一个参数是Storm生成的事务ID，作为批次的惟一性标识。
	 * prevMetadata是协调器生成的前一个事务元数据对象
	 */
	@Override
	public Integer initializeTransaction(BigInteger txid, Integer prevMetadata) {
		return DataSource.clientId.get();
	}

	@Override
	public boolean isReady() {
		return DataSource.getIncreClientId()%10==0;
	}

	@Override
	public void close() {
		
	}

}
