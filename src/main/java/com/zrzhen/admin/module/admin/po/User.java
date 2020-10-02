package com.zrzhen.admin.module.admin.po;

public class User {

    private Long id;
    private String name;
    private String pwd;
    private int role;

    public User(Long id, String name, String pwd, int role) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
