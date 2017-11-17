package com.cif.kafka;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerTools {
	public static void main(String[] args) throws Exception {
		// String fileName = "/programfiles/kafka_2.11-0.10.2.1/bin/binlog.txt";
		String fileName = "C:\\Users\\cnbjpuhui-5051a\\Desktop\\kafka_EOE.txt";
		// String fileName = "C:\\Users\\cnbjpuhui-5051a\\Desktop\\kafka.txt";
		// String fileName =
		// "C:\\Users\\cnbjpuhui-5051a\\Desktop\\测试\\Flink\\Oplog\\WC.txt";
		Properties props = new Properties();
		// props.setProperty("bootstrap.servers", "10.10.31.31:6667");
		// props.setProperty("zookeeper.connect", "10.10.31.31:2181");
		props.setProperty("bootstrap.servers", "10.10.56.138:9092");
		props.setProperty("zookeeper.connect", "10.10.56.138:2181");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.setProperty("serializer.class", "kafka.serializer.DefaultEncoder");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// 配置key的序列化类
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		KafkaProducer producer = new KafkaProducer(props);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		String data = null;
		while ((data = br.readLine()) != null) {
			String key = data.split("EOE")[0];
			String value = data.split("EOE")[1];
			// ProducerRecord record = new ProducerRecord("mysqlBinLogTopic1",
			// key, value);
			ProducerRecord record = new ProducerRecord("oplogbumble", key, value);
			producer.send(record);
			System.out.println(key);
		}
		producer.flush();
		producer.close();
	}
}
