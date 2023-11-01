package com.codingrecipe.springEx1.sample3;

import lombok.Getter;

public class EmployImpl implements Employ {
    private Integer id;
    private String name;
    private Department department;

    public EmployImpl(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public EmployImpl(Integer id, String name, Department department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public void info() {
        System.out.printf("사원 번호 : %d, 이름 : %s, 부서명 : %s, 부서 위치 : %s%n",
                id, name, department.getName(), department.getLocation());
    }

}
