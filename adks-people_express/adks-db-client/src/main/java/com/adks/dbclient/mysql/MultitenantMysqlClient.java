package com.adks.dbclient.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * (Multitenant)多租户MysqClient, 该client根据ThreadLocal变量获取当前要执行的SQL是对应哪个特定的数据库的。
 * SAAS多租户每个租户一个单独的数据库，数据完全隔离。 
 * @author panpanxu
 *
 */
public class MultitenantMysqlClient extends MysqlClient {
    Logger logger = LoggerFactory.getLogger(MultitenantMysqlClient.class);
    
    /**
     * ThreadLocal 变量存当前所执行的线程所对应的服务数据库名称
     */
    private final ThreadLocal<String> serviceDbName = new ThreadLocal<String>();
    
    /**
     * 多租户SQL注解
     */
    public static final String MLTITENANT_SQL_COMMENT = "/*!mycat:schema=%s */ ";
	
	/**
	 * 设置当前线程所对应的数据库名称
	 * @param dbName
	 */
	public void setServiceDb(String dbName) {
		serviceDbName.set(dbName);
	}
	
	/**
	 * 生成多租户SQL
	 * @param sql
	 * @return
	 */
	public String genMultitenantSql(String sql) {
		String dbName = serviceDbName.get();
		if (dbName == null) {
			throw new RuntimeException("serviceDbName is not found.");
		}
		return String.format(MLTITENANT_SQL_COMMENT, dbName) + sql;
	}
	
	/**
	 * 解析SQL
	 * @param sql
	 * @return
	 */
	@Override
	public String parseSql(String sql) {
		return genMultitenantSql(sql);
	}
}
