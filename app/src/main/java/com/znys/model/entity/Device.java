package com.znys.model.entity;

/**
 * Created by mushroom on 2015/6/7.
 */
public class Device {
    private int id;
    private String mac;
    private String name;
    private boolean latest;

    public Device(int id, String mac, String name, int latest) {
        this.id = id;
        this.mac = mac;
        this.name = name;
        this.latest = latest == 0 ? false : true;
    }

    public int getId() {
        return id;
    }

    public String getMacAddress() {
        return mac;
    }

    public String getName() {
        return name;
    }

    public boolean isLatest() {
        return latest;
    }
}
