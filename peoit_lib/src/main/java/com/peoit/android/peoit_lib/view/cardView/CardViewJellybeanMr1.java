package com.peoit.android.peoit_lib.view.cardView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * author:libo
 * time:2015/8/3
 * E-mail:boli_android@163.com
 * last: ...
 */
public class CardViewJellybeanMr1 extends CardViewEclairMr1 {
    @Override
    public void initStatic() {
        ShadowDrawable.sRoundRectHelper
                = new ShadowDrawable.RoundRectHelper() {
            @Override
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius,
                                      Paint paint) {
                canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
            }
        };
    }
}
