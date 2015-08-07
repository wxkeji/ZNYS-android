package com.znys.model.entity;

import java.util.UUID;

/**
 * Created by mushroom on 2015/6/29.
 */
public class ToothBrush {
    private int id;
    private String name;
    private String macAddress;
    private UUID userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
