/**
 * Ctrip.com Inc. Copyright (c) 2000-2018 All Rights Reserved.
 */
package com.xiao.showcase.esper.quickstart;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * @author zpxiao
 * @date 2015-1-23 上午9:49:55
 */
public class QuertStrartMain {
	
	public static void main(String[] args) {
        Configuration configuration = new Configuration();
    	Map<String,Object> typeMap = new HashMap<String,Object>();
    	typeMap.put("itemName", String.class);
    	typeMap.put("price", Double.class);
        configuration.addEventType("myEvent", typeMap); //添加事件类型定义
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
		String  expression = "select avg(price) from myEvent.win:time(30 sec)";
		EPStatement statement = epService.getEPAdministrator().createEPL(expression);	
		MyListener listener = new MyListener();
		statement.addListener(listener);
		OrderEvent event = new OrderEvent("shirt", 74.50);
		OrderEvent event1 = new OrderEvent("shirt", 174.50);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("itemName", "123");
		map.put("price", 123);
		epService.getEPRuntime().sendEvent(map,"myEvent");
	}

}
