package com.xter.design.proxy;

public class DBMProxy implements IDBM {
	private DBM dbm;

	public DBMProxy() {
	}

	@Override
	public String quest() {
		if (this.dbm == null) {
			this.dbm = new DBM();
		}

		return this.dbm.quest();
	}
}
