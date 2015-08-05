package com.xiao.showcase.helloworld.bolts;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.FailedException;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordNormalizer extends BaseBasicBolt {

	private static final long serialVersionUID = 1L;


	public void cleanup() {}

	/**
	 * The bolt will receive the line from the
	 * words file and process it to Normalize this line
	 * 
	 * The normalize will be put the words in lower case
	 * and split the line to get all words in this 
	 */
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
        String sentence = input.getString(0);
        String[] words = sentence.split(" ");
        for(String word : words){
            word = word.trim();
            if(word.equals("5")){
            	throw new FailedException("**FAIL**");
  		  	}else{
	            if(!word.isEmpty()){
	                word = word.toLowerCase();
	                System.out.println("WordNormalizer[" + Thread.currentThread().getName() + "]tuple=" + word);
	                collector.emit(new Values(word));
	            }
  		  	}
        }
	}
	

	/**
	 * The bolt will only emit the field "word" 
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}
}
