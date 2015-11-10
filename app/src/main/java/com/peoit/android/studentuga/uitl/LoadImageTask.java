package com.peoit.android.studentuga.uitl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peoit.android.studentuga.config.CommonUtil;

import java.util.concurrent.ExecutionException;

/**
 * author:libo
 * time:2015/8/4
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LoadImageTask extends AsyncTask<String, Integer, Drawable> {

    private final Context mContext;
    private final TextView mTextView;
    private final LevelListDrawable mDrawable;

    public LoadImageTask(Context context, TextView textView, LevelListDrawable mDrawable) {
        this.mContext = context;
        this.mTextView = textView;
        this.mDrawable = mDrawable;
    }

    @Override
    protected Drawable doInBackground(String... params) {
        String source = params[0];
        try {
            return Glide.with(mContext).load(source).into(CommonUtil.w_screeen - CommonUtil.dip2px(32), 400).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        if (drawable != null && mTextView != null && mDrawable != null) {

            DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

            DisplayMetrics dm = mTextView.getContext().getResources().getDisplayMetrics();

            int width, height;
            int originalWidthScaled = (int) (drawable.getIntrinsicWidth() * metrics.density);
            int originalHeightScaled = (int) (drawable.getIntrinsicHeight() * metrics.density);

            width = CommonUtil.w_screeen - CommonUtil.dip2px(32);
            height = originalHeightScaled * width / originalWidthScaled;

            mDrawable.addLevel(1, 1, drawable);
            mDrawable.setBounds(0, 0, width, height);
            mDrawable.setLevel(1);
            CharSequence text = mTextView.getText();
            mTextView.setText(text);
        }
    }
}
