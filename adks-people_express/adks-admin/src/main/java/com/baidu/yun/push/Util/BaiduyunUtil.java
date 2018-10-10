/**
 * 
 */
package com.baidu.yun.push.Util;

import java.util.List;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.MsgSendInfo;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.QueryMsgStatusRequest;
import com.baidu.yun.push.model.QueryMsgStatusResponse;

/**
 * @author Administrator
 *
 */
public class BaiduyunUtil {

	// Android
	private static String apiKeyAndroid = "MdbiXdBPfikWvurOMb2mpRPX";
	private static String secretKeyAndroid = "s5GOQE5Yeid0uHinf60gXP1UMopyg2aV";

	// IOS
	private static String apiKeyIOS = "LQ5pNPPW8qnAS6kSFPDC6eds";
	private static String secretKeyIOS = "vcbVNKxNEV9szBxWBhBZ2yBVcTQQ6b9p";

	/**
	 * 发送百度云推送消息
	 * 
	 * @param channelIds
	 *            设备id
	 * @param title
	 *            发送消息标题
	 * @param description
	 *            发送消息内容
	 * @param device_type
	 *            设备类型 3：Android 4：IOS
	 * @throws PushServerException
	 * @throws PushClientException
	 */
	public static String sendBaiduyunMessage(String context, String device)
			throws PushServerException, PushClientException {
		PushKeyPair pair = null;
		Integer deviceType = 0;
		Integer messageType = 0;
		String msgId = "";
		if (device.equals(DeviceEnum.Android.toString())) {
			pair = new PushKeyPair(apiKeyAndroid, secretKeyAndroid);
			messageType = 0;
			deviceType = 3;
		} else if (device.equals(DeviceEnum.IOS.toString())) {
			pair = new PushKeyPair(apiKeyIOS, secretKeyIOS);
			messageType = 1;
			deviceType = 4;
		}
		BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);

		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		try {

			/**
			 * 推送消息给所有设备，即广播推送 Start
			 */
			PushMsgToAllRequest request = new PushMsgToAllRequest().addMsgExpires(new Integer(3600))
					.addMessageType(messageType).addMessage(context).addDeviceType(deviceType);
			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
			// Http请求返回值解析
			msgId = response.getMsgId();
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime() + ",timerId: "
					+ response.getTimerId());
			/**
			 * 推送消息给所有设备，即广播推送End
			 */
		} catch (

		PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			System.out.println(String.format("requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return msgId;
	}

	/**
	 * 查询消息发送状态
	 * 
	 * @param msgIds
	 *            发送消息成功后返回的id
	 * @param device
	 *            设备类型
	 * @throws PushServerException
	 * @throws PushClientException
	 */
	public static void getMessageStatus(String[] msgIds, String device)
			throws PushServerException, PushClientException {
		System.out.println("==========================================================");
		PushKeyPair pair = null;
		Integer deviceType = 0;
		if (device.equals(DeviceEnum.Android.toString())) {
			pair = new PushKeyPair(apiKeyAndroid, secretKeyAndroid);
			deviceType = 3;
		} else if (device.equals(DeviceEnum.IOS.toString())) {
			pair = new PushKeyPair(apiKeyIOS, secretKeyIOS);
			deviceType = 4;
		}

		BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);

		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		try {
			/**
			 * 查询消息推送状态，包括成功、失败、待发送、发送中4种状态 Start
			 */
			QueryMsgStatusRequest qmsRequest = new QueryMsgStatusRequest().addMsgIds(msgIds).addDeviceType(deviceType);
			// 5. http request
			QueryMsgStatusResponse qmsResponse = pushClient.queryMsgStatus(qmsRequest);
			// Http请求返回值解析
			System.out.println("totalNum: " + qmsResponse.getTotalNum() + "\n" + "result:");
			if (null != qmsResponse) {
				List<?> list = qmsResponse.getMsgSendInfos();
				for (int i = 0; i < list.size(); i++) {
					Object object = list.get(i);
					if (object instanceof MsgSendInfo) {
						MsgSendInfo msgSendInfo = (MsgSendInfo) object;
						StringBuilder strBuilder = new StringBuilder();
						strBuilder.append("List[" + i + "]: {" + "msgId = " + msgSendInfo.getMsgId() + ",status = "
								+ msgSendInfo.getMsgStatus() + ",sendTime = " + msgSendInfo.getSendTime()
								+ ",successCount = " + msgSendInfo.getSuccessCount());
						strBuilder.append("}\n");
						System.out.println(strBuilder.toString());
					}
				}
			}
			/**
			 * 查询消息推送状态，包括成功、失败、待发送、发送中4种状态 End
			 */
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			System.out.println(String.format("requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
	}

}
