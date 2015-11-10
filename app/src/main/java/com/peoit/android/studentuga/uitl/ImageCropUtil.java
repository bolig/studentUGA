package com.peoit.android.studentuga.uitl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;

/**
 * 调用系统剪切图片
 * author:libo
 * time:2015/9/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ImageCropUtil {

    public static final int CROP_REQUESSTCODE = 0x00000102;

    /**
     * 剪切图片
     *
     * @param mAc
     * @param uri         // 图片路径
     * @param requestCode // 请求code
     * @param aspectX     // x轴比例值(用于与aspectY做比较)
     * @param aspectY     // y轴比例值(用于与aspectX做比较)
     * @param outputX     // 输出图片x轴的宽度
     * @param outputY     // 输出图片x轴的高度
     */
    public static void toPhotoZoom(Activity mAc, Uri uri, int requestCode, int aspectX, int aspectY, int outputX, int outputY, boolean isSave) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);

        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", aspectX == aspectY);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, createCropImageOutputUri(isSave));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        mAc.startActivityForResult(intent, requestCode);
    }

    public static void toPhotoZoom(Activity mAc, Uri uri, int requestCode, int aspectX, int aspectY, int outputX, int outputY) {
        toPhotoZoom(mAc, uri, requestCode, aspectX, aspectY, outputX, outputY, false);
    }

    public static void toPhotoZoom(Activity mAc, Uri uri, int requestCode, int aspectX, int aspectY) {
        toPhotoZoom(mAc, uri, requestCode, aspectX, aspectY, 250, 250);
    }

    public static void toPhotoZoom(Activity mAc, Uri uri, int requestCode) {
        toPhotoZoom(mAc, uri, requestCode, 1, 1);
    }

    public static void toPhotoZoom(Activity mAc, Uri uri) {
        toPhotoZoom(mAc, uri, CROP_REQUESSTCODE);
    }

    public static void toPhotoZoom(Activity mAc, String imagePath) {
        toPhotoZoom(mAc, Uri.fromFile(new File(imagePath)), CROP_REQUESSTCODE);
    }

    public static void toPhotoZoom(Activity mAc, String imagePath, int requestCode) {
        toPhotoZoom(mAc, Uri.fromFile(new File(imagePath)), requestCode);
    }

    /**
     * 剪切图片
     *
     * @param mFrag
     * @param uri         // 图片路径
     * @param requestCode // 请求code
     * @param aspectX     // x轴比例值(用于与aspectY做比较)
     * @param aspectY     // y轴比例值(用于与aspectX做比较)
     * @param outputX     // 输出图片x轴的宽度
     * @param outputY     // 输出图片x轴的高度
     */
    public static void toPhotoZoom(Fragment mFrag, Uri uri, int requestCode, int aspectX, int aspectY, int outputX, int outputY, boolean isSave) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);

        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", aspectX == aspectY);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, createCropImageOutputUri(isSave));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        mFrag.startActivityForResult(intent, requestCode);
    }

    public static void toPhotoZoom(Fragment mFrag, Uri uri, int requestCode, int aspectX, int aspectY, int outputX, int outputY) {
        toPhotoZoom(mFrag, uri, requestCode, aspectX, aspectY, outputX, outputY, false);
    }

    public static void toPhotoZoom(Fragment mFrag, Uri uri, int requestCode, int aspectX, int aspectY) {
        toPhotoZoom(mFrag, uri, requestCode, aspectX, aspectY, 250, 250);
    }

    public static void toPhotoZoom(Fragment mFrag, Uri uri, int requestCode) {
        toPhotoZoom(mFrag, uri, requestCode, 1, 1);
    }

    public static void toPhotoZoom(Fragment mFrag, Uri uri) {
        toPhotoZoom(mFrag, uri, CROP_REQUESSTCODE);
    }

    public static void toPhotoZoom(Fragment mFrag, String imagePath) {
        toPhotoZoom(mFrag, Uri.fromFile(new File(imagePath)), CROP_REQUESSTCODE);
    }

    public static void toPhotoZoom(Fragment mFrag, String imagePath, int requestCode) {
        toPhotoZoom(mFrag, Uri.fromFile(new File(imagePath)), requestCode);
    }

    private static Uri createCropImageOutputUri(boolean isSave) {
        Uri outUri = null;
        if (isSave) {
            outUri = Uri.fromFile(createSaveFile());
        } else {
            outUri = Uri.fromFile(createTempFile());
        }
        return outUri;
    }

    private static String SDPATH = Environment.getExternalStorageDirectory()
            + "/img/crop/";

    private static String TMEP_NAME = "/crop.JPEG";

    private static String currentPath;

    public static String getCurrentPath() {
        return currentPath;
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
        String save_path = "/crop" + System.currentTimeMillis() + ".JPEG";
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
