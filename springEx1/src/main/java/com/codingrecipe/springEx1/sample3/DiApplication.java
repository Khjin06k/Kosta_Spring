package com.codingrecipe.springEx1.sample3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DiApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans3.xml");
        Employ employ = context.getBean("employ", Employ.class);
        employ.info();
    }
}
