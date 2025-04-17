package com.carl.mqRabbitmq.domain;

import java.io.Serializable;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */

public class User implements Serializable {
    private String id;
    private String userName;

    public User(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", userName='" + userName + '\'' + '}';
    }
}
