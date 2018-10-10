package com.adks.dbclient.dao.singaltanent;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.commons.Page;
import com.mysql.jdbc.Statement;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * BaseDao提供一些共用的方法，所有DAO应该需继承BaseDao
 */
abstract public class BaseDao {

    @Resource
    protected MysqlClient mysqlClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 每个子类实现该方法返回table name
     * @return
     */
    abstract protected String getTableName();
    
    /**
     * 查询SQL语句前辍，如果子实不愿意查询所有列，可以Override该方法
     * @return
     */
    protected String getQueryPrefix() {
    	return new StringBuilder("SELECT * FROM ").append(getTableName()).append(" WHERE 1=1 ").toString();
    }

    /**
     * 分页查询
     * @author panpanxu
     * @param queryColumnValueMap
     * @param orderByClause
     * @return
     */
    public Page<List<Map<String,Object>>> queryPage(Map<String,Object> queryColumnValueMap, String orderByClause, int currentPage, int pageSize) {
    	List<Object> columnValueList = new ArrayList<Object>();
    	String condition = constructQueryCondition(queryColumnValueMap, columnValueList);
    	int count = count(condition, columnValueList);
    	
    	StringBuilder querySqlSb = new StringBuilder(getQueryPrefix());
    	querySqlSb.append(condition);

        if(StringUtils.isNotBlank(orderByClause)){
        	querySqlSb.append(" order by ");
        	querySqlSb.append(orderByClause);
        }
        querySqlSb.append(" limit ?, ?");
        columnValueList.add(currentPage * pageSize);
        columnValueList.add(pageSize);
        List<Map<String,Object>> resultMapList = mysqlClient.queryForList(querySqlSb.toString(), columnValueList.toArray());
        return new Page<List<Map<String,Object>>>(currentPage, pageSize, count, resultMapList);
    }
    
    /**
     * 分页查询, 并指定需要返回的列
     * @author panpanxu
     * @param queryColumnValueMap
     * @param returnColumns - 需要返回的列list
     * @param orderByClause
     * @return
     */
    public Page<List<Map<String,Object>>> queryPage(Map<String,Object> queryColumnValueMap, List<String> returnColumns, String orderByClause, int currentPage, int pageSize) {
    	return queryPage(queryColumnValueMap, StringUtils.join(returnColumns.iterator(), ","), orderByClause, currentPage, pageSize);
    }
    
    /**
     * 分页查询, 并指定需要返回的列
     * @author panpanxu
     * @param queryColumnValueMap
     * @param returnColumns - 需要返回的列string
     * @param orderByClause
     * @return
     */
    public Page<List<Map<String,Object>>> queryPage(Map<String,Object> queryColumnValueMap, String returnColumns, String orderByClause, int currentPage, int pageSize) {
    	List<Object> columnValueList = new ArrayList<Object>();
    	String condition = constructQueryCondition(queryColumnValueMap, columnValueList);
    	int count = count(condition, columnValueList);
    	
    	StringBuilder querySqlSb = new StringBuilder("SELECT ");
    	querySqlSb.append(returnColumns);
    	querySqlSb.append(" FROM ").append(getTableName());
    	querySqlSb.append(" WHERE 1=1 ");
    	querySqlSb.append(condition);

        if(StringUtils.isNotBlank(orderByClause)) {
        	querySqlSb.append(" order by ");
        	querySqlSb.append(orderByClause);
        }
        querySqlSb.append(" limit ?, ?");
        columnValueList.add(currentPage * pageSize);
        columnValueList.add(pageSize);
        List<Map<String,Object>> resultMapList = mysqlClient.queryForList(querySqlSb.toString(), columnValueList.toArray());
        return new Page<List<Map<String,Object>>>(currentPage, pageSize, count, resultMapList);
    }


