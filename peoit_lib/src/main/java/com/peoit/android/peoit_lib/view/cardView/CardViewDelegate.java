package com.peoit.android.peoit_lib.view.cardView;

import android.graphics.drawable.Drawable;

/**
 * author:libo
 * time:2015/8/3
 * E-mail:boli_android@163.com
 * last: ...
 */
public interface CardViewDelegate {
    void setBackgroundDrawable(Drawable paramDrawable);

    Drawable getBackground();

    boolean getUseCompatPadding();

    boolean getPreventCornerOverlap();

    float getRadius();

    void setShadowPadding(int left, int top, int right, int bottom);
}
