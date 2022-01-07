package com.xter.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.BrokerInfo;

import java.util.concurrent.TimeUnit;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

public class OpenWireDemo {

	public static void main(String[] args) {

		try {
			//new OpenWireDemo().testPublish();
			new OpenWireDemo().testOpenwireClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testOpenwireClient() {
		OpenWireClient client = new OpenWireClient.Builder()
				.url("tcp://192.168.21.104:61616")
				.username("xxx")
				.password("dddddd")
				.clientId("desk")
				.build();
		//在连接前设定回调
		client.setCallback(new OpenWireClient.OpenWireCallback() {
			@Override
			public void subscribeFinish(String topic) {
				System.out.println("订阅完成, " + topic);
			}

			@Override
			public void connectionCreated(BrokerInfo brokerInfo) {
				System.out.println("连接成功, " + brokerInfo.toString());
				try {
					client.subscribe("xter", "xter", false);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void messageArrived(String topic, Message message) throws Exception {
				if (message instanceof TextMessage) {
					//文字消息接收
					TextMessage textMessage = (TextMessage) message;
					//接收消息
					String msg = textMessage.getText();
					System.out.println("收到[" + topic + "]的消息, " + msg);
				} else if (message instanceof BytesMessage) {
					//字节消息接收
					BytesMessage bytesMessage = (BytesMessage) message;
					long len = bytesMessage.getBodyLength();
					byte[] bytes = new byte[(int) len];
					bytesMessage.readBytes(bytes);
					String msg = new String(bytes);
					System.out.println("收到[" + topic + "]的消息, " + msg);
				}
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		});
		try {
			client.connect();
			TimeUnit.SECONDS.sleep(20);
			client.disconnect();
		} catch (JMSException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	//编写消息的发送方---消息的生产者
	public void testPublish() throws Exception {
		//创建连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		//从工厂中获取连接对象
		Connection connection = connectionFactory.createConnection();
		connection.setClientID("client-1");
		//连接MQ服务
		connection.start();
		//获得回话（session）对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//通过session对象创建Topic
		Topic topic = session.createTopic("itheimaTopic");
		//通过session对象创建消息的发送者
		MessageProducer producer = session.createProducer(topic);
		//通过session创建消息对象
		TextMessage message = session.createTextMessage("ping111");
		//发送消息
		producer.send(message, DeliveryMode.PERSISTENT, 1, 1000 * 60 * 60 * 24);
		TimeUnit.SECONDS.sleep(20);
		//关闭相关资源
		producer.close();
		session.close();
		connection.close();
	}

	//编写消息的接收方---消息的消费者
	public void testSubscribe() throws Exception {
		//创建连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		//从工厂中获取连接对象
		Connection connection = connectionFactory.createConnection();
		//设置客户端id
		connection.setClientID("client-1");
		//连接MQ服务
		connection.start();
		//获得回话（session）对象
		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//通过session对象创建Topic
		Topic topic = session.createTopic("itheimaTopic");
		//通过session对象创建消息的消费者
		//MessageConsumer consumer = session.createConsumer(topic);
		//客户端持久化订阅
		TopicSubscriber consumer = session.createDurableSubscriber(topic, "client1");
		//指定消息监听器
		consumer.setMessageListener(new MessageListener() {
			//当我们监听的topic 中存在消息 这个方法自动执行
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("消费者接收到了消息：" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//消息消费者 要时刻在线 连接对象 会话对象均不需要关闭
		//模拟web场景当执行结束后不能让线程结束
		while (true) {
			//死循环  不让消费者线程停掉
		}
	}
}
