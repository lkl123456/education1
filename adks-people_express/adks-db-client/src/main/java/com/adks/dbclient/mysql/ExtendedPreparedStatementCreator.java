package com.adks.dbclient.mysql;

import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 * 需要重写SQL语用可以用该类
 * @author panpanxu
 *
 */
public abstract class ExtendedPreparedStatementCreator implements PreparedStatementCreator {
	protected String sql;
	
	public ExtendedPreparedStatementCreator(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
}
