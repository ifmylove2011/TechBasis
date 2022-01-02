package com.xter.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.AsyncCallback;

import java.util.HashMap;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

/**
 * 简易客户端，供openwire协议使用；
 */
public class OpenWireClient {

	private ActiveMQConnection mConnection;
	private ActiveMQSession mSession;
	private ActiveMQConnectionFactory mConnectionFactory;
	private Map<String, MessageProducer> producerMap;
	private Map<String, MessageConsumer> consumerMap;

	private OpenWireClient() {
		producerMap = new HashMap<>(4);
		consumerMap = new HashMap<>(8);
	}

	public void connect() throws JMSException {
		if (mConnectionFactory == null) {
			System.out.println("mConnectionFactory is null");
			return;
		}
		if (mConnection == null) {
			mConnection = (ActiveMQConnection) mConnectionFactory.createConnection();
		}
		mConnection.start();
		mSession = (ActiveMQSession) mConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public void publish(String topicStr, String messageStr) throws JMSException {
		Topic topic = mSession.createTopic(topicStr);
		MessageProducer producer = producerMap.get(topicStr);
		if (producer == null) {
			producer = mSession.createProducer(topic);
			producerMap.put(topicStr, producer);
		}
		System.out.println(producer.hashCode());
		TextMessage message = mSession.createTextMessage(messageStr);
		//发送消息
		((ActiveMQMessageProducer) producer).send(producer.getDestination(), message, DeliveryMode.PERSISTENT, 4, 0, new AsyncCallback() {
			@Override
			public void onSuccess() {
				System.out.println("发送成功");
			}

			@Override
			public void onException(JMSException e) {
				e.printStackTrace();
			}
		});
	}

	public void subscribe(String topicStr, String subscriptionName) throws JMSException {
		Topic topic = mSession.createTopic(topicStr);
		TopicSubscriber subscriber = (TopicSubscriber) consumerMap.get(topicStr);
		if (subscriber == null) {
			subscriber = mSession.createDurableSubscriber(topic, subscriptionName);
			consumerMap.put(topicStr, subscriber);
		}
		System.out.println(subscriber.hashCode());
		subscriber.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("消费者接收到了消息：" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void disconnect() throws JMSException {
		if (mSession != null) {
			mSession.close();
			mSession = null;
		}
		if (mConnection != null) {
			mConnection.stop();
			mConnection = null;
		}
	}

	static class Builder {
		private OpenWireClient target = new OpenWireClient();

		private String url;
		private String clientId;
		private String username;
		private String password;

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public Builder clientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public OpenWireClient build() {
			if (url == null) {
				throw new IllegalArgumentException("url must not be null");
			}
			target.mConnectionFactory = new ActiveMQConnectionFactory(url);
			target.mConnectionFactory.setClientID(clientId);
			if (username != null && password != null) {
				target.mConnectionFactory.setUserName(username);
				target.mConnectionFactory.setPassword(password);
			}
			return target;
		}
	}


}
