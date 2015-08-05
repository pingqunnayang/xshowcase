package com.xiao.showcase.transation.spouts;

import java.math.BigInteger;

import backtype.storm.coordination.BatchOutputCollector;
import backtype.storm.transactional.ITransactionalSpout.Emitter;
import backtype.storm.transactional.TransactionAttempt;
import backtype.storm.tuple.Values;

/**
 * 分发器从数据源读取数据并从数据流组发送数据。分发器应当问题能够为相同的事务id和事务元数据发送相同的批次。
 * 这样，如果在处理批次的过程中发生了故障，Storm就能够利用分发器重复相同的事务id和事务元数据，
 * 并确保批次已经重复过了。Storm会在TransactionAttempt对象里为尝试次数增加计数（译者注：attempt id）。
 * 这样就能知道批次已经重复过了。
 * @author zpxiao
 * @date 2014-10-22 下午2:55:15
 */
public class MineTransactionEmitter implements Emitter<Integer> {

	@Override
	public void emitBatch(TransactionAttempt tx, Integer coordinatorMeta, BatchOutputCollector collector) {
		System.out.println(Thread.currentThread().getName() + "===========emit txid=" + tx.getTransactionId()+"================" + coordinatorMeta);
		collector.emit(new Values(tx, coordinatorMeta));
	}

	@Override
	public void cleanupBefore(BigInteger txid) {

	}

	@Override
	public void close() {

	}

}
