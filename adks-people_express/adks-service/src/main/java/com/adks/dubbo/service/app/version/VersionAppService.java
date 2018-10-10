package com.adks.dubbo.service.app.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.version.Adks_version;
import com.adks.dubbo.dao.app.version.VersionAppDao;

@Service
public class VersionAppService extends BaseService<VersionAppDao> {

	@Autowired
	private VersionAppDao versionDao;

	@Override
	protected VersionAppDao getDao() {
		return versionDao;
	}

	/**
	 * 获取版本信息
	 * 
	 * @param versionCode
	 * @return
	 */
	public Adks_version getVersion() {
		return versionDao.getVersion();
	}
}
