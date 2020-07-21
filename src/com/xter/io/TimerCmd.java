package com.xter.io;

import java.io.Serializable;
import java.util.Objects;

public class TimerCmd implements Serializable {

	public static final long serialVersionUID = 123456789000000L;

	public String mac;
	public int period;
	public int timeout;
	public String txt;

	public TimerCmd() {
	}

	public TimerCmd(String mac, int period, int timeout, String txt) {
		this.mac = mac;
		this.period = period;
		this.timeout = timeout;
		this.txt = txt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TimerCmd timerCmd = (TimerCmd) o;
		return Objects.equals(mac, timerCmd.mac) &&
				Objects.equals(txt, timerCmd.txt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mac, txt);
	}

	@Override
	public String toString() {
		return "TimerCmd{" +
				"mac='" + mac + '\'' +
				", period=" + period +
				", timeout=" + timeout +
				", txt='" + txt + '\'' +
				'}';
	}
}