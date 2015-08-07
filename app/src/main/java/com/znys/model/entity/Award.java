package com.znys.model.entity;

/**
 * Created by mushroom on 2015/6/22.
 */
public class Award {
    private int id;
    private int imageResourceId;
    private int awardType;

    public Award(int id, int resourceId, int awardType) {
        this.id = id;
        this.imageResourceId = resourceId;
        this.awardType = awardType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setImageResourceId(int id) {
        this.imageResourceId = id;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setAwardState(String awardState) {
    }

    public String getAwardState() {
        return null;
    }
}




