package com.adks.dubbo.api.data.user;

import java.io.Serializable;
import java.util.Date;

public class Adks_user_bind implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId; // 用户Id
	private String remoteUserId; // 百度的userid
	private Integer clientType;
	private String deviceId; // 百度的channelId
	private Integer bindType; // 1：个推，2：极光，3：百度（默认）
	private Integer deviceType; // 1：浏览器设备；2：PC设备；3：Android设备；4：IOS设备；5：Windows
								// phone设备
	private Date createTime;
	private Date updateTime;

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
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the remoteUserId
	 */
	public String getRemoteUserId() {
		return remoteUserId;
	}

	/**
	 * @param remoteUserId
	 *            the remoteUserId to set
	 */
	public void setRemoteUserId(String remoteUserId) {
		this.remoteUserId = remoteUserId;
	}

	/**
	 * @return the clientType
	 */
	public Integer getClientType() {
		return clientType;
	}

	/**
	 * @param clientType
	 *            the clientType to set
	 */
	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the bindType
	 */
	public Integer getBindType() {
		return bindType;
	}

	/**
	 * @param bindType
	 *            the bindType to set
	 */
	public void setBindType(Integer bindType) {
		this.bindType = bindType;
	}

	/**
	 * @return the deviceType
	 */
	public Integer getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
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

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
