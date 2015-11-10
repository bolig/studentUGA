package com.peoit.android.studentuga.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * author:libo
 * time:2015/9/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class NoSrcollGridView extends GridView {
    public NoSrcollGridView(Context context) {
        super(context);
    }

    public NoSrcollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoSrcollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
