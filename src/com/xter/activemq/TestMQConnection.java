package com.xter.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/12/31
 * 描述:
 */
public class TestMQConnection {

	public static void main(String[] args) {
		try {
			new TestMQConnection().testProducerTopic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testProducerTopic() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.21.104:61616");
//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.108.26.230:16161");
		((ActiveMQConnectionFactory) connectionFactory).setClientID("xterxx");
		((ActiveMQConnectionFactory) connectionFactory).setUserName("xter");
		((ActiveMQConnectionFactory) connectionFactory).setPassword("hhhhllll");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("xter");
		MessageProducer producer = session.createProducer(topic);
		Message message = session.createTextMessage("hello topic message1");
		producer.send(message);
		TimeUnit.SECONDS.sleep(20);
		producer.close();
		session.close();
		connection.close();
	}

	public void testConsumerTopic() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.21.100:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println("the first consumer...");
						System.out.println(textMessage.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		System.in.read();
		consumer.close();
		session.close();
		connection.close();
	}

	public void testQueueProducer() throws Exception {
		//建立连接工厂，单例
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.21.100:61616");
		((ActiveMQConnectionFactory) connectionFactory).setUserName("xter");
		((ActiveMQConnectionFactory) connectionFactory).setClientID("xterxx");
		((ActiveMQConnectionFactory) connectionFactory).setPassword("hhhhllll");
		//建立连接
		Connection connection = connectionFactory.createConnection();
		//连接开启
		connection.start();
		//创建一个服务对象session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建一个目的地Destination，,即ActiveMQ的接收点
		Queue queue = session.createQueue("xxxx");
		//创建一个生产者，并将目的地告诉他
		MessageProducer producer = session.createProducer(queue);
		//创建一个消息
		Message message = session.createTextMessage("hello queue message2");
		//生产者发送消息
		producer.send(message);
		//关闭连接
		producer.close();
		session.close();
		connection.close();
	}

	public void testQueueCustomer() throws Exception {
		//建立连接工厂，单例
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616/");
		//建立连接
		Connection connection = connectionFactory.createConnection();
		//连接开启
		connection.start();
		//创建一个服务对象session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建一个目的地Destination,即ActiveMQ的接收点
		Queue queue = session.createQueue("test-queue");
		//创建一个消费者，并将目的地告诉他
		MessageConsumer consumer = session.createConsumer(queue);
		//消费者实时监听是否有消息传来，传来后打印出来
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println(textMessage.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		//手动关闭实时等待
		System.in.read();
		//关闭连接
		consumer.close();
		session.close();
		connection.close();
	}
}
