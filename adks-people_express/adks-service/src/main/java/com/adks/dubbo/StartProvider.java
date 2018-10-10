package com.adks.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartProvider {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        context.start();
        System.out.println("start provider");
        synchronized (StartProvider.class) {
            while (true) {
                try {
                    StartProvider.class.wait();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
