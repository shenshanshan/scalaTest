package com.cnc.cache.vo;

/**
 * @author shenss
 * @create 2021-06-15 16:06
 **/
public class User {
    private String name;
    private String gender;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }


    public User(String name, String gender, Integer age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
