package com.adks.dbclient.service.singletanent;


import java.util.List;
import java.util.Map;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.commons.Page;

/**
 * Base service, 提供单表的增删改查操作。每一张数据库的表应该有对应一个Service, DAO, 这些Service应该继承BaseService.
 * @author panpanxu
 */
public abstract class BaseService<T extends BaseDao> {
	abstract protected T getDao();
	
	/**
     * 查询符合条件的记录条数
     * @author panpanxu
     * 
     * @param queryColumnValueMap - 查询条件，如果没有查询条件可传null
     * @return
     */
    public int count(Map<String, Object> queryColumnValueMap) {
    	return getDao().count(queryColumnValueMap);
    }
    
    /**
     * 插入一条记录
     * @param insertColumnValueMap - 要插入记录的键值对 
     * @return - 插入后新记录的主键
     */
    public int insert(Map<String,Object> insertColumnValueMap) {
    	return getDao().insert(insertColumnValueMap);
    }
    
    /**
     * 更新记录
     * @param paramValue - 要更新列的键值对
     * @param updateWhereConditionMap - 查询条件
     * @return - 影响的记录条数
     */
    public int update(Map<String,Object> paramValue, Map<String,Object> updateWhereConditionMap) {
    	return getDao().update(paramValue, updateWhereConditionMap);
    }
	
	/**
	 * 分页查询，有默认每页条数
	 * @author panpanxu
	 * 
	 * @param queryColumnValueMap - 查询条件，如果没有查询条件可以传null
	 * @param orderByClause - 排序(例：根据ID升序：id asc)
	 * @param currentPage - 当前页数，从0开始
	 * @return - 分页数据 {@code Page}
	 */
	public Page<List<Map<String,Object>>> queryPage(Map<String,Object> queryColumnValueMap, String orderByClause, int currentPage) {
		return getDao().queryPage(queryColumnValueMap, orderByClause, currentPage, 10);
    }
	
	/**
	 * 分页查询, 可指定每页的记录数
	 * @author panpanxu
	 * 
	 * @param queryColumnValueMap - 查询条件，如果没有查询条件可以传null
	 * @param orderByClause - 排序(例：根据ID升序：id asc)
	 * @param currentPage - 当前页数，从0开始
	 * @param pageSize - 每页条数
	 * @return - 分页数 {@code Page}
	 */
	public Page<List<Map<String,Object>>> queryPage(Map<String,Object> queryColumnValueMap, String orderByClause, int currentPage, int pageSize) {
		return getDao().queryPage(queryColumnValueMap, orderByClause, currentPage, pageSize);
    }
    
	/**
	 * 分页查询，可指定返回哪些列
	 * @author panpanxu
	 * 
	 * @param queryColumnValueMap - 查询条件，如果没有查询条件可以传null
	 * @param returnColumns - 返回列的集合
	 * @param orderByClause - 排序(例：根据ID升序：id asc)
	 * @param currentPage - 当前页数，从0开始
	 * @param pageSize - 每页条数
	 * @return - 分页数 {@code Page}
	 */
    public Page<List<Map<String,Object>>> queryPage(Map<String,Object> queryColumnValueMap, List<String> returnColumns, String orderByClause, int currentPage, int pageSize) {
    	return getDao().queryPage(queryColumnValueMap, returnColumns, orderByClause, currentPage, pageSize);
    }
    
    /**
	 * 分页查询，可指定返回哪些列
	 * @author panpanxu
	 * 
	 * @param queryColumnValueMap - 查询条件，如果没有查询条件可以传null
	 * @param returnColumns - 返回列的集合
	 * @param orderByClause - 排序(例：根据ID升序：id asc)
	 * @param currentPage - 当前页数，从0开始
	 * @return - 分页数 {@code Page}
	 */
    public Page<List<Map<String,Object>>> queryPage(Map<String,Object> queryColumnValueMap, List<String> returnColumns, String orderByClause, int currentPage) {
    	return getDao().queryPage(queryColumnValueMap, returnColumns, orderByClause, currentPage, 10);
    }


	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
     */
	public Map<String, Object> queryOneById(int id) {
		return getDao().queryOneById(id);
	}

    
    /**
     * 查询记录，返回所有列
     * @param queryColumnValueMap - 查询条件，如果没有查询条件可以传null
     * @param orderByClause - 排序(例：根据ID升序：id asc)
     * @return
     */
    public List<Map<String,Object>> query(Map<String,Object> queryColumnValueMap,String orderByClause) {
    	return getDao().query(queryColumnValueMap, orderByClause);
    }
    
    /**
     * 查询记录，可指定返回哪些列
     * @param queryColumnValueMap - 查询条件，如果没有查询条件可以传null
     * @param returnColumns - 返回列的集合
     * @param orderByClause - 排序(例：根据ID升序：id asc)
     * @return
     */
    public List<Map<String,Object>> query(Map<String,Object> queryColumnValueMap, List<String> returnColumns, String orderByClause) {
    	return getDao().query(queryColumnValueMap, returnColumns, orderByClause);
    }


}
