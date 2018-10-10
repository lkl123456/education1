package com.adks.dbclient.kafka;

import com.adks.dbclient.kafka.message.IMessageHandler;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler implements IMessageHandler {
    @Override
    public boolean handle(String topic, Object message) {
    	System.out.println("topic:" + topic + ", message:" + message.toString());
//        Map<String, Object> paramMap = JSON.parseObject(message.toString(),HashMap.class);
//        System.out.println("The paramMap is "+paramMap);
//        //System.out.println("The paramMap is "+paramMap.get("cjdMqParaMap"));
//       //Map<String,Object> param= (Map<String, Object>) paramMap.get("cjdMqParaMap");
//        System.out.println("The paramMap name value is "+paramMap.get("name"));
//        //System.out.println("The paramMap is "+JSON.parseObject(paramMap.get("cjdMqParaMap").toString(),HashMap.class).get("name"));
        return true;
    }
}
