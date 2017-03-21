package com.webber.mcorelibspace.dataBinding;

/**
 * Created by mxh on 2017/3/3.
 * Describe：
 */

public class User {
    private String name;
    private String gander;
    private String age;

    public User() {
    }

    public User(String name, String gander, String age) {
        this.name = name;
        this.gander = gander;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGander() {
        return gander;
    }

    public void setGander(String gander) {
        this.gander = gander;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
