package com.peoit.android.studentuga.uitl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;

/**
 * 调用系统拍照
 * <p/>
 * author:libo
 * time:2015/9/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ImagePhotoUtil {

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/img/";

    public static String TMEP_NAME = "/temp.JPEG";

    public static final int REQUEST_CODE = 0x00000101;

    private static String currentPath;

    public static String getCurrentPath() {
        return currentPath;
    }

    public static void toTakePhoto(Activity mAc) {
        toTakePhoto(mAc, false);
    }

    public static void toTakePhoto(Activity mAc, boolean isSave) {
        toTakePhoto(mAc, isSave, REQUEST_CODE);
    }

    public static void toTakePhoto(Activity mAc, boolean isSave, int toTakePhotorequestCode) {
        Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (isSave) {
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(createSaveFile()));
        } else {
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(createTempFile()));
        }
        mAc.startActivityForResult(getImageByCamera, toTakePhotorequestCode);
    }

    public static void toTakePhoto(Fragment mFrag) {
        toTakePhoto(mFrag, false);
    }

    public static void toTakePhoto(Fragment mFrag, boolean isSave) {
        toTakePhoto(mFrag, isSave, REQUEST_CODE);
    }

    public static void toTakePhoto(Fragment mFrag, boolean isSave, int toTakePhotorequestCode) {
        Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (isSave) {
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(createSaveFile()));
        } else {
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(createTempFile()));
        }
        mFrag.startActivityForResult(getImageByCamera, toTakePhotorequestCode);
    }

    private static File createTempFile() {
        File file = new File(createDir(), TMEP_NAME);
        if (file.exists() && file.length() > 100) {
            file.delete();
            file = new File(createDir(), TMEP_NAME);
        }
        currentPath = file.getAbsolutePath();
        return file;
    }

    private static File createSaveFile() {
        String save_path = "/temp" + System.currentTimeMillis() + ".JPEG";
        File file = new File(createDir(), save_path);
        if (file.exists() && file.length() > 100) {
            file.delete();
            file = new File(createDir(), save_path);
        }
        currentPath = file.getAbsolutePath();
        return file;
    }

    private static File createDir() {
        File file = new File(SDPATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
