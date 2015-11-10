package com.peoit.android.peoit_lib.view.cardView;

import android.content.Context;

/**
 * author:libo
 * time:2015/8/3
 * E-mail:boli_android@163.com
 * last: ...
 */
public interface CardViewImpl {
    void initialize(CardViewDelegate cardView, Context context, int backgroundColor, float radius,
                    float elevation, float maxElevation);

    void setRadius(CardViewDelegate cardView, float radius);

    float getRadius(CardViewDelegate cardView);

    void setElevation(CardViewDelegate cardView, float elevation);

    float getElevation(CardViewDelegate cardView);

    void initStatic();

    void setMaxElevation(CardViewDelegate cardView, float maxElevation);

    float getMaxElevation(CardViewDelegate cardView);

    float getMinWidth(CardViewDelegate cardView);

    float getMinHeight(CardViewDelegate cardView);

    void updatePadding(CardViewDelegate cardView);

    void onCompatPaddingChanged(CardViewDelegate cardView);

    void onPreventCornerOverlapChanged(CardViewDelegate cardView);

    void setBackgroundColor(CardViewDelegate cardView, int color);

}
