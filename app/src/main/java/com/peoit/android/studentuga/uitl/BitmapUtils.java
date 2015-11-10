package com.peoit.android.studentuga.uitl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * bitmap 辅助类
 * <p/>
 * author:libo
 * time:2015/9/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class BitmapUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/care/";

    public static String FILE_PATH = Environment.getExternalStorageDirectory()
            + "/care/temp.JPEG";

    /**
     * 保存 bitmap 到文件
     *
     * @param bm
     * @return
     */
    public static boolean saveBitmap(Bitmap bm) {
        Log.e("", "保存图片");
        try {
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            }
            File f = new File(FILE_PATH);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("", f.getAbsolutePath() + "," + f.length());
            Log.e("", "已经保存");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存 bitmap 到文件
     *
     * @param bm
     * @return
     */
    public static String saveBitmap(Bitmap bm, boolean isSave) {
        Log.e("", "保存图片");
        String path = isSave ? SDPATH + System.currentTimeMillis() + ".JPEG" : FILE_PATH;
        try {
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            }
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("", f.getAbsolutePath() + "," + f.length());
            Log.e("", "已经保存");
            return path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static InputStream getInputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    public static boolean saveDrawable(Drawable drawable) {
        return saveBitmap(drawable2Bitmap(drawable));
    }

    /**
     * 将 drawable 转换成 bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 创建文件夹
     *
     * @param dirName
     * @return
     * @throws IOException
     */
    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    /**
     * 删除文件夹
     */
    public static void deleteDir() {
        File dir = new File(SDPATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 判断绝对路径下的文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 压缩bitmap，当文件过大的时候， 通过此方法获取
     *
     * @param path
     * @return
     */
    public static Bitmap compressBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap photo = BitmapFactory.decodeFile(path, options);
        return photo;
    }

    public static Bitmap compressBitmap(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.outWidth = width;
        options.outHeight = height;
        Bitmap photo = BitmapFactory.decodeFile(path, options);
        return photo;
    }

    public static Bitmap commpress(String path, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        // 记得把assets目录下的图片拷贝到SD卡中
        // 由于设置inJustDecodeBounds为true，因此执行下面代码后bitmap为空
        Bitmap mBitmap = BitmapFactory.decodeFile(path, options);

        // 计算缩放比例，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int scale = (int) (options.outHeight / (float) height);
        MyLogger.e("options.outHeight = " + options.outHeight + "");
        // 因为结果为int型，如果相除后值为0.n，则最终结果将是0

        if (scale <= 0) {
            scale = 1;
        }
        MyLogger.e("Scale=" + scale);

        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds设回false
        mBitmap = BitmapFactory.decodeFile(path, options);
        MyLogger.e("options.outHeight = " + options.outHeight + "");
        return mBitmap;
    }
}
