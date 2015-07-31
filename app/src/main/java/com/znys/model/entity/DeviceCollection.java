package com.znys.model.entity;

import android.database.Cursor;

import com.znys.model.Constant;
import com.znys.model.service.DBAccessHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mushroom on 2015/6/7.
 */
public class DeviceCollection {
    private DBAccessHelper dbAccessHelper;
    private List<Device> deviceList;

    public DeviceCollection(DBAccessHelper dbAccessHelper) {
        this.dbAccessHelper = dbAccessHelper;
        String sql = "select * from " + Constant.TABLE_NAME + ";";
        Cursor cursor = dbAccessHelper.query(sql, null);
        deviceList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            deviceList.add(new Device(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
        }
    }

    public String getMacById(int id) {
        for (int i = 0; i < deviceList.size(); i++) {
            Device device = deviceList.get(i);
            if (device.getId() == id) {
                return device.getMacAddress();
            }
        }
        return null;
    }

    public String getMacByUsage() {
        for (int i = 0; i < deviceList.size(); i++) {
            Device device = deviceList.get(i);
            if (device.isLatest() == true) {
                return device.getMacAddress();
            }
        }
        return null;
    }

    public int getRecordCount() {
        return deviceList.size();
    }

    public void addDevice(String mac, String name) {
        Device device = new Device(deviceList.size(), mac, name, 1);
        String sql = "update " + Constant.TABLE_NAME + " set latest = 0;insert into " + Constant.TABLE_NAME + "(mac,name,latest) values('" + mac + "','" + name + "',1);";
        dbAccessHelper.execute(sql);
    }
}
