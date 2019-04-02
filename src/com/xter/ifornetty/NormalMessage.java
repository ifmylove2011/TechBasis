package com.xter.ifornetty;

public class NormalMessage {
	public int type;
	public int length;
	public String content;

	public NormalMessage(int type, int length, String content) {
		this.type = type;
		this.length = length;
		this.content = content;
	}

	@Override
	public String toString() {
		return "NormalMessage{" +
				"type=" + type +
				", length=" + length +
				", content='" + content + '\'' +
				'}';
	}
}
