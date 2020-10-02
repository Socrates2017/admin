package com.zrzhen.admin.module.admin.po;

import java.util.Objects;

public enum UserEnum {


    ARTICLE_ADMIN(1L, new User(1L, "admin", "123456", 1)),

    ;

    private long id;

    private User user;

    UserEnum(long id, User user) {
        this.id = id;
        this.user = user;
    }

    public static User getUserBy(long id) {
        for (UserEnum one : UserEnum.values()) {
            if (one.getId() == id) {
                return one.getUser();
            }
        }
        return null;
    }

    public static User getUserByNameAndPwd(String name, String pwd) {
        for (UserEnum one : UserEnum.values()) {
            if (Objects.equals(name, one.getUser().getName()) && Objects.equals(pwd, one.getUser().getPwd())) {
                return one.getUser();
            }
        }
        return null;
    }

    public static User getUserByName(String name) {
        for (UserEnum one : UserEnum.values()) {
            if (Objects.equals(name, one.getUser().getName())) {
                return one.getUser();
            }
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
