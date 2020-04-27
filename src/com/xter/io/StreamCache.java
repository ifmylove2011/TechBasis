package com.xter.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author XTER
 * 项目名称: BccaSDKDemo
 * 创建时间: 2020/4/27
 * 描述:
 */
public class StreamCache {

	static class Holder {
		static StreamCache INSTANCE = new StreamCache();
	}

	public static StreamCache get() {
		return Holder.INSTANCE;
	}

	private static final String TIMER_CMD = "timer_cmd";

	public void writeTimerCmdO(Set<TimerCmd> timerCmds) {
		File file = new File(TIMER_CMD);
		try {
			OutputStream os = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(timerCmds);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addTimerCmd(TimerCmd timerCmd) {
		Set<TimerCmd> timerCmds = readTimerCmdO();
		if(timerCmds==null){
			timerCmds = new HashSet<>();
		}
		timerCmds.add(timerCmd);
		writeTimerCmdO(timerCmds);
	}

	public void removeTimerCmd(String mac) {
		Set<TimerCmd> timerCmds = readTimerCmdO();
		timerCmds.removeIf(timerCmd -> timerCmd.mac.equalsIgnoreCase(mac));
		writeTimerCmdO(timerCmds);
	}

	public Set<TimerCmd> readTimerCmdO() {
		try {
			File file = new File(TIMER_CMD);
			InputStream is = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(is);
			return (Set<TimerCmd>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
