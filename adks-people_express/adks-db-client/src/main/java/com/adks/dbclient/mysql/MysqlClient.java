package com.adks.dbclient.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.*;

public class MysqlClient {
    Logger logger = LoggerFactory.getLogger(MysqlClient.class);
    private DataSource dataSource;
    private List<DataSource> dataSourceSlaves;
    private Random random = new Random();
   
    private JdbcTemplate masterJdbcTemplate;
    private JdbcTemplate[] slaveJdbcTemplateArr;
    
    public JdbcTemplate getJdbcTemplate(boolean isWrite) {
        if (isWrite || (this.slaveJdbcTemplateArr == null || this.slaveJdbcTemplateArr.length == 0)) {
            return masterJdbcTemplate;
        }
        if (this.slaveJdbcTemplateArr.length == 1) {
            return slaveJdbcTemplateArr[0];
        }
        return this.slaveJdbcTemplateArr[this.random.nextInt(this.dataSourceSlaves.size())];
    }

    public JdbcTemplate getJdbcTemplate(){
    	return getJdbcTemplate(false);
    }
    /**
     * 数据库增删改，参数为对象数组
     * @param sql
     * @param args
     * @return
     */
    public int update(String sql, Object... args) {
    	sql = parseSql(sql);
        logger.debug("sql:{}, args:{}", sql, args);
        return this.getJdbcTemplate(true).update(sql, args);
    }

    /**
     * 数据库增加后返回主键
     * @param preparedStatementCreator
     * @return
     */
    public int insertAndGetKey(PreparedStatementCreator preparedStatementCreator) {
    	if (preparedStatementCreator instanceof ExtendedPreparedStatementCreator) {
    		ExtendedPreparedStatementCreator creator = (ExtendedPreparedStatementCreator) preparedStatementCreator;
    		creator.setSql(parseSql(creator.getSql()));
    	}
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.getJdbcTemplate(true).update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().intValue();
    }


    /**
     * 批量修改数据库
     * JiangRui
     * @param sql
     * @param batchPreparedStatementSetter
     * @return
     * @throws DataAccessException
     */
    public int[] batchUpdate(String sql, BatchPreparedStatementSetter batchPreparedStatementSetter)throws DataAccessException {
    	sql = parseSql(sql);
    	return getJdbcTemplate(true).batchUpdate(sql, batchPreparedStatementSetter);
    }

    /**
     * 数据库查询，返回结果集list
     * @param sql - sql语句
     * @param args - sql参数
     * @param rowMapper
     * @return
     */
    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
    	sql = parseSql(sql);
        return this.getJdbcTemplate(false).query(sql, args, rowMapper);
    }

    /**
     * 数据库查询，返回结果集List
     * @param sql
     * @param args
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Object... args) {
    	sql = parseSql(sql);
        List<Map<String, Object>> resultList = this.getJdbcTemplate(false).queryForList(sql, args);
        if (resultList == null) {
            resultList = new ArrayList<Map<String, Object>>();
        }
        return resultList;
    }

    /**
     * 数据库查询，返回结果集List
     * @param sql
     * @param args
     * @isOnMasterQuery 当前查询是否在主库上查询,isOnMasterQuery:true,在主库查询,false:在从库查询
     * @return
     */
    public List<Map<String, Object>> queryForListOnMasterOrSlave(boolean isOnMasterQuery, String sql, Object... args) {
    	sql = parseSql(sql);
        List<Map<String, Object>> resultList = this.getJdbcTemplate(isOnMasterQuery).queryForList(sql, args);
        if (resultList == null) {
            resultList = new ArrayList<Map<String, Object>>();
        }
        return resultList;
    }

    /**
     * 数据库查询，返回结果集Map
     * @param sql
     * @param args
     * @return
     */
    public Map<String, Object> queryForMap(String sql, Object... args) {
    	sql = parseSql(sql);
        Map<String, Object> resultMap = null;
        try {
            resultMap = this.getJdbcTemplate(false).queryForMap(sql, args);
        } catch (EmptyResultDataAccessException e) {
            resultMap = new HashMap<String, Object>();
        }
        return resultMap;
    }


    /**
     * 数据库查询，返回结果集Map
     * @param sql
     * @param args
     * @isOnMasterQuery 当前查询是否在主库上查询,isOnMasterQuery:true,在主库查询,false:在从库查询
     * @return
     */
    public Map<String, Object> queryForMapOnMasterOrSlave(String sql, Object[] args, boolean isOnMasterQuery) {
    	sql = parseSql(sql);
        Map<String, Object> resultMap = null;
        try {
            resultMap = this.getJdbcTemplate(isOnMasterQuery).queryForMap(sql, args);
        } catch (EmptyResultDataAccessException e) {
            resultMap = new HashMap<String, Object>();
        }
        return resultMap;
    }

    /**
     * 数据库查询，返回原生结果集list<map>
     * @param sql
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List queryList(String sql) {
    	sql = parseSql(sql);
        return this.getJdbcTemplate(false).queryForList(sql);
    }

    /**
     * 查询结果封装对象，只查一列的情况
     * @param sql
     * @return
     */
    public Integer queryforInt(String sql,Object... args){
    	sql = parseSql(sql);
    	return this.getJdbcTemplate(false).queryForObject(sql, args, Integer.class);
    }
    
    public void execute(String sql){
    	sql = parseSql(sql);
        getJdbcTemplate(true).execute(sql);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
    public void setDataSource(DataSource dataSource) {
        logger.info("mysql init success,dataSource:{}", dataSource);
        this.dataSource = dataSource;
        this.masterJdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public List<DataSource> getDataSourceSlaves() {
        return dataSourceSlaves;
    }

    public void setDataSourceSlaves(List<DataSource> dataSourceSlaves) {
        this.dataSourceSlaves = dataSourceSlaves;
        slaveJdbcTemplateArr = new JdbcTemplate[dataSourceSlaves.size()];
        for (int index = 0; index < dataSourceSlaves.size(); index++) {
        	slaveJdbcTemplateArr[index] = new JdbcTemplate(this.dataSourceSlaves.get(index));
        }
    }

	public JdbcTemplate getMasterJdbcTemplate() {
		return masterJdbcTemplate;
	}

	public JdbcTemplate[] getSlaveJdbcTemplateArr() {
		return slaveJdbcTemplateArr;
	}
	
	/**
	 * 解析SQL
	 * @param sql
	 * @return
	 */
	public String parseSql(String sql) {
		return sql;
	}
}
