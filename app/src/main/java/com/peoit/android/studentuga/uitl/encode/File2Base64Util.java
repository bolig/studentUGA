package com.peoit.android.studentuga.uitl.encode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * author:libo
 * time:2015/9/25
 * E-mail:boli_android@163.com
 * last: ...
 */
public class File2Base64Util {
    /**
     * 将文件转成base64 字符串
     *
     * @param
     * @return *
     * @throws Exception
     */

    public static String encodeBase64File(String path) {
        File file = new File(path);
        if (file == null || !file.exists()) {
            return "";
        }
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encode(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String encodeBase64File(File file) {
        if (file == null || !file.exists()) {
            return "";
        }
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encode(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
