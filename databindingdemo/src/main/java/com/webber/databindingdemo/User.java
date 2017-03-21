package com.webber.databindingdemo;

/**
 * Created by mxh on 2017/3/3.
 * Describe：
 */

public class User {

    private String name;
    private String gender;
    private String age;

    public User() {
    }

    public User(String name, String gender, String age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraden() {
        return gender;
    }

    public void setGraden(String graden) {
        this.gender = graden;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
