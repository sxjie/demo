package com.hexin.entity;

/**
 * @Description TODO
 * @ClassName Student
 * @Author shenxiaojie
 * @Date 2019-09-26 22:56
 * @Version 1.0
 */
public class Student {

    private Integer age;

    private String username;

    public Student(Integer age, String username) {
        this.age = age;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", username='" + username + '\'' +
                '}';
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //
//    public Student(Integer age, Integer state) {
//        this.age = age;
//        this.state = state;
//    }
//
//    public Student() {
//    }
//
//    @Override
//    public String toString() {
//        return "Student{" +
//                "age=" + age +
//                ", state=" + state +
//                '}';
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public Integer getState() {
//        return state;
//    }
//
//    public void setState(Integer state) {
//        this.state = state;
//    }


}
