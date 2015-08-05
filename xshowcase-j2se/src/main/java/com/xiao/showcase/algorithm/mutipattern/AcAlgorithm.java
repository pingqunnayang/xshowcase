/**
 * Ctrip.com Inc. Copyright (c) 2000-2018 All Rights Reserved.
 */
package com.xiao.showcase.algorithm.mutipattern;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Collection;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

/**
 * @author zpxiao
 * @date 2014-12-22 下午2:32:32
 */
public class AcAlgorithm {
	static Trie trie = new Trie().removeOverlaps();
	static {
		try{
			InputStream in = AcAlgorithm.class.getClassLoader().getResourceAsStream("word.txt");
			LineNumberReader lineReader = new LineNumberReader(new BufferedReader(new InputStreamReader(in)));
			String line = null;
			while((line = lineReader.readLine())!=null){
				trie.addKeyword(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Long startTime = System.currentTimeMillis();
	    Collection<Emit> emits = trie.parseText("尊敬的宾客：江泽民罪行间1江泽民罪行间谍江泽民罪行间谍感谢您将东方宾馆作为马仔李东生此次出行的下榻之所，并与我们一同分享您的入住体验。我们将继续努力，" +
	    											"为您及更多客人提供更丁子霖母亲天安门优质更个性化的服务。再次感谢您对我们的支持，诚挚期待您和家人、朋友的再次到来。");
	    Long endTime = System.currentTimeMillis();
	    System.out.println("cost:" + (endTime-startTime) + "ms");
	    for(Emit e : emits){
	    	System.out.println(e.getKeyword());
	    }
	    
	} 
}