    public Page<List<Map<String, Object>>> queryPage(String returnColumns, String conditions, List values, String orderByClause, int page, int pageSize) {

        //获取总行数
        if(StringUtils.isNotBlank(conditions)) {
            conditions = " and " + conditions;
        }
        int count = count(conditions, values);

        StringBuilder querySqlSb = new StringBuilder("SELECT ");
        querySqlSb.append(returnColumns);
        querySqlSb.append(" FROM ").append(getTableName());
        querySqlSb.append(" WHERE 1=1 ");

        if(StringUtils.isNotBlank(conditions)) {
            querySqlSb.append(conditions);
        }

        if(StringUtils.isNotBlank(orderByClause)) {
            querySqlSb.append(" order by ");
            querySqlSb.append(orderByClause);
        }

        querySqlSb.append(" limit ?, ?");
        values.add(page * pageSize);
        values.add(pageSize);

        List<Map<String,Object>> resultMapList = mysqlClient.queryForList(querySqlSb.toString(), values.toArray());
        return new Page<List<Map<String,Object>>>(page, pageSize, count, resultMapList);
    }

    /**
     * 查询列表
     * @author panpanxu
     * @param queryColumnValueMap
     * @param returnColumns - 返回的列list
     * @param orderByClause
     * @return
     */
    public List<Map<String,Object>> query(Map<String,Object> queryColumnValueMap, List<String> returnColumns, String orderByClause) {
    	return query(queryColumnValueMap, StringUtils.join(returnColumns.iterator(), ","), orderByClause);
    }

    /**
     * 查询列表
     * @author panpanxu
     * @param queryColumnValueMap
     * @param returnColumns - 返回的列string
     * @param orderByClause
     * @return
     */
    public List<Map<String,Object>> query(Map<String,Object> queryColumnValueMap, String returnColumns, String orderByClause) {
    	StringBuilder querySqlSb = new StringBuilder("SELECT ");
    	querySqlSb.append(returnColumns);
        querySqlSb.append(" FROM ").append(getTableName());
    	querySqlSb.append(" WHERE 1=1 ");
    	List<Object> columnValueList = new ArrayList<Object>();
    	querySqlSb.append(constructQueryCondition(queryColumnValueMap, columnValueList));
        if(StringUtils.isNotBlank(orderByClause)){
            querySqlSb.append(" order by ");
        	querySqlSb.append(orderByClause);
        }
        logger.debug("sql:{},values:{}",querySqlSb.toString(), columnValueList.toArray());
        List<Map<String,Object>> resultMapList = mysqlClient.queryForList(querySqlSb.toString(), columnValueList.toArray());
        return resultMapList;
    }


    /**
     * 根绝ID查询数据
     * @param id
     * @return
     */
    public Map<String, Object> queryOneById(int id) {

        StringBuilder querySqlSb = new StringBuilder(getQueryPrefix());
        querySqlSb.append(" and id = ?");

        return mysqlClient.queryForMap(querySqlSb.toString(), new Object[]{id});
    }

    /**
     * 查询列表, 可以指定查询的列
     * @author panpanxu
     * @param queryColumnValueMap
     * @param orderByClause
     * @return
     */
    public List<Map<String,Object>> query(Map<String,Object> queryColumnValueMap,String orderByClause) {
    	StringBuilder querySqlSb = new StringBuilder(getQueryPrefix());
    	List<Object> columnValueList = new ArrayList<Object>();
        querySqlSb.append(constructQueryCondition(queryColumnValueMap, columnValueList));
        if(StringUtils.isNotBlank(orderByClause)){
        	querySqlSb.append(orderByClause);
        }
        List<Map<String,Object>> resultMapList = mysqlClient.queryForList(querySqlSb.toString(), columnValueList.toArray());
        return resultMapList;
    }
    
