package com.znys.model.service;

/**
 * Created by mushroom on 2015/6/6.
 */

import com.znys.model.Constant;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DBAccessHelper {
    private SQLiteDatabase db;

    public DBAccessHelper(Context context) {
        db = context.openOrCreateDatabase(Constant.SQLITE_DB_NAME, Context.MODE_PRIVATE, null);
        createTables();
    }

    public void close() {
        db.close();
    }

    public void createTables() {
        String sql = "create table if not exists " + Constant.TABLE_NAME + " (id integer primary key autoincrement,mac char(17),name varchar(20),latest integer);";
        db.execSQL(sql);
    }

    public void storeDevice(String macAddress, String name) {

    }

    public Cursor query(String sql, String args[]) {
        return db.rawQuery(sql, args);
    }

    public void execute(String sql) {
        db.execSQL(sql);
    }

    public void saveUsageRecord(int time) {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String sqlString;
        sqlString = "select * from " + Constant.TABLE_NAME + " where date = '" + formatter.format(date) + "'";
        Cursor data = query(sqlString, null);
        if (data.getCount() == 1) {
            data.moveToFirst();
            time += data.getInt(data.getColumnIndex("time"));
            sqlString = "update " + Constant.TABLE_NAME + " set time = " + time + " where date = '" + formatter.format(date) + "'";
        } else
            sqlString = "insert into " + Constant.TABLE_NAME + "(date,time,dirty) values('" + formatter.format(date) + "'," + time + ",1)";
        execute(sqlString);
    }

    public void updateUsageRecord(int time) {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String sqlString;
        sqlString = "select * from " + Constant.TABLE_NAME + " where date = '" + formatter.format(date) + "'";
        Cursor data = query(sqlString, null);
        if (data.getCount() == 1) {
            data.moveToFirst();
            time = data.getInt(data.getColumnIndex("time")) + time;
            sqlString = "update " + Constant.TABLE_NAME + " set time = " + time + " where date = '" + formatter.format(date) + "'";
        } else {
            sqlString = "insert into " + Constant.TABLE_NAME + "(date,time,dirty) values('" + formatter.format(date) + "'," + time + ",1)";
        }
        execute(sqlString);
    }

    public Cursor getUsageRecord() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String sqlString = "select * from " + Constant.TABLE_NAME + " where dirty = 1 and date < '" + formatter.format(date) + "'";
        return query(sqlString, null);
    }

    public void clearDirtyRecord() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String sqlString = "update " + Constant.TABLE_NAME + " set dirty = 0 where dirty = 1 and date < '" + formatter.format(date) + "'";
        execute(sqlString);
    }

    public void writeIDToSDCard(String id) {
        if (Environment.getExternalStorageState().equals((Environment.MEDIA_MOUNTED))) {
            String sdCardDir;
            File saveFileDir;
            File saveFile;
            FileWriter fileWriter;
            try {
                sdCardDir = Environment.getExternalStorageDirectory() + Constant.SDCARD_DIR;
                saveFileDir = new File(sdCardDir);

                if (!saveFileDir.exists())
                    saveFileDir.mkdir();

                saveFile = new File(sdCardDir + "/" + "");
                if (!saveFile.exists())
                    saveFile.createNewFile();

                fileWriter = new FileWriter(saveFile);
                fileWriter.write(id);
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    public String readIDFromSDCard() {
        String id = null;
        if (Environment.getExternalStorageState().equals((Environment.MEDIA_MOUNTED))) {
            String filePath;
            File readFile;
            FileReader fileReader;
            try {
                filePath = Environment.getExternalStorageDirectory() + Constant.SDCARD_DIR + "/" + "";
                readFile = new File(filePath);

                if (!readFile.exists())
                    return null;

                char[] charBuffer = new char[(int) readFile.length()];
                fileReader = new FileReader(readFile);
                fileReader.read(charBuffer);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(charBuffer);
                id = stringBuffer.toString();
                if (id.length() < 2)
                    id = null;
            } catch (IOException ioException) {
                id = null;
                ioException.printStackTrace();
            }

        }
        return id;
    }

    public void writeNameToSDCard(String name) {
        if (Environment.getExternalStorageState().equals((Environment.MEDIA_MOUNTED))) {
            String sdCardDir;
            File saveFileDir;
            File saveFile;
            FileWriter fileWriter;
            try {
                sdCardDir = Environment.getExternalStorageDirectory() + Constant.SDCARD_DIR;
                saveFileDir = new File(sdCardDir);

                if (!saveFileDir.exists())
                    saveFileDir.mkdir();

                saveFile = new File(sdCardDir + "/" + Constant.SDCARD_DIR);
                if (!saveFile.exists())
                    saveFile.createNewFile();

                fileWriter = new FileWriter(saveFile);
                fileWriter.write(name);
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    public String readNameFromSDCard() {
        String name = null;
        if (Environment.getExternalStorageState().equals((Environment.MEDIA_MOUNTED))) {
            String filePath;
            File readFile;
            FileReader fileReader;
            try {
                filePath = Environment.getExternalStorageDirectory() + Constant.SDCARD_DIR + "/" + Constant.SDCARD_DIR;
                readFile = new File(filePath);

                if (!readFile.exists())
                    return null;

                char[] charBuffer = new char[(int) readFile.length()];
                fileReader = new FileReader(readFile);
                fileReader.read(charBuffer);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(charBuffer);
                name = stringBuffer.toString();
                if (name.equals("") || name == null)
                    name = null;
            } catch (IOException ioException) {
                name = null;
                ioException.printStackTrace();
            }

        }
        return name;
    }
}
