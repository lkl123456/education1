package com.adks.dbclient.dbutil;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.commons.Page;

public class QueryUtil {
	public static Page<List<Map<String, Object>>> queryPage(MysqlClient mysqlClient, String sql, List<Object> columnValueList, int currentPage, int pageSize) {
		int count = count(mysqlClient, sql, columnValueList);
		sql +=  "limit ?, ?";
        columnValueList.add(currentPage * pageSize);
        columnValueList.add(pageSize);
        List<Map<String,Object>> resultMapList = mysqlClient.queryForList(sql, columnValueList.toArray());
        return new Page<List<Map<String,Object>>>(currentPage+1, pageSize, count, resultMapList);
	}
	
	/**
     * 根据查询条件查询记录条数
     * @author panpanxu
     * @param condition - 查询条件
     * @param columnValueList - 查询条件对应的值
     * @return
     */
    public static int count(MysqlClient mysqlClient, String sql, List<Object> columnValueList) {
    	int fromIndex = sql.indexOf("from");
    	if (fromIndex < 0) {
    		fromIndex = sql.indexOf("FROM");
    		if (fromIndex < 0) {
    			throw new RuntimeException("bad sql:" + sql);
    		}
    	}
    	String targetSql = "SELECT COUNT(*) as total " + sql.substring(fromIndex);
    	Map<String, Object> countMap = mysqlClient.queryForMap(targetSql, columnValueList.toArray());
    	return MapUtils.getIntValue(countMap, "total", 0);
    }
}
