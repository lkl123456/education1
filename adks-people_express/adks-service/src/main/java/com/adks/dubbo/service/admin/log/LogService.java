package com.adks.dubbo.service.admin.log;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.log.LogDao;

/**
 * 
 * ClassName LogService
 * @Description：系统日志service
 * @author xrl
 * @Date 2017年6月22日
 */
@Service
public class LogService extends BaseService<LogDao> {
	
	@Autowired
	private LogDao logDao;

	@Override
	protected LogDao getDao() {
		return logDao;
	}
	
	public Page<List<Map<String, Object>>> getLogStatisticsListJson(Page<List<Map<String, Object>>> page){
		return logDao.getLogStatisticsListJson(page);
	}

}
