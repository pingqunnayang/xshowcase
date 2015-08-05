package com.xiao.showcase.helloworld;


import com.xiao.showcase.helloworld.bolts.WordCounter;
import com.xiao.showcase.helloworld.bolts.WordNormalizer;
import com.xiao.showcase.helloworld.spout.WordReader;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;


public class TopologyMain {
	
	/**
	 * arg[] 1=topologyname 2=normalizerThreadNum 3=counterNum 4=workersNum
	 * @param args
	 * @throws AlreadyAliveException
	 * @throws InvalidTopologyException
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException{
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader",new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer(),1)
			.shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounter(),1)
			.fieldsGrouping("word-reader", new Fields("word"));
		Config conf = new Config();
		conf.setDebug(false);
		conf.setNumWorkers(1);
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("TopoLogy-" + 1, conf, builder.createTopology());
	    Thread.sleep(40000);
	    cluster.shutdown();
		
	}
}
