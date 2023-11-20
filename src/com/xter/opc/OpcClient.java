package com.xter.opc;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.list.ClassDetails;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.AccessStateListener;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.Async20Access;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.openscada.opc.lib.list.Categories;
import org.openscada.opc.lib.list.Category;
import org.openscada.opc.lib.list.ServerList;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class OpcClient extends Observable {

	private Server mServer = null;

	/**
	 * 连接opc server
	 */
	public synchronized boolean connectServer(String host, String clsid, String user, String password, String domain) {
		boolean mState = false;
		ServerList serverList = null;
		try {
			// 获取server上的opc server应用列表
			serverList = new ServerList(host, user, password, domain);

			// 连接server
			final ConnectionInformation connectionInfo = new ConnectionInformation();
			connectionInfo.setHost(host);

			// 两种设值Clsid方式
			// 1. 设置ProgId，获取指定点位对应的Clsid；
			// connectionInfo.setClsid(serverList.getClsIdFromProgId(""));
			// 2. 设置Clsid，连接server成功
			connectionInfo.setClsid(clsid);

			connectionInfo.setUser(user);
			connectionInfo.setPassword(password);

			ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
			mServer = new Server(connectionInfo, executor);
			mServer.connect();

			mServer.addStateListener(state -> System.out.println("connectionStateChanged state=" + state));

			mState = true;
		} catch (IllegalArgumentException | UnknownHostException | JIException | AlreadyConnectedException e) {
			e.printStackTrace();
		} finally {
			if (!mState) {
				mServer = null;
			}
		}

		return mState;
	}

	/**
	 * 断开连接opc server
	 */
	public synchronized void disconnectServer() {
		if (mServer == null) {
			return;
		}
		mServer.disconnect();
		mServer = null;
	}

	/*
	 * 显示server上的OPC服务器应用列表
	 */
	public void showAllOPCServer(String host, String user, String password, String domain) {
		try {
			ServerList serverList = new ServerList(host, user, password, domain);
			// 支持DA 1.0，DA 2.0规范
			Collection<ClassDetails> detailsList = serverList.listServersWithDetails(
					new Category[] { Categories.OPCDAServer10, Categories.OPCDAServer20 }, new Category[] {});
			for (final ClassDetails details : detailsList) {
				System.out.println("ClsId=" + details.getClsId() + " ProgId=" + details.getProgId() + " Description="
						+ details.getDescription());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查opc server中是否包含指定的监测点列表
	 */
	public boolean checkItemList(List<String> list) {
		// 获取opc server上的所有检测点
		FlatBrowser flatBrowser = mServer.getFlatBrowser();
		if (flatBrowser == null) {
			return false;
		}

		try {
			Collection<String> collection = flatBrowser.browse();
			return collection.containsAll(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 异步读取数据 Async20Access实现了IOPCDataCallback接口，基于事件回调的实现
	 */
	public void asyncReadObject(final String itemId, int period) {
		// 第三个参数用于设置初始化时是否执行访问
		AccessBase accessBase;
		try {
			accessBase = new Async20Access(mServer, period, false);
			accessBase.addItem(itemId, (item, itemState) -> {
				System.out.println("asyncReadObject item=" + itemState);
				try {
					Object value = itemState.getValue().getObject();
					setData(itemId, value);
				} catch (JIException e) {
					e.printStackTrace();
				}
			});
			// 开始读取
			accessBase.bind();
		} catch (IllegalArgumentException | UnknownHostException | NotConnectedException | JIException | DuplicateGroupException | AddFailedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 同步读取数据
	 */
	public void syncReadObject(final String itemId, int period) {
		AccessBase accessBase;
		try {
			// SyncAccess会开启一个线程，按照period时间间隔，定时去获取监控点数据
			accessBase = new SyncAccess(mServer, period);
			accessBase.addStateListener(new AccessStateListener() {
				@Override
				public void stateChanged(boolean state) {
					System.out.println("stateChanged state=" + state);
				}

				@Override
				public void errorOccured(Throwable arg) {
					System.out.println("errorOccured arg=" + arg);
				}
			});

			accessBase.addItem(itemId, (item, itemState) -> {
				System.out.println("syncReadObject item=" + itemState);
				if (itemState == null) {
					System.out.println("itemState is null");
					return;
				}
				try {
					Object value = itemState.getValue().getObject();
					setData(itemId, value);
				} catch (JIException e) {
					e.printStackTrace();
				}
			});
			// 开始读取
			accessBase.bind();
			// 解除绑定，停止读取
			// accessBase.unbind();
		} catch (IllegalArgumentException | UnknownHostException | JIException | NotConnectedException | DuplicateGroupException | AddFailedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册
	 */
	public void subscribe(Observer observer) {
		this.addObserver(observer);
	}

	/**
	 * 数据变化，通知观察者
	 */
	private void setData(String itemId, Object value) {
		setChanged();
		notifyObservers(new Result(itemId, value));
	}

}