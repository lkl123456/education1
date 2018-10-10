package com.adks.dubbo.providers.admin.log;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.adks.dubbo.service.admin.log.LogService;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName LogApiImpl
 * @Description:系统操作日志ApiImpl
 * @author xrl
 * @Date 2017年6月22日
 */
public class LogApiImpl implements LogApi {

	@Autowired
	private LogService logService;
	
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
	@Override
	public Integer saveLog(Integer dataId,String dataName,Integer moduleId,Integer operateType,Map map){
		Integer flag=0;
		if(map!=null&&map.size()>0){
			Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
			insertColumnValueMap.put("operateType",operateType);
			insertColumnValueMap.put("moduleId", moduleId);
			insertColumnValueMap.put("dataId",dataId);
			if(dataName!=null){
				insertColumnValueMap.put("dataName", dataName);
			}
			insertColumnValueMap.put("creatorId", map.get("userId"));
			insertColumnValueMap.put("creatorName", map.get("username"));
			insertColumnValueMap.put("orgId", map.get("orgId"));
			insertColumnValueMap.put("orgCode", map.get("orgCode"));
			insertColumnValueMap.put("createTime", new Date());
			flag=logService.insert(insertColumnValueMap);
			return flag;
		}else{
			System.out.println("Save Log fail!!!");
		}
		return flag;
	}
	
	@Override
	public Page<List<Map<String, Object>>> getLogStatisticsListJson(Page<List<Map<String, Object>>> page){
		return logService.getLogStatisticsListJson(page);
	}
}
