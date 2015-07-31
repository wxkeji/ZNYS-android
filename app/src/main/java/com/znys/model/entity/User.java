package com.znys.model.entity;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by mushroom on 2015/6/29.
 */
public class User {
    private UUID userId;
    private String userName;
    private String gender;
    private Bitmap userIcon;
    private int star;
    private int iterationTimes;
    private int age;
    private int userLevel;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getIterationTimes() {
        return iterationTimes;
    }

    public void setIterationTimes(int iterationTimes) {
        this.iterationTimes = iterationTimes;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Bitmap getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(Bitmap userIcon) {
        this.userIcon = userIcon;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
