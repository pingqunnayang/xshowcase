package com.xiao.showcase.transation;


import java.io.IOException;

import com.xiao.showcase.transation.bolts.MineCommiterBolt;
import com.xiao.showcase.transation.bolts.MineHashBolt;
import com.xiao.showcase.transation.bolts.MineJoinBolt;
import com.xiao.showcase.transation.bolts.MineSplitBolt;
import com.xiao.showcase.transation.spouts.MineTransactionSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.transactional.TransactionalTopologyBuilder;
import backtype.storm.tuple.Fields;

/**
 * @author zpxiao
 */
public class SpoutMain {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		TransactionalTopologyBuilder builder = new TransactionalTopologyBuilder("test", "tranSpout", new MineTransactionSpout(),2);
		builder.setBolt("minesplit", new MineSplitBolt(),4).shuffleGrouping("tranSpout");
		builder.setBolt("minehash", new MineHashBolt(),4).shuffleGrouping("tranSpout");
		builder.setBolt("minejoin", new MineJoinBolt(),4)
			.fieldsGrouping("minesplit","split", new Fields("ctripid"))
			.fieldsGrouping("minehash","hash", new Fields("ctripid"));
		builder.setBolt("minecommit", new MineCommiterBolt())
			.allGrouping("minejoin");
		Config conf = new Config();
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.buildTopology());
	}
}
