package com.xiao.showcase.transation.bolts;

import java.util.Map;

import backtype.storm.coordination.BatchOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseTransactionalBolt;
import backtype.storm.transactional.ICommitter;
import backtype.storm.transactional.TransactionAttempt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * @author zpxiao
 */
public class MineCommiterBolt extends BaseTransactionalBolt implements ICommitter{
	
	private static final long serialVersionUID = 1L;
	
	TransactionAttempt _attempt;
     BatchOutputCollector _collector;
     int _sum = 0;
     
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
		this._collector = collector;
		this._attempt = id;
	}

	@Override
	public void execute(Tuple tuple) {
		_sum+=tuple.getInteger(1);
	}

	@Override
	public void finishBatch() {
		System.out.println("commit == " + _attempt.getTransactionId() + "====sum=" + _sum);
		if(_sum==10){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		_collector.emit(new Values(_attempt));
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("id"));		
	}

}
