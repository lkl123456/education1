package com.adks.dbclient.kafka;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adks.dbclient.kafka.message.IMessageConsumer;
import com.adks.dbclient.kafka.message.IMessageHandler;
import com.adks.dbclient.kafka.message.IMessageProducer;


public class KafkaClientTest {
    private ClassPathXmlApplicationContext context;
    private IMessageProducer producer;
    private IMessageConsumer consumer;

    @Before
    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(new String[]{"adksKafkaProducer.xml", "adksKafkaConsumer.xml"});
        context.start();
        producer = context.getBean("messageProducer", IMessageProducer.class);
        consumer = context.getBean("messageConsumer", IMessageConsumer.class);
    }

    @After
    public void teardown() {
        context.destroy();
    }

//    @Ignore
    @Test
    public void testProducer() {
        Date date = new Date();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> message = new HashMap<String, Object>();
            message.put("index", i + 1);
            message.put("message", "test" + (i + 1));
            producer.send("test1", message);
            System.out.println("after send message:" + "test" + (i + 1));
        }
//		for (int i = 0; i < 100000; i++) {
//			Map<String, Object> message = new HashMap<String, Object>();
//			message.put("index", i);
//			message.put("message", "andy" + i);
//			producer.send("test1", message);
//			System.out.println("after send message:" + "andy" + i);
//		}
        System.out.println("Time consumed:" + (new Date().getTime() - date.getTime()));
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    @Ignore
    @Test
    public void testConsumer() {

       /* consumer.consume("add_tender", new IMessageHandler() {
            AtomicInteger total = new AtomicInteger(0);

            @Override
            public boolean handle(String topic, Object message) {
                System.out.println("testconsumer test receive, topic:" + topic + ",message:" + message + ",total:" + total.incrementAndGet());
                return true;
            }
        });*/
        IMessageHandler messageHandler = new MessageHandler();
        consumer.consume("test1", messageHandler );
//		consumer.consume("andy", new IMessageHandler() {
//			@Override
//			public boolean handle(String topic, Object message) {
//				System.out.println("testconsumer andy receive, topic:" + topic + ",message:" + message);
//				return true;
//			}
//		});
        consumer.start();
        System.out.println("consumer started!");
       try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void kafkaDemo() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            Map<String, Object> message = new HashMap<String, Object>();
            Map<String, Object> addMessage = new HashMap<String, Object>();
            message.put("id", "123");
            message.put("name", "zhangsan");
            message.put("account", 20000);
            message.put("borrow_apr", "12.5");
            addMessage.put("cjdMqParaMap", message);
            //修改成kafka发送消息 add by jihaifeng 2016-07-26
            //sendMessage.sendMsgWithQueueName(MQMessageKeyEnum.ADD_TENDER.getMessageKey(),wangcaiAddtenderParaMap);
            producer.send("add_tender", addMessage);
            System.out.println(" producer message ...");

        }

    }
    @Ignore
    @Test
    public void testConsumerDemo2() {
       MessageHandler messageHandler = new MessageHandler();
        consumer.consume("add_tender", messageHandler);
        consumer.start();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
