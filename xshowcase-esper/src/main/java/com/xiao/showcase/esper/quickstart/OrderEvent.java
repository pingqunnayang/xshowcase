/**
 * Ctrip.com Inc. Copyright (c) 2000-2018 All Rights Reserved.
 */
package com.xiao.showcase.esper.quickstart;
/**
 * @author zpxiao
 * @date 2015-1-23 上午9:38:58
 */
public class OrderEvent {
	private String itemName;
    private double price;

    public OrderEvent(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }
}
