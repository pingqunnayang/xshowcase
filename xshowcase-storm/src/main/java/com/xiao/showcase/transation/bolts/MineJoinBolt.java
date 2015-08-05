package com.xiao.showcase.transation.bolts;

import java.util.Map;
import backtype.storm.coordination.BatchOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBatchBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**BaseBatchBolt。
 * execute方法会操作接收到的元组，
 * 但是不会分发新的元组。批次完成时，Storm会调用finishBatch方法
 * @author zpxiao  
 * @date 2014-10-22 下午3:45:25
 */
@SuppressWarnings("rawtypes")
public class MineJoinBolt extends BaseBatchBolt{
	
	private static final long serialVersionUID = 1L;
	
	private BatchOutputCollector collector;
	/**
	 * Every tuple is a new object
	 */
	private int count = 0;
	Object _id;
	
	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, Object id) {
		this.collector = collector;
		this._id = id;
	}

	@Override
	public void execute(Tuple tuple) {
		String source = tuple.getSourceStreamId();
		int tempCount = tuple.getIntegerByField("count");
		//Check diff streamid
		if("split".equals(source)){
			count +=tempCount;
		}else if("hash".equals(source)){
			count +=tempCount;
		}
		
	}

	@Override
	public void finishBatch() {
		System.out.println("***join===count = " + count);
		collector.emit(new Values(_id,count));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("id","join"));
	}

}
