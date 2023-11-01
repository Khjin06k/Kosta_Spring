package com.codingrecipe.springEx1.sample;

public class MessageBeanEn implements MessageBean{

    @Override
    public void sayHello(String name){
        System.out.println("Hello, "+name);
    }
}
