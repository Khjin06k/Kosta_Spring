package com.codingrecipe.springEx1.sample;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DiApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans1.xml");
        MessageBean bean = context.getBean("messageBean", MessageBean.class);
        bean.sayHello("My Spring");
    }
}
