package com.peoit.android.studentuga.uitl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Html;
import android.widget.TextView;

/**
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HtmlImageGetter implements Html.ImageGetter {

    private final Context mContext;
    private final TextView tvContext;

    public HtmlImageGetter(Context context, TextView tvContext) {
        mContext = context;
        this.tvContext = tvContext;
    }

    @Override
    public Drawable getDrawable(String source) {
        // 在此必须异步加载图片
        final LevelListDrawable mDrawable = new LevelListDrawable();
//        Drawable empty = mContext.getResources().getDrawable(R.drawable.ic_launcher);
//        mDrawable.addLevel(0, 0, empty);
//        mDrawable.setBounds(0, 0, 0, 0);
//        mDrawable.setLevel(0);
//        LoadImageTask loadImageTask = new LoadImageTask(mContext, tvContext, mDrawable);
//        loadImageTask.execute("http://123.57.221.31:8081/" + source);
        return mDrawable;
    }
}
