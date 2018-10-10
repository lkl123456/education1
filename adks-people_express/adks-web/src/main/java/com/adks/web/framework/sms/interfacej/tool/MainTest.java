package com.adks.web.framework.sms.interfacej.tool;

import com.adks.web.framework.sms.interfacej.SmsClientBanlance;
import com.adks.web.framework.sms.interfacej.SmsClientSend;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sendsmsurl = "http://139.129.128.71:8086/msgHttp/json/mt";
		String banlanceurl = "http://139.129.128.71:8086/msgHttp/json/balance";

		String account = "aidi106";
		String password = "jgRbmhgk";
		// String phonenumber =
		// "17326865430,18611778542,18911137837,18618414022,18394011835,18211099680,15981879340,18310988140,18510664813,15810693364,18612617639";
		String phonenumber = "17326865430";// 节能环保高部长13910907090
		String msgContent = "【北京节能环保中心】yanzhengma333";

		 String sendResult =SmsClientSend.sendSms(sendsmsurl,account,password,phonenumber,msgContent);
		 System.out.println("account sendResult "+sendResult);

		String banlanceResult = SmsClientBanlance.queryBanlance(banlanceurl, account, password);

		System.out.println("account banlance value " + banlanceResult);
	}

}
