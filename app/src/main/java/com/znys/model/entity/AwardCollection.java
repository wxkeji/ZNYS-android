package com.znys.model.entity;

import android.database.Cursor;

import com.znys.model.service.DBAccessHelper;

import java.util.ArrayList;
import java.util.List;

import com.znys.model.Constant;

/**
 * Created by mushroom on 2015/6/22.
 */
public class AwardCollection {
    private DBAccessHelper dbAccessHelper;
    private List<Award> awardList;

    public AwardCollection(DBAccessHelper dbAccessHelper) {
        this.dbAccessHelper = dbAccessHelper;
        String sql = "select * from " + Constant.TABLE_NAME + ";";
        Cursor cursor = dbAccessHelper.query(sql, null);
        awardList = new ArrayList<>();
        while (!cursor.isAfterLast()) {

            cursor.moveToNext();
        }
    }

    public void addAward(int resourceId, int count, String awardType) {
        String sql = "update " + Constant.TABLE_NAME + " set latest = 0;insert into " + Constant.TABLE_NAME + "(mac,name,latest) values('" + resourceId + "','" + count + "',1);";
        dbAccessHelper.execute(sql);
    }
}
