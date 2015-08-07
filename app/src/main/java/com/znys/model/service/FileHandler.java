package com.znys.model.service;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.znys.model.Constant;

/**
 * Created by ms on 2015/5/17.
 */
public class FileHandler {
    public static void writeToFile(String fileName, byte[] data) {
        File file = null;
        String SDPATH = Environment.getExternalStorageDirectory().toString();
        FileOutputStream output = null;
        File dir = new File(SDPATH + Constant.SDCARD_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            file = new File(dir + File.separator + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            output = new FileOutputStream(file);
            output.write(data);
            output.flush();
            System.out.println("writed");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
