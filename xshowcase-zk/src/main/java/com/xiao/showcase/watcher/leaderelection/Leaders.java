package com.xiao.showcase.watcher.leaderelection;

import java.util.List;
import java.util.TreeSet;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;

import com.xiao.showcase.base.ZkUtils;

/**
 * 监视 Leader 失效也是非常重要的，当前的 Leader 失效后需要一个新的客户端起来接替旧的 Leader 的位置。
 * 一个简单的方式是让所有的应用进程监视当前序号最小的 Znode 节点， 
 * 并在当前 序号最小的 Znode 节点失效是检查他们是否为新的 Leader
 * （注意当前序号最小的节点可能会随着 Leader 的消失而消失，他们可能是该Leader 节点的临时子节点）. 
 * 但是这会导致'羊群效应(herd effect)"：
 * 在当前 Leader 失效后，其他所有的进程（节点）将会收到通知，
 * 并在 "/election" 节点上执行 getChildren()来获取"/election"节点的子节点列表，
 * 如果客户端数目很大，它会使得Zookeeper服务器处理的操作次数急剧上升。为了避免羊群效应，
 * 客户端只需要监视 Znode 节点中的下一个节点就足够。如果某个客户端收到了它正在监视的节点消失的通知，
 * 它将成为新的 Leader，因为此时没有其它的 Znode 节点的序号比它小。所以这就避免了羊群效应，
 * 并且客户端也没有必要监视同一个最小的 Znode 节点。
 * @author zpxiao
 * @date 2014-12-4 下午5:24:07
 */
public class Leaders implements Watcher{
	
	private String path;
	private String name;
	
	public Leaders(String name){
		try{
			String path = ZkUtils.getConn().getZooKeeper().create("/leader/electer", name.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			this.path = path;
			this.name = name;
			String leader = findSmller();
			 if(leader == null){
	            	System.out.println("I am the master,my name is " + name + " path=" + path);
	            }else{
	            	System.out.println("My name is " + path + "I am not the smallest,I watch leader for " + leader);
	            	ZkUtils.getConn().getZooKeeper().exists("/leader/"+leader, this);
	            }
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		try{
			System.out.println("EventPath = [" + event.getPath() + "] EventType = [" + event.getType() + "]" );
			String leader = findSmller();
            if(leader == null){
            	System.out.println("My name is " + name + "I am the master,my name=" + name + " path=" + path);
            }else{
            	System.out.println("My name is " + path +"I am not the smallest,I watch leader for " + leader);
            	ZkUtils.getConn().getZooKeeper().exists("/leader/"+leader, this);
            }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private  String findSmller(){
		TreeSet<String> sortedNames = new TreeSet<String>();  
		try{
			List<String> list = ZkUtils.getConn().getZooKeeper().getChildren("/leader", false);
	        for (String name : list) {  
	            sortedNames.add(name);  
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		String subpath =  path.substring(8);
		System.out.println("---lower is----" + subpath);
        return sortedNames.lower(subpath);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
