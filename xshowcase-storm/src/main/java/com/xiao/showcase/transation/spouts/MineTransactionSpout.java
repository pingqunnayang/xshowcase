package com.xiao.showcase.transation.spouts;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseTransactionalSpout;
import backtype.storm.tuple.Fields;

/**
 * @author zpxiao
 */
@SuppressWarnings("rawtypes")
public class MineTransactionSpout extends BaseTransactionalSpout<Integer>{

	private static final long serialVersionUID = 1L;

	@Override
	public backtype.storm.transactional.ITransactionalSpout.Coordinator<Integer> getCoordinator(Map conf,
			TopologyContext context) {
		return new MineCoorDinator();
	}

	@Override
	public backtype.storm.transactional.ITransactionalSpout.Emitter<Integer> getEmitter(Map conf, TopologyContext context) {
		return new MineTransactionEmitter();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tx","clentid"));		
	}

}
