package com.peoit.android.studentuga.uitl;

import android.os.Environment;

import java.io.File;

/**
 * author:libo
 * time:2015/10/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class FileUtil {

    public static final String CUSTOM_DIR = Environment.getExternalStorageDirectory() + "/custom/";

    public static final String CUSTOM_PATH = Environment.getExternalStorageDirectory() + "/custom/temp.jpeg";

    public static String getCustomPath(String fileName) {
        File file = new File(CUSTOM_DIR);
        if (file != null && !file.exists())
            file.mkdirs();
        file = new File(CUSTOM_DIR + fileName);
        if (file != null && file.exists())
            file.delete();
        return CUSTOM_DIR + fileName;
    }

    public static String getCustomPath() {
        File file = new File(CUSTOM_PATH);
        if (file != null && file.exists())
            file.delete();
        return CUSTOM_PATH;
    }

}
