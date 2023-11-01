package com.codingrecipe.springEx1.sample2;

import java.io.IOException;

public class MessageBeanImpl implements MessageBean{
    private String name;
    private String greeting;
    private Outputter outputter; // MessageBeanImpl 기준에서 외부 클래스도 변수 (변수의 타입이 클래스)
    // 여러개의 데이터를 묶어 데이터 타입으로 만든 것
    // 마이바티스가 제공하는 기능은 하나의 클래스
    // Bean은 xml로 만드는 것, 빈 객체. new 해서 쓰지 않고 클래스를 API가 생성해준 객체
    // 빈으로 만들고 이 빈을 누군가 가지고 있게 만들어 주는 것을 의존성 주입이라고 함
    // 데이터 소스 객체를 SQL Session Factory 객체로 연결 >> 계속 객체끼리 연결해주는 것
    // 생성자를 통한 주입, setter를 통한 주입 >> setter를 통한 주입이 가장 많이 사용됨
    // Property는 setter를 호출 constructor-args는 생성자를 호출

    public Outputter getOutputter() {
        return outputter;
    }

    public void setOutputter(Outputter outputter) {
        this.outputter = outputter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    @Override
    public void sayHello() {
        String message = greeting  + ", " + name;
        System.out.println(message);

        try{
            outputter.output(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
