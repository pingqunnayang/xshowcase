package com.xiao.showcase.curator;
import org.apache.curator.framework.CuratorFramework;

import com.xiao.showcase.curator.base.ClientUtils;

/**
 * @author zpxiao
 * @date 2014-12-5 下午4:51:04
 */
public class CuratorSimple {
	public static void main(String[] args) throws Exception {
		CuratorFramework  client = ClientUtils.getClient();
		byte[] s = client.getData().forPath("/leader");
		System.out.println(new String(s));
	}
}
