/**
 * 
 */
package com.baidu.yun.push.main;

import com.baidu.yun.push.Util.BaiduyunUtil;
import com.baidu.yun.push.Util.DeviceEnum;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;

/**
 * @author Administrator
 *
 */
public class testMain {

	// 安卓
	public static void testAndroidSend() throws PushClientException, PushServerException {
		String jsonAndroid = "{\"title\":\"test\",\"description\":\"test\",\"notification_builder_id\": 0, \"notification_basic_style\": 7,\"open_type\":0,\"url\": \"http://developer.baidu.com\",\"pkg_content\":\"\",\"custom_content\":{\"key\":\"value\"}}";
		// String deviceAndroid =
		// BaiduyunUtil.sendBaiduyunMessage(channelIdsAndroid, title,
		// description,
		// DeviceEnum.Android.toString());
		// // 查询消息状态
		// String[] msgIdsAndroid = { deviceAndroid };
		// BaiduyunUtil.getMessageStatus(msgIdsAndroid,
		// DeviceEnum.Android.toString());
		String deviceAndroid = BaiduyunUtil.sendBaiduyunMessage(jsonAndroid, DeviceEnum.Android.toString());
		// 查询消息状态
		String[] msgIdsAndroid = { deviceAndroid };
		BaiduyunUtil.getMessageStatus(msgIdsAndroid, DeviceEnum.Android.toString());
	}

	// 安卓
	public static void testAndroidGet(String deviceAndroid) throws PushClientException, PushServerException {
		// 查询消息状态
		String[] msgIdsAndroid = { deviceAndroid };
		BaiduyunUtil.getMessageStatus(msgIdsAndroid, DeviceEnum.Android.toString());
	}

	// IOS
	public static void testIOSSend() throws PushClientException, PushServerException {
		String[] channelIdsIOS = { "5709988285669254861" };
		String jsonIOS = "{\"aps\": {\"alert\":\"ceshi\",\"sound\":\"\",\"badge\":0},\"key1\":\"value1\",\"key2\":\"value2\"}";
		// String deviceIOS =
		// BaiduyunUtil.sendBaiduyunMessage(channelIdsIOS, "", jsonIOS,
		// DeviceEnum.IOS.toString());

		String deviceIOS = BaiduyunUtil.sendBaiduyunMessage(jsonIOS, DeviceEnum.IOS.toString());
		// 查询消息状态
		String[] msgIdsAndroid = { deviceIOS };
		BaiduyunUtil.getMessageStatus(msgIdsAndroid, DeviceEnum.IOS.toString());
	}

	public static void main(String[] args) throws PushClientException, PushServerException {
		testAndroidSend();
		// testIOSSend();
		// String deviceAndroid = "983720064182973027";
		// testAndroidGet(deviceAndroid);
	}
}