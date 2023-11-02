package com.codingrecipe.springex2.dto;

// dto의 경우
// 프론트로부터 데이터를 받는 용도
// 디비에 데이터를 주고 받는 용도 2가지로 사용우 가능
// 그래서 2개의 dto를 만드는 경우도 존재함
public class Account {
	private String id;
	private String name;
	private Integer balance;
	private String type;
	private String grade;
	
	public Account() {}
	public Account(String id, String name, Integer balance, String type, String grade) {
		this.id=id;
		this.name=name;
		this.balance=balance;
		this.type=type;
		this.grade=grade;
	}
	public void deposit(Integer balance) {
		if(balance>0) {
			balance += balance;
		}
	}
	public void withdraw(Integer balance) {
		if(balance>=balance) {
			balance -= balance;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
