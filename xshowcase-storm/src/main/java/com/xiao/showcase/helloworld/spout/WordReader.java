package com.xiao.showcase.helloworld.spout;

import java.util.Map;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/**
 * spout不断发送数据tmp++
 */
public class WordReader extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private String id;

	public void ack(Object msgId) {
		System.out.println(id + " ACK:" + msgId);
	}

	public void close() {
	}

	public void fail(Object msgId) {
		System.out.println("FAIL:" + msgId);
	}

	/**
	 * The only thing that the methods will do It is emit each 
	 * file line
	 */
	public void nextTuple() {
		try {
			Thread.sleep(10000);
			int tmp = 0;
			while (tmp < 10) {
				// sendData to bolt
				System.out.println("send data -------" + tmp);
				this.collector.emit(new Values(tmp++ + ""), (tmp - 1) + "");
			}

		} catch (Exception e) {
			throw new RuntimeException("Error reading tuple", e);
		} finally {
		}
	}

	/**
	 * We will create the file and get the collector object
	 */
	@SuppressWarnings("rawtypes")
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.id = context.getThisComponentId();
		this.collector = collector;
	}

	/**
	 * Declare the output field "word"
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
