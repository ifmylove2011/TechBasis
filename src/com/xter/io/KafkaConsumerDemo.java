package com.xter.io;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerDemo {

	public static void main(String[] args) {
		Properties p = new Properties();
//		p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "219.152.63.49:19092,219.152.63.49:19093,219.152.63.49:19094,219.152.63.49:19095");
		p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "219.152.63.49:19092");

		p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// 自动提交秒
		// p.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, false);
		// 是否开启自动提交
		p.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		// group 代表一个消费组,加入组里面,消息只能被该组的一个消费者消费
		// 如果所有消费者在一个组内,就是传统的队列模式,排队拿消息
		// 如果所有的消费者都不在同一个组内,就是发布-订阅模式,消息广播给所有组
		// 如果介于两者之间,那么广播的消息在组内也是要排队的
		p.put(ConsumerConfig.GROUP_ID_CONFIG, "jinxin_test_1");
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(p);
		// 订阅消息；subscribe()
		// 方法接受一个主题列表作为参数：也可以接收一个正则表达式，匹配多个主题；consumer.subscribe("test.*");
//		kafkaConsumer.subscribe(Collections.singletonList("aiops-parsed"));
		kafkaConsumer.subscribe(Collections.singletonList("test-new"));

		while (true) {
			// 100 是超时时间（ms），在该时间内 poll 会等待服务器返回数据
			ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

			// poll 返回一个记录列表。
			// 每条记录都包含了记录所属主题的信息、记录所在分区的信息、记录在分区里的偏移量，以及记录的键值对。
			for (ConsumerRecord<String, String> record : records) {
				// 主题
				System.out.println("主题：" + record.topic());
				System.out.println("读取位置：" + record.offset());
				System.out.println("Key：" + record.key());
				System.out.println("内容：" + record.value().length());
			}
			// 手动提交
			try {
				// poll 的数据全部处理完提交
				kafkaConsumer.commitAsync();
			} catch (CommitFailedException e) {
				e.printStackTrace();
			}
		}
	}
}
