package com.adks.dubbo.api.interfaces.admin.log;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName LogApi
 * @Description：操作日志API
 * @author xrl
 * @Date 2017年6月22日
 */
public interface LogApi {

	/**
	 * 
	 * @Title saveLog
	 * @Description
	 * @author xrl
	 * @Date 2017年6月23日
	 * @param dataId   被操作的数据的ID
	 * @param dataName   被操作的数据的Name（若是删除，此项可以为null）
	 * @param moduleId  模块Id
	 * @param operateType   操作类型ID
	 * @param map   从request中获取到的用户信息Map
	 * @param 相关参数详细，参照Adks_log实体中注释
	 * @return
	 */
	public Integer saveLog(Integer dataId,String dataName,Integer moduleId,Integer operateType,Map map);
	
	//系统操作日志分页
	public Page<List<Map<String, Object>>> getLogStatisticsListJson(Page<List<Map<String, Object>>> page);
	
}
