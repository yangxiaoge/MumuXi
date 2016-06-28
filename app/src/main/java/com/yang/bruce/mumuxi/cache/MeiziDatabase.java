package com.yang.bruce.mumuxi.cache;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yang.bruce.mumuxi.MumuXiApp;
import com.yang.bruce.mumuxi.bean.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * MeiziDatabase类其实不是数据库存储数据---> 只是普通文件存储数据(取名为数据库文件而已)
 */
public class MeiziDatabase {
    private static String DATA_FILE_NAME = "data.db";

    private static MeiziDatabase INSTANCE;
    File dataFile = new File(MumuXiApp.getInstance().getFilesDir(), DATA_FILE_NAME);
    Gson gson = new Gson();

    private MeiziDatabase() {

    }

    public static MeiziDatabase getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MeiziDatabase();

        }

        return INSTANCE;
    }

    // 读取缓存数据
    public List<Item> readItems() {
        // Hard code adding some delay, to distinguish reading from memory and reading disk clearly
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Reader reader = new FileReader(dataFile);
            return gson.fromJson(reader, new TypeToken<List<Item>>() {
            }.getType());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将妹子数据写入缓存
    public void writeItems(List<Item> items) {
        // 将集合转成 json 字符串
        String json = gson.toJson(items);
        try {
            if (!dataFile.exists()) {
                try {
                    dataFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Writer writer = new FileWriter(dataFile);
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        dataFile.delete();
    }
}
