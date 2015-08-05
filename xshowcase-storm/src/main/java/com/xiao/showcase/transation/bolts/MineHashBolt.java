package com.xiao.showcase.transation.bolts;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.transactional.TransactionAttempt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * @author zpxiao
 */
public class MineHashBolt implements IBasicBolt{
	private static final long serialVersionUID = 1L;

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declareStream("hash",new Fields("txid","ctripid","count"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		Integer clientid = input.getInteger(1);
		TransactionAttempt tx = (TransactionAttempt)input.getValueByField("tx");
		System.out.println("hashBolt=" + clientid);
		collector.emit("hash", new Values(tx,"ctripid" + clientid,--clientid));
	}

	@Override
	public void cleanup() {
	}

}
