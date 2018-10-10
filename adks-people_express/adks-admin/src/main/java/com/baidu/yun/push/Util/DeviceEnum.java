/**
 * 
 */
package com.baidu.yun.push.Util;

/**
 * @author Administrator
 *
 */
public enum DeviceEnum {
	Android("Android"), IOS("IOS");
	private final String device;

	private DeviceEnum(final String device) {
		this.device = device;
	}

	@Override
	public String toString() {
		return device;
	}
}
