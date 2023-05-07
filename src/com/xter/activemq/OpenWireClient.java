package com.xter.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.command.BrokerInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

	/**
	 * 唯一连接
	 */
	private ActiveMQConnection mConnection;
	/**
	 * 唯一会话
	 */
	private ActiveMQSession mSession;
	/**
	 * 工厂实例
	 */
	private ActiveMQConnectionFactory mConnectionFactory;
	/**
	 * 生产者、发布者集合
	 */
	private Map<String, MessageProducer> mProducerMap;
	/**
	 * 消费者、订阅者集合
	 */
	private Map<String, MessageConsumer> mConsumerMap;

	/**
	 * 集中监听
	 */
	private Map<String, MessageListener> messageListenerMap;

	/**
	 * 集中回调
	 */
	private OpenWireCallback mOpenWireCallback;

	private String keyURL;

	public interface OpenWireCallback {
		/**
		 * 订阅主题完成后
		 *
		 * @param topic 主题
		 */
		public void subscribeFinish(String topic);

		/**
		 * 连接建立完成后
		 *
		 * @param brokerInfo 节点信息
		 */
		public void connectionCreated(BrokerInfo brokerInfo);

		/**
		 * 接收、消费消息后
		 *
		 * @param topic   主题
		 * @param message 消息
		 */
		public void messageArrived(String topic, Message message) throws Exception;

		/**
		 * 发送或接收消息有异常出现
		 *
		 * @param ex 异常
		 */
		public void onException(Exception ex);
	}

	private OpenWireClient() {
		mProducerMap = new HashMap<>(4);
		mConsumerMap = new HashMap<>(8);
		messageListenerMap = new HashMap<>(8);
	}

	/**
	 * 连接
	 */
	public void connect() throws JMSException, IllegalArgumentException {
		if (mConnectionFactory == null) {
			throw new IllegalArgumentException("ConnectFactory must not be null");
		}
		if (mConnection == null) {
			mConnection = (ActiveMQConnection) mConnectionFactory.createConnection();
		}
		mSession = (ActiveMQSession) mConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		if (mOpenWireCallback != null) {
			mOpenWireCallback.connectionCreated(mConnection.getBrokerInfo());
		}
	}

	/**
	 * 是否建立了连接
	 *
	 * @return isConnected
	 */
	public boolean isConnected() {
		return mConnection != null && mSession != null && mConnection.isStarted();
	}

	public String getServerURI() {
		return keyURL;
	}

	/**
	 * 发布主题
	 *
	 * @param topicStr   主题名称
	 * @param messageStr 消息内容
	 * @param callback   回调
	 * @throws JMSException
	 */
	public void publish(String topicStr, String messageStr, AsyncCallback callback) throws JMSException {
		Topic topic = mSession.createTopic(topicStr);
		MessageProducer producer = mProducerMap.get(topicStr);
		if (producer == null) {
			producer = mSession.createProducer(topic);
			mProducerMap.put(topicStr, producer);
		}
		TextMessage message = mSession.createTextMessage(messageStr);
		//发送消息
		((ActiveMQMessageProducer) producer).send(producer.getDestination(), message, DeliveryMode.PERSISTENT, 4, 0, callback);
	}

	/**
	 * 订阅主题
	 *
	 * @param topicStr         主题名称
	 * @param subscriptionName 订阅项命名
	 * @throws JMSException
	 */
	public void subscribe(String topicStr, String subscriptionName, boolean isDurable) throws JMSException {
		Topic topic = mSession.createTopic(topicStr);
		TopicSubscriber subscriber = (TopicSubscriber) mConsumerMap.get(topicStr);
		if (subscriber == null) {
			//创建持久化的订阅者，会接收到之前所有未被消费的主题消息，反之则不会
			subscriber = isDurable ? mSession.createDurableSubscriber(topic, subscriptionName) : mSession.createSubscriber(topic);
			mConsumerMap.put(topicStr, subscriber);
		}
		MessageListener listener = messageListenerMap.get(topicStr);
		if (listener == null) {
			listener = new MessageListener() {
				@Override
				public void onMessage(Message message) {
					if (mOpenWireCallback != null) {
						try {
							mOpenWireCallback.messageArrived(topicStr, message);
						} catch (Exception e) {
							e.printStackTrace();
							mOpenWireCallback.onException(e);
						}
					}
				}
			};
			subscriber.setMessageListener(listener);
			if (mOpenWireCallback != null) {
				mOpenWireCallback.subscribeFinish(topicStr);
			}
			messageListenerMap.put(topicStr, listener);
		}
	}

	/**
	 * 设置回调
	 *
	 * @param callback OpenWireCallback
	 */
	public void setCallback(OpenWireCallback callback) {
		this.mOpenWireCallback = callback;
	}

	/**
	 * 取消订阅
	 *
	 * @param topicStr 主题
	 * @return bool
	 * @throws JMSException
	 */
	public boolean unSubscribe(String topicStr) throws JMSException {
		MessageConsumer messageConsumer = mConsumerMap.remove(topicStr);
		if (messageConsumer != null) {
			messageConsumer.close();
			messageListenerMap.remove(topicStr);
		}
		return messageConsumer != null;
	}

	/**
	 * 取消订阅
	 *
	 * @param topics 主题,可多个
	 * @throws JMSException
	 */
	public void unSubscribe(String[] topics) throws JMSException {
		for (String topic : topics) {
			unSubscribe(topic);
		}
	}

	/**
	 * 取消所有订阅
	 *
	 * @throws JMSException
	 */
	public void unSubscribeAll() throws JMSException {
		for (Map.Entry<String, MessageConsumer> entry : mConsumerMap.entrySet()) {
			entry.getValue().close();
		}
		mConsumerMap.clear();
	}

	/**
	 * 断开当前连接
	 *
	 * @throws JMSException
	 */
	public void disconnect() throws JMSException {
		for (Map.Entry<String, MessageProducer> entry : mProducerMap.entrySet()) {
			entry.getValue().close();
		}
		for (Map.Entry<String, MessageConsumer> entry : mConsumerMap.entrySet()) {
			messageListenerMap.remove(entry.getKey());
			entry.getValue().close();
		}
		if (mSession != null) {
			mSession.close();
			mSession = null;
		}
		if (mConnection != null) {
			mConnection.close();
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
			target.keyURL = url;
			target.mConnectionFactory = new ActiveMQConnectionFactory(url);
			target.mConnectionFactory.setClientID(clientId);
			if (username != null && password != null) {
				target.mConnectionFactory.setUserName(username);
				target.mConnectionFactory.setPassword(password);
			}
			return target;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OpenWireClient that = (OpenWireClient) o;
		return Objects.equals(keyURL, that.keyURL);
	}

	@Override
	public int hashCode() {
		return Objects.hash(keyURL);
	}
}
