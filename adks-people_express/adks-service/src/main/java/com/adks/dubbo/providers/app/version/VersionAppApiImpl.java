/**
 * 
 */
package com.adks.dubbo.providers.app.version;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.version.Adks_version;
import com.adks.dubbo.api.interfaces.app.version.VersionAppApi;
import com.adks.dubbo.service.app.version.VersionAppService;

/**
 * @author Administrator
 *
 */
public class VersionAppApiImpl implements VersionAppApi {
	@Autowired
	private VersionAppService versionService;

	@Override
	public Adks_version getVersion() {
		return versionService.getVersion();
	}

}
