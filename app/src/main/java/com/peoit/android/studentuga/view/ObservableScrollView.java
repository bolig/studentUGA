package com.peoit.android.studentuga.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * author:libo
 * time:2015/9/14
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ObservableScrollView extends ScrollView {

    private List<OnScrollCallBack> mScrollCallBacks = new ArrayList<>();
    private boolean isHeader;
    private OnTouchCallBack mOnTouchCallBack;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                if (isHeader) {
                    if (mOnTouchCallBack != null){
                       return mOnTouchCallBack.onCallBack(ev);
                    }
                } else {
                    return super.dispatchTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setScrollHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        for (OnScrollCallBack callBack :
                mScrollCallBacks) {
            callBack.onScrollCallBack(l - oldl, t - oldt, t);
        }
    }

    public void addScrollCallBack(OnScrollCallBack callBack) {
        if (callBack != null && !mScrollCallBacks.contains(callBack)) {
            mScrollCallBacks.add(callBack);
        }
    }

    public void setOnTouchCallBack(OnTouchCallBack callBack) {
        this.mOnTouchCallBack = callBack;
    }

    public interface OnScrollCallBack {
        void onScrollCallBack(int deltaX, int deltaY, int y);
    }

    public interface OnTouchCallBack {
        boolean onCallBack(MotionEvent event);
    }
}
