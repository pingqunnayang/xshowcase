/**
 * Ctrip.com Inc. Copyright (c) 2000-2018 All Rights Reserved.
 */
package com.xiao.showcase.esper.quickstart;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * @author zpxiao
 * @date 2015-1-23 上午9:39:18
 */
public class MyListener implements UpdateListener{
	
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean event = newEvents[0];
        System.out.println("avg=" + event.get("avg(price)"));
    }
}
