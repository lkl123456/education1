/**
 * 
 */
package com.adks.dubbo.api.data.version;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class Adks_version implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String versionCode;
	private String apkUrl;
	private Date createTime;

	/**
	 * @return the apkUrl
	 */
	public String getApkUrl() {
		return apkUrl;
	}

	/**
	 * @param apkUrl
	 *            the apkUrl to set
	 */
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the versionCode
	 */
	public String getVersionCode() {
		return versionCode;
	}

	/**
	 * @param versionCode
	 *            the versionCode to set
	 */
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
