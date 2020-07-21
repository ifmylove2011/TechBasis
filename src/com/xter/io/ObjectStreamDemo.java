package com.xter.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/4/26
 * 描述:
 */
public class ObjectStreamDemo {

	public static void main(String[] args) {
		TimerCmd timerCmd1 = new TimerCmd("001",10,100,"00001");
		TimerCmd timerCmd2 = new TimerCmd("001",10,100,"00002");

		Set<TimerCmd> timerCmds = new HashSet<>();
		timerCmds.add(timerCmd1);
		timerCmds.add(timerCmd2);

		StreamCache.get().addTimerCmd(timerCmd1);
		StreamCache.get().addTimerCmd(timerCmd2);
		StreamCache.get().addTimerCmd(timerCmd2);
		System.out.println(StreamCache.get().readTimerCmdO());
		StreamCache.get().removeTimerCmd("001");
		System.out.println(StreamCache.get().readTimerCmdO());
//		write(timerCmds);
//
//		List<TimerCmd> list = (List<TimerCmd>) read();
//		System.out.println(list.toString());
	}

//	private static void write(Object o){
//		File file = new File("timer_cmd");
//		try{
//			OutputStream os = new FileOutputStream(file);
//			ObjectOutputStream oos = new ObjectOutputStream(os);
//			oos.writeObject(o);
//		}catch (IOException e){
//			e.printStackTrace();
//		}
//	}
//
//	private static Object read(){
//		File file = new File("timer_cmd");
//		try{
//			InputStream is = new FileInputStream(file);
//			ObjectInputStream ois = new ObjectInputStream(is);
//			return ois.readObject();
//		}catch (IOException | ClassNotFoundException e){
//			e.printStackTrace();
//		}
//		return null;
//	}
}
