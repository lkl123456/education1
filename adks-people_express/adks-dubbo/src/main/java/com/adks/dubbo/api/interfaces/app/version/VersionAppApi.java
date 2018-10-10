/**
 * 
 */
package com.adks.dubbo.api.interfaces.app.version;

import com.adks.dubbo.api.data.version.Adks_version;

/**
 * @author Administrator
 *
 */
public interface VersionAppApi {
	/**
	 * 获取版本信息
	 * 
	 * @param versionCode
	 * @return
	 */
	public Adks_version getVersion();
}