    /**
     * 根据查询条件查询记录条数
     * @author panpanxu
     * @param condition - 查询条件
     * @param columnValueList - 查询条件对应的值
     * @return
     */
    private int count(String condition, List<Object> columnValueList) {
    	StringBuilder countSqlSb = new StringBuilder("SELECT COUNT(1) AS total FROM ");
    	countSqlSb.append(getTableName()).append(" WHERE 1=1 ").append(condition);
    	
    	Map<String, Object> countMap = mysqlClient.queryForMap(countSqlSb.toString(), columnValueList.toArray());
    	return MapUtils.getIntValue(countMap, "total", 0);
    }
    
    /**
     * 根据条件查询记录条数
     * @param queryColumnValueMap - 查询条件
     * @return - 符合条件的记录数
     */
    public int count(Map<String, Object> queryColumnValueMap) {
    	List<Object> columnValueList = new ArrayList<Object>();
    	String condition = constructQueryCondition(queryColumnValueMap, columnValueList);
    	return count(condition, columnValueList);
    }


    /**
     * 插入记录
     * @param insertColumnValueMap
     * @return - 主键
     */
    public int insert(Map<String,Object> insertColumnValueMap) {
        String insertSql = " insert into " + getTableName() + " set ";

        final List<Object> valueList = new ArrayList<Object>();
        if(!insertColumnValueMap.isEmpty()){
            for (Entry<String, Object> entry : insertColumnValueMap.entrySet()) {
            	insertSql += entry.getKey() + " = ? ,";
                valueList.add(entry.getValue());
            }
        }

        final String finalInsertSql = insertSql.substring(0,insertSql.length() - 1);
        int id = mysqlClient.insertAndGetKey(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(finalInsertSql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatementSetter psParaSetter = new ArgumentPreparedStatementSetter(valueList.toArray());
                try{
                    if(psParaSetter != null ){
                        psParaSetter.setValues(ps);
                    }
                } finally {
                    if(psParaSetter  instanceof ParameterDisposer){
                        ((ParameterDisposer)psParaSetter).cleanupParameters();
                    }
                }
                return ps;
            }
        });
        return id;
    }


    /**
     * 更新实体
     * @param paramValue
     * @param updateWhereConditionMap
     * @return
     */
    public int update(Map<String,Object> paramValue, Map<String,Object> updateWhereConditionMap) {
        String updateSql = "update " + getTableName() + " set ";
        if(updateWhereConditionMap == null || paramValue == null || paramValue.isEmpty() || updateWhereConditionMap.isEmpty()){
            throw new RuntimeException("update wallet product sql grammar Exception");
        }
        List<Object> valueList = new ArrayList<Object>();
        if(!paramValue.isEmpty()){
            for (Entry<String, Object> entry : paramValue.entrySet()) {
            	updateSql += entry.getKey() + "=?,";
                valueList.add(entry.getValue());
            }
        }

        updateSql = updateSql.substring(0,updateSql.length()-1);
        //链接where条件
        updateSql += " where 1 = 1 ";
        if(!updateWhereConditionMap.isEmpty()){
            for (Entry<String, Object> entry : updateWhereConditionMap.entrySet()) {
            	updateSql += " and " + entry.getKey() + " = ? " ;
                valueList.add(entry.getValue());
            }
        }

        logger.debug("sql:{},values:{}", updateSql,valueList.toArray());
        int effectiveRows = mysqlClient.update(updateSql,valueList.toArray());
        return effectiveRows;
    }
    
    private String constructQueryCondition(Map<String,Object> queryColumnValueMap, List<Object> columnValuleList) {
    	String conditionStr = "";
    	if(queryColumnValueMap != null && !queryColumnValueMap.isEmpty()){//查询条件不为空,则遍历Map,拼接where子句
            Iterator<Map.Entry<String, Object>> mapEntryIterator = queryColumnValueMap.entrySet().iterator();
            while(mapEntryIterator.hasNext()){
                Map.Entry<String,Object> columnMapEntry = mapEntryIterator.next();
                //拼接SQL where子句
                conditionStr += " and "+columnMapEntry.getKey() + " = ? ";
                columnValuleList.add(columnMapEntry.getValue());
            }
        }
        return conditionStr;
    }
}
