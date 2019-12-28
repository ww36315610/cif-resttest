package com.cif.utils.es;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class EsUtils243 implements Serializable {
	private static final long serialVersionUID = -282899222749487842L;

	private String keyName = "esPrimaryKey";
	// 从什么位置取
	private static int from = 0;
	// 取多少条目
	private static int size = 10000;
	// 单例
	private static EsUtils243 instance;
	// ES客户端
	private static TransportClient client = null;

	private EsUtils243() {
	}

	/**
	 * 初始化连接 多联
	 * <p>
	 * 可以通过两种方式来连接到elasticsearch（简称es）集群，
	 * 第一种是通过在你的程序中创建一个嵌入es节点（Node），使之成为es集群的一部分，然后通过这个节点来与es集群通信。
	 * 第二种方式是用TransportClient这个接口和es集群通信。
	 * 
	 * @param esClusterName
	 *            集群名称
	 * @param ipAndPorts
	 *            ip:端口,ip:端口,ip:端口
	 */
	public static synchronized EsUtils243 getInstance(String esClusterName, String esIp, int esPort) {
		if (instance == null) {
			Settings settings = Settings.settingsBuilder().put("cluster.name", esClusterName).build();
			client = TransportClient.builder().settings(settings).build();
			try {
				client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esIp), esPort));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			instance = new EsUtils243();
		}
		return instance;
	}

	public String replaceValue(String value) {
		return QueryParser.escape(value);
	}

	public List<String> insertRow(String indexName, String typeName, List<Map<String, Object>> list) {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (int j = 0; j < list.size(); j++) {
			// ES 唯一ID 不指定的话则ES自动生成
			String esPrimaryKey = null;
			if (list.get(j).get(keyName) != null) {
				esPrimaryKey = list.get(j).get(keyName).toString();
				list.get(j).remove(keyName);
			}
			bulkRequest.add(client.prepareIndex(indexName, typeName, esPrimaryKey).setSource(list.get(j)));
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		List<String> idList = new ArrayList();
		BulkItemResponse[] br = bulkResponse.getItems();
		for (int i = 0; i < br.length; i++) {
			idList.add(br[i].getId());
		}
		return idList;
	}

	/**
	 * 插入一条数据
	 *
	 * @param indexName
	 *            索引名称
	 * @param typeName
	 *            类型名称
	 * @param json
	 *            需要插入的数据
	 * @return 插入数据后生成的主键ID
	 */
	public String insertRow(String indexName, String typeName, Map<String, Object> json) {
		// ES 唯一ID 不指定的话则ES自动生成
		String esPrimaryKey = null;
		if (json.get(keyName) != null) {
			esPrimaryKey = json.get(keyName).toString();
			json.remove(keyName);
		}
		IndexResponse indexResponse = client.prepareIndex(indexName, typeName, esPrimaryKey).setSource(json).get();
		return indexResponse.getId();
	}

	/**
	 * 查询数据 （根据主键ID）
	 *
	 * @param indexName
	 *            索引名称
	 * @param typeName
	 *            类型名称
	 * @param id
	 *            主键id
	 * @return 查询结果
	 */
	public Map<String, Object> idQuery(String indexName, String typeName, String id) {
		GetResponse getResponse = client.prepareGet(indexName, typeName, id).get();
		return getResponse.getSource();
	}

	public void deleteById(String index, String type, String id) {
		try {
			Settings settings = Settings.settingsBuilder().put("cluster.name", "es_cluster")
					.put("client.transport.sniff", true).build();
			/*
			 * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象 用完记得要关闭
			 */
			// Client client
			// =TransportClient.builder().settings(settings).build().addTransportAddress(newInetSocketTransportAddress(InetAddress.getByName("192.168.223.11"),9300));
			// 在这里创建我们要删除的对象
			DeleteResponse response = client.prepareDelete(index, type, id).get();
			System.out.println(response.isFound());
			client.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void deleteById(String index, String type, List<String> list) {
		try {
			DeleteResponse response = null;
			Iterator<String> iter = list.iterator();
			while (iter.hasNext()) {
				response = client.prepareDelete(index, type, iter.next()).get();
			}
			System.out.println(response.isFound());
			client.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void main(String[] args) {
		EsUtils243 esUtils = getInstance("cif-new-elasticsearch-2-4-0", "10.10.56.138", 9300);
		// esUtils.deleteById("tag", "flink_tag",
		// "4466b4e58ff68a571087f42f0704c412");
		// esUtils.deleteById("tag_supply", "flink_supply",
		// "732042100671212f694076c1bd313578");
		// esUtils.deleteById("tag_supply", "flink_supply",
		// "4466b4e58ff68a571087f42f0704c412");
		// esUtils.deleteById("tag_supply", "flink_supply",
		// "b5c7392463aa52a4afc2a5844b56cf0a");
		List<String> list = new ArrayList<String>();
		list.add("4466b4e58ff68a571087f42f0704c412");
		list.add("732042100671212f694076c1bd313578");
		list.add("b5c7392463aa52a4afc2a5844b56cf0a");
		list.add("1204caa49119594d883dbc93a8e59e50");
		esUtils.deleteById("tag_supply", "flink_supply", list);

		// }
	}
}
